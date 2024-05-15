package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品适用车型
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "product_vehicle_rel", autoResultMap = true)
public class ProductVehicleRelDO extends BaseDO {
    private Integer productId;
    private Integer vehicleModelId      ;
}
