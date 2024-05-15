package org.geely.domain.support.data;

import lombok.Data;
import org.geely.common.enums.PlatformTypeEnum;

import java.io.Serializable;
import java.util.List;

/**
 * 员工角色
 *
 * @author ricardo zhou
 */
@Data
public class RoleData implements Serializable {
    /**
     * 角色ID
     */
    private Integer id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 角色描述
     */
    private String remark;
    /**
     * 平台类型
     */
    private PlatformTypeEnum platformType;
    /**
     * 平台id
     */
    private Integer platformId;
    /**
     * 权限
     */
    private List<String> permissions;
    /**
     * 版本号
     */
    private Integer version;
}
