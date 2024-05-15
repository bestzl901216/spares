package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ricardo zhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "message_produce_log", autoResultMap = true)
public class MessageProduceLogDO extends BaseDO {
    private String messageId;
    private String topic;
    private String tag;
    private String body;
}
