package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class ProductBrandData implements Serializable {
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
     * 图片url
     */
    private String logo;
    /**
     * 版本号
     */
    private Integer version;

}
