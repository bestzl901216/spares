package org.geely.domain.common.data;

import lombok.Data;
import org.geely.common.enums.PlatformTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ricardo zhou
 */
@Data
public class MarketChannelRelData implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 平台类型
     */
    private PlatformTypeEnum platformType;
    /**
     * 平台id
     */
    private Integer platformId;
    /**
     * 渠道id
     */
    private Integer marketChannelId;
    /**
     * 渠道名称
     */
    private String marketChannel;
    /**
     * 加价率
     */
    private BigDecimal markupRate;
    /**
     * 版本号
     */
    private Integer version;

}
