package org.geely.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.annotation.NoPlatform;
import org.geely.common.constants.AuthConstant;
import org.geely.common.constants.MvcConstant;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.common.exception.BizException;
import org.geely.common.model.ResultJson;
import org.geely.controller.dto.*;
import org.geely.domain.common.Account;
import org.geely.domain.common.MarketChannel;
import org.geely.domain.common.data.MarketChannelData;
import org.geely.domain.common.data.SessionData;
import org.geely.domain.core.Customer;
import org.geely.domain.core.Mall;
import org.geely.domain.core.Shop;
import org.geely.domain.core.Supplier;
import org.geely.domain.core.data.CustomerData;
import org.geely.domain.support.Role;
import org.geely.domain.support.data.RoleData;
import org.geely.infrastructure.db.gateway.ConsoleDbGateway;
import org.geely.infrastructure.redis.RedisGateway;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author cong huang
 */
@Api(value = "管理平台接口", tags = "管理平台接口")
@Slf4j
@Validated
@RestController
@RequestMapping(MvcConstant.PLATFORM)
public class ConsoleController {
    @Resource
    private ConsoleDbGateway consoleDbGateway;
    @Resource
    private RedisGateway redisGateway;

    @ApiOperation("统计概况")
    @GetMapping("overview")
    public ResultJson<String> overview(@RequestParam Integer mallId, @RequestParam Integer supplierId, @RequestParam Integer shopId,
                                       @RequestParam Long startTime, @RequestParam Long endTime) {
        return ResultJson.success("overview");
    }

    @ApiOperation("渠道列表")
    @GetMapping("marketChannel")
    public ResultJson<Page<MarketChannelDTO>> marketChannel(
            @ApiParam(name = "current", required = true, value = "页码")
            @Min(1)
            @RequestParam Long current,
            @ApiParam(name = "size", required = true, value = "每页数量")
            @Min(1)
            @Max(1000)
            @RequestParam Long size) {
        Page<MarketChannelDTO> page = new Page<>(current, size);
        consoleDbGateway.marketChannelPage(page);
        return ResultJson.success(page);
    }

    @ApiOperation("新建渠道")
    @PostMapping("marketChannel")
    public ResultJson<Integer> createMarketChannel(@Validated @RequestBody MarketChannelCreateDTO dto) {
        MarketChannelData data = Convert.INSTANCE.convert(dto);
        MarketChannel channel = MarketChannel.newInstance(data);
        return ResultJson.success(channel.getId());
    }

    @ApiOperation("更新渠道")
    @PutMapping("marketChannel/{id}")
    public ResultJson<Integer> updateMarketChannel(@PathVariable Integer id, @Validated @RequestBody MarketChannelUpdateDTO dto) {
        MarketChannel channel = MarketChannel.instanceOf(id);
        channel.updateName(dto.name).updateRemark(dto.remark).save();
        return ResultJson.success(1);
    }

    @ApiOperation("删除渠道")
    @DeleteMapping("marketChannel/{id}")
    public ResultJson<Integer> deleteMarketChannel(@NotNull(message = "渠道id不能为空") @PathVariable Integer id) {
        MarketChannel channel = MarketChannel.instanceOf(id);
        channel.delete();
        return ResultJson.success(1);
    }

    @ApiOperation("租户列表")
    @GetMapping("mall")
    public ResultJson<Page<MallDTO>> mallPage(
            @ApiParam(value = "租户名称")
            @RequestParam(required = false) String name,
            @ApiParam(value = "渠道id")
            @RequestParam(required = false) Integer marketChannelId,
            @ApiParam(value = "状态 0：禁用 1：启用")
            @RequestParam(required = false) Integer state,
            @ApiParam(name = "current", required = true, value = "页码")
            @Min(1) @RequestParam Long current,
            @ApiParam(name = "size", required = true, value = "每页数量")
            @Min(1) @Max(1000) @RequestParam Long size) {
        Page<MallDTO> page = new Page<>(current, size);
        consoleDbGateway.mallPage(page, name, marketChannelId, state);
        return ResultJson.success(page);
    }

