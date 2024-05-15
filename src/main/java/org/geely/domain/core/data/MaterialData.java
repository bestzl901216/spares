package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 67091
 */
@Data
public class MaterialData implements Serializable {
    private Integer id;
    private Integer mallId;
    private Integer supplierId;
    private String code;
    private String name;
    private String oeNo;
    private String category;
    private Integer stock;
    private String unit;
    private BigDecimal price;
    private Integer version;
}
