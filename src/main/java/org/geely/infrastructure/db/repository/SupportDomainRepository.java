package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.common.exception.BizException;
import org.geely.domain.support.data.AccountRoleRelData;
import org.geely.domain.support.data.RoleData;
import org.geely.domain.support.data.SaleOrderFlowData;
import org.geely.infrastructure.db.AccountRoleRelDO;
import org.geely.infrastructure.db.RoleDO;
import org.geely.infrastructure.db.SaleOrderFlowDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.service.AccountRoleRelDbService;
import org.geely.infrastructure.db.service.RoleDbService;
import org.geely.infrastructure.db.service.SaleOrderFlowDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ricardo zhou
 */
@Repository
public class SupportDomainRepository {

    @Resource
    private RoleDbService roleDbService;
    @Resource
    private AccountRoleRelDbService accountRoleRelDbService;
    @Resource
    private SaleOrderFlowDbService saleOrderFlowDbService;

    public RoleData saveRole(RoleData roleData) {
        RoleDO roleDO = SupportDomainDataConvert.INSTANCE.convert(roleData);
        try {
            boolean result = roleDbService.saveOrUpdate(roleDO);
            Assert.isTrue(result, "数据保存失败");
        } catch (Exception e) {
            throw new BizException("role name error", "角色名已存在");
        }
        return SupportDomainDataConvert.INSTANCE.convert(roleDO);
    }

    public RoleData getRole(Integer roleId) {
        RoleDO roleDO = roleDbService.getById(roleId);
        Assert.notNull(roleDO, "不存在该角色");
        return SupportDomainDataConvert.INSTANCE.convert(roleDO);
    }

    public Set<RoleData> getRole(Set<Integer> roleIds) {
        List<RoleDO> list = roleDbService.listByIds(roleIds);
        Assert.isTrue(roleIds.size() == list.size(), "角色数据异常");
        return list.stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toSet());
    }

    public RoleData getRole(String roleName, PlatformTypeEnum platformType, Integer platformId) {
        LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleDO::getPlatformType, platformType.getId());
        queryWrapper.eq(RoleDO::getPlatformId, platformId);
        queryWrapper.eq(RoleDO::getName, roleName);
        RoleDO roleDO = roleDbService.getOne(queryWrapper);
        Assert.notNull(roleDO, "不存在该角色");
        return SupportDomainDataConvert.INSTANCE.convert(roleDO);
    }

    public void deleteRole(Integer id) {
        boolean result = roleDbService.removeById(id);
        Assert.isTrue(result, "数据删除失败");
    }

    public AccountRoleRelData saveAccountRoleRel(AccountRoleRelData accountRoleRelData) {
        AccountRoleRelDO accountRoleRelDO = SupportDomainDataConvert.INSTANCE.convert(accountRoleRelData);
        boolean result = accountRoleRelDbService.saveOrUpdate(accountRoleRelDO);
        Assert.isTrue(result, "数据保存失败");
        return SupportDomainDataConvert.INSTANCE.convert(accountRoleRelDO);
    }

    public void deleteAccountRoleRel(Integer id) {
        Assert.notNull(id, "账号角色关系id不能为空");
        boolean result = accountRoleRelDbService.removeById(id);
        Assert.isTrue(result, "数据删除失败");
    }

    public Set<AccountRoleRelData> getAccountRoleRelByAccountId(Integer accountId) {
        Assert.notNull(accountId, "账号id不能为空");
        LambdaQueryWrapper<AccountRoleRelDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AccountRoleRelDO::getAccountId, accountId);
        return accountRoleRelDbService.list(queryWrapper).stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toSet());
    }

    public Set<AccountRoleRelData> getAccountRoleRelByRoleId(Integer roleId) {
        Assert.notNull(roleId, "角色id不能为空");
        LambdaQueryWrapper<AccountRoleRelDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AccountRoleRelDO::getRoleId, roleId);
        return accountRoleRelDbService.list(queryWrapper).stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toSet());
    }

    public AccountRoleRelData getAccountRoleRel(Integer accountId, Integer roleId) {
        Assert.notNull(accountId, "账号id不能为空");
        Assert.notNull(roleId, "角色id不能为空");
        LambdaQueryWrapper<AccountRoleRelDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AccountRoleRelDO::getAccountId, accountId);
        queryWrapper.eq(AccountRoleRelDO::getRoleId, roleId);
        AccountRoleRelDO accountRoleRelDO = accountRoleRelDbService.getOne(queryWrapper);
        Assert.notNull(accountRoleRelDO, "不存在该账号与角色关系");
        return SupportDomainDataConvert.INSTANCE.convert(accountRoleRelDO);
    }

    public SaleOrderFlowData saveSaleOrderFlow(SaleOrderFlowData saleOrderFlowData) {
        SaleOrderFlowDO saleOrderFlowDO = SupportDomainDataConvert.INSTANCE.convert(saleOrderFlowData);
        boolean result = saleOrderFlowDbService.saveOrUpdate(saleOrderFlowDO);
        Assert.isTrue(result, "订单追踪数据保存失败");
        return SupportDomainDataConvert.INSTANCE.convert(saleOrderFlowDO);
    }
}
