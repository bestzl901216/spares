package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("新建客户认证记录")
public class CertifiedInfoCreateDTO {

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

    @ApiModelProperty(value = "营业执照url", required = true)
    @NotBlank(message = "营业执照不能为空")
    public String identityImage;

    @ApiModelProperty(value = "门头照片url", required = true)
    @NotBlank(message = "门头照不能为空")
    public String shopImage;

    @ApiModelProperty(value = "工作间照片url", required = true)
    @NotBlank(message = "工作间照片不能为空")
    public List<String> workroomImage;

}
