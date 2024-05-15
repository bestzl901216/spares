package org.geely.domain.support;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.domain.support.data.RoleData;
import org.geely.infrastructure.db.repository.SupportDomainRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 员工角色
 *
 * @author ricardo zhou
 */
@EqualsAndHashCode
public class Role {
    private RoleData data;

    private Role(RoleData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static Role newInstance(RoleData data) {
        Role role = new Role(data);
        role.data.setId(null);
        role.data.setVersion(0);
        role.save();
        return role;
    }

    public static Role instanceOf(Integer roleId) {
        RoleData data = SpringUtil.getBean(SupportDomainRepository.class).getRole(roleId);
        return new Role(data);
    }

    public static Set<Role> instanceOf(Set<Integer> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Sets.newHashSet();
        }
        Set<RoleData> roleDataSet = SpringUtil.getBean(SupportDomainRepository.class).getRole(roleIds);
        return roleDataSet.stream().map(Role::new).collect(Collectors.toSet());
    }

    public static Role instanceOf(String roleName, PlatformTypeEnum platformType, Integer platformId) {
        RoleData data = SpringUtil.getBean(SupportDomainRepository.class).getRole(roleName, platformType, platformId);
        return new Role(data);
    }

    public void save() {
        Assert.notBlank(this.data.getName(), "未设置角色名");
        Assert.notNull(this.data.getRemark(), "未设置备注信息");
        Assert.notNull(this.data.getPlatformType(), "未设置平台类型");
        Assert.notNull(this.data.getPlatformId(), "未设置平台id");
        this.data = SpringUtil.getBean(SupportDomainRepository.class).saveRole(this.data);
    }

    public RoleData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Integer getId() {
        return this.data.getId();
    }

    public PlatformTypeEnum getPlatformType() {
        return this.data.getPlatformType();
    }
    public Integer getPlatformId() {
        return this.data.getPlatformId();
    }


    public Boolean isBelong(PlatformTypeEnum platformType, Integer platformId) {
        return this.data.getPlatformType() == platformType && Objects.equals(this.data.getPlatformId(), platformId);
    }

    /**
     * 清除关联
     *
     * @return 角色
     */
    public Role clearRelation() {
        Set<AccountRoleRel> accountRoleRelSet = AccountRoleRel.instanceOfRoleId(this.data.getId());
        accountRoleRelSet.forEach(AccountRoleRel::delete);
        return this;
    }

    public Role updateName(String name) {
        this.data.setName(name);
        return this;
    }

    public Role updateRemark(String remark) {
        this.data.setRemark(remark);
        return this;
    }

    public Role updatePermissions(List<String> permissions) {
        this.data.setPermissions(permissions);
        return this;
    }

    public Role delete() {
        this.clearRelation();
        SpringUtil.getBean(SupportDomainRepository.class).deleteRole(this.data.getId());
        return this;
    }
}
