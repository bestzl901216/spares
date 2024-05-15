package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.SaleOrderPayApproveData;
import org.geely.infrastructure.db.SaleOrderPayApproveDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.service.SaleOrderPayApproveDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Repository
public class SaleOrderPayApproveRepository {
    @Resource
    private SaleOrderPayApproveDbService service;

    public SaleOrderPayApproveData save(SaleOrderPayApproveData data) {
        SaleOrderPayApproveDO dbModel = SupportDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(dbModel);
        Assert.isTrue(result, "商家支付凭证保存失败");
        return SupportDomainDataConvert.INSTANCE.convert(dbModel);
    }

    public List<SaleOrderPayApproveData> listByBatchId(Integer batchId) {
        Assert.notNull(batchId);
        LambdaQueryWrapper<SaleOrderPayApproveDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SaleOrderPayApproveDO::getBatchId, batchId);
        List<SaleOrderPayApproveDO> list = service.list(wrapper);
        Assert.isFalse(list.isEmpty(), "无支付凭证记录");
        return list.stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
