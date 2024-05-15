package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 销售订单
 *
 * @author ricardo zhou
 */
@Data
@ApiModel("销售订单查询")
public class SaleOrderQueryDTO {
    @ApiModelProperty(hidden = true)
    private Integer shopId;
    @ApiParam(value = "订单编号")
    private String sn;
    @ApiParam(value = "sap销售单号")
    private String sapSn;
    @ApiParam(value = "商品编号、名称")
    private String productKeyword;
    @ApiParam(value = "客户名称、账号名称")
    private String customerKeyword;
    @ApiParam(value = "下单时间-开始")
    private Integer createTimeStart;
    @ApiParam(value = "下单时间-结束")
    private Integer createTimeEnd;
    @ApiParam(name = "current", required = true, value = "页码")
    @Min(1)
    private Long current;
    @ApiParam(name = "size", required = true, value = "每页数量")
    @Min(1)
    @Max(1000)
    private Long size;
}
