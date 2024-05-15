package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.EnableStateEnum;

/**
 * 账号
 *
 * @author ricardo zhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "account", autoResultMap = true)
public class AccountDO extends BaseDO {
    private String name;
    private String encodedPassword;
    private String phone;
    private EnableStateEnum state;
    private String icon;
}
