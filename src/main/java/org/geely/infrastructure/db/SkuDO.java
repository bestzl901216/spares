package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.EnableStateEnum;

/**
 * SKU
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sku", autoResultMap = true)
public class SkuDO extends BaseDO {
    private Integer productId;
    private String code;
    private Integer stock;
    private Boolean isFreeShipping;
    private Integer moq;
    private Integer materialId;
    private EnableStateEnum state;
    private String unit;
    private Integer salesVolume;
    private String defaultImage;
    private Integer sort;
}
