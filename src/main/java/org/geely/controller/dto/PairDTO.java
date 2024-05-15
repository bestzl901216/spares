package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("id name pair")
public class PairDTO {
    private Integer id;
    private String name;
}
