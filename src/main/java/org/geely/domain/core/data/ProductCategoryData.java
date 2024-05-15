package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class ProductCategoryData implements Serializable {
    private Integer id;
    private Integer mallId;
    private Integer parentId;
    private String parentPath;
    private String name;
    private Integer sort;
    private Integer level;
    private Integer version;
}
