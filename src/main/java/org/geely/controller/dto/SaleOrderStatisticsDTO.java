package org.geely.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
public class SaleOrderStatisticsDTO {
    @ApiModelProperty(value = "状态，-2=已退款，-1=已取消， 1=待支付，2=待审批，3=待同步，4=同步失败，5=待发货，6=待收货，7=已收货，8=已完成")
    public Integer state;
    @ApiModelProperty(value = "数量")
    public int count;
}
