package org.geely.domain.support;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.EnableStateEnum;
import org.geely.domain.support.data.AccountRoleRelData;
import org.geely.infrastructure.db.repository.SupportDomainRepository;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 账号角色关系
 *
 * @author ricardo zhou
 */
@EqualsAndHashCode
public class AccountRoleRel {
    private AccountRoleRelData data;

    private AccountRoleRel(AccountRoleRelData data) {
        this.data = ObjectUtil.clone(data);
    }

    /**
     * 创建账号角色关系
     *
     * @param data 账号角色关系数据
     * @return 账号角色关系
     */
    public static AccountRoleRel newInstance(AccountRoleRelData data) {
        AccountRoleRel accountRoleRel = new AccountRoleRel(data);
        accountRoleRel.data.setId(null);
        accountRoleRel.data.setVersion(0);
        accountRoleRel.save();
        return accountRoleRel;
    }

    /**
     * 获取账号的全部关联关系
     *
     * @param accountId 账号id
     * @return 账号角色关系
     */
    public static Set<AccountRoleRel> instanceOfAccountId(Integer accountId) {
        Set<AccountRoleRelData> accountRoleRelDataSet = SpringUtil.getBean(SupportDomainRepository.class).getAccountRoleRelByAccountId(accountId);
        return accountRoleRelDataSet.stream().map(AccountRoleRel::new).collect(Collectors.toSet());
    }

    /**
     * 获取角色的全部关联关系
     *
     * @param roleId 角色id
     */
    public static Set<AccountRoleRel> instanceOfRoleId(Integer roleId) {
        Set<AccountRoleRelData> accountRoleRelDataSet = SpringUtil.getBean(SupportDomainRepository.class).getAccountRoleRelByRoleId(roleId);
        return accountRoleRelDataSet.stream().map(AccountRoleRel::new).collect(Collectors.toSet());
    }

    /**
     * 获取账号角色关系
     *
     * @param accountId 账号id
     * @param roleId    角色id
     * @return 账号角色关系
     */
    public static AccountRoleRel instanceOf(Integer accountId, Integer roleId) {
        AccountRoleRelData accountRoleRelData = SpringUtil.getBean(SupportDomainRepository.class).getAccountRoleRel(accountId, roleId);
        return new AccountRoleRel(accountRoleRelData);
    }

    /**
     * 保存账号角色关系
     */
    public void save() {
        Assert.notNull(this.data.getAccountId(), "账号id不能为空");
        Assert.notNull(this.data.getRoleId(), "角色id不能为空");
        Assert.notNull(this.data.getState(), "关系状态不能为空");
        this.data = SpringUtil.getBean(SupportDomainRepository.class).saveAccountRoleRel(this.data);
    }

    /**
     * 删除账号角色关系
     */
    public void delete() {
        SpringUtil.getBean(SupportDomainRepository.class).deleteAccountRoleRel(this.data.getId());
        this.data = null;
    }

    /**
     * 获取角色id
     *
     * @return 角色id
     */
    public Integer getRoleId() {
        return this.data.getRoleId();
    }

    /**
     * 启用、禁用该关系
     */
    public AccountRoleRel enableOrDisable() {
        EnableStateEnum state = this.data.getState() == EnableStateEnum.ENABLE ? EnableStateEnum.DISABLE : EnableStateEnum.ENABLE;
        this.data.setState(state);
        return this;
    }

}
