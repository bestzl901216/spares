package org.geely.domain.common.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ricardo zhou
 */
@Data
public class MessageProduceLogData implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 消息ID
     */
    private String messageId;
    /**
     * 消息主题
     */
    private String topic;
    /**
     * 消息标签
     */
    private String tag;
    /**
     * 消息体
     */
    private String body;
    /**
     * 版本号
     */
    private Integer version;

}
