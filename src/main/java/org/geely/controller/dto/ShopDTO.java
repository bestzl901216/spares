package org.geely.controller.dto;

import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
public class ShopDTO {
    private Integer id;
    private String name;
    private String adminPhone;
    private String adminName;
    private Integer productAudit;
    private Integer state;
    private String createTime;
    private String channelNames;
}
