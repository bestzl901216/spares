package org.geely.domain.core;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.domain.core.data.HomePageSkuData;
import org.geely.infrastructure.db.repository.HomePageSkuRepository;


/**
 * 账户
 *
 * @author ricardo zhou
 */
public class HomePageSku {
    private HomePageSkuData data;

    private HomePageSku(HomePageSkuData data) {
        this.data = ObjectUtil.clone(data);
    }

    /**
     * 创建账户
     *
     * @param data 账户数据
     * @return 账户
     */
    public static HomePageSku newInstance(HomePageSkuData data) {
        HomePageSku account = new HomePageSku(data);
        account.data = SpringUtil.getBean(HomePageSkuRepository.class).save(data);
        return account;
    }
    public Integer getId() {
        return this.data.getId();
    }
}
