package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("新建客户")
public class CustomerCreateDTO {

    @ApiModelProperty(value = "管理员名字", required = true)
    @NotBlank(message = "管理员名字不能为空")
    public String adminName;

    @ApiModelProperty(value = "管理员手机号", required = true)
    @NotBlank(message = "管理员手机号不能为空")
    public String adminPhone;

    @ApiModelProperty(value = "渠道id", required = true)
    @NotNull(message = "渠道id不能为空")
    public Integer marketChannelId;

    @ApiModelProperty(value = "客户名称", required = true)
    @NotBlank(message = "客户名称不能为空")
    public String name;

    @ApiModelProperty(value = "公司税号或个人身份证", required = true)
    @NotBlank(message = "公司税号或个人身份证不能为空")
    public String identitySn;

    @ApiModelProperty(value = "省市区编码", required = true)
    @NotBlank(message = "省市区编码不能为空")
    public String pcaCode;

    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank(message = "详细地址不能为空")
    public String address;

    @ApiModelProperty(value = "营业执照或身份证照片url", required = true)
    @NotBlank(message = "营业执照或身份证照片url不能为空")
    public String identityImage;

    @ApiModelProperty(value = "门头照片url", required = true)
    @NotBlank(message = "名称不能为空")
    public String shopImage;

    @ApiModelProperty(value = "工作间照片url", required = true)
    @NotEmpty(message = "工作间照片不能为空")
    public List<String> workroomImage;

}
