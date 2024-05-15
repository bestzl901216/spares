package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.domain.common.data.TrackData;
import org.geely.domain.core.data.DeliveryNoteData;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "delivery_note", autoResultMap = true)
public class DeliveryNoteDO extends BaseDO {
    /**
     * 销售订单ID
     */
    private Integer saleOrderId;
    /**
     * 发货单号
     */
    private String sn;
    /**
     * 物流公司
     */
    private String expressCompany;
    /**
     * 物流单号
     */
    private String expressSn;
    /**
     * 收货人姓名
     */
    private String receiverPhone;
    /**
     * 物流状态：来自快递100
     */
    private String expressState;
    /**
     * 物流信息
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<TrackData.TrailNodeData> data;
    /**
     * 同步时间
     */
    private LocalDateTime syncTime;
    /**
     * 状态
     */
    private DeliveryNoteData.StateEnum state;
    /**
     * 发货时间
     */
    private LocalDateTime sendTime;
    /**
     * 收货时间
     */
    private LocalDateTime receivedTime;
    /**
     * 收货截止时间
     */
    private LocalDateTime receivedDeadline;
}
