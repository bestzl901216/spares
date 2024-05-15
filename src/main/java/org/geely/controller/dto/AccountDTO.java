package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("个人信息")
public class AccountDTO {
    private Integer id;
    /**
     * 名字
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    private String icon;
    @ApiModelProperty(value = "0.禁用 1.启用")
    private Integer state;
    /**
     * 公司名
     */
    private String customerName;
    /**
     * 公司税号
     */
    private String identitySn;
    /**
     * 公司地址
     */
    private String address;
    private List<SaleOrderStatisticsDTO> saleOrderStatistics;
    private String province;
    private String city;
    private String area;
}
