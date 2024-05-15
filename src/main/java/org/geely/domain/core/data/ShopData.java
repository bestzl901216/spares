package org.geely.domain.core.data;

import lombok.Data;
import org.geely.common.enums.EnableStateEnum;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class ShopData implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 租户id
     */
    private Integer mallId;
    /**
     * 供应商id
     */
    private Integer supplierId;
    /**
     * 名称
     */

    private String name;

    private EnableStateEnum state;
    /**
     * 备注
     */
    private String remark;
    /**
     * 版本号
     */
    private Integer version;

}
