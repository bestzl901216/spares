package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.EnableStateEnum;

import java.math.BigDecimal;

/**
 * 租户
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "mall", autoResultMap = true)
public class MallDO extends BaseDO {
    private String name;
    private String motto;
    private String servicePhone;
    private String logo;
    private EnableStateEnum state;
    private Integer orderPayExpiration;
    private Integer orderReceiveExpiration;
    private Integer orderReturnExpiration;
    private BigDecimal taxPoint;
    private Integer styleMode;
}
