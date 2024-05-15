package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("收款账户")
public class MallBankAccountDTO {
    private Integer id;
    private String name;
    private String bank;
    private String bankAccount;
    private String createTime;
}
