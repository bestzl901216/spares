package org.geely.domain.core;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.common.enums.EnableStateEnum;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.domain.common.MallBankAccount;
import org.geely.domain.common.MallSap;
import org.geely.domain.common.MarketChannel;
import org.geely.domain.common.MarketChannelRel;
import org.geely.domain.core.data.SupplierData;
import org.geely.domain.support.Role;
import org.geely.domain.support.data.RoleData;
import org.geely.infrastructure.db.repository.SupplierRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
public class Supplier {
    public static final String ADMIN_ROLE_NAME = "管理员";
    private SupplierData data;

    private Supplier(SupplierData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static Supplier newInstance(SupplierData data) {
        Supplier supplier = new Supplier(data);
        supplier.data.setId(null);
        supplier.data.setVersion(0);
        MallBankAccount bankAccount = MallBankAccount.instanceOf(supplier.data.getBankAccountId());
        Assert.isTrue(bankAccount.getMallId().equals(supplier.data.getMallId()), "收款账号不正确");
        if(supplier.data.getSapId() != null && !supplier.data.getSapId().equals(0)) {
            MallSap sap = MallSap.instanceOf(supplier.data.getSapId());
            Assert.isTrue(sap.getMallId().equals(supplier.data.getMallId()), "Sap类型不正确");
            Assert.isTrue(sap.getState() == EnableStateEnum.ENABLE, "该Sap类型已禁用");
        }
        supplier.save();
        return supplier;
    }

    public static Supplier instanceOf(Integer id) {
        SupplierData data = SpringUtil.getBean(SupplierRepository.class).getById(id);
        return new Supplier(data);
    }

    public static List<Supplier> instancesOf(Set<Integer> ids) {
        return SpringUtil.getBean(SupplierRepository.class).listByIds(ids).stream().map(Supplier::new).collect(Collectors.toList());
    }

    public void save() {
        this.data = SpringUtil.getBean(SupplierRepository.class).save(this.data);
    }

    public SupplierData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Role createAdminRole() {
        RoleData roleData = new RoleData();
        roleData.setName("管理员");
        roleData.setRemark("拥有该客户最高权限");
        roleData.setPlatformType(PlatformTypeEnum.SUPPLIER);
        roleData.setPlatformId(this.data.getId());
        roleData.setVersion(0);
        return Role.newInstance(roleData);
    }
    public void associateMarketChannels(Set<MarketChannel> marketChannels) {
        MarketChannelRel.batchAssociate(PlatformTypeEnum.SUPPLIER, this.data.getId(), marketChannels);
    }
    public EnableStateEnum getState(){
        return this.data.getState();
    }

    public Integer getBankAccountId() {
        return this.data.getBankAccountId();
    }
    public Integer getId() {
        return this.data.getId();
    }

    public String getName() {
        return this.data.getName();
    }
    public Integer getMallId() {
        return this.data.getMallId();
    }
}
