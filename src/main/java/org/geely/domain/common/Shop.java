package org.geely.domain.common;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.common.enums.EnableStateEnum;
import org.geely.domain.core.data.ShopData;
import org.geely.infrastructure.db.repository.ShopRepository;


/**
 * 账户
 *
 * @author ricardo zhou
 */
public class Shop {
    private ShopData data;

    private Shop(ShopData data) {
        this.data = ObjectUtil.clone(data);
    }

    /**
     * 获取账户
     *
     * @param id 账户ID
     * @return 账户
     */
    public static Shop instanceOfId(Integer id) {
        ShopData shopData = SpringUtil.getBean(ShopRepository.class).getById(id);
        return new Shop(shopData);
    }

    public Integer getShopId() {
        return data.getId();
    }

    public Integer getMallId() {
        return data.getMallId();
    }

    public Integer getSupplierId() {
        return data.getSupplierId();
    }

    public EnableStateEnum getState() {
        return data.getState();
    }

    public String getName() {
        return data.getName();
    }
}
