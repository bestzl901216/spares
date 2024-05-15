package org.geely.domain.core.data;

import cn.hutool.core.lang.Assert;
import lombok.Data;
import org.geely.common.enums.OrderPayApproveStateEnum;
import org.geely.controller.dto.site.OrderPaySupplierApproveDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ricardo zhou
 */
@Data
public class SaleOrderPayApproveData implements Serializable {
    private Integer id;
    private Integer batchId;
    private Integer supplierId;
    private BigDecimal amount;
    private String paySn;
    private OrderPayApproveStateEnum state;
    private String remark;
    private String approveRemark;
    private String vouchers;
    private Integer version;
    private LocalDateTime approveTime;

    public SaleOrderPayApproveData() {
    }
    public SaleOrderPayApproveData(Integer batchId, BigDecimal amount, OrderPaySupplierApproveDTO dto) {
        Assert.notNull(dto);
        this.batchId = batchId;
        this.amount = amount;
        supplierId = dto.getSupplierId();
        vouchers = dto.getVouchers();
        remark = dto.getRemark();
        state = OrderPayApproveStateEnum.PENDING_APPROVAL;
    }
}
