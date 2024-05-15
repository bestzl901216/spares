package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 发货单创建DTO
 *
 * @author ricardo zhou
 */
@Data
@ApiModel("创建发货单")
public class DeliveryCreateDTO {
    /**
     * 物流公司
     */
    @ApiModelProperty("物流公司")
    @NotBlank(message = "请选择物流公司")
    public String expressCompany;
    /**
     * 物流单号
     */
    @ApiModelProperty("物流单号")
    @NotBlank(message = "请输入物流单号")
    @Length(max = 20, message = "物流单号长度不能超过20")
    public String expressSn;
    /**
     * 货物数量map
     */
    @ApiModelProperty("货物数量")
    public List<QuantityDTO> quantityList;

    @Data
    public static class QuantityDTO {
        /**
         * 商品id
         */
        @ApiModelProperty("订单子项id")
        public Integer saleOrderItemId;
        /**
         * 数量
         */
        @ApiModelProperty("数量")
        public Integer quantity;
    }

}
