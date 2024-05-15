package org.geely.domain.core.data;

import lombok.Data;
import org.geely.common.enums.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ricardo zhou
 */
@Data
public class SaleOrderPayData implements Serializable {
    private Integer id;
    private Integer saleOrderId;
    private String sn;
    private Integer parentId;
    private PayTypeEnum payType;
    private BigDecimal amount;
    private String paySn;
    private OrderPayStateEnum state;
    private LocalDateTime payTime;
    private Integer version;

    public void success(String paySn) {
        this.state = OrderPayStateEnum.SUCCESS;
        this.paySn = paySn;
    }

    public void fail() {
        this.state = OrderPayStateEnum.TO_BE_PAID;
        this.payTime = null;
        this.payType = PayTypeEnum.EMPTY;
    }

    public void pendingApproval() {
        this.state = OrderPayStateEnum.PENDING_APPROVAL;
        this.payTime = LocalDateTime.now();
        this.payType = PayTypeEnum.ENTERPRISE_OFFLINE;
    }
}
