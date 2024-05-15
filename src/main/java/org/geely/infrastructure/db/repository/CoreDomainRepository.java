package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.*;
import org.geely.infrastructure.db.*;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ricardo zhou
 */
@Repository
public class CoreDomainRepository {

    @Resource
    private CustomerDbService customerDbService;
    @Resource
    private SaleOrderDbService saleOrderDbService;
    @Resource
    private SaleOrderItemDbService saleOrderItemDbService;
    @Resource
    private DeliveryNoteDbService deliveryNoteDbService;
    @Resource
    private DeliveryPackageDbService deliveryPackageDbService;

    public CustomerData saveCustomer(CustomerData customerData) {
        CustomerDO customerDO = CoreDomainDataConvert.INSTANCE.convert(customerData);
        boolean result = customerDbService.saveOrUpdate(customerDO);
        Assert.isTrue(result, "客户数据保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(customerDO);
    }

    public CustomerData getCustomerById(Integer id) {
        CustomerDO customerDO = customerDbService.getById(id);
        Assert.notNull(customerDO, "客户数据不存在");
        return CoreDomainDataConvert.INSTANCE.convert(customerDO);
    }

    public SaleOrderData saveSaleOrder(SaleOrderData saleOrderData) {
        SaleOrderDO saleOrderDO = CoreDomainDataConvert.INSTANCE.convert(saleOrderData);
        boolean result = saleOrderDbService.saveOrUpdate(saleOrderDO);
        Assert.isTrue(result, "销售订单数据保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(saleOrderDO);
    }

    public SaleOrderData getSaleOrder(Integer id) {
        Assert.isTrue(id != null && id > 0, "订单id不合法");
        SaleOrderDO saleOrderDO = saleOrderDbService.getById(id);
        Assert.notNull(saleOrderDO, "订单数据不存在");
        return CoreDomainDataConvert.INSTANCE.convert(saleOrderDO);
    }

    public List<SaleOrderData> listSaleOrdersByIds(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<SaleOrderDO> saleOrderDOs = saleOrderDbService.listByIds(ids);
        Assert.isTrue(ids.size() == saleOrderDOs.size(), "包含不存在的订单id");
        return saleOrderDOs.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public SaleOrderItemData saveSaleOrderItem(SaleOrderItemData saleOrderItemData) {
        SaleOrderItemDO saleOrderItemDO = CoreDomainDataConvert.INSTANCE.convert(saleOrderItemData);
        boolean result = saleOrderItemDbService.saveOrUpdate(saleOrderItemDO);
        Assert.isTrue(result, "销售订单子项数据保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(saleOrderItemDO);
    }

    public List<SaleOrderItemData> getSaleOrderItems(Integer saleOrderId) {
        Assert.isTrue(saleOrderId != null && saleOrderId > 0, "订单id不合法");
        LambdaQueryWrapper<SaleOrderItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SaleOrderItemDO::getSaleOrderId, saleOrderId);
        List<SaleOrderItemDO> saleOrderItemDOList = saleOrderItemDbService.list(queryWrapper);
        return saleOrderItemDOList.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public DeliveryNoteData saveDeliveryNote(DeliveryNoteData deliveryNoteData) {
        DeliveryNoteDO deliveryNoteDO = CoreDomainDataConvert.INSTANCE.convert(deliveryNoteData);
        boolean result = deliveryNoteDbService.saveOrUpdate(deliveryNoteDO);
        Assert.isTrue(result, "数据保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(deliveryNoteDO);
    }

    public DeliveryPackageData saveDeliveryPackage(DeliveryPackageData deliveryPackageData) {
        DeliveryPackageDO deliveryPackageDO = CoreDomainDataConvert.INSTANCE.convert(deliveryPackageData);
        boolean result = deliveryPackageDbService.saveOrUpdate(deliveryPackageDO);
        Assert.isTrue(result, "数据保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(deliveryPackageDO);
    }

    public List<DeliveryNoteData> getDeliveryNote(Integer saleOrderId) {
        Assert.isTrue(saleOrderId != null && saleOrderId > 0, "销售单id不合法");
        LambdaQueryWrapper<DeliveryNoteDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeliveryNoteDO::getSaleOrderId, saleOrderId);
        List<DeliveryNoteDO> deliveryNoteDOList = deliveryNoteDbService.list(queryWrapper);
        Assert.notEmpty(deliveryNoteDOList, "发货单数据不存在");
        return deliveryNoteDOList.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public List<DeliveryPackageData> getDeliveryPackages(Integer saleOrderId) {
        Assert.isTrue(saleOrderId != null && saleOrderId > 0, "销售单id不合法");
        LambdaQueryWrapper<DeliveryPackageDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeliveryPackageDO::getSaleOrderId, saleOrderId);
        List<DeliveryPackageDO> list = deliveryPackageDbService.list(queryWrapper);
        Assert.notEmpty(list, "发货包裹数据不存在");
        return list.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public DeliveryNoteData getDeliveryNoteByDeliveryId(Integer deliveryId) {
        Assert.isTrue(deliveryId != null && deliveryId > 0, "发货单id不合法");
        DeliveryNoteDO deliveryNoteDO = deliveryNoteDbService.getById(deliveryId);
        Assert.notNull(deliveryNoteDO, "发货单数据不存在");
        return CoreDomainDataConvert.INSTANCE.convert(deliveryNoteDO);
    }

    public List<DeliveryPackageData> getDeliveryPackagesByDeliveryId(Integer deliveryId) {
        Assert.isTrue(deliveryId != null && deliveryId > 0, "发货单id不合法");
        LambdaQueryWrapper<DeliveryPackageDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeliveryPackageDO::getDeliveryNoteId, deliveryId);
        List<DeliveryPackageDO> list = deliveryPackageDbService.list(queryWrapper);
        Assert.notEmpty(list, "发货包裹数据不存在");
        return list.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public List<SaleOrderItemData> listOrderItemsByOrderIds(Set<Integer> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SaleOrderItemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SaleOrderItemDO::getSaleOrderId, orderIds);
        return saleOrderItemDbService.list(queryWrapper).stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
