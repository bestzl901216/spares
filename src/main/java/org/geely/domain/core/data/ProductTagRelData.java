package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class ProductTagRelData implements Serializable {
    private Integer id;
    private Integer tagId;
    private Integer productId;
    private Integer version;

    public ProductTagRelData() {
    }

    public ProductTagRelData(Integer tagId, Integer productId) {
        this.tagId = tagId;
        this.productId = productId;
        this.version = 0;
    }
}
