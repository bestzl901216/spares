package org.geely.domain.core.data;

import lombok.Data;
import org.geely.common.enums.EnableStateEnum;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class ProductTagData implements Serializable {
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
     * 字体颜色
     */
    private String fontColor;
    /**
     * 背景颜色
     */
    private String backgroundColor;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序
     */
    private Integer sort;
    /**
     *
     */
    private EnableStateEnum state;
    /**
     * 版本号
     */
    private Integer version;

}
