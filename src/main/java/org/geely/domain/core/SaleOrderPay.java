package org.geely.domain.core;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.common.enums.OrderPayStateEnum;
import org.geely.common.enums.PayTypeEnum;
import org.geely.common.enums.SnPrefixEnum;
import org.geely.domain.common.utils.SnGenerator;
import org.geely.domain.core.data.SaleOrderPayData;
import org.geely.infrastructure.db.repository.SaleOrderPayRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 租户
 *
 * @author cong huang
 */
public class SaleOrderPay {
    private SaleOrderPayData data;
    private List<SaleOrderPayData> children;

    private SaleOrderPay(SaleOrderPayData productTagData) {
        this.data = ObjectUtil.clone(productTagData);
    }

    public static SaleOrderPay newParentInstance(BigDecimal amount) {
        SaleOrderPayData data = new SaleOrderPayData();
        SaleOrderPay instance = new SaleOrderPay(data);
        instance.data.setId(null);
        instance.data.setVersion(0);
        instance.data.setParentId(0);
        instance.data.setSaleOrderId(0);
        instance.data.setPayType(PayTypeEnum.EMPTY);
        instance.data.setAmount(amount);
        instance.data.setState(OrderPayStateEnum.TO_BE_PAID);
        instance.data.setSn(SnGenerator.get(SnPrefixEnum.OP.toString()));
        instance.save();
        return instance;
    }

    public static SaleOrderPay newChildInstance(Integer parentId, Integer saleOrderId, BigDecimal amount) {
        SaleOrderPayData data = new SaleOrderPayData();
        SaleOrderPay instance = new SaleOrderPay(data);
        instance.data.setId(null);
        instance.data.setVersion(0);
        instance.data.setParentId(parentId);
        instance.data.setSaleOrderId(saleOrderId);
        instance.data.setPayType(PayTypeEnum.EMPTY);
        instance.data.setAmount(amount);
        instance.data.setState(OrderPayStateEnum.TO_BE_PAID);
        instance.data.setSn(SnGenerator.get(SnPrefixEnum.OP.toString()));
        instance.save();
        return instance;
    }

    public static SaleOrderPay instanceOf(String sn) {
        SaleOrderPayData data = SpringUtil.getBean(SaleOrderPayRepository.class).getBySn(sn);
        Assert.isTrue(data.getParentId().equals(0), "不正确的支付单号");
        return new SaleOrderPay(data);
    }

    public static List<SaleOrderPay> instancesOfOrders(Set<Integer> orderIds) {
        return SpringUtil.getBean(SaleOrderPayRepository.class).listByOrderIds(orderIds).stream().map(SaleOrderPay::new).collect(Collectors.toList());
    }

    public static List<SaleOrderPay> instancesOfParents(Set<Integer> parentIds) {
        return SpringUtil.getBean(SaleOrderPayRepository.class).listByParentIds(parentIds).stream().map(SaleOrderPay::new).collect(Collectors.toList());
    }

    public static List<SaleOrderPay> instancesOf(Set<Integer> ids) {
        return SpringUtil.getBean(SaleOrderPayRepository.class).listByIds(ids).stream().map(SaleOrderPay::new).collect(Collectors.toList());
    }

    public void save() {
        SaleOrderPayRepository repository = SpringUtil.getBean(SaleOrderPayRepository.class);
        this.data = repository.save(this.data);
        if(children != null && !children.isEmpty()) {
            List<SaleOrderPayData> newItems = new ArrayList<>();
            for (SaleOrderPayData item : children) {
                newItems.add(repository.save(item));
            }
            this.children = newItems;
        }
    }

    public SaleOrderPayData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Integer getId() {
        return data.getId();
    }


    public Set<Integer> getSaleOrderIds() {
        Set<Integer> result = new HashSet<>();
        if(Boolean.FALSE.equals(isBatchPay())) {
            result.add(data.getSaleOrderId());
        } else {
            loadChildren();
            result = children.stream().map(SaleOrderPayData::getSaleOrderId).collect(Collectors.toSet());
            Assert.isTrue(result.size() > 1, "订单数量应大于1");
        }
        return result;
    }

    public Integer getSaleOrderId() {
        return this.data.getSaleOrderId();
    }

    public Boolean isBatchPay() {
        return data.getSaleOrderId().equals(0);
    }

    private void loadChildren() {
        if(!data.getSaleOrderId().equals(0)) {
            return;
        }
        if(children != null && !children.isEmpty()) {
            return;
        }
        children = SpringUtil.getBean(SaleOrderPayRepository.class).getByParentId(data.getId());
    }

    public String getSn() {
        return data.getSn();
    }
    public OrderPayStateEnum getState() {
        return data.getState();
    }

    public SaleOrderPay pendingApproval() {
        Assert.isTrue(this.data.getState().equals(OrderPayStateEnum.TO_BE_PAID), "支付单状态不正确");
        data.pendingApproval();
        loadChildren();
        if(children == null) {
            return this;
        }
        for (SaleOrderPayData child : children) {
            Assert.isTrue(child.getState().equals(OrderPayStateEnum.TO_BE_PAID), "支付单状态不正确");
            child.pendingApproval();
        }
        return this;
    }

    public SaleOrderPay approved(Set<Integer> orderIds) {
        Assert.isTrue(this.data.getState().equals(OrderPayStateEnum.PENDING_APPROVAL), "支付单状态不正确");
        loadChildren();
        if(children == null || children.isEmpty()) {
            this.data.success(SnGenerator.get(SnPrefixEnum.XX.toString()));
        } else {
            for (SaleOrderPayData child : children) {
                if(orderIds.contains(child.getSaleOrderId())) {
                    child.success(SnGenerator.get(SnPrefixEnum.XX.toString()));
                }
            }
            if(children.stream().allMatch(x->x.getState().equals(OrderPayStateEnum.SUCCESS))) {
                this.data.success(SnGenerator.get(SnPrefixEnum.XX.toString()));
            }
        }
        return this;
    }

    public SaleOrderPay unApproved(Set<Integer> orderIds) {
        Assert.isTrue(this.data.getState().equals(OrderPayStateEnum.PENDING_APPROVAL), "支付单状态不正确");
        loadChildren();
        if(children == null || children.isEmpty()) {
            this.data.fail();
        } else {
            for (SaleOrderPayData child : children) {
                if(orderIds.contains(child.getSaleOrderId())) {
                    child.fail();
                    child.setParentId(0);
                    data.setAmount(data.getAmount().subtract(child.getAmount()));
                }
            }
            if(children.stream().allMatch(x->x.getState().equals(OrderPayStateEnum.TO_BE_PAID))) {
                this.data.setState(OrderPayStateEnum.FAIL);
            }
        }
        return this;
    }

    public Integer getParentId() {
        return data.getParentId();
    }

    public SaleOrderPay attachParent(Integer parentId) {
        data.setParentId(parentId);
        return this;
    }

    public void delete() {
        SpringUtil.getBean(SaleOrderPayRepository.class).delete(data);
        data = null;
    }

    public LocalDateTime getPayTime() {
        return data.getPayTime();
    }
}
