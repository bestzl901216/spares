package org.geely.infrastructure.db.gateway;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.geely.controller.dto.*;

import java.util.List;

/**
 * @author ricardo zhou
 */
@Mapper
public interface SupplierDbGateway {
    /**
     * 分页查询消息
     *
     * @param page       分页参数
     * @param supplierId 商家id
     * @param channelId  渠道id
     * @param state      启用禁用
     * @return 消息集合
     */
    IPage<ShopDTO> shopPage(Page<?> page, @Param("supplierId") Integer supplierId, @Param("shopName") String shopName, @Param("channelId") Integer channelId, @Param("state") Integer state);

    /**
     * 分页查询员工
     *
     * @param page       分页参数
     * @param supplierId 商家id
     * @param phone      手机号
     * @return 消息集合
     */
    IPage<StaffDTO> staffPage(Page<?> page, @Param("supplierId") Integer supplierId, @Param("phone") String phone);

    /**
     * 分页查询角色
     *
     * @param page       分页参数
     * @param supplierId 商家id
     * @return 消息集合
     */
    IPage<RoleDTO> rolePage(Page<?> page, @Param("supplierId") Integer supplierId);

    /**
     * 分页查询物料
     *
     * @param page       分页参数
     * @param supplierId 商家id
     * @param keyword    物料编码/描述/oe号
     * @param category   后台类目
     * @return 消息集合
     * @Param shopIds 授权店铺
     */
    IPage<MaterialDTO> materialPage(Page<?> page, @Param("supplierId") Integer supplierId, @Param("keyword") String keyword, @Param("category") String category, @Param("shopIds") List<Integer> shopIds);

    /**
     * @param page       分页参数
     * @param supplierId 商家id
     * @return 商家订单分页
     */
    IPage<SupplierSaleOrderDTO> saleOrders(Page<?> page, @Param("supplierId") Integer supplierId);

    /**
     * @param sn         支付批次号
     * @param supplierId 商家id
     * @return 订单支付信息
     */
    SupplierSaleOrderPayApproveDTO payApprove(@Param("sn") String sn, @Param("supplierId") Integer supplierId);

    /**
     * @param sn         订单号
     * @param supplierId 商家id
     * @return 订单详情
     */
    SupplierSaleOrderDetailDTO saleOrder(@Param("sn") String sn, @Param("supplierId") Integer supplierId);

    List<SaleOrderItemDTO> getSaleOrderItems(@Param("saleOrderId") Integer saleOrderId, @Param("supplierId") Integer supplierId);
}
