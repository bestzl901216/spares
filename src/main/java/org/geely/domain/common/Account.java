package org.geely.domain.common;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.*;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.common.enums.EnableStateEnum;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.domain.common.data.AccountData;
import org.geely.domain.support.AccountRoleRel;
import org.geely.domain.support.Role;
import org.geely.domain.support.data.AccountRoleRelData;
import org.geely.infrastructure.db.repository.AccountRepository;

import javax.crypto.SecretKey;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 账户
 *
 * @author ricardo zhou
 */
public class Account {
    private AccountData data;

    private Account(AccountData data) {
        this.data = ObjectUtil.clone(data);
    }

    /**
     * 创建账户
     *
     * @param data 账户数据
     * @return 账户
     */
    public static Account newInstance(AccountData data) {
        Assert.notBlank(data.getPassword(), "账户密码异常");
        Account account = new Account(data);
        account.data.setId(null);
        account.data.setVersion(0);
        account.save();
        return account;
    }

    /**
     * 根据手机号创建账号或者获取账号
     *
     * @param phone phone
     * @return 账号
     */
    public static Account newInstanceOrGetBy(String phone) {
        try {
            return instanceOfPhone(phone);
        } catch (Exception e) {
            AccountData data = new AccountData();
            data.setName(phone);
            data.setPhone(phone);
            // 随机8位数字
            data.setPassword(String.valueOf(RandomUtil.randomInt(1000000, 99999999)));
            data.setVersion(0);
            return newInstance(data);
        }
    }

    /**
     * 获取账户
     *
     * @param id 账户ID
     * @return 账户
     */
    public static Account instanceOfId(Integer id) {
        AccountData accountData = SpringUtil.getBean(AccountRepository.class).getById(id);
        return new Account(accountData);
    }

    /**
     * 获取账户
     *
     * @param phone 手机号码
     * @return 账户
     */
    public static Account instanceOfPhone(String phone) {
        AccountData accountData = SpringUtil.getBean(AccountRepository.class).getByPhone(phone, false);
        return new Account(accountData);
    }

    public static Optional<Account> instanceOfPhoneOptional(String phone) {
        AccountData accountData = SpringUtil.getBean(AccountRepository.class).getByPhone(phone, true);
        if(accountData == null) return Optional.empty();
        return Optional.of(new Account(accountData));
    }

    /**
     * 保存账户
     */
    public void save() {
        Assert.isTrue(ReUtil.isMatch("^[\\u4e00-\\u9fa5\\w]{1,30}$", this.data.getName()), "账户名由30个以内的汉字、英文字母、数字和下划线组成");
        Assert.isTrue(PhoneUtil.isPhone(this.data.getPhone()), "手机号码异常");
        Assert.isTrue(CharSequenceUtil.isBlank(this.data.getPassword()) || ReUtil.isMatch("^[a-zA-Z0-9~!@#$%^&*_]{8,16}$", this.data.getPassword()), "密码由8-16个英文字母、数字和特殊字符组成");
        if (CharSequenceUtil.isNotBlank(this.data.getPassword())) {
            this.data.setEncodedPassword(encodedPassword(this.data.getPassword()));
        }
        this.data = SpringUtil.getBean(AccountRepository.class).save(this.data);
    }

    public AccountData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Integer getId() {
        return this.data.getId();
    }

    /**
     * 更新账户密码
     *
     * @param password 密码
     * @return 账户
     */
    public Account updatePassword(String password) {
        this.data.setPassword(password);
        return this;
    }

    /**
     * 更新账号名称
     */
    public Account updateName(String name) {
        this.data.setName(name);
        return this;
    }

    /**
     * 验证密码
     *
     * @param password 密码
     * @return 密码是否正确
     */
    public boolean verifyPassword(String password) {
        return this.data.getEncodedPassword().equals(encodedPassword(decryptPassword(password)));
    }

    /**
     * 解密被前端加密的密码
     * @param password 被前端加密的密码
     * @return 明文密码
     */
    public String decryptPassword(String password) {
        try {
            String key = "gdbploginkey1234";
            SecretKey secretKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), key.getBytes());
            AES aes = SecureUtil.aes(secretKey.getEncoded());
            return aes.decryptStr(password, CharsetUtil.CHARSET_UTF_8);
        }catch (Exception ex){
            throw new IllegalArgumentException("密码错误");
        }
    }

    /**
     * 获取指定平台关联的角色
     *
     * @param platformType 平台类型
     * @param platformId   平台id
     * @return 关联角色
     */
    public Optional<Role> roleOf(PlatformTypeEnum platformType, Integer platformId) {
        Set<AccountRoleRel> accountRoleRelSet = AccountRoleRel.instanceOfAccountId(this.data.getId());
        Set<Integer> roleIds = accountRoleRelSet.stream().map(AccountRoleRel::getRoleId).collect(Collectors.toSet());
        Set<Role> roles = Role.instanceOf(roleIds);
        return roles.stream().filter(e -> e.isBelong(platformType, platformId)).findFirst();
    }

    /**
     * 获取账号关联的全部角色
     *
     * @return 获取账号关联的角色
     */
    public Set<Role> roles() {
        Set<AccountRoleRel> accountRoleRelSet = AccountRoleRel.instanceOfAccountId(this.data.getId());
        Set<Integer> roleIds = accountRoleRelSet.stream().map(AccountRoleRel::getRoleId).collect(Collectors.toSet());
        return Role.instanceOf(roleIds);
    }

    /**
     * 获取当前账号关联的客户角色
     *
     * @return 客户角色
     */
    public Optional<Role> customerRole() {
        Set<AccountRoleRel> accountRoleRelSet = AccountRoleRel.instanceOfAccountId(this.data.getId());
        Set<Integer> roleIds = accountRoleRelSet.stream().map(AccountRoleRel::getRoleId).collect(Collectors.toSet());
        Set<Role> roles = Role.instanceOf(roleIds);
        return roles.stream().filter(e -> e.getPlatformType() == PlatformTypeEnum.CUSTOMER).findFirst();
    }

    /**
     * 加密密码
     *
     * @param password 密码
     * @return 加密后的密码
     */
    private static String encodedPassword(String password) {
        return SecureUtil.sha256("QWE_ASD_ZXC" + password);
    }

    /**
     * 账号关联角色
     *
     * @param role 账号
     */
    public Account associate(Role role) {
        AccountRoleRelData accountRoleRelData = new AccountRoleRelData();
        accountRoleRelData.setAccountId(this.data.getId());
        accountRoleRelData.setRoleId(role.getId());
        accountRoleRelData.setState(EnableStateEnum.ENABLE);
        accountRoleRelData.setVersion(0);
        AccountRoleRel.newInstance(accountRoleRelData);
        return this;
    }

    /**
     * 账号解除关联角色
     *
     * @param role 角色
     * @return 账号
     */
    public Account disassociate(Role role) {
        AccountRoleRel accountRoleRel = AccountRoleRel.instanceOf(this.data.getId(), role.getId());
        accountRoleRel.delete();
        return this;
    }

    /**
     * 启用或禁用账号下的该角色关系
     */
    public Account enableOrDisableRole(Role role) {
        AccountRoleRel accountRoleRel = AccountRoleRel.instanceOf(this.data.getId(), role.getId());
        accountRoleRel.enableOrDisable().save();
        return this;
    }

    public String getPhone() {
        return this.data.getPhone();
    }

    public String getName() {
        return this.data.getName();
    }

    public EnableStateEnum getState() {
        return data.getState();
    }
}
