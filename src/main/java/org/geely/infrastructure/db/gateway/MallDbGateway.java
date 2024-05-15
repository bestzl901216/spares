package org.geely.infrastructure.db.gateway;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.geely.common.enums.EnableStateEnum;
import org.geely.controller.dto.*;
import org.geely.controller.dto.site.SaleOrderFlowDTO;
import org.geely.controller.dto.site.SearchHistoryDTO;

import java.util.List;

/**
 * @author cong huang
 */
@Mapper
public interface MallDbGateway {
    IPage<SupplierDTO> pageSupplier(Page<?> page, @Param("mallId") Integer mallId, @Param("name") String name, @Param("marketChannelId") Integer marketChannelId, @Param("sapId") Integer sapId, @Param("state") EnableStateEnum state);

    MallBaseInfoDTO getMallPlatformInfo();

    MallBaseInfoDTO getMallBaseInfo(@Param("mallId") Integer mallId);

    List<String> listMallBanner(@Param("mallId") Integer mallId);

    /**
     * 收货地址列表
     *
     * @param customerId 客户编号
     * @param mallId     商城编号
     * @return 收货地址列表
     */
    List<CustomerAddressDTO> listCustomerAddress(@Param("customerId") Integer customerId, @Param("mallId") Integer mallId);

    /**
     * @param mallId     商城Id
     * @param customerId 客户id
     * @param accountId  账号id
     * @return 最近10条搜索历史列表
     */
    List<SearchHistoryDTO> searchHistoryList(@Param("mallId") Integer mallId, @Param("customerId") Integer customerId, @Param("accountId") Integer accountId);

    /**
     * 租户管理后台-销售订单分页
     *
     * @param page   分页
     * @param mallId 租户Id
     */
    IPage<MallSaleOrderDTO> saleOrderPage(Page<?> page, @Param("mallId") Integer mallId);

    /**
     * @param id     订单id
     * @param mallId 租户id
     * @return 订单详情
     */
    MallSaleOrderDetailDTO getSaleOrderDetail(@Param("id") Integer id, @Param("mallId") Integer mallId);

    /**
     * @param id     订单id
     * @param mallId 租户id
     * @return 订单商品列表
     */
    List<MallSaleOrderItemDTO> getSaleOrderItems(@Param("id") Integer id, @Param("mallId") Integer mallId);

    /**
     * @param id 销售订单id
     * @return 发货单列表
     */
    List<MallDeliveryOrderDTO> deliveryOrderList(@Param("id") Integer id);

    /**
     * @param id 销售订单id
     * @return 订单追踪记录
     */
    List<SaleOrderFlowDTO> saleOrderFlow(@Param("id") Integer id);
}
