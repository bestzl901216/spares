package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "province_city_area", autoResultMap = true)
public class ProvinceCityAreaDO extends BaseDO {
    private String code;
    private String province;
    private String city;
    private String area;
}
