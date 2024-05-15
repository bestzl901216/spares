package org.geely.infrastructure.db.gateway;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.geely.controller.dto.*;

import java.util.List;

/**
 * @author ricardo zhou
 */
@Mapper
public interface ConsoleDbGateway {
    /**
     * 获取账号可以登录的管理后台列表
     *
     * @param accountId 账号id
     * @return 管理后台列表
     */
    List<PlatformDTO> platformList(Integer accountId);

    /**
     * 分页查询消息
     *
     * @param page         分页参数
     * @param platformType 平台类型
     * @param platformId   平台id
     * @return 消息集合
     */
    IPage<NoticeDTO> noticePage(Page<?> page, @Param("platformType") Integer platformType, @Param("platformId") Integer platformId);

    /**
     * 分页查询渠道
     */
    IPage<MarketChannelDTO> marketChannelPage(Page<?> page);

    /**
     * 分页查询租户
     */
    IPage<MallDTO> mallPage(Page<?> page, @Param("name") String name, @Param("marketChannelId") Integer marketChannelId, @Param("state") Integer state);

    /**
     * 分页查询客户
     */
    IPage<CustomerDTO> customerPage(Page<?> page, @Param("admin") String admin, @Param("name") String name, @Param("marketChannelId") Integer marketChannelId, @Param("state") Integer state);

    /**
     * 分页查询员工信息
     */
    IPage<StaffDTO> staffPage(Page<?> page, @Param("phone") String phone);

    /**
     * 分页查询角色信息
     */
    IPage<RoleDTO> rolePage(Page<RoleDTO> page);

    /**
     * 分页查询商城轮播图
     */
    IPage<MallBannerDTO> mallBannerPage(Page<?> page, @Param("mallName") String mallName, @Param("title") String title, @Param("state") Integer state);

    /**
     * @return 平台基本信息
     */
    List<SettingDTO> setting();
}
