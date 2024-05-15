package org.geely.job;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.enums.OrderStateEnum;
import org.geely.common.utils.OperatorUtil;
import org.geely.domain.core.SaleOrder;
import org.geely.domain.core.Sku;
import org.geely.domain.support.SaleOrderFlow;
import org.geely.infrastructure.db.SaleOrderDO;
import org.geely.infrastructure.db.service.SaleOrderDbService;
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
public class SaleOrderAutoCancelJob extends JavaProcessor {

    @Resource
    private SaleOrderDbService saleOrderDbService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult process(JobContext context) {
        // 获取订单状态为待支付、支付截止时间小于当前时间的订单
        LambdaQueryWrapper<SaleOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SaleOrderDO::getId).eq(SaleOrderDO::getState, OrderStateEnum.TO_BE_PAID)
                .lt(SaleOrderDO::getPayDeadline, LocalDateTime.now());
        OperatorUtil.actAsSystem();
        saleOrderDbService.list(queryWrapper).stream().map(SaleOrderDO::getId).forEach(id -> {
            log.info("自动取消订单：{}", id);
            SaleOrder order = SaleOrder.instanceOf(id).cancel("支付超时，订单自动取消");
            order.save();
            SaleOrderFlow.newSellerCancelRecord(id);
            Sku.releaseStock(order.getSkuQtyMap());
        });
        OperatorUtil.clear();

        return new ProcessResult(true);
    }
}