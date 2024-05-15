package org.geely.domain.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.controller.dto.CustomerAddressCreateDTO;
import org.geely.controller.dto.CustomerAddressDTO;
import org.geely.domain.core.data.CustomerAddressData;
import org.geely.infrastructure.db.convert.CoreDomainDtoConvert;
import org.geely.infrastructure.db.repository.CustomerAddressRepository;

/**
 * @author ricardo zhou
 */
public class CustomerAddress {
    private CustomerAddressData data;

    private CustomerAddress(CustomerAddressData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static CustomerAddress newInstance(CustomerAddressCreateDTO dto) {
        CustomerAddressData customerAddressData = new CustomerAddressData();
        BeanUtil.copyProperties(dto, customerAddressData);
        CustomerAddress customerAddress = new CustomerAddress(customerAddressData);
        customerAddress.data.setVersion(0);
        customerAddress.save();
        return customerAddress;
    }

    public void save() {
        this.data = SpringUtil.getBean(CustomerAddressRepository.class).save(this.data);
    }

    public CustomerAddressData getData() {
        return ObjectUtil.clone(this.data);
    }

    public static CustomerAddress instanceOf(Integer id, Integer customerId) {
        CustomerAddressData data = SpringUtil.getBean(CustomerAddressRepository.class).getById(id);
        Assert.isTrue(customerId.equals(data.getCustomerId()), "无效的收货地址id");
        return new CustomerAddress(data);
    }

    public CustomerAddressDTO getDTO() {
        return CoreDomainDtoConvert.INSTANCE.convert(data);
    }

    public void delete() {
        SpringUtil.getBean(CustomerAddressRepository.class).delete(this.data.getId());
        data = null;
    }
}
