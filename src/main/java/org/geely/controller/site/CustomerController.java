package org.geely.controller.site;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.annotation.NoLogin;
import org.geely.common.annotation.NoPlatform;
import org.geely.common.constants.AuthConstant;
import org.geely.common.constants.MvcConstant;
import org.geely.common.enums.EnableStateEnum;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.common.model.ResultJson;
import org.geely.common.utils.OperatorUtil;
import org.geely.controller.Convert;
import org.geely.controller.dto.*;
import org.geely.controller.dto.site.CustomerDeliveryPackageDTO;
import org.geely.controller.dto.site.SaleOrderFlowDTO;
import org.geely.domain.common.Account;
import org.geely.domain.common.LoginFailed;
import org.geely.domain.common.data.AccountData;
import org.geely.domain.common.data.SessionData;
import org.geely.domain.core.*;
import org.geely.domain.core.data.CustomerData;
import org.geely.domain.core.data.HomePageSkuData;
import org.geely.domain.support.Role;
import org.geely.domain.support.SaleOrderFlow;
import org.geely.infrastructure.db.gateway.AccountDbGateway;
import org.geely.infrastructure.db.gateway.MallDbGateway;
import org.geely.infrastructure.db.gateway.SaleOrderDbGateway;
import org.geely.infrastructure.redis.RedisGateway;
import org.geely.infrastructure.sms.SmsGateway;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ricardo zhou
 */
@Api(value = "商城端-客户接口", tags = "商城端-客户接口")
@Slf4j
@RestController
@RequestMapping(MvcConstant.CUSTOMER)
public class CustomerController {

    @Resource
    private RedisGateway redisGateway;
    @Resource
    private SmsGateway smsGateway;
    @Resource
    private MallDbGateway mallDbGateway;
    @Resource
    private SaleOrderDbGateway saleOrderDbGateway;
    @Resource
    private AccountDbGateway accountDbGateway;
    public static final String NO_PERMISSION = "无权限操作";
    private static final String MSG_LOGIN_FAILED = "用户名或密码错误";

    @ApiOperation("获取手机验证码")
    @GetMapping("smsCode/{phone}")
    public ResultJson<String> smsCode(@PathVariable String phone) {
        String phoneCode = redisGateway.getPhoneCode(phone);
        if (phoneCode != null) {
            return ResultJson.fail("", "验证码已发送，请稍后再试");
        }
        String smsCode = smsGateway.sendSmsCode(phone);
        redisGateway.savePhoneCode(phone, smsCode);
        return ResultJson.success(smsCode);
    }

    @ApiOperation("新建账号")
    @PostMapping("account")
    public ResultJson<Integer> createAccount(@Validated @RequestBody AccountCreateDTO dto) {
        log.info("creatAccount: dto={}", JSONUtil.toJsonStr(dto));
        // convert方法中，默认将输入的phone作为账号名
        AccountData accountData = Convert.INSTANCE.convert(dto);
        Account account = Account.newInstance(accountData);
        log.info("creatAccount success");
        return ResultJson.success(account.getId());
    }

    /**
     * 更新账号名称
     *
     * @param dto 更新账号名称
     * @return 更新结果
     */
    @ApiOperation("更新账号名称")
    @PutMapping("account")
    public ResultJson<Integer> updateAccountPassword(@Validated @RequestBody AccountUpdateDTO dto) {
        Account account = Account.instanceOfId(OperatorUtil.getAccountId());
        account.updateName(dto.getName()).save();
        return ResultJson.success(account.getId());
    }


