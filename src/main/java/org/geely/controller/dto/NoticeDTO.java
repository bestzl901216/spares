package org.geely.controller.dto;

import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
public class NoticeDTO {
    private Integer id;
    private String title;
    private String content;
    private String time;
    private Integer state;
}
