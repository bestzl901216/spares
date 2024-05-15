package org.geely.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
public class SupplierDTO {
    @ApiModelProperty(value = "商家id")
    private Integer id;
    @ApiModelProperty(value = "商家名称")
    private String name;
    @ApiModelProperty(value = "商户号")
    private String merchantNo;
    @ApiModelProperty(value = "管理员")
    private String admin;
    @ApiModelProperty(value = "管理员手机")
    private String adminPhone;
    @ApiModelProperty(value = "销售渠道Id")
    private String marketChannelIds;
    @ApiModelProperty(value = "sap类型Id")
    private Integer sapId;
    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;
    @ApiModelProperty(value = "开户行")
    private String bank;
    @ApiModelProperty(value = "银行账户")
    private String bankAccount;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "状态")
    private Integer state;

}
