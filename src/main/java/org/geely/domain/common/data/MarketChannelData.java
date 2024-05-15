package org.geely.domain.common.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ricardo zhou
 */
@Data
public class MarketChannelData implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
    /**
     * 版本号
     */
    private Integer version;

}