    @ApiOperation("创建token")
    @NoLogin
    @PostMapping("account/token")
    public ResultJson<AuthDTO> createToken(@Validated @RequestBody AccountLoginDTO dto) {
        LoginFailed loginFailed = LoginFailed.instanceOfAndCheck(dto.getPhone());
        Optional<Account> accountOptional = Account.instanceOfPhoneOptional(dto.getPhone());
        if (!accountOptional.isPresent()) {
            loginFailed.increase();
            throw new IllegalArgumentException(MSG_LOGIN_FAILED);
        }
        Account account = accountOptional.get();
        boolean pass = account.verifyPassword(dto.getPassword());
        if (pass) {
            loginFailed.delete();
        } else {
            loginFailed.increase();
        }

        Assert.isTrue(pass, MSG_LOGIN_FAILED);
        Assert.isTrue(account.getState() == EnableStateEnum.ENABLE, "该账号已被禁用");
        Integer customerId = account.roles().stream().filter(e -> e.getPlatformType() == PlatformTypeEnum.CUSTOMER).findFirst().map(Role::getPlatformId).orElse(0);
        Assert.isTrue(customerId != 0, MSG_LOGIN_FAILED);
        //检验customer
        Customer customer = Customer.instanceOf(customerId);
        Assert.isTrue(customer.getState() == EnableStateEnum.ENABLE, "客户公司已被禁用");
        Integer marketChannelId = customer.getMarketChannelId();
        List<Mall> malls = Mall.instanceOfMarket(marketChannelId);

        SessionData sessionData = new SessionData();
        sessionData.setAccountData(account.getData());
        sessionData.setRoleDataSet(account.roles().stream().map(Role::getData).collect(Collectors.toSet()));
        sessionData.setMallDataSet(malls.stream().map(Mall::getData).collect(Collectors.toSet()));
        String token = redisGateway.saveSessionData(sessionData);

        AuthDTO authDTO = new AuthDTO();
        authDTO.setToken(token);
        authDTO.setMallIds(malls.stream().map(Mall::getId).collect(Collectors.toSet()));
        authDTO.setCustomerId(customerId);
        authDTO.setUserName(account.getPhone());
        authDTO.setNick(account.getName());
        authDTO.setVip(false);
        authDTO.setAvater("");
        return ResultJson.success(authDTO);
    }

    @ApiOperation("注销")
    @DeleteMapping("account/logout")
    @NoPlatform
    public ResultJson<Boolean> logout(@RequestHeader(value = AuthConstant.TOKEN_KEY) String token) {
        redisGateway.removeSessionData(token);
        return ResultJson.success(true);
    }

    @ApiOperation("客户认证状态")
    @PostMapping("certifiedState")
    public ResultJson<Integer> customerCertifiedState() {
        // 0: 未提交认证 1: 认证中 2: 认证通过 3: 认证失败
        return ResultJson.success(0);
    }

    @ApiOperation("提交客户认证资料")
    @PostMapping("certifiedInfo")
    public ResultJson<Integer> createCertifiedInfo(@Validated @RequestBody CertifiedInfoCreateDTO dto) {
        return ResultJson.success(1);
    }

    @ApiOperation("个人主页")
    @GetMapping("account/home")
    public ResultJson<AccountDTO> accountHome() {
        Integer accountId = OperatorUtil.getAccountId();
        AccountDTO result = accountDbGateway.getAccountById(accountId);
        Integer customerId = OperatorUtil.getPlatformId();
        Customer customer = Customer.instanceOf(customerId);
        CustomerData customerData = customer.getData();
        result.setCustomerName(customerData.getName());
        result.setIdentitySn(customerData.getIdentitySn());
        result.setAddress(customerData.getAddress());
        result.setProvince(customerData.getProvince());
        result.setCity(customerData.getCity());
        result.setArea(customerData.getArea());

        Integer mallId = OperatorUtil.getMallId();
        result.setSaleOrderStatistics(saleOrderDbGateway.getSaleOrderStatistics(mallId, customerId));

        return ResultJson.success(result);
    }

    @ApiOperation("提醒-订单数量")
    @GetMapping("reminder/saleOrderCount")
    public ResultJson<Integer> orderReminder() {
        Integer customerId = OperatorUtil.getPlatformId();
        Integer mallId = OperatorUtil.getMallId();
        return ResultJson.success(saleOrderDbGateway.getOrderReminder(mallId, customerId));
    }

