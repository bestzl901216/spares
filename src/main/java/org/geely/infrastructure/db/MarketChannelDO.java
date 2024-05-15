package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 渠道
 *
 * @author cong huang
 */
@Data
@TableName(value = "market_channel", autoResultMap = true)
public class MarketChannelDO extends BaseDO {
    private String name;
    private String remark;
}
