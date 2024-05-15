package org.geely.controller.dto;

import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
public class MallBannerDTO {
    private Integer mallId;
    private String mallName;
    private String title;
    private String imageUrl;
    private String actionUrl;
    private Integer sort;
    private Integer position;
    private Integer state;
    private String createTime;
}