    @ApiOperation("新建租户")
    @PostMapping("mall")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<Integer> createMall(@Validated @RequestBody MallCreateDTO dto) {
        Mall mall = Mall.newInstance(dto.getName());
        Set<MarketChannel> marketChannels = MarketChannel.instanceOf(dto.getMarketChannels());
        mall.associateMarketChannels(marketChannels);
        Account.newInstanceOrGetBy(dto.getAdminPhone()).associate(mall.createAdminRole());
        return ResultJson.success(1);
    }

    @ApiOperation("更新租户")
    @PutMapping("mall/{mallId}")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<Integer> updateMall(@PathVariable Integer mallId, @Validated @RequestBody MallUpdateDTO dto) {
        Mall mall = Mall.instanceOf(mallId);
        mall.updateName(dto.name).save();
        Set<MarketChannel> marketChannels = MarketChannel.instanceOf(dto.marketChannels);
        mall.associateMarketChannels(marketChannels);
        Account.newInstanceOrGetBy(dto.adminPhone).associate(mall.getAdminRole().clearRelation());
        return ResultJson.success(1);
    }

    @ApiOperation("启用、禁用租户")
    @PutMapping("mall/{mallId}/state")
    public ResultJson<Integer> updateMallState(@PathVariable Integer mallId) {
        Mall.instanceOf(mallId).updateState().save();
        return ResultJson.success(1);
    }

    @ApiOperation("客户列表")
    @GetMapping("customer")
    public ResultJson<Page<CustomerDTO>> customerPage(
            @ApiParam(value = "管理员名称或者手机号")
            @RequestParam(required = false) String admin,
            @ApiParam(value = "公司名称")
            @RequestParam(required = false) String name,
            @ApiParam(value = "渠道id")
            @RequestParam(required = false) Integer marketChannelId,
            @ApiParam(value = "状态 0：禁用 1：启用")
            @RequestParam(required = false) Integer state,
            @ApiParam(name = "current", required = true, value = "页码")
            @Min(1) @RequestParam Long current,
            @ApiParam(name = "size", required = true, value = "每页数量")
            @Min(1) @Max(1000) @RequestParam Long size) {
        Page<CustomerDTO> page = new Page<>(current, size);
        consoleDbGateway.customerPage(page, admin, name, marketChannelId, state);
        return ResultJson.success(page);
    }

    @ApiOperation("新建客户")
    @PostMapping("customer")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<Integer> createCustomer(@Validated @RequestBody CustomerCreateDTO dto) {
        MarketChannel marketChannel = MarketChannel.instanceOf(dto.marketChannelId);
        CustomerData customerData = Convert.INSTANCE.convert(dto);
        Customer customer = Customer.newInstance(customerData);
        customer.updateMarketChannel(marketChannel).enable().finishedCertify().save();
        Role adminRole = customer.createAdminRole();
        customer.createFinanceRole();
        customer.createPurchaserRole();
        Account.newInstanceOrGetBy(dto.adminPhone).associate(adminRole).updateName(dto.adminName).save();
        return ResultJson.success(1);
    }

    @ApiOperation("员工列表")
    @GetMapping("staff")
    public ResultJson<Page<StaffDTO>> staffPage(@ApiParam(value = "手机号")
                                                @Length(max = 11) @RequestParam(required = false) String phone,
                                                @ApiParam(name = "current", required = true, value = "页码")
                                                @Min(1) @RequestParam Long current,
                                                @ApiParam(name = "size", required = true, value = "每页数量")
                                                @Min(1) @Max(1000) @RequestParam Long size) {
        Page<StaffDTO> page = new Page<>(current, size);
        consoleDbGateway.staffPage(page, phone);
        return ResultJson.success(page);
    }

    @ApiOperation("添加员工")
    @PostMapping("staff")
    public ResultJson<Integer> createStaff(@Validated @RequestBody StaffCreateDTO dto) {
        Account account = Account.newInstanceOrGetBy(dto.phone);
        Optional<Role> roleOptional = account.roleOf(PlatformTypeEnum.PLATFORM, 0);
        if (roleOptional.isPresent()) {
            throw new BizException("staff create error", "员工已存在");
        } else {
            account.associate(Role.instanceOf(dto.roleName, PlatformTypeEnum.PLATFORM, 0));
        }
        return ResultJson.success(1);
    }

