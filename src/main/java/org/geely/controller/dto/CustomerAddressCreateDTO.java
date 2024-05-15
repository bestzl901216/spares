package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author 67091
 */
@Data
@ApiModel("新建客户收货地址")
public class CustomerAddressCreateDTO {

    private Integer id;
    @ApiModelProperty(value = "收货人", required = true)
    @NotBlank(message = "请输入收货人姓名")
    @Length(max = 10, message = "收货人名称长度不能超过10")
    private String receiver;
    @ApiModelProperty(value = "收货人手机号", required = true)
    @NotBlank(message = "请填写收货人手机号")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机号")
    private String phone;
    @ApiModelProperty(value = "省份", required = true)
    @NotBlank(message = "请选择省份")
    @Length(max = 50)
    private String province;
    @ApiModelProperty(value = "市", required = true)
    @NotBlank(message = "请选择城市")
    @Length(max = 50)
    private String city;
    @ApiModelProperty(value = "区", required = true)
    @Length(max = 50)
    @NotBlank(message = "请选择地区")
    private String area;
    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank(message = "请填写详细地址")
    @Length(max = 50, message = "详细地址长度不能超过50")
    private String address;
    @ApiModelProperty(value = "是否默认地址")
    private Boolean isDefault;
    private Integer customerId;
    private Integer mallId;
}