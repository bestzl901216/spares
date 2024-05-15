package org.geely.infrastructure.db.gateway;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.geely.controller.dto.*;
import org.geely.controller.dto.site.SaleOrderFlowDTO;

import java.util.List;

/**
 * @author ricardo zhou
 */
@Mapper
public interface ShopDbGateway {
    /**
     * 订单分页查询
     *
     * @param page 分页参数
     * @param dto  查询条件
     * @return 分页查询结果
     */
    IPage<SaleOrderDTO> saleOrderPage(Page<?> page, @Param("dto") SaleOrderQueryDTO dto);

    /**
     * 分页查询员工
     *
     * @param page   分页参数
     * @param shopId 店铺id
     * @param phone  手机号
     * @return 消息集合
     */
    IPage<StaffDTO> staffPage(Page<?> page, @Param("shopId") Integer shopId, @Param("phone") String phone);

    /**
     * 分页查询角色
     *
     * @param page   分页参数
     * @param shopId 店铺id
     * @return 消息集合
     */
    IPage<RoleDTO> rolePage(Page<?> page, @Param("shopId") Integer shopId);

    /**
     * 分页查询物料
     *
     * @param page     分页参数
     * @param shopId   店铺id
     * @param keyword  物料编码/描述/oe号
     * @param category 后台类目
     * @return 消息集合
     */
    IPage<MaterialDTO> materialPage(Page<?> page, @Param("shopId") Integer shopId, @Param("keyword") String keyword, @Param("category") String category);

    /**
     * 订单详情
     */
    SaleOrderDetailDTO saleOrderDetail(@Param("saleOrderId") Integer saleOrderId, @Param("shopId") Integer shopId);

    /**
     * 订单子项
     */
    List<SaleOrderItemDTO> saleOrderItems(@Param("saleOrderId") Integer saleOrderId, @Param("shopId") Integer shopId);

    /**
     * 发货单列表
     */
    List<DeliveryOrderDTO> deliveryOrderList(@Param("saleOrderId") Integer saleOrderId);

    List<SaleOrderFlowDTO> saleOrderFlow(@Param("id") Integer id);
}
