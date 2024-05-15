package org.geely.infrastructure.db.gateway;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.geely.controller.dto.*;
import org.geely.controller.dto.site.CustomerDeliveryPackageDTO;
import org.geely.controller.dto.site.SaleOrderFlowDTO;

import java.util.List;

/**
 * @author cong huang
 */
@Mapper
public interface SaleOrderDbGateway {
    List<SaleOrderStatisticsDTO> getSaleOrderStatistics(@Param("mallId") Integer mallId, @Param("customerId") Integer customerId);

    /**
     * 客户订单分页查询
     *
     * @param page       分页参数
     * @param customerId 客户id
     * @param mallId     商城id
     * @param state      订单状态
     * @return 分页查询结果
     */
    IPage<CustomerSaleOrderDTO> saleOrderPage(Page<?> page, @Param("customerId") Integer customerId, @Param("mallId") Integer mallId, @Param("state") Integer state);

    /**
     * @param id         订单id
     * @param customerId 客户id
     * @param mallId     商城id
     * @return 订单详情
     */
    CustomerSaleOrderDetailDTO getSaleOrderDetail(@Param("id") Integer id, @Param("customerId") Integer customerId, @Param("mallId") Integer mallId);

    /**
     * 订单详情-子项列表
     *
     * @param id         订单id
     * @param customerId 客户id
     * @param mallId     商城id
     * @return 订单详情-子项列表
     */
    List<CustomerSaleOrderItemDTO> getSaleOrderItems(@Param("id") Integer id, @Param("customerId") Integer customerId, @Param("mallId") Integer mallId);

    /**
     * 订单详情-发货单
     *
     * @param saleOrderId 订单id
     * @return 订单详情
     */
    List<CustomerDeliveryOrderDTO> deliveryOrderList(@Param("saleOrderId") Integer saleOrderId);

    /**
     * 为待付款、待审批、待发货、待收货数量之和
     *
     * @param mallId     商城id
     * @param customerId 客户id
     * @return 订单提醒
     */
    Integer getOrderReminder(@Param("mallId") Integer mallId, @Param("customerId") Integer customerId);

    /**
     * 购物车商品种类数量
     *
     * @param mallId     商城id
     * @param customerId 客户id
     * @return 购物车数量
     */
    Integer getCartCount(@Param("mallId") Integer mallId, @Param("customerId") Integer customerId);

    /**
     * @param id 订单id
     * @return 订单追踪记录
     */
    List<SaleOrderFlowDTO> saleOrderFlow(@Param("id") Integer id);

    /**
     * @param saleOrderId     订单id
     * @param deliveryOrderId 发货单id
     * @return 发货单包裹列表
     */
    List<CustomerDeliveryPackageDTO> deliveryPackageList(@Param("saleOrderId") Integer saleOrderId, @Param("deliveryOrderId") Integer deliveryOrderId);
}
