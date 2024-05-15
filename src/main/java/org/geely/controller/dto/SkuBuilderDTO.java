package org.geely.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuBuilderDTO {
    private Integer id;
    private Integer productId;
    private Integer stock;
    private BigDecimal price;
    private String image;
}
