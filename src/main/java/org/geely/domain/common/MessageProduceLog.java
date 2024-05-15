package org.geely.domain.common;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.domain.common.data.MessageProduceLogData;
import org.geely.infrastructure.db.repository.CommonDomainRepository;

/**
 * 消息生产日志
 *
 * @author ricardo zhou
 */
public class MessageProduceLog {

    private MessageProduceLogData data;

    private MessageProduceLog(MessageProduceLogData data) {
        this.data = ObjectUtil.clone(data);
    }

    /**
     * 创建消息生产日志
     *
     * @param data 消息数据
     * @return 消息生产日志
     */
    public static MessageProduceLog newInstance(MessageProduceLogData data) {
        MessageProduceLog messageProduceLog = new MessageProduceLog(data);
        messageProduceLog.data.setId(null);
        messageProduceLog.data.setVersion(0);
        messageProduceLog.save();
        return messageProduceLog;
    }

    /**
     * 获取消息生产日志
     *
     * @param messageId 消息ID
     * @return 消息生产日志
     */
    public static MessageProduceLog instanceOf(String messageId) {
        MessageProduceLogData data = SpringUtil.getBean(CommonDomainRepository.class).getMessageProduceLog(messageId);
        return new MessageProduceLog(data);
    }

    /**
     * 保存消息生产日志
     */
    public void save() {
        this.data = SpringUtil.getBean(CommonDomainRepository.class).saveMessageProduceLog(this.data);
    }

    /**
     * @return 消息生产日志数据
     */
    public MessageProduceLogData getData() {
        return ObjectUtil.clone(this.data);
    }

    public String getMessageId(){
        return data.getMessageId();
    }

}
