package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 67091
 */
@Data
public class ProductImageData implements Serializable {
    private Integer id;
    private Integer productId;
    private String url;
    private Integer sort;

    private Integer version;
    public ProductImageData() {

    }
    public ProductImageData(Integer productId, String url, Integer sort) {
        this.productId = productId;
        this.url = url;
        this.sort = sort;
        this.version = 0;
    }
}
