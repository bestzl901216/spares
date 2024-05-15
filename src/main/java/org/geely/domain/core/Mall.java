package org.geely.domain.core;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.schedulerx.shade.scala.Int;
import org.geely.common.enums.EnableStateEnum;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.domain.common.MarketChannel;
import org.geely.domain.common.MarketChannelRel;
import org.geely.domain.core.data.MallData;
import org.geely.domain.core.data.ProductBrandData;
import org.geely.domain.core.data.ProductQualityData;
import org.geely.domain.core.data.ProductTagData;
import org.geely.domain.support.Role;
import org.geely.domain.support.data.RoleData;
import org.geely.infrastructure.db.repository.MallRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 租户
 *
 * @author cong huang
 */
public class Mall {

    public static final String ADMIN_ROLE_NAME = "管理员";

    private MallData data;

    private Mall(MallData mallData) {
        this.data = ObjectUtil.clone(mallData);
    }

    public static Mall newInstance(String name) {
        MallData data = new MallData();
        data.setName(name);
        data.setVersion(0);
        Mall mall = new Mall(data);
        mall.save();
        return mall;
    }

    public static Mall instanceOf(Integer mallId) {
        MallData data = SpringUtil.getBean(MallRepository.class).getById(mallId);
        return new Mall(data);
    }

    public static List<Mall> instancesOf(Set<Integer> ids) {
        return SpringUtil.getBean(MallRepository.class).listByIds(ids).stream().map(Mall::new).collect(Collectors.toList());
    }

    /**
     * 根据市场渠道ID获取租户列表
     *
     * @param marketChannelId 市场渠道ID
     * @return 租户列表
     */
    public static List<Mall> instanceOfMarket(Integer marketChannelId) {
        Set<MarketChannelRel> marketChannelRels = MarketChannelRel.listByChannelId(PlatformTypeEnum.MALL, marketChannelId);
        List<Integer> mallIds = marketChannelRels.stream().map(MarketChannelRel::getPlatformId).collect(Collectors.toList());
        List<MallData> mallDataList = SpringUtil.getBean(MallRepository.class).listByIds(new HashSet<>(mallIds));
        return mallDataList.stream().map(Mall::new).collect(Collectors.toList());
    }

    public Mall updateName(String name) {
        this.data.setName(name);
        return this;
    }

    /**
     * 启用或禁用租户
     */
    public Mall updateState() {
        EnableStateEnum state = this.data.getState() == EnableStateEnum.ENABLE ? EnableStateEnum.DISABLE : EnableStateEnum.ENABLE;
        this.data.setState(state);
        return this;
    }

    public void save() {
        Assert.notBlank(this.data.getName(), "租户名称不能为空");
        this.data = SpringUtil.getBean(MallRepository.class).save(this.data);
    }

    public MallData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Role createAdminRole() {
        RoleData roleData = new RoleData();
        roleData.setName(ADMIN_ROLE_NAME);
        roleData.setRemark("拥有该租户最高权限");
        roleData.setPlatformType(PlatformTypeEnum.MALL);
        roleData.setPlatformId(this.data.getId());
        roleData.setVersion(0);
        return Role.newInstance(roleData);
    }

    public Role getAdminRole() {
        return Role.instanceOf(ADMIN_ROLE_NAME, PlatformTypeEnum.MALL, this.data.getId());
    }

    public ProductTag createTag(String name, String remark, String fontColor, String backgroundColor) {
        ProductTagData productTagData = new ProductTagData();
        productTagData.setMallId(this.data.getId());
        productTagData.setName(name);
        productTagData.setRemark(remark);
        productTagData.setFontColor(fontColor);
        productTagData.setBackgroundColor(backgroundColor);
        return ProductTag.newInstance(productTagData);
    }

    public ProductBrand createBrand(String name, String logo) {
        ProductBrandData productBrandData = new ProductBrandData();
        productBrandData.setMallId(this.data.getId());
        productBrandData.setName(name);
        productBrandData.setLogo(logo);
        return ProductBrand.newInstance(productBrandData);
    }

    public ProductQuality createQuality(String name, String remark) {
        ProductQualityData productQualityData = new ProductQualityData();
        productQualityData.setMallId(this.data.getId());
        productQualityData.setName(name);
        productQualityData.setRemark(remark);
        return ProductQuality.newInstance(productQualityData);
    }

    public void associateMarketChannels(Set<MarketChannel> marketChannels) {
        MarketChannelRel.batchAssociate(PlatformTypeEnum.MALL, this.data.getId(), marketChannels);
    }

    public Integer getId() {
        return this.data.getId();
    }

    /**
     * @return 自动签收时间（天数）
     */
    public Integer getOrderReceiveExpiration() {
        return this.data.getOrderReceiveExpiration();
    }

    public Integer getOrderReturnExpiration() {
        return data.getOrderReturnExpiration();
    }

    public String getName() {
        return data.getName();
    }

    public Integer getOrderPayExpiration() {
        return data.getOrderPayExpiration();
    }
}
