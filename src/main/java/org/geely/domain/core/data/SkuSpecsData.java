package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class SkuSpecsData implements Serializable {
    private Integer id;
    private Integer typeId;
    private String typeName;
    private String name;
    private Integer sort;
    private Boolean selectable;
    private Integer version;

    public  SkuSpecsData() {

    }
    public SkuSpecsData(Integer typeId, String name) {
        this.typeId = typeId;
        this.name = name;
    }
    public SkuSpecsData(Integer typeId, String name, String typeName) {
        this.typeId = typeId;
        this.name = name;
        this.typeName = typeName;
    }
}
