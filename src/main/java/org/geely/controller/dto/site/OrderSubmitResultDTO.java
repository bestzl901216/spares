package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.common.enums.OrderSubmitErrorEnum;

/**
 * @author cong huang
 */
@Data
@ApiModel("提交订单结果")
public class OrderSubmitResultDTO {
    @ApiModelProperty(value = "是否提交成功")
    private boolean success;
    @ApiModelProperty(value = "支付单号")
    private String paySn;
    @ApiModelProperty(value = "错误代码:1-商品失效，2-库存或起订量变更，3价格变更")
    private Integer errorCode;
    @ApiModelProperty(value = "错误信息")
    private String errorMessage;

    public static OrderSubmitResultDTO success(String paySn){
        OrderSubmitResultDTO result = new OrderSubmitResultDTO();
        result.success = true;
        result.paySn = paySn;
        return  result;
    }

    public static OrderSubmitResultDTO fail(OrderSubmitErrorEnum errorEnum){
        OrderSubmitResultDTO result = new OrderSubmitResultDTO();
        result.success = false;
        result.errorCode = errorEnum.getId();
        result.errorMessage = errorEnum.getMessage();
        return  result;
    }
}

