package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.geely.common.enums.EnableStateEnum;

/**
 * 商家
 *
 * @author cong huang
 */
@Data
@TableName(value = "supplier", autoResultMap = true)
public class SupplierDO extends BaseDO {
    private Integer mallId;
    private String name;
    private Integer sapId;
    private EnableStateEnum state;
    private Integer bankAccountId;
    private String merchantNo;
}
