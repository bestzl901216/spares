package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 合并支付失败关联订单记录
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "batch_pay_fail_rel", autoResultMap = true)
public class BatchPayFailRelDO extends BaseDO {
    private Integer batchId;
    private Integer saleOrderId;
}
