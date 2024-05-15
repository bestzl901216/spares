package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("Sap类型")
public class MallSapDTO {
    private Integer id;
    private String name;
}
