package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.controller.dto.MallSapDTO;
import org.geely.domain.common.data.MallBankAccountData;
import org.geely.domain.common.data.MallSapData;
import org.geely.infrastructure.db.MallSapDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.convert.SupportDomainDtoConvert;
import org.geely.infrastructure.db.service.MallSapDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Repository
public class MallSapRepository {
    @Resource
    private MallSapDbService service;
    public MallSapData getById(Integer id) {
        MallSapDO result = service.getById(id);
        Assert.notNull(result, "SAP类型不存在");
        return SupportDomainDataConvert.INSTANCE.convert(result);
    }

    public void delete(MallBankAccountData data) {
        Assert.notNull(data, "收款账户不存在");
        boolean result = service.removeById(data.getId());
        Assert.isTrue(result, "收款账户删除失败");
    }

    public List<MallSapDTO> list(Integer mallId) {
        LambdaQueryWrapper<MallSapDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MallSapDO::getMallId, mallId);
        return service.list(wrapper).stream().map(SupportDomainDtoConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
