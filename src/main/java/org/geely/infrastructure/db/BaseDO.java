package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ricardo zhou
 */
@Data
public abstract class BaseDO {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(fill = FieldFill.INSERT)
    private String creator;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic(value = "0", delval = "id")
    private Integer deletedFlag;
    @Version
    private Integer version;
}
