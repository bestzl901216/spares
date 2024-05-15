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
@ApiModel("发货单")
public class DeliveryOrderDTO {
    @ApiModelProperty("发货单id")
    public Integer id;
    @ApiModelProperty("发货单号")
    public String sn;
    @ApiModelProperty("物流公司")
    public String expressCompany;
    @ApiModelProperty("快递单号")
    public String expressSn;
    @ApiModelProperty("状态")
    public String state;
    @ApiModelProperty("商品数量")
    public Integer quantity;
    @ApiModelProperty("发货时间")
    public String createTime;

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
