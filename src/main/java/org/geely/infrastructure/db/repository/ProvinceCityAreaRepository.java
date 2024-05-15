package org.geely.infrastructure.db.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.ProvinceCityAreaData;
import org.geely.infrastructure.db.ProvinceCityAreaDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.service.ProvinceCityAreaDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author cong huang
 */
@Repository
public class ProvinceCityAreaRepository {
    @Resource
    private ProvinceCityAreaDbService provinceCityAreaDbService;

    public ProvinceCityAreaData getByCode(String code) {
        LambdaQueryWrapper<ProvinceCityAreaDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProvinceCityAreaDO::getCode, code);
        ProvinceCityAreaDO result = provinceCityAreaDbService.getOne(wrapper);
        if(result==null)
            return null;
        return SupportDomainDataConvert.INSTANCE.convert(result);
    }
}
