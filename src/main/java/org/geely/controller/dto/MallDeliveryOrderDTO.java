package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.common.enums.ExpressCompanyEnum;
import org.geely.domain.core.data.DeliveryNoteData;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("租户后台-发货单")
public class MallDeliveryOrderDTO {
    @ApiModelProperty("发货单id")
    private Integer id;
    @ApiModelProperty("发货单号")
    private String sn;
    @ApiModelProperty("物流公司")
    private String expressCompany;
    @ApiModelProperty("快递单号")
    private String expressSn;
    @ApiModelProperty("状态")
    private String state;
    @ApiModelProperty("商品数量")
    private Integer quantity;
    @ApiModelProperty("发货时间")
    private String createTime;

    public void setState(String state) {
        this.state = DeliveryNoteData.StateEnum.getName(state);
    }

    public void setExpressCompany(String expressCompany) {
        try {
            this.expressCompany = ExpressCompanyEnum.valueOf(expressCompany).getName();
        } catch (IllegalArgumentException e) {
            this.expressCompany = "未知快递公司";
        }
    }
}
