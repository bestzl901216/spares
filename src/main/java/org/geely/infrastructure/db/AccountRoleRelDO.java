package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.EnableStateEnum;

/**
 * 账户角色关联
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "account_role_rel", autoResultMap = true)
public class AccountRoleRelDO extends BaseDO {
    private Integer accountId;
    private Integer roleId;
    private EnableStateEnum state;
}