    @ApiOperation("编辑员工")
    @PutMapping("staff/{id}")
    public ResultJson<Integer> updateStaff(@PathVariable Integer id,
                                           @Validated @RequestBody StaffUpdateDTO dto) {
        Account account = Account.instanceOfId(id);
        Role role = Role.instanceOf(dto.roleName, PlatformTypeEnum.PLATFORM, 0);
        Optional<Role> roleOptional = account.roleOf(PlatformTypeEnum.PLATFORM, 0);
        if (roleOptional.isPresent()) {
            Role previousRole = roleOptional.get();
            if (Objects.equals(previousRole.getId(), role.getId())) {
                return ResultJson.success(1);
            } else {
                account.disassociate(previousRole).associate(role);
            }
        } else {
            account.associate(role);
        }
        return ResultJson.success(1);
    }

    @ApiOperation("启用、禁用员工")
    @PutMapping("staff/{id}/state")
    public ResultJson<Integer> updateStaffState(@PathVariable Integer id) {
        Account account = Account.instanceOfId(id);
        Optional<Role> roleOptional = account.roleOf(PlatformTypeEnum.PLATFORM, 0);
        if (roleOptional.isPresent()) {
            account.enableOrDisableRole(roleOptional.get());
        } else {
            throw new BizException("staff state error", "不存在该员工");
        }
        return ResultJson.success(1);
    }

    @ApiOperation("角色列表")
    @GetMapping("role")
    public ResultJson<Page<RoleDTO>> rolePage(
            @ApiParam(name = "current", required = true, value = "页码")
            @Min(1) @RequestParam Long current,
            @ApiParam(name = "size", required = true, value = "每页数量")
            @Min(1) @Max(1000) @RequestParam Long size) {
        Page<RoleDTO> page = new Page<>(current, size);
        consoleDbGateway.rolePage(page);
        return ResultJson.success(page);
    }

    @ApiOperation("添加角色")
    @PostMapping("role")
    public ResultJson<Integer> createRole(@Validated @RequestBody RoleCreateDTO dto) {
        RoleData roleData = Convert.INSTANCE.convert(dto);
        roleData.setPlatformType(PlatformTypeEnum.PLATFORM);
        roleData.setPlatformId(0);
        Role role = Role.newInstance(roleData);
        return ResultJson.success(role.getId());
    }

    @ApiOperation("编辑角色")
    @PutMapping("role/{id}")
    public ResultJson<Integer> updateRole(@PathVariable Integer id, @Validated @RequestBody RoleUpdateDTO dto) {
        Role role = Role.instanceOf(id);
        if (Boolean.FALSE.equals(role.isBelong(PlatformTypeEnum.PLATFORM, 0))) {
            throw new BizException("role update error", "不允许编辑该角色");
        }
        role.updateName(dto.name).updateRemark(dto.remark).updatePermissions(dto.permissions).save();
        return ResultJson.success(1);
    }

    @ApiOperation("删除角色")
    @DeleteMapping("role/{id}")
    public ResultJson<Integer> deleteRole(@NotNull(message = "角色id不能为空") @PathVariable Integer id) {
        Role role = Role.instanceOf(id);
        if (Boolean.FALSE.equals(role.isBelong(PlatformTypeEnum.PLATFORM, 0))) {
            throw new BizException("role delete error", "不允许删除该角色");
        }
        role.delete();
        return ResultJson.success(1);
    }

    @ApiOperation("商城轮播广告列表")
    @GetMapping("mall/banner")
    public ResultJson<Page<MallBannerDTO>> mallBannerPage(
            @ApiParam(value = "商城名称")
            @RequestParam(required = false) String mallName,
            @ApiParam(value = "标题")
            @RequestParam(required = false) String title,
            @ApiParam(value = "状态 0：禁用 1：启用")
            @RequestParam(required = false) Integer state,
            @ApiParam(name = "current", required = true, value = "页码")
            @Min(1) @RequestParam Long current,
            @ApiParam(name = "size", required = true, value = "每页数量")
            @Min(1) @Max(1000) @RequestParam Long size) {
        Page<MallBannerDTO> page = new Page<>(current, size);
        consoleDbGateway.mallBannerPage(page, mallName, title, state);
        return ResultJson.success(page);
    }

    @ApiOperation("平台设置")
    @GetMapping("setting")
    public ResultJson<List<SettingDTO>> setting() {
        return ResultJson.success(consoleDbGateway.setting());
    }

