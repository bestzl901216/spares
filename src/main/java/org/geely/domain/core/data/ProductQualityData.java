package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class ProductQualityData implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 租户id
     */
    private Integer mallId;
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
