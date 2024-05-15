package org.geely.domain.support;

import cn.hutool.extra.spring.SpringUtil;
import org.geely.common.enums.PayTypeEnum;
import org.geely.common.utils.OperatorUtil;
import org.geely.domain.support.data.SaleOrderFlowData;
import org.geely.infrastructure.db.repository.SupportDomainRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * 订单跟踪记录
 *
 * @author ricardo zhou
 */
public class SaleOrderFlow {
    private SaleOrderFlowData data;

    private SaleOrderFlow(SaleOrderFlowData data) {
        this.data = data;
    }

    public static SaleOrderFlow newInstance(Integer saleOrderId, String content, List<String> images) {
        SaleOrderFlowData data = new SaleOrderFlowData();
        data.setSaleOrderId(saleOrderId);
        data.setAccountId(OperatorUtil.getAccountId());
        data.setRoleName(OperatorUtil.getRoleData().getName());
        data.setContent(content);
        data.setImages(images == null ? new ArrayList<>() : images);
        data.setVersion(0);

        SaleOrderFlow saleOrderFlow = new SaleOrderFlow(data);
        saleOrderFlow.save();
        return saleOrderFlow;
    }

    /**
     * 创建订单
     *
     * @param saleOrderId 订单id
     * @return 订单提交记录
     */
    public static SaleOrderFlow newConfirmRecord(Integer saleOrderId) {
        return newInstance(saleOrderId, "买家提交订单", null);
    }

    /**
     * 买家取消订单
     *
     * @param saleOrderId 订单id
     * @return 订单取消记录
     */
    public static SaleOrderFlow newCustomerCancelRecord(Integer saleOrderId) {
        return newInstance(saleOrderId, "买家取消订单", null);
    }

    /**
     * 卖家取消订单
     *
     * @param saleOrderId 订单id
     * @return 订单取消记录
     */
    public static SaleOrderFlow newSellerCancelRecord(Integer saleOrderId) {
        return newInstance(saleOrderId, "卖家取消订单", null);
    }

    /**
     * 非线下付款
     *
     * @param saleOrderId 订单id
     * @param payType     支付方式
     * @param sn          流水号
     * @return 订单支付记录
     */
    public static SaleOrderFlow newOnlinePayRecord(Integer saleOrderId, PayTypeEnum payType, String sn) {
        return newInstance(saleOrderId, String.format("买家付款，支付方式：%s，流水号：%s", payType.getName(), sn), null);
    }

    /**
     * 线下付款
     *
     * @param saleOrderId 订单id
     * @param sn          流水号
     * @param images      支付凭证
     * @return 订单支付记录
     */
    public static SaleOrderFlow newOfflinePayRecord(Integer saleOrderId, String sn, List<String> images) {
        return newInstance(saleOrderId, String.format("买家提交线下付款支付凭证（批次号：%s）", sn), images);
    }

    /**
     * 确认线下收款
     *
     * @param saleOrderId 订单id
     * @param sn          流水号
     * @return 订单收款记录
     */
    public static SaleOrderFlow newConfirmOfflinePayRecord(Integer saleOrderId, String sn) {
        return newInstance(saleOrderId, String.format("确认线下付款（批次号：%s）收款成功", sn), null);
    }

    /**
     * 线下收款失败
     *
     * @param saleOrderId 订单id
     * @param sn          流水号
     * @return 订单收款失败记录
     */
    public static SaleOrderFlow newOfflinePayFailRecord(Integer saleOrderId, String sn, String reason) {
        return newInstance(saleOrderId, String.format("线下付款（批次号：%s）收款失败（审核备注：%s）", sn, reason), null);
    }

    /**
     * 同步失败店铺退款
     *
     * @param saleOrderId 订单id
     * @return 订单退款记录
     */
    public static SaleOrderFlow newSyncFailShopRefundRecord(Integer saleOrderId) {
        return newInstance(saleOrderId, "商家已退款", null);
    }

    /**
     * 发货
     *
     * @param saleOrderId 订单id
     * @param deliverySn  发货单号
     * @return 订单发货记录
     */
    public static SaleOrderFlow newDeliveryRecord(Integer saleOrderId, String deliverySn) {
        return newInstance(saleOrderId, String.format("商家发货，发货单号：%s", deliverySn), null);
    }

    /**
     * 所有商品发货完成
     *
     * @param saleOrderId 订单id
     * @return 订单发货记录
     */
    public static SaleOrderFlow newAllDeliveryRecord(Integer saleOrderId) {
        return newInstance(saleOrderId, "商家发货完成", null);
    }

    /**
     * 买家确认收货
     *
     * @param saleOrderId 订单id
     * @param deliverySn  发货单号
     * @return 订单收货记录
     */
    public static SaleOrderFlow newCustomerConfirmReceiveRecord(Integer saleOrderId, String deliverySn) {
        return newInstance(saleOrderId, String.format("买家确认收货，发货单号：%s", deliverySn), null);
    }

    /**
     * 所有商品收货完成
     *
     * @param saleOrderId 订单id
     * @return 订单收货记录
     */
    public static SaleOrderFlow newAllReceiveRecord(Integer saleOrderId) {
        return newInstance(saleOrderId, "买家收货完成", null);
    }

    /**
     * 已过售后期
     *
     * @param saleOrderId 订单id
     * @return 订单完成记录
     */
    public static SaleOrderFlow newAfterSalePeriodRecord(Integer saleOrderId) {
        return newInstance(saleOrderId, "售后保障已过期，订单完成", null);
    }

    public void save() {
        this.data = SpringUtil.getBean(SupportDomainRepository.class).saveSaleOrderFlow(this.data);
    }


}
