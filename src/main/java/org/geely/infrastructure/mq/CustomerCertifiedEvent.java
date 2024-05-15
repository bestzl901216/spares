package org.geely.infrastructure.mq;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.geely.common.exception.BizException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户认证成功事件
 *
 * @author ricardo zhou
 */
@Slf4j
@Builder
public class CustomerCertifiedEvent extends AbstractEvent {
    /**
     * 不同的事件通过消息队列中的tag来进行隔离
     * 需要保证唯一性
     */
    public static final String TAG = "CustomerCertifiedEvent";
    /**
     * 消费组：领悟SAP创建客户
     */
    public static final String GROUP1 = TOPIC + "_" + TAG + "_" + 1;
    /**
     * 消费组：备品SAP创建客户
     */
    public static final String GROUP2 = TOPIC + "_" + TAG + "_" + 2;

    /**
     * 客户id
     */
    private Integer customerId;

    @Override
    public void push() {
       /* TransactionSendResult sendResult = SpringUtil.getBean(RocketMQTemplate.class)
                .sendMessageInTransaction(TOPIC + ":" + TAG, MessageBuilder.withPayload(customerId).build(), null);
        log.info("localTransactionState = {}", sendResult.getLocalTransactionState());*/
    }


    @Slf4j
//    @Component
    @RocketMQMessageListener(topic = TOPIC, selectorExpression = TAG, consumerGroup = GROUP1)
    public static class MyConsumer1 implements RocketMQListener<Message> {
        @Override
        @Transactional(rollbackFor = Exception.class)
        public void onMessage(Message message) {
            // 注意幂等消费
            if (isProcessed(message, TAG, GROUP1)) {
                return;
            }
            // 业务逻辑
            Integer customerId = Integer.valueOf(new String(message.getBody()));
            log.info("tag = {} consumerGroup = {} customerId = {}", TAG, GROUP1, customerId);
            log.info("领悟SAP创建客户");
        }
    }

    @Slf4j
//    @Component
    @RocketMQMessageListener(topic = TOPIC, selectorExpression = TAG, consumerGroup = GROUP2)
    public static class MyConsumer2 implements RocketMQListener<Message> {
        @Override
        @Transactional(rollbackFor = Exception.class)
        public void onMessage(Message message) {
            // 注意幂等消费
            if (isProcessed(message, TAG, GROUP2)) {
                return;
            }
            // 业务逻辑
            Integer customerId = Integer.valueOf(new String(message.getBody()));
            log.info("tag = {} consumerGroup = {} customerId = {}", TAG, GROUP2, customerId);
            log.info("备品SAP创建客户");
            throw new BizException("error sap", "备品SAP创建客户失败");
        }
    }
}
