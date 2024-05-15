package org.geely.domain.core;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.common.enums.OrderPayApproveStateEnum;
import org.geely.common.enums.SnPrefixEnum;
import org.geely.domain.common.utils.SnGenerator;
import org.geely.domain.core.data.SaleOrderPayApproveData;
import org.geely.infrastructure.db.repository.SaleOrderPayApproveRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
public class SaleOrderPayApprove {
    private SaleOrderPayApproveData data;

    private SaleOrderPayApprove(SaleOrderPayApproveData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static SaleOrderPayApprove newInstance(SaleOrderPayApproveData data) {
        SaleOrderPayApprove instance = new SaleOrderPayApprove(data);
        instance.data.setId(null);
        instance.data.setVersion(0);
        instance.save();
        return instance;
    }

    public static List<SaleOrderPayApprove> instancesOf(Integer batchId) {
        return SpringUtil.getBean(SaleOrderPayApproveRepository.class).listByBatchId(batchId).stream().map(SaleOrderPayApprove::new).collect(Collectors.toList());
    }
    public void save() {
        this.data = SpringUtil.getBean(SaleOrderPayApproveRepository.class).save(this.data);
    }

    public SaleOrderPayApproveData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Integer getSupplierId() {
        return this.data.getSupplierId();
    }

    public OrderPayApproveStateEnum getState() {
        return this.data.getState();
    }

    public SaleOrderPayApprove success(String remark) {
        Assert.isTrue(data.getState().equals(OrderPayApproveStateEnum.PENDING_APPROVAL), "支付凭证状态不正确");
        data.setState(OrderPayApproveStateEnum.SUCCESS);
        data.setRemark(remark);
        data.setApproveTime(LocalDateTime.now());
        data.setPaySn(SnGenerator.get(SnPrefixEnum.XX.toString()));
        return this;
    }
    public SaleOrderPayApprove fail(String remark) {
        Assert.isTrue(data.getState().equals(OrderPayApproveStateEnum.PENDING_APPROVAL), "支付凭证状态不正确");
        data.setState(OrderPayApproveStateEnum.FAIL);
        data.setRemark(remark);
        data.setApproveTime(LocalDateTime.now());
        return this;
    }
}