    @ApiOperation("提醒-购物车数量")
    @GetMapping("reminder/cartCount")
    public ResultJson<Integer> cartReminder() {
        Integer customerId = OperatorUtil.getPlatformId();
        Integer mallId = OperatorUtil.getMallId();
        Integer cartCount = saleOrderDbGateway.getCartCount(mallId, customerId);
        return ResultJson.success(cartCount);
    }

    @ApiOperation("订单列表")
    @GetMapping("saleOrders")
    public ResultJson<Page<CustomerSaleOrderDTO>> saleOrderPage(
            @ApiParam(value = "订单状态")
            @RequestParam(required = false) Integer state,
            @ApiParam(name = "current", required = true, value = "页码")
            @Min(1) @RequestParam Long current,
            @ApiParam(name = "size", required = true, value = "每页数量")
            @Min(1) @Max(1000) @RequestParam Long size) {
        Page<CustomerSaleOrderDTO> page = new Page<>(current, size);
        saleOrderDbGateway.saleOrderPage(page, OperatorUtil.getPlatformId(), OperatorUtil.getMallId(), state);
        return ResultJson.success(page);
    }

    @ApiOperation("订单详情")
    @GetMapping("saleOrders/{id}")
    public ResultJson<CustomerSaleOrderDetailDTO> saleOrderDetail(@PathVariable Integer id) {
        CustomerSaleOrderDetailDTO saleOrderDetail = saleOrderDbGateway.getSaleOrderDetail(id, OperatorUtil.getPlatformId(), OperatorUtil.getMallId());
        saleOrderDetail = saleOrderDetail.afterPropertiesSet();
        return ResultJson.success(saleOrderDetail);
    }

    @ApiOperation("订单详情-订单子项")
    @GetMapping("saleOrders/{id}/items")
    public ResultJson<List<CustomerSaleOrderItemDTO>> saleOrderItems(@PathVariable Integer id) {
        List<CustomerSaleOrderItemDTO> saleOrderItems = saleOrderDbGateway.getSaleOrderItems(id, OperatorUtil.getPlatformId(), OperatorUtil.getMallId());
        return ResultJson.success(saleOrderItems);
    }

    @ApiOperation("订单详情-发货单列表")
    @GetMapping("saleOrders/{id}/delivery")
    public ResultJson<List<CustomerDeliveryOrderDTO>> deliveryOrderList(@PathVariable Integer id) {
        SaleOrder saleOrder = SaleOrder.instanceOf(id);
        Assert.isTrue(saleOrder.getCustomerId().equals(OperatorUtil.getPlatformId()) && saleOrder.getMallId().equals(OperatorUtil.getMallId()), NO_PERMISSION);
        return ResultJson.success(saleOrderDbGateway.deliveryOrderList(id));
    }

    @ApiOperation("订单详情-包裹清单")
    @GetMapping("saleOrders/{id}/delivery/{deliveryId}/packages")
    public ResultJson<List<CustomerDeliveryPackageDTO>> deliveryPackageList(@PathVariable Integer id, @PathVariable Integer deliveryId) {
        SaleOrder saleOrder = SaleOrder.instanceOf(id);
        Assert.isTrue(saleOrder.getCustomerId().equals(OperatorUtil.getPlatformId()) && saleOrder.getMallId().equals(OperatorUtil.getMallId()), NO_PERMISSION);
        return ResultJson.success(saleOrderDbGateway.deliveryPackageList(id, deliveryId));
    }

    @ApiOperation("订单详情-追踪记录")
    @GetMapping("saleOrders/{id}/flow")
    public ResultJson<List<SaleOrderFlowDTO>> saleOrderFlow(@PathVariable Integer id) {
        SaleOrder saleOrder = SaleOrder.instanceOf(id);
        Assert.isTrue(saleOrder.getCustomerId().equals(OperatorUtil.getPlatformId()) && saleOrder.getMallId().equals(OperatorUtil.getMallId()), NO_PERMISSION);
        return ResultJson.success(saleOrderDbGateway.saleOrderFlow(id));
    }

