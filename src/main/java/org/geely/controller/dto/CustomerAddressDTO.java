package org.geely.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
public class CustomerAddressDTO {
    @ApiModelProperty(value = "地址编码")
    public Integer id;
    @ApiModelProperty(value = "详细地址")
    public String address;
    @ApiModelProperty(value = "收货人")
    public String receiver;
    @ApiModelProperty(value = "收货人手机号")
    public String phone;
    @ApiModelProperty(value = "是否默认地址")
    public Boolean isDefault;
    @ApiModelProperty(value = "注册日期")
    public String createTime;
    @ApiModelProperty(value = "省")
    public String province;
    @ApiModelProperty(value = "市")
    public String city;
    @ApiModelProperty(value = "区")
    public String area;

}
