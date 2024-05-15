package org.geely.job;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.utils.OperatorUtil;
import org.geely.domain.core.DeliveryOrder;
import org.geely.domain.core.SaleOrder;
import org.geely.domain.core.data.DeliveryNoteData;
import org.geely.domain.support.SaleOrderFlow;
import org.geely.infrastructure.db.DeliveryNoteDO;
import org.geely.infrastructure.db.service.DeliveryNoteDbService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 定时任务：销售订单自动取消
 *
 * @author ricardo zhou
 */
@Slf4j
@Component
public class DeliveryOrderAutoConfirmJob extends JavaProcessor {

    @Resource
    private DeliveryNoteDbService deliveryNoteDbService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult process(JobContext context) {
        // 获取订单状态为待签收、支付截止时间小于当前时间的订单
        LambdaQueryWrapper<DeliveryNoteDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(DeliveryNoteDO::getId).eq(DeliveryNoteDO::getState, DeliveryNoteData.StateEnum.UNRECEIVED)
                .lt(DeliveryNoteDO::getReceivedDeadline, LocalDateTime.now());
        OperatorUtil.actAsSystem();
        deliveryNoteDbService.list(queryWrapper).stream().map(DeliveryNoteDO::getId).forEach(id -> {
            log.info("自动收货 发货单id：{}", id);
            DeliveryOrder deliveryOrder = DeliveryOrder.instanceOfDeliveryId(id);
            SaleOrder saleOrder = SaleOrder.instanceOf(deliveryOrder.getSaleOrderId());
            deliveryOrder.confirm().save();
            deliveryOrder.getPackages().forEach(deliveryPackage ->
                    saleOrder.updateReceivedQuantity(deliveryPackage.getSaleOrderItemId(), deliveryPackage.getQuantity())
            );
            saleOrder.save();
            SaleOrderFlow.newCustomerConfirmReceiveRecord(id, deliveryOrder.getDeliverySn());
            if (saleOrder.isAllReceived()) {
                SaleOrderFlow.newAllReceiveRecord(id);
            }
        });
        OperatorUtil.clear();

        return new ProcessResult(true);
    }
}