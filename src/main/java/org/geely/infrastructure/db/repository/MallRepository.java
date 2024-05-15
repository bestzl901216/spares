package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import org.geely.domain.core.data.MallData;
import org.geely.infrastructure.db.MallDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.service.MallDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Repository
public class MallRepository {
    @Resource
    private MallDbService mallDbService;

    public MallData save(MallData mallData) {
        MallDO mallDo = SupportDomainDataConvert.INSTANCE.convert(mallData);
        boolean result = mallDbService.saveOrUpdate(mallDo);
        Assert.isTrue(result, "租户数据保存失败");
        return SupportDomainDataConvert.INSTANCE.convert(mallDo);
    }

    public MallData getById(Integer id) {
        MallDO result = mallDbService.getById(id);
        Assert.notNull(result, "租户不存在");
        return SupportDomainDataConvert.INSTANCE.convert(result);
    }

    public List<MallData> listByIds(Set<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Lists.newArrayList();
        }
        List<MallDO> result = mallDbService.listByIds(ids);
        return result.stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
