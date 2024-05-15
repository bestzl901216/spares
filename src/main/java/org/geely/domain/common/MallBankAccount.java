package org.geely.domain.common;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.geely.controller.dto.MallBankAccountDTO;
import org.geely.controller.dto.MallBankAccountPageDTO;
import org.geely.domain.common.data.MallBankAccountData;
import org.geely.infrastructure.db.repository.MallBankAccountRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 银行账户
 *
 * @author cong huang
 */
public class MallBankAccount {

    private MallBankAccountData data;

    private MallBankAccount(MallBankAccountData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static MallBankAccount newInstance(MallBankAccountData data) {
        MallBankAccount instance = new MallBankAccount(data);
        instance.data.setId(null);
        instance.data.setVersion(0);
        instance.save();
        return instance;
    }

    public static MallBankAccount instanceOf(Integer id) {
        MallBankAccountData data = SpringUtil.getBean(MallBankAccountRepository.class).getById(id);
        return new MallBankAccount(data);
    }

    public static Page<MallBankAccountDTO> page(MallBankAccountPageDTO pageDTO) {
        return SpringUtil.getBean(MallBankAccountRepository.class).page(pageDTO);
    }

    public static List<MallBankAccount> instancesOf(Set<Integer> ids) {
        return SpringUtil.getBean(MallBankAccountRepository.class).listByIds(ids).stream().map(MallBankAccount::new).collect(Collectors.toList());
    }

    public void save() {
        this.data = SpringUtil.getBean(MallBankAccountRepository.class).save(this.data);
    }

    public void delete() {
        SpringUtil.getBean(MallBankAccountRepository.class).delete(this.data);
        data = null;
    }

    public MallBankAccountData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Integer getId() {
        return this.data.getId();
    }

    public Integer getMallId() {
        return data.getMallId();
    }
}
