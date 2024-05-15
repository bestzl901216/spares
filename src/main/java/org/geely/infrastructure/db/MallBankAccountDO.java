package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 收款账户
 *
 * @author cong huang
 */
@Data
@TableName(value = "mall_bank_account", autoResultMap = true)
public class MallBankAccountDO extends BaseDO {
    private Integer mallId;
    private String name;
    private String bank;
    private String bankAccount;
}
