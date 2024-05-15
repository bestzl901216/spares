package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 车型目录
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "vehicle", autoResultMap = true)
public class VehicleDO extends BaseDO {
    private Integer mallId;
    private String code;
    private String name;
    private Integer sort;
    private Integer type;
    private Integer parentId;
}
