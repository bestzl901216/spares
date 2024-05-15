package org.geely.controller.dto;

import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author cong huang
 */
@Data
@ApiModel("商品分类")
public class ProductCategoryDTO {
    private Integer id;
    private Integer parentId;
    private String parentPath;
    private String name;
    private Integer sort;
    private Integer level;
    private List<ProductCategoryDTO> children;
    private String code;
    public String getCode() {
        if (StringUtils.isNullOrEmpty(parentPath)) {
            return id.toString();
        } else {
            return parentPath + "," + id;
        }
    }
}
