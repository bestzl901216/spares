package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Set;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("登录状态")
public class AuthDTO {
    private String token;
    private Set<Integer> mallIds;
    private Integer customerId;
    private String userName;
    private String nick;
    private Boolean vip;
    private String avater;
}
