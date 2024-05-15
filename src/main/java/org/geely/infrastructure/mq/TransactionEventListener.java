package org.geely.infrastructure.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.geely.domain.common.MessageProduceLog;
import org.geely.domain.common.data.MessageProduceLogData;
import org.springframework.messaging.Message;

import java.nio.charset.StandardCharsets;

/**
 * @author ricardo zhou
 */
@Slf4j
@RocketMQTransactionListener
public class TransactionEventListener implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        MessageProduceLogData data = new MessageProduceLogData();
        data.setMessageId(msg.getHeaders().get("rocketmq_TRANSACTION_ID", String.class));
        data.setTopic(msg.getHeaders().get("rocketmq_TOPIC", String.class));
        data.setTag(msg.getHeaders().get("rocketmq_TAGS", String.class));
        data.setBody(new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8));
        MessageProduceLog messageProduceLog = MessageProduceLog.newInstance(data);
        log.info("executeLocalTransaction: messageId={}", messageProduceLog.getMessageId());
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        try {
            String messageId = msg.getHeaders().get("rocketmq_TRANSACTION_ID", String.class);
            log.info("checkLocalTransaction: messageId={}", messageId);
            MessageProduceLog.instanceOf(messageId);
            log.info("checkLocalTransaction success");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("checkLocalTransaction error", e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
