package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.geely.controller.dto.MallBankAccountDTO;
import org.geely.controller.dto.MallBankAccountPageDTO;
import org.geely.domain.common.data.MallBankAccountData;
import org.geely.infrastructure.db.MallBankAccountDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.convert.SupportDomainDtoConvert;
import org.geely.infrastructure.db.service.MallBankAccountDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Repository
public class MallBankAccountRepository {
    @Resource
    private MallBankAccountDbService service;

    public MallBankAccountData save(MallBankAccountData data) {
        MallBankAccountDO doModel = SupportDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(doModel);
        Assert.isTrue(result, "收款账户保存失败");
        return SupportDomainDataConvert.INSTANCE.convert(doModel);
    }

    public MallBankAccountData getById(Integer id) {
        MallBankAccountDO result = service.getById(id);
        Assert.notNull(result, "收款账户不存在");
        return SupportDomainDataConvert.INSTANCE.convert(result);
    }

    public void delete(MallBankAccountData data) {
        Assert.notNull(data, "收款账户不存在");
        boolean result = service.removeById(data.getId());
        Assert.isTrue(result, "收款账户删除失败");
    }

    public Page<MallBankAccountDTO> page(MallBankAccountPageDTO pageDTO) {
        LambdaQueryWrapper<MallBankAccountDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(MallBankAccountDO::getId);
        wrapper.eq(MallBankAccountDO::getMallId, pageDTO.getMallId());
        if (pageDTO.getName() != null && !pageDTO.getName().isEmpty()) {
            wrapper.like(MallBankAccountDO::getName, pageDTO.getName());
        }
        if (pageDTO.getBank() != null && !pageDTO.getBank().isEmpty()) {
            wrapper.like(MallBankAccountDO::getBank, pageDTO.getBank());
        }
        if (pageDTO.getBankAccount() != null && !pageDTO.getBankAccount().isEmpty()) {
            wrapper.like(MallBankAccountDO::getBankAccount, pageDTO.getBankAccount());
        }
        Page<MallBankAccountDO> page = service.page(new Page<>(pageDTO.getCurrent(), pageDTO.getSize()), wrapper);
        Page<MallBankAccountDTO> result = PageDTO.of(page.getCurrent(), page.getSize(), page.getTotal());
        result.setRecords(page.getRecords().stream().map(SupportDomainDtoConvert.INSTANCE::convert).collect(Collectors.toList()));
        return result;
    }

    public List<MallBankAccountData> listByIds(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<MallBankAccountDO> dos = service.listByIds(ids);
        Assert.isTrue(dos.size() == ids.size(), "包含不存在的收款账户id");
        return dos.stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
