package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author cong huang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("商品规格")
public class ProductPropsUpdateDTO extends ProductPropsCreateDTO {
    @ApiModelProperty(value = "规格id，0为新增，非0为更新", required = true)
    @Min(0)
    @NotNull(message = "规格id不能为空")
    private int id;
}
