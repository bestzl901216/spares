package org.geely.domain.core;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.common.enums.SnPrefixEnum;
import org.geely.domain.common.utils.SnGenerator;
import org.geely.domain.core.data.DeliveryNoteData;
import org.geely.domain.core.data.DeliveryNoteData.StateEnum;
import org.geely.domain.core.data.DeliveryPackageData;
import org.geely.infrastructure.db.repository.CoreDomainRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 发货单
 *
 * @author ricardo zhou
 */
public class DeliveryOrder {
    private DeliveryNoteData note;
    private List<DeliveryPackageData> packages;

    private DeliveryOrder(DeliveryNoteData note, List<DeliveryPackageData> packages) {
        this.note = note;
        this.packages = packages;
    }

    public static DeliveryOrder newInstance(Integer saleOrderId, String expressCompany, String expressSn, String receiverPhone, Integer day, Map<Integer, Integer> quantityMap) {
        DeliveryNoteData note = new DeliveryNoteData();
        note.setSaleOrderId(saleOrderId);
        note.setSn(SnGenerator.get(SnPrefixEnum.EX.toString()));
        note.setExpressCompany(expressCompany);
        note.setExpressSn(expressSn);
        note.setReceiverPhone(receiverPhone);
        note.setExpressState("");
        note.setData(new ArrayList<>());
        note.setSyncTime(LocalDateTime.now());
        note.setSendTime(LocalDateTime.now());
        note.setReceivedDeadline(LocalDateTime.now().plusDays(day));
        note.setState(StateEnum.UNRECEIVED);
        note.setVersion(0);

        List<DeliveryPackageData> packages = new ArrayList<>();
        quantityMap.forEach((saleOrderItemId, quantity) -> {
            DeliveryPackageData deliveryPackage = new DeliveryPackageData();
            deliveryPackage.setSaleOrderId(saleOrderId);
            deliveryPackage.setSaleOrderItemId(saleOrderItemId);
            deliveryPackage.setQuantity(quantity);
            deliveryPackage.setVersion(0);
            packages.add(deliveryPackage);
        });
        DeliveryOrder deliveryOrder = new DeliveryOrder(note, packages);
        deliveryOrder.save();
        return deliveryOrder;
    }

    public static List<DeliveryOrder> instanceOf(Integer saleOrderId) {
        CoreDomainRepository coreDomainRepository = SpringUtil.getBean(CoreDomainRepository.class);
        List<DeliveryNoteData> deliveryNote = coreDomainRepository.getDeliveryNote(saleOrderId);
        List<DeliveryPackageData> deliveryPackages = coreDomainRepository.getDeliveryPackages(saleOrderId);

        List<DeliveryOrder> deliveryOrders = new ArrayList<>();

        deliveryNote.forEach(note -> {
            List<DeliveryPackageData> packages = deliveryPackages.stream().filter(deliveryPackage -> deliveryPackage.getDeliveryNoteId().equals(note.getId())).collect(Collectors.toList());
            deliveryOrders.add(new DeliveryOrder(note, packages));
        });
        return deliveryOrders;
    }

    public static DeliveryOrder instanceOfDeliveryId(Integer deliveryId) {
        CoreDomainRepository coreDomainRepository = SpringUtil.getBean(CoreDomainRepository.class);
        DeliveryNoteData deliveryNote = coreDomainRepository.getDeliveryNoteByDeliveryId(deliveryId);
        List<DeliveryPackageData> deliveryPackages = coreDomainRepository.getDeliveryPackagesByDeliveryId(deliveryId);
        return new DeliveryOrder(deliveryNote, deliveryPackages);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save() {
        CoreDomainRepository coreDomainRepository = SpringUtil.getBean(CoreDomainRepository.class);
        this.note = coreDomainRepository.saveDeliveryNote(note);
        List<DeliveryPackageData> deliveryPackages = new ArrayList<>();
        this.packages.forEach(deliveryPackage -> {
            deliveryPackage.setDeliveryNoteId(this.note.getId());
            deliveryPackages.add(coreDomainRepository.saveDeliveryPackage(deliveryPackage));
        });
        this.packages = deliveryPackages;
    }

    public Integer getDeliveryNoteId() {
        return note.getId();
    }

    public DeliveryOrder confirm() {
        Assert.isTrue(note.getState() == StateEnum.UNRECEIVED, "发货单已签收");
        note.setState(StateEnum.RECEIVED);
        note.setReceivedTime(LocalDateTime.now());
        return this;
    }

    /**
     * @return 发货单号
     */
    public String getDeliverySn() {
        return this.note.getSn();
    }

    public List<DeliveryPackageData> getPackages() {
        return ObjectUtil.clone(this.packages);
    }

    public Integer getSaleOrderId() {
        return this.note.getSaleOrderId();
    }
}
