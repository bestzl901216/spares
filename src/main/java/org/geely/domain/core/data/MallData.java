package org.geely.domain.core.data;

import lombok.Data;
import org.geely.common.enums.EnableStateEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author cong huang
 */
@Data
public class MallData implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 标语
     */
    private String motto;
    /**
     * 服务热线
     */
    private String servicePhone;
    /**
     * logo url
     */
    private String logo;
    /**
     * 状态
     */
    private EnableStateEnum state;
    /**
     * 支付过期时间（分钟）
     */
    private Integer orderPayExpiration;
    /**
     * 自动收货时间（天）
     */
    private Integer orderReceiveExpiration;
    /**
     * 收货过期时间（天）
     */
    private Integer orderReturnExpiration;
    /**
     * 税率0.05
     */
    private BigDecimal taxPoint;
    /**
     * 商城样式
     */
    private Integer styleMode;
    /**
     * 版本号
     */
    private Integer version;

}
