package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ricardo zhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "search_history", autoResultMap = true)
public class SearchHistoryDO extends BaseDO {
    private Integer mallId;
    private Integer customerId;
    private Integer accountId;
    private String keyword;
}
