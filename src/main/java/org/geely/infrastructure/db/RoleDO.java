package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.PlatformTypeEnum;

import java.util.List;

/**
 * 角色
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "role", autoResultMap = true)
public class RoleDO extends BaseDO {
    private String name;
    private String remark;
    private PlatformTypeEnum platformType;
    private Integer platformId;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> permissions;
}
