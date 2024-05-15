package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.PlatformTypeEnum;

import java.math.BigDecimal;

/**
 * 渠道
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "market_channel_rel", autoResultMap = true)
public class MarketChannelRelDO extends BaseDO {
    private PlatformTypeEnum platformType;
    private Integer platformId;
    private Integer marketChannelId;
    private BigDecimal markupRate;
}
