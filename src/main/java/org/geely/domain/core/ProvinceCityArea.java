package org.geely.domain.core;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.domain.core.data.ProvinceCityAreaData;
import org.geely.infrastructure.db.repository.ProvinceCityAreaRepository;


/**
 * 租户
 *
 * @author cong huang
 */
public class ProvinceCityArea {
    private ProvinceCityAreaData data;

    private ProvinceCityArea(ProvinceCityAreaData mallData) {
        this.data = ObjectUtil.clone(mallData);
    }

    public static ProvinceCityArea instanceOf(String code) {
        ProvinceCityAreaData data = SpringUtil.getBean(ProvinceCityAreaRepository.class).getByCode(code);
        return new ProvinceCityArea(data);
    }
    public ProvinceCityAreaData getData() {
        return ObjectUtil.clone(this.data);
    }
}
