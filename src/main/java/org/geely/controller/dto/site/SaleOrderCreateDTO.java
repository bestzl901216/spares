package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cong huang
 */
@Data
@ApiModel("订单")
public class SaleOrderCreateDTO {
    @ApiModelProperty(value = "店铺id", required = true)
    @NotNull(message = "店铺id不能为空")
    private Integer shopId;
    @ApiModelProperty(value = "配送方式：1-厂家直发", required = true)
    @NotNull(message = "配送方式不能为空")
    private Integer deliveryType;
    @ApiModelProperty(value = "备注", required = true)
    @Length(max = 100)
    private String remark;
    @ApiModelProperty(value = "订单子项", required = true)
    @NotEmpty(message = "订单子项不能为空")
    @Valid
    private List<SaleOrderItemCreateDTO> items;
}

