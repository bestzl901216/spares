package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.domain.common.data.NoticeData;

import java.time.LocalDateTime;

/**
 * 通知
 *
 * @author ricardo zhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "notice", autoResultMap = true)
public class NoticeDO extends BaseDO {
    private PlatformTypeEnum platformType;
    private Integer platformId;
    private String title;
    private String content;
    private LocalDateTime time;
    private NoticeData.StateEnum state;
}
