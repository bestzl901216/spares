package org.geely.infrastructure.express;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.kuaidi100.sdk.api.QueryTrack;
import com.kuaidi100.sdk.core.IBaseClient;
import com.kuaidi100.sdk.request.QueryTrackParam;
import com.kuaidi100.sdk.request.QueryTrackReq;
import com.kuaidi100.sdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.exception.BizException;
import org.geely.domain.common.data.TrackData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ricardo zhou
 */
@Slf4j
@Component
public class ExpressGateway {

    /**
     * 授权码，在快递100平台请申请企业版获取
     */
    @Value("${kuaidi100.customer}")
    private String customer;
    /**
     * 授权key
     */
    @Value("${kuaidi100.key}")
    private String key;

    /**
     * 实时快递查询
     *
     * @param company 查询的快递公司的编码， 一律用小写字母
     * @param shipSn  查询的快递单号， 单号的最小长度6个字符，最大长度32个字符
     * @param phone   收、寄件人的电话号码（手机和固定电话均可，只能填写一个，顺丰速运、顺丰快运必填，其他快递公司选填。如座机号码有分机号，分机号无需传入；如号码是电商虚拟号码需传入“-“后的后四位）
     * @return 快递信息
     */
    public TrackData queryTrack(String company, String shipSn, String phone) {
        QueryTrackParam queryTrackParam = new QueryTrackParam();
        queryTrackParam.setCom(company);
        queryTrackParam.setNum(shipSn);
        queryTrackParam.setPhone(phone);
        queryTrackParam.setResultv2("4");

        String param = JSON.toJSONString(queryTrackParam);
        QueryTrackReq queryTrackReq = new QueryTrackReq();
        queryTrackReq.setParam(param);
        queryTrackReq.setCustomer(customer);
        queryTrackReq.setSign(SignUtils.querySign(param, key, customer));

        IBaseClient baseClient = new QueryTrack();
        TrackData trackData;
        try {
            trackData = JSON.parseObject(baseClient.execute(queryTrackReq).getBody(), TrackData.class);
        } catch (Exception e) {
            log.error("快递查询服务调用失败", e);
            throw new BizException("ExpressGateway queryTrack error", "快递查询服务调用失败");
        }
        Assert.equals("200", trackData.getStatus(), trackData.getMessage());
        return trackData;
    }
}
