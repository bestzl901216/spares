package org.geely.domain.core;

import cn.hutool.core.lang.Assert;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.common.enums.*;
import org.geely.common.exception.BizException;
import org.geely.common.utils.OperatorUtil;
import org.geely.controller.dto.site.OrderSkuDTO;
import org.geely.controller.dto.site.SaleOrderCreateDTO;
import org.geely.controller.dto.site.SaleOrderItemCreateDTO;
import org.geely.controller.dto.site.SaleOrderSubmitDTO;
import org.geely.domain.common.utils.SnGenerator;
import org.geely.domain.core.data.CustomerAddressData;
import org.geely.domain.core.data.MallData;
import org.geely.domain.core.data.SaleOrderData;
import org.geely.domain.core.data.SaleOrderItemData;
import org.geely.infrastructure.db.gateway.ProductDbGateway;
import org.geely.infrastructure.db.repository.CoreDomainRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单
 *
 * @author ricardo zhou
 */
public class SaleOrder {
    private SaleOrderData data;
    private List<SaleOrderItemData> items;

    private SaleOrder(SaleOrderData data, List<SaleOrderItemData> items) {
        this.data = data;
        this.items = items;
    }

    private SaleOrder(SaleOrderData data) {
        this.data = data;
    }

    private static SaleOrder newInstance(SaleOrderData data, List<SaleOrderItemData> items) {
        SaleOrder saleOrder = new SaleOrder(data, items);
        saleOrder.data.setId(null);
        saleOrder.data.setVersion(0);
        for (SaleOrderItemData item : saleOrder.items) {
            item.setId(null);
            item.setVersion(0);
        }
        saleOrder.save();
        return saleOrder;
    }

    public static SaleOrder instanceOf(Integer id) {
        SaleOrderData data = SpringUtil.getBean(CoreDomainRepository.class).getSaleOrder(id);
        return new SaleOrder(data);
    }

