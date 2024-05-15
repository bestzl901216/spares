package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.geely.domain.core.data.CustomerAddressData;
import org.geely.infrastructure.db.CustomerAddressDO;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.CustomerAddressDbService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author cong huang
 */
@Repository
public class CustomerAddressRepository {
    @Resource
    private CustomerAddressDbService service;

    @Transactional(rollbackFor = Exception.class)
    public CustomerAddressData save(CustomerAddressData data) {
        if (data.getId() != null && data.getId() > 0) {
            CustomerAddressDO existAddress = service.getById(data.getId());
            Assert.notNull(existAddress, "收货地址不存在");
            data.setVersion(existAddress.getVersion());
        }
        CustomerAddressDO newDO = CoreDomainDataConvert.INSTANCE.convert(data);
        newDO.setAddressFull(newDO.getProvince() + newDO.getCity() + newDO.getArea() + newDO.getAddress());
        if (Boolean.TRUE.equals(data.getIsDefault())) {
            clearDefault(newDO.getCustomerId(), newDO.getMallId());
        }
        try {
            boolean result = service.saveOrUpdate(newDO);
            Assert.isTrue(result, "收货地址保存失败");
        } catch (DuplicateKeyException e) {
            Assert.isTrue(false, "收货地址重复，请重新填写");
        }
        return CoreDomainDataConvert.INSTANCE.convert(newDO);
    }

    public void clearDefault(Integer customerId, Integer mallId) {
        LambdaUpdateWrapper<CustomerAddressDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(CustomerAddressDO::getIsDefault, 0);
        wrapper.eq(CustomerAddressDO::getCustomerId, customerId);
        wrapper.eq(CustomerAddressDO::getMallId, mallId);
        service.update(wrapper);
    }

    public CustomerAddressData getById(Integer id) {
        CustomerAddressDO result = service.getById(id);
        Assert.notNull(result, "收货地址不存在");
        return CoreDomainDataConvert.INSTANCE.convert(result);
    }

    public void delete(Integer id) {
        Assert.notNull(id, "收货地址id不能为空");
        boolean result = service.removeById(id);
        Assert.isTrue(result, "收货地址删除失败");
    }
}
