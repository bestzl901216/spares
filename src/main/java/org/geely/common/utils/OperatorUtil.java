package org.geely.common.utils;

import cn.hutool.core.lang.Assert;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.geely.common.enums.ResponseEnum;
import org.geely.common.exception.BizException;
import org.geely.domain.common.data.AccountData;
import org.geely.domain.core.data.MallData;
import org.geely.domain.support.data.RoleData;

import java.util.Optional;

/**
 * @author ricardo zhou
 */
@Slf4j
@UtilityClass
public class OperatorUtil {
    private static final ThreadLocal<AccountData> THREAD_LOCAL_ACCOUNT_DATA = new ThreadLocal<>();
    private static final ThreadLocal<RoleData> THREAD_LOCAL_ROLE_DATA = new ThreadLocal<>();
    private static final ThreadLocal<MallData> THREAD_LOCAL_MALL_DATA = new ThreadLocal<>();


    /**
     * 设置accountData
     *
     * @param accountData accountData
     */
    public void setAccountData(AccountData accountData) {
        Assert.notNull(accountData, "不正确的用户");
        THREAD_LOCAL_ACCOUNT_DATA.set(accountData);
    }

    /**
     * 设置roleData
     *
     * @param roleData roleData
     */
    public void setRoleData(RoleData roleData) {
        Assert.notNull(roleData, "不正确的角色");
        THREAD_LOCAL_ROLE_DATA.set(roleData);
    }

    /**
     * 设置mallData
     *
     * @param mallData mallData
     */
    public void setMallData(MallData mallData) {
        Assert.notNull(mallData, "不正确的商城");
        THREAD_LOCAL_MALL_DATA.set(mallData);
    }

    /**
     * 清理线程本地数据
     */
    public void clear() {
        THREAD_LOCAL_ACCOUNT_DATA.remove();
        THREAD_LOCAL_ROLE_DATA.remove();
        THREAD_LOCAL_MALL_DATA.remove();
    }

    /**
     * 获取操作人
     *
     * @return 当前操作人
     */
    public String getOperator() {
        String accountName = Optional.ofNullable(THREAD_LOCAL_ACCOUNT_DATA.get()).map(AccountData::getName).orElse("");
        if (StringUtils.isBlank(accountName)) {
            accountName = "anonymousUser";
            log.warn("请注意匿名操作人！！！");
        }
        return accountName;
    }

    /**
     * 获取操作人账号id
     */
    public Integer getAccountId() {
        return Optional.ofNullable(THREAD_LOCAL_ACCOUNT_DATA.get()).map(AccountData::getId)
                .orElseThrow(() -> new BizException(ResponseEnum.USER_NOT_LOGIN.getCode(), ResponseEnum.USER_NOT_LOGIN.getMessage()));
    }

    public String getAccountPhone() {
        return Optional.ofNullable(THREAD_LOCAL_ACCOUNT_DATA.get()).map(AccountData::getPhone)
                .orElseThrow(() -> new BizException(ResponseEnum.USER_NOT_LOGIN.getCode(), ResponseEnum.USER_NOT_LOGIN.getMessage()));
    }

    /**
     * 获取操作人当前角色
     */
    public RoleData getRoleData() {
        return Optional.ofNullable(THREAD_LOCAL_ROLE_DATA.get()).orElseThrow(() -> new BizException("platform role error", "角色异常"));
    }

    public Integer getPlatformId() {
        return getRoleData().getPlatformId();
    }

    /**
     * 商城端获取当前商城id
     */
    public Integer getMallId() {
        return Optional.ofNullable(THREAD_LOCAL_MALL_DATA.get()).map(MallData::getId)
                .orElseThrow(() -> new BizException(ResponseEnum.USER_NOT_LOGIN.getCode(), ResponseEnum.USER_NOT_LOGIN.getMessage()));
    }

    public void actAsSystem() {
        AccountData accountData = new AccountData();
        accountData.setId(0);
        accountData.setName("系统");
        setAccountData(accountData);
        RoleData roleData = new RoleData();
        roleData.setPlatformId(0);
        roleData.setName("系统");
        setRoleData(roleData);
    }
}
