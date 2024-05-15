package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ricardo zhou
 */
@Data
public class SaleOrderItemData implements Serializable {
    private Integer id;
    /**
     * 销售订单id
     */
    private Integer saleOrderId;
    /**
     * 商品id
     */
    private Integer productId;
    /**
     * skuId
     */
    private Integer skuId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品编码
     */
    private String productCode;
    /**
     * SKU编码
     */
    private String skuCode;
    /**
     * SKU图片
     */
    private String image;
    /**
     * 物料编码
     */
    private String materialCode;
    /**
     * 商品品质
     */
    private String productQuality;
    /**
     * 商品品牌
     */
    private String productBrand;
    /**
     * SKU规格
     */
    private String skuSpecs;
    /**
     * 原价
     */
    private BigDecimal originPrice;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 是否包邮
     */
    private Boolean isFreeShipping;
    /**
     * 是否支持退货
     */
    private Boolean supportReturn;
    /**
     * 单位
     */
    private String unit;
    /**
     * 已发货数量
     */
    private Integer deliveryQuantity;
    /**
     * 已收货数量
     */
    private Integer receivedQuantity;
    /**
     * 版本号
     */
    private Integer version;
}
