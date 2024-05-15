package org.geely.domain.core.data;

import lombok.Data;
import org.geely.common.enums.ProductStateEnum;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class ProductData implements Serializable {
    private Integer id;
    private Integer mallId;
    private Integer supplierId;
    private Integer shopId;
    private String code;
    private Integer categoryId;
    private String category;
    private Integer qualityId;
    private String quality;
    private Integer brandId;
    private String brand;
    private String name;
    private String description;
    private ProductStateEnum state;
    private Integer sort;
    private Integer version;
}
