package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.geely.common.enums.EnableStateEnum;

/**
 * SAP类型
 *
 * @author cong huang
 */
@Data
@TableName(value = "mall_sap", autoResultMap = true)
public class MallSapDO extends BaseDO {
    private Integer mallId;
    private String name;
    private EnableStateEnum state;
}
