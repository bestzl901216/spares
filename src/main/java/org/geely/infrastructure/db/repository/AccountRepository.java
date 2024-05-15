package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.common.data.AccountData;
import org.geely.infrastructure.db.AccountDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.service.AccountDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author ricardo zhou
 */
@Repository
public class AccountRepository {
    @Resource
    private AccountDbService accountDbService;

    public AccountData save(AccountData accountData) {
        AccountDO accountDO = SupportDomainDataConvert.INSTANCE.convert(accountData);
        boolean result = accountDbService.saveOrUpdate(accountDO);
        Assert.isTrue(result, "数据保存失败");
        return SupportDomainDataConvert.INSTANCE.convert(accountDO);
    }

    public AccountData getById(Integer id) {
        Assert.notNull(id, "账号id不能为空");
        AccountDO accountDO = accountDbService.getById(id);
        Assert.notNull(accountDO, "账号信息不存在");
        return SupportDomainDataConvert.INSTANCE.convert(accountDO);
    }

    public AccountData getByPhone(String phone, boolean nullable) {
        Assert.isTrue(CharSequenceUtil.isNotBlank(phone), "手机号不能为空");
        LambdaQueryWrapper<AccountDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountDO::getPhone, phone);
        AccountDO accountDO = accountDbService.getOne(wrapper);
        if(!nullable) {
            Assert.notNull(accountDO, "账号信息不存在");
        }
        return SupportDomainDataConvert.INSTANCE.convert(accountDO);
    }

}