    public static List<SaleOrder> submit(SaleOrderSubmitDTO dto) {
        dto.baseCheck();
        MallData mall = Mall.instanceOf(dto.getMallId()).getData();
        CustomerAddressData address = CustomerAddress.instanceOf(dto.getAddressId(), dto.getCustomerId()).getData();
        ProductDbGateway productDbGateway = SpringUtil.getBean(ProductDbGateway.class);
        Set<Integer> skuIds = dto.getSkuSet();
        Map<Integer, OrderSkuDTO> skuMap = productDbGateway.orderSkuList(dto.getMallId(), dto.getCustomerId(), skuIds).stream().collect(Collectors.toMap(OrderSkuDTO::getSkuId, v -> v));
        Map<Integer, Integer> supplierMap = new HashMap<>();
        for (OrderSkuDTO value : skuMap.values()) {
            if(!supplierMap.containsKey(value.getShopId())) {
                supplierMap.put(value.getShopId(),value.getSupplierId());
            }
        }
        List<SaleOrder> results = new ArrayList<>();
        for (SaleOrderCreateDTO orderDto : dto.getSaleOrders()) {
            DeliveryTypeEnum deliveryType = DeliveryTypeEnum.of(orderDto.getDeliveryType());
            Assert.isFalse(deliveryType.equals(DeliveryTypeEnum.EMPTY), "包含无效的发货方式");
            SaleOrderData orderData = new SaleOrderData();
            orderData.setCustomerId(dto.getCustomerId());
            orderData.setCustomerAccountId(dto.getAccountId());
            orderData.setMallId(dto.getMallId());
            orderData.setShopId(orderDto.getShopId());
            orderData.setSupplierId(supplierMap.getOrDefault(orderDto.getShopId(), 0));
            orderData.setReceiver(address.getReceiver());
            orderData.setReceiverPhone(address.getPhone());
            orderData.setReceiverAddress(address.getAddress());
            orderData.setReceiverProvince(address.getProvince());
            orderData.setReceiverCity(address.getCity());
            orderData.setReceiverArea(address.getArea());
            orderData.setRemark(orderDto.getRemark());
            orderData.setSn(SnGenerator.get(SnPrefixEnum.SO.toString()));
            orderData.setState(OrderStateEnum.TO_BE_PAID);
            orderData.setDeliveryType(deliveryType);
            orderData.setPayDeadline(LocalDateTime.now().plusMinutes(mall.getOrderPayExpiration()));
            orderData.setPayAmount(BigDecimal.ZERO);

            BigDecimal amount = BigDecimal.ZERO;
            List<SaleOrderItemData> itemDataList = new ArrayList<>();
            for (SaleOrderItemCreateDTO item : orderDto.getItems()) {
                Assert.isTrue(skuMap.containsKey(item.getSkuId()), "包含不存在的skuId");
                OrderSkuDTO sku = skuMap.get(item.getSkuId());
                Assert.isTrue(sku.getPrice().compareTo(item.getPrice()) == 0, OrderSubmitErrorEnum.PRICE_CHANGED.getMessage());
                SaleOrderItemData itemData = sku.toOrderItem(item.getQuantity());
                itemDataList.add(itemData);
                amount = amount.add(itemData.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
            orderData.setAmount(amount);
            FreightTypeEnum freightType = itemDataList.stream().anyMatch(SaleOrderItemData::getIsFreeShipping) ? FreightTypeEnum.FREE_SHIPPING : FreightTypeEnum.COLLECT;
            orderData.setFreightType(freightType);
            SaleOrder saleOrder = SaleOrder.newInstance(orderData, itemDataList);
            results.add(saleOrder);
        }
        return results;
    }

    public static List<SaleOrder> instancesOf(Set<Integer> saleOrderIds) {
        List<SaleOrderData> list = SpringUtil.getBean(CoreDomainRepository.class).listSaleOrdersByIds(saleOrderIds);
        return list.stream().map(SaleOrder::new).collect(Collectors.toList());
    }

    public static void attachItems(List<SaleOrder> saleOrders) {
        if (saleOrders == null || saleOrders.isEmpty()) {
            return;
        }
        Set<Integer> orderIds = saleOrders.stream().map(SaleOrder::getId).collect(Collectors.toSet());
        Map<Integer, List<SaleOrderItemData>> orderItemMap = SpringUtil.getBean(CoreDomainRepository.class).listOrderItemsByOrderIds(orderIds).stream().collect(Collectors.groupingBy(SaleOrderItemData::getSaleOrderId));
        for (SaleOrder saleOrder : saleOrders) {
            if (orderItemMap.containsKey(saleOrder.getId())) {
                saleOrder.items = orderItemMap.get(saleOrder.getId());
            }
        }
    }

    private List<SaleOrderItemData> getItems() {
        if(items == null || items.isEmpty()) {
            items = SpringUtil.getBean(CoreDomainRepository.class).getSaleOrderItems(data.getId());
        }
        return items;
    }

    public void save() {
        CoreDomainRepository coreDomainRepository = SpringUtil.getBean(CoreDomainRepository.class);
        this.data = coreDomainRepository.saveSaleOrder(data);
        if (this.items == null || this.items.isEmpty()) {
            return;
        }
        List<SaleOrderItemData> newItems = new ArrayList<>();
        this.items.forEach(item -> {
            item.setSaleOrderId(this.data.getId());
            newItems.add(coreDomainRepository.saveSaleOrderItem(item));
        });
        this.items = newItems;
    }

    /**
     * 取消订单
     *
     * @param cancelReason 取消原因
     * @return 订单
     */
    public SaleOrder cancel(String cancelReason) {
        Assert.notBlank(cancelReason, "取消原因不能为空");
        Assert.isTrue(this.data.getState() == OrderStateEnum.TO_BE_PAID, "只有待支付订单可以取消");
        this.data.setState(OrderStateEnum.CANCELED);
        this.data.setCancelReason(cancelReason);
        this.data.setCancelBy(OperatorUtil.getAccountId());
        PlatformTypeEnum platformType = OperatorUtil.getRoleData().getPlatformType();
        this.data.setCancelFrom(platformType == null ? OperateFromEnum.SYSTEM : platformType == PlatformTypeEnum.CUSTOMER ? OperateFromEnum.BUYER : OperateFromEnum.SELLER);
        this.data.setCancelTime(LocalDateTime.now());
        return this;
    }

    public SaleOrder finish() {
        Assert.isTrue(this.data.getState() == OrderStateEnum.RECEIVED, "订单未收货，不能完成");
        this.data.setState(OrderStateEnum.FINISHED);
        this.data.setFinishedTime(LocalDateTime.now());
        return this;
    }

    public SaleOrder updateDeliveryQuantity(Integer saleOrderItemId, Integer quantity) {
        Assert.isTrue(quantity > 0, "发货数量必须大于0");
        Assert.isTrue(data.getState() == OrderStateEnum.TO_BE_SHIPPED, "订单不处于发货状态");
        // 累加发货数量
        SaleOrderItemData saleOrderItemData = getItems().stream().filter(item -> item.getId().equals(saleOrderItemId)).findFirst().orElseThrow(() -> new BizException("sale order item error", "订单子项不存在"));
        int deliveryQuantity = saleOrderItemData.getDeliveryQuantity() + quantity;
        Assert.isTrue(deliveryQuantity <= saleOrderItemData.getQuantity(), "可发货数量不足");
        saleOrderItemData.setDeliveryQuantity(deliveryQuantity);
        // 是否已全部发货，如果是则修改订单状态为已发货
        if (this.isAllDelivery()) {
            this.data.setState(OrderStateEnum.TO_BE_RECEIVED);
            this.data.setDeliveryTime(LocalDateTime.now());
        }
        return this;
    }

    public SaleOrder updateReceivedQuantity(Integer saleOrderItemId, Integer quantity) {
        Assert.isTrue(quantity > 0, "收货数量必须大于0");
        // 累加收货数量
        SaleOrderItemData saleOrderItemData = getItems().stream().filter(item -> item.getId().equals(saleOrderItemId)).findFirst().orElseThrow(() -> new BizException("sale order item error", "订单子项不存在"));
        int receivedQuantity = saleOrderItemData.getReceivedQuantity() + quantity;
        Assert.isTrue(receivedQuantity <= saleOrderItemData.getDeliveryQuantity(), "可收货数量不足");
        saleOrderItemData.setReceivedQuantity(receivedQuantity);
        // 是否已全部收货，如果是则修改订单状态为已收货
        if (this.isAllReceived()) {
            this.data.setState(OrderStateEnum.RECEIVED);
            this.data.setReceivedTime(LocalDateTime.now());
            this.data.setFinishedDeadline(LocalDateTime.now().plusDays(Mall.instanceOf(data.getMallId()).getOrderReturnExpiration()));
        }
        return this;
    }

    public String getReceiverPhone() {
        return this.data.getReceiverPhone();
    }

    public Integer getShopId() {
        return this.data.getShopId();
    }

    public Integer getSupplerId() {
        return this.data.getSupplierId();
    }

    public Integer getCustomerId() {
        return this.data.getCustomerId();
    }

    public Integer getMallId() {
        return this.data.getMallId();
    }

    public Integer getId() {
        return this.data.getId();
    }

    public String getSn() {
        return this.data.getSn();
    }

    public LocalDateTime getPayDeadline() {
        return this.data.getPayDeadline();
    }

    public LocalDateTime getReceivedDeadline() {
        return this.data.getReceivedDeadline();
    }

    public LocalDateTime getFinishedDeadline() {
        return this.data.getFinishedDeadline();
    }

    public OrderStateEnum getState() {
        return this.data.getState();
    }


    /**
     * @return 是否全部发货
     */
    public boolean isAllDelivery() {
        return this.getItems().stream().allMatch(item -> item.getDeliveryQuantity().equals(item.getQuantity()));
    }

    /**
     * @return 是否全部收货
     */
    public boolean isAllReceived() {
        return this.getItems().stream().allMatch(item -> item.getReceivedQuantity().equals(item.getQuantity()));
    }

    public BigDecimal getAmount() {
        return this.data.getAmount();
    }

    public SaleOrder toBeApproved() {
        Assert.isTrue(this.data.getState().equals(OrderStateEnum.TO_BE_PAID), "订单状态不正确");
        this.data.setState(OrderStateEnum.TO_BE_APPROVED);
        return this;
    }

    public SaleOrder approved(LocalDateTime payTime) {
        Assert.isTrue(this.data.getState().equals(OrderStateEnum.TO_BE_APPROVED), "订单状态不正确");
        this.data.setState(OrderStateEnum.TO_BE_SHIPPED);
        this.data.setPayTime(payTime);
        this.data.setPayType(PayTypeEnum.ENTERPRISE_OFFLINE);
        this.data.setPayAmount(data.getAmount());
        return this;
    }

    public SaleOrder unApproved(Integer payExpirationMinutes) {
        Assert.isTrue(this.data.getState().equals(OrderStateEnum.TO_BE_APPROVED), "订单状态不正确");
        this.data.setState(OrderStateEnum.TO_BE_PAID);
        //重置自动取消时间
        if(payExpirationMinutes != null) {
            this.data.setPayDeadline(LocalDateTime.now().plusMinutes(payExpirationMinutes));
        }
        return this;
    }

    public Map<Integer, Integer> getSkuQtyMap() {
        return getItems().stream().collect(Collectors.groupingBy(SaleOrderItemData::getSkuId, Collectors.collectingAndThen(Collectors.toList(), y -> y.stream().mapToInt(SaleOrderItemData::getQuantity).sum())));
    }
}
