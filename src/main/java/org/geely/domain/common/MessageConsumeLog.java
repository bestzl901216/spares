package org.geely.domain.common;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.domain.common.data.MessageConsumeLogData;
import org.geely.infrastructure.db.repository.CommonDomainRepository;

/**
 * 消息消费日志
 *
 * @author ricardo zhou
 */
public class MessageConsumeLog {

    private MessageConsumeLogData data;

    private MessageConsumeLog(MessageConsumeLogData data) {
        this.data = ObjectUtil.clone(data);
    }

    /**
     * 创建消息消费日志
     *
     * @param data 消息数据
     * @return 消息消费日志
     */
    public static MessageConsumeLog newInstance(MessageConsumeLogData data) {
        MessageConsumeLog messageProduceLog = new MessageConsumeLog(data);
        messageProduceLog.data.setId(null);
        messageProduceLog.data.setVersion(0);
        messageProduceLog.save();
        return messageProduceLog;
    }

    /**
     * 获取消息消费日志
     *
     * @param messageId     消息ID
     * @param consumerGroup 消息消费分组
     * @return 消息消费日志
     */
    public static MessageConsumeLog instanceOf(String messageId, String consumerGroup) {
        MessageConsumeLogData data = SpringUtil.getBean(CommonDomainRepository.class).getMessageConsumeLog(messageId, consumerGroup);
        return new MessageConsumeLog(data);
    }

    /**
     * 保存消息消费日志
     */
    public void save() {
        this.data = SpringUtil.getBean(CommonDomainRepository.class).saveMessageConsumeLog(this.data);
    }

    /**
     * @return 消息消费日志数据
     */
    public MessageConsumeLogData getData() {
        return ObjectUtil.clone(this.data);
    }

}
