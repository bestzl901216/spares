package org.geely.domain.core;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.common.enums.EnableStateEnum;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.domain.common.MarketChannel;
import org.geely.domain.core.data.CustomerData;
import org.geely.domain.core.data.CustomerData.CertifyStateEnum;
import org.geely.domain.core.data.CustomerData.TypeEnum;
import org.geely.domain.support.Role;
import org.geely.domain.support.data.RoleData;
import org.geely.infrastructure.db.repository.CoreDomainRepository;
import org.geely.infrastructure.mq.CustomerCertifiedEvent;


/**
 * @author ricardo zhou
 */
public class Customer {

    /**
     * 管理员角色名
     */
    public static final String ADMIN_ROLE_NAME = "管理员";
    /**
     * 财务角色名
     */
    public static final String FINANCE_ROLE_NAME = "财务";
    /**
     * 采购角色名
     */
    public static final String PURCHASER_ROLE_NAME = "采购";

    private CustomerData data;

    private Customer(CustomerData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static Customer newInstance(CustomerData data) {
        Customer customer = new Customer(data);
        customer.data.setId(null);
        customer.data.setMarketChannelId(0);
        customer.data.setPayPassword("123456");
        customer.data.setType(TypeEnum.ENTERPRISE);
        customer.data.setState(EnableStateEnum.DISABLE);
        customer.data.setCertifyState(CertifyStateEnum.UNFINISHED);
        customer.data.setVersion(0);
        customer.save();
        return customer;
    }

    public void save() {
        this.data = SpringUtil.getBean(CoreDomainRepository.class).saveCustomer(this.data);
    }

    public CustomerData getData() {
        return ObjectUtil.clone(this.data);
    }

    public static Customer instanceOf(Integer id) {
        CustomerData data = SpringUtil.getBean(CoreDomainRepository.class).getCustomerById(id);
        return new Customer(data);
    }

    /**
     * 启用
     */
    public Customer enable() {
        this.data.setState(EnableStateEnum.ENABLE);
        return this;
    }

    /**
     * 完成认证
     */
    public Customer finishedCertify() {
        this.data.setCertifyState(CertifyStateEnum.FINISHED);
        CustomerCertifiedEvent.builder().customerId(this.data.getId()).build().push();
        return this;
    }

    /**
     * 更新销售渠道
     */
    public Customer updateMarketChannel(MarketChannel marketChannel) {
        this.data.setMarketChannelId(marketChannel.getId());
        return this;
    }

    public Role createAdminRole() {
        RoleData roleData = new RoleData();
        roleData.setName(ADMIN_ROLE_NAME);
        roleData.setRemark("拥有该客户最高权限");
        roleData.setPlatformType(PlatformTypeEnum.CUSTOMER);
        roleData.setPlatformId(this.data.getId());
        roleData.setVersion(0);
        return Role.newInstance(roleData);
    }

    public Role createFinanceRole() {
        RoleData roleData = new RoleData();
        roleData.setName(FINANCE_ROLE_NAME);
        roleData.setRemark("财务");
        roleData.setPlatformType(PlatformTypeEnum.CUSTOMER);
        roleData.setPlatformId(this.data.getId());
        roleData.setVersion(0);
        return Role.newInstance(roleData);
    }

    public Role createPurchaserRole() {
        RoleData roleData = new RoleData();
        roleData.setName(PURCHASER_ROLE_NAME);
        roleData.setRemark("采购");
        roleData.setPlatformType(PlatformTypeEnum.CUSTOMER);
        roleData.setPlatformId(this.data.getId());
        roleData.setVersion(0);
        return Role.newInstance(roleData);
    }

    /**
     * 获取客户销售渠道id
     *
     * @return 销售渠道id
     */
    public Integer getMarketChannelId() {
        return this.data.getMarketChannelId();
    }
    public Integer getId() {return this.data.getId();}

    public EnableStateEnum getState() {
        return data.getState();
    }
}