    @ApiOperation("取消订单")
    @PutMapping("saleOrders/{id}/cancel")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<Void> cancelSaleOrder(@PathVariable Integer id, @Validated @RequestParam @Length(max = 100) String reason) {
        SaleOrder saleOrder = SaleOrder.instanceOf(id);
        Assert.isTrue(saleOrder.getCustomerId().equals(OperatorUtil.getPlatformId()) && saleOrder.getMallId().equals(OperatorUtil.getMallId()), NO_PERMISSION);
        saleOrder.cancel(reason).save();
        SaleOrderFlow.newCustomerCancelRecord(id);
        //释放库存
        Sku.releaseStock(saleOrder.getSkuQtyMap());
        return ResultJson.success();
    }

    @ApiOperation("确认收货")
    @PutMapping("saleOrders/{id}/delivery")
    public ResultJson<Void> confirmSaleOrder(@PathVariable Integer id, @RequestBody(required = false) List<Integer> deliveryOrderIds) {
        SaleOrder saleOrder = SaleOrder.instanceOf(id);
        Assert.isTrue(saleOrder.getCustomerId().equals(OperatorUtil.getPlatformId()) && saleOrder.getMallId().equals(OperatorUtil.getMallId()), NO_PERMISSION);
        List<DeliveryOrder> deliveryOrders = DeliveryOrder.instanceOf(id);
        if (deliveryOrderIds != null && !deliveryOrderIds.isEmpty()) {
            deliveryOrders = deliveryOrders.stream().filter(e -> deliveryOrderIds.contains(e.getDeliveryNoteId())).collect(Collectors.toList());
        }
        deliveryOrders.forEach(deliveryOrder -> {
            deliveryOrder.confirm().save();
            deliveryOrder.getPackages().forEach(deliveryPackage ->
                    saleOrder.updateReceivedQuantity(deliveryPackage.getSaleOrderItemId(), deliveryPackage.getQuantity())
            );
            SaleOrderFlow.newCustomerConfirmReceiveRecord(id, deliveryOrder.getDeliverySn());
        });
        saleOrder.save();
        if (saleOrder.isAllReceived()) {
            SaleOrderFlow.newAllReceiveRecord(id);
        }
        return ResultJson.success();
    }

    @ApiOperation("收货地址列表")
    @GetMapping("address/list")
    public ResultJson<List<CustomerAddressDTO>> addressList() {
        Integer customerId = OperatorUtil.getPlatformId();
        Integer mallId = OperatorUtil.getMallId();
        List<CustomerAddressDTO> customerAddresses = mallDbGateway.listCustomerAddress(customerId, mallId);
        return ResultJson.success(customerAddresses);
    }

    @ApiOperation("新建或编辑收货地址")
    @PostMapping("address")
    public ResultJson<CustomerAddressDTO> createAddress(@Validated @RequestBody CustomerAddressCreateDTO dto) {
        dto.setCustomerId(OperatorUtil.getPlatformId());
        dto.setMallId(OperatorUtil.getMallId());
        CustomerAddress customerAddress = CustomerAddress.newInstance(dto);
        return ResultJson.success(customerAddress.getDTO());
    }

    @ApiOperation("删除收货地址")
    @DeleteMapping("address/{id}")
    public ResultJson<Integer> deleteAddress(@PathVariable Integer id) {
        CustomerAddress customerAddress = CustomerAddress.instanceOf(id, OperatorUtil.getPlatformId());
        customerAddress.delete();
        return ResultJson.success(1);
    }

    @ApiOperation("home_page_category")
    @PostMapping("/homepage/category")
    @NoLogin
    public ResultJson<Integer> setHomePageCategorySku(@Validated @RequestBody HomePageSkuUpdateDTO dto) {
        // convert方法中，默认将输入的phone作为账号名
        HomePageSkuData homePageSkuData = Convert.INSTANCE.convert(dto);
        HomePageSku homePageSku = HomePageSku.newInstance(homePageSkuData);
        log.info("creatAccount success");
        return ResultJson.success(homePageSku.getId());
    }
}
