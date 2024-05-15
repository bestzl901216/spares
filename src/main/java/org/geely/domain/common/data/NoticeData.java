package org.geely.domain.common.data;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.geely.common.enums.PlatformTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通知
 *
 * @author ricardo zhou
 */
@Data
public class NoticeData implements Serializable {
    /**
     * 通知id
     */
    private Integer id;
    /**
     * 通知标题
     */
    private String title;
    /**
     * 通知内容
     */
    private String content;
    /**
     * 平台类型
     */
    private PlatformTypeEnum platformType;
    /**
     * 平台id
     */
    private Integer platformId;
    /**
     * 通知时间
     */
    private LocalDateTime time;
    /**
     * 通知状态
     * 0:未读 1:已读
     */
    private StateEnum state;
    /**
     * 版本号
     */
    private Integer version;

    @Getter
    @AllArgsConstructor
    public enum StateEnum {
        UNREAD(0, "未读"),
        READ(1, "已读");

        @EnumValue
        private final Integer id;
        private final String name;

    }

}
