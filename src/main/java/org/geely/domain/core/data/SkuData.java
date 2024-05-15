package org.geely.domain.core.data;

import lombok.Data;
import org.geely.common.enums.EnableStateEnum;

import java.io.Serializable;
/**
 * @author cong huang
 */
@Data
public class SkuData implements Serializable {
    private Integer id;
    private Integer productId;
    private String code;
    private String unit;
    private Integer stock;
    private Boolean isFreeShipping;
    private Integer moq;
    private Integer materialId;
    private EnableStateEnum state;
    private Integer salesVolume;
    private String defaultImage;
    private Integer sort;
    private Integer version;
}
