package org.geely.domain.core.data;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.geely.domain.common.data.TrackData;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
public class DeliveryNoteData implements Serializable {
    private Integer id;
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
    private List<TrackData.TrailNodeData> data;
    /**
     * 同步时间
     */
    private LocalDateTime syncTime;
    /**
     * 状态
     */
    private StateEnum state;
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
    /**
     * 版本
     */
    private Integer version;

    @Getter
    @AllArgsConstructor
    public enum StateEnum {
        /**
         * 代签收
         */
        UNRECEIVED(0, "待签收"),
        /**
         * 已签收
         */
        RECEIVED(1, "已签收");

        @EnumValue
        private final Integer id;
        private final String name;

        public static String getName(String id) {
            for (StateEnum stateEnum : StateEnum.values()) {
                if (stateEnum.id.toString().equals(id)) {
                    return stateEnum.name;
                }
            }
            return "";
        }
    }
}
