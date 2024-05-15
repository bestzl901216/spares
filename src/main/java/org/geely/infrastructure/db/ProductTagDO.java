package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.EnableStateEnum;

/**
 * 商品标签
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "product_tag", autoResultMap = true)
public class ProductTagDO extends BaseDO {
    private Integer mallId;
    private String name;
    private String fontColor;
    private String backgroundColor;
    private String remark;
    private Integer sort;
    private EnableStateEnum state;
}
