package org.geely.infrastructure.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.geely.domain.common.MessageConsumeLog;
import org.geely.domain.common.data.MessageConsumeLogData;

import java.nio.charset.StandardCharsets;

/**
 * @author ricardo zhou
 */
@Slf4j
public abstract class AbstractEvent {

    /**
     * 所有的事件都在一个topic下面
     */
    public static final String TOPIC = "EventBus";

    public abstract void push();

    /**
     * 检验是否已响应该事件
     */
    public static boolean isProcessed(Message msg, String tag, String consumerGroup) {
        MessageConsumeLogData data = new MessageConsumeLogData();
        data.setMessageId(msg.getProperty("UNIQ_KEY"));
        data.setTopic(TOPIC);
        data.setTag(tag);
        data.setBody(new String(msg.getBody(), StandardCharsets.UTF_8));
        data.setConsumerGroup(consumerGroup);
        try {
            MessageConsumeLog.newInstance(data);
            log.info("保存消息消费记录 messageConsumeLog = {}", data);
            return false;
        } catch (Exception e) {
            log.warn("重复处理：message = {} consumerGroup = {}", msg, consumerGroup);
            return true;
        }
    }

}
