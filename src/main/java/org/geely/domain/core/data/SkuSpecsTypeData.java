package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class SkuSpecsTypeData implements Serializable {
    private Integer id;
    private Integer mallId;
    private String name;
    private Integer sort;
    private Integer version;
}