    @ApiOperation("切换后台")
    @NoPlatform
    @GetMapping("list")
    public ResultJson<List<PlatformSwitchDTO>> listPlatform(@RequestHeader(value = AuthConstant.TOKEN_KEY) String token) {
        Optional<SessionData> sessionDataOptional = redisGateway.querySessionData(token);

        if (!sessionDataOptional.isPresent()) {
            return ResultJson.success(new ArrayList<>());
        }
        List<PlatformSwitchDTO> results = new ArrayList<>();
        Map<Integer, List<RoleData>> roleMap = sessionDataOptional.get().getRoleDataSet().stream().collect(Collectors.groupingBy(x -> x.getPlatformType().getId()));

        Set<Integer> mallIds = new HashSet<>();
        Set<Integer> supplierIds = new HashSet<>();
        Set<Integer> shopIds = new HashSet<>();

        for (Integer platformType : roleMap.keySet()) {
            if (platformType.equals(PlatformTypeEnum.PLATFORM.getId())) {
                results.add(new PlatformSwitchDTO(PlatformTypeEnum.PLATFORM));
            } else if (platformType.equals(PlatformTypeEnum.MALL.getId())) {
                PlatformSwitchDTO mallDTO = new PlatformSwitchDTO(PlatformTypeEnum.MALL);
                for (RoleData roleData : roleMap.get(platformType)) {
                    mallIds.add(roleData.getPlatformId());
                    mallDTO.getChildren().add(new PlatformNameDTO(roleData.getPlatformId()));
                }
                results.add(mallDTO);
            } else if (platformType.equals(PlatformTypeEnum.SUPPLIER.getId())) {
                PlatformSwitchDTO supplierDTO = new PlatformSwitchDTO(PlatformTypeEnum.SUPPLIER);
                for (RoleData roleData : roleMap.get(platformType)) {
                    supplierIds.add(roleData.getPlatformId());
                    supplierDTO.getChildren().add(new PlatformNameDTO(roleData.getPlatformId()));
                }
                results.add(supplierDTO);
            } else if (platformType.equals(PlatformTypeEnum.SHOP.getId())) {
                PlatformSwitchDTO shopDTO = new PlatformSwitchDTO(PlatformTypeEnum.SHOP);
                for (RoleData roleData : roleMap.get(platformType)) {
                    shopIds.add(roleData.getPlatformId());
                    shopDTO.getChildren().add(new PlatformNameDTO(roleData.getPlatformId()));
                }
                results.add(shopDTO);
            }
        }

        Map<Integer, Shop> shopMap = Shop.instancesOf(shopIds).stream().collect(Collectors.toMap(Shop::getId, v -> v));
        supplierIds.addAll(shopMap.values().stream().map(Shop::getSupplierId).collect(Collectors.toSet()));
        Map<Integer, Supplier> supplierMap = Supplier.instancesOf(supplierIds).stream().collect(Collectors.toMap(Supplier::getId, v -> v));
        mallIds.addAll(supplierMap.values().stream().map(Supplier::getMallId).collect(Collectors.toSet()));
        Map<Integer, Mall> mallMap = Mall.instancesOf(mallIds).stream().collect(Collectors.toMap(Mall::getId, v -> v));

        for (PlatformSwitchDTO dto : results) {
            if (dto.getCode().equals(PlatformTypeEnum.MALL.toString())) {
                for (PlatformNameDTO child : dto.getChildren()) {
                    child.setName(mallMap.get(child.getId()).getName());
                }
            } else if (dto.getCode().equals(PlatformTypeEnum.SUPPLIER.toString())) {
                for (PlatformNameDTO child : dto.getChildren()) {
                    Supplier supplier = supplierMap.get(child.getId());
                    child.setName(supplier.getName());
                    child.setMallName(mallMap.get(supplier.getMallId()).getName());
                }
            } else if (dto.getCode().equals(PlatformTypeEnum.SHOP.toString())) {
                for (PlatformNameDTO child : dto.getChildren()) {
                    Shop shop = shopMap.get(child.getId());
                    child.setName(shop.getName());
                    Supplier supplier = supplierMap.get(shop.getSupplierId());
                    child.setSupplierName(supplier.getName());
                    child.setMallName(mallMap.get(supplier.getMallId()).getName());
                }
            }
        }
        return ResultJson.success(results);
    }
}
