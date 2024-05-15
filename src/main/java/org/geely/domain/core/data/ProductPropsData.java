package org.geely.domain.core.data;

import cn.hutool.core.lang.Assert;
import lombok.Data;
import org.geely.controller.dto.ProductPropsUpdateDTO;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class ProductPropsData implements Serializable {
    private Integer id;
    private Integer productId;
    private String name;
    private String value;
    private Integer sort;
    private Integer version;

    public ProductPropsData() {
    }

    public ProductPropsData(Integer productId, String name, String value, Integer sort) {
        this.productId = productId;
        this.name = name;
        this.value = value;
        this.sort = sort;
        this.version = 0;
    }

    public ProductPropsData(ProductPropsUpdateDTO updateDTO, Integer productId, Integer sort) {
        Assert.notNull(updateDTO, "ProductPropsUpdateDTO不能为空");
        this.productId = productId;
        this.sort = sort;
        this.id = updateDTO.getId();
        this.name = updateDTO.getName();
        this.value = updateDTO.getValue();
    }
}
