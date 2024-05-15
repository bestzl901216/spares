package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ricardo zhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "message_consume_log", autoResultMap = true)
public class MessageConsumeLogDO extends BaseDO {
    private String messageId;
    private String topic;
    private String tag;
    private String body;
    private String consumerGroup;
}
