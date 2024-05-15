package org.geely.domain.core;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.common.enums.EnableStateEnum;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.domain.core.data.ShopData;
import org.geely.domain.support.Role;
import org.geely.domain.support.data.RoleData;
import org.geely.infrastructure.db.repository.ShopRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 店铺
 *
 * @author cong huang
 */
public class Shop {

    public static final String ADMIN_ROLE_NAME = "管理员";
    private ShopData data;

    private Shop(ShopData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static Shop newInstance(ShopData data) {
        Shop instance = new Shop(data);
        instance.data.setId(null);
        instance.data.setVersion(0);
        instance.save();
        instance.createAdminRole();
        return instance;
    }

    public static Shop instanceOf(Integer id) {
        ShopData data = SpringUtil.getBean(ShopRepository.class).getById(id);
        return new Shop(data);
    }

    public static List<Shop> instancesOf(Set<Integer> shopIds) {
        return SpringUtil.getBean(ShopRepository.class).listByIds(shopIds).stream().map(Shop::new).collect(Collectors.toList());
    }

    public void save() {
        this.data = SpringUtil.getBean(ShopRepository.class).save(this.data);
    }

    public ShopData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Role createAdminRole() {
        RoleData roleData = new RoleData();
        roleData.setName("管理员");
        roleData.setRemark("拥有该客户最高权限");
        roleData.setPlatformType(PlatformTypeEnum.SHOP);
        roleData.setPlatformId(this.data.getId());
        roleData.setVersion(0);
        return Role.newInstance(roleData);
    }
    public Integer getSupplierId(){
        return this.data.getSupplierId();
    }

    public Integer getId() {
        return this.data.getId();
    }
    public String getName() {
        return this.data.getName();
    }
    public EnableStateEnum getState() {
        return this.data.getState();
    }
}
