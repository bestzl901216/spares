package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.EnableStateEnum;
import org.geely.common.model.PageDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("商家查询")
public class SupplierQueryDTO extends PageDTO {
    @ApiModelProperty(hidden = true)
    private Integer mallId;
    @ApiModelProperty(value = "商家名称")
    private String name;
    @ApiModelProperty(value = "渠道id")
    private Integer marketChannelId;
    @ApiModelProperty(value="sap类型id")
    private Integer sapId;
    @ApiModelProperty(value="状态")
    private EnableStateEnum state;
}
