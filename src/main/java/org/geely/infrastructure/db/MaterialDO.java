package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 物料
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "material", autoResultMap = true)
public class MaterialDO extends BaseDO {
    private Integer mallId;
    private Integer supplierId;
    private String code;
    private String name;
    private String oeNo;
    private String category;
    private Integer stock;
    private String unit;
    private BigDecimal price;
}
