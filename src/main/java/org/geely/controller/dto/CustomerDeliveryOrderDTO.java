package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.common.enums.ExpressCompanyEnum;
import org.geely.domain.core.data.DeliveryNoteData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("客户端发货单")
public class CustomerDeliveryOrderDTO {
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
    @ApiModelProperty("包裹数量")
    public Integer quantity;
    @ApiModelProperty("发货时间")
    public String sendTime;
    @ApiModelProperty("收货时间")
    public String receivedTime;
    @ApiModelProperty("收货截止时间")
    public String receivedDeadline;
    @ApiModelProperty("物流信息")
    public List<String> logisticsFlow = new ArrayList<>();

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
