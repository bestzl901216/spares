package org.geely.domain.common.data;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 物流信息
 * <p>
 * 物流状态值	物流状态名称	高级物流状态值	高级物流状态名称	含义
 * 1	揽收	1	揽收	快件揽件
 * 101	已下单	已经下快件单
 * 102	待揽收	待快递公司揽收
 * 103	已揽收	快递公司已经揽收
 * 0	在途	0	在途	快件在途中
 * 1001	到达派件城市	快件到达收件人城市
 * 1002	干线	快件处于运输过程中
 * 1003	转递	快件发往到新的收件地址
 * 5	派件	5	派件	快件正在派件
 * 501	投柜或驿站	快件已经投递到快递柜或者快递驿站
 * 3	签收	3	签收	快件已签收
 * 301	本人签收	收件人正常签收
 * 302	派件异常后签收	快件显示派件异常，但后续正常签收
 * 303	代签	快件已被代签
 * 304	投柜或站签收	快件已从快递柜或者驿站取出签收
 * 6	退回	6	退回	快件正处于返回发货人的途中
 * 4	退签	4	退签	此快件单已退签
 * 401	已销单	此快件单已撤销
 * 14	拒签	收件人拒签快件
 * 7	转投	7	转投	快件转给其他快递公司邮寄
 * 2	疑难	2	疑难	快件存在疑难
 * 201	超时未签收	快件长时间派件后未签收
 * 202	超时未更新	快件长时间没有派件或签收
 * 203	拒收	收件人发起拒收快递,待发货方确认
 * 204	派件异常	快件派件时遇到异常情况
 * 205	柜或驿站超时未取	快件在快递柜或者驿站长时间未取
 * 206	无法联系	无法联系到收件人
 * 207	超区	超出快递公司的服务区范围
 * 208	滞留	快件滞留在网点，没有派送
 * 209	破损	快件破损
 * 210	销单	寄件人申请撤销寄件
 * 8	清关	8	清关	快件清关
 * 10	待清关	快件等待清关
 * 11	清关中	快件正在清关流程中
 * 12	已清关	快件已完成清关流程
 * 13	清关异常	货物在清关过程中出现异常
 * 14	拒签	\	\	收件人拒签快件
 *
 * @author ricardo zhou
 */
@Data
public class TrackData implements Serializable {
    /**
     * 消息体，请忽略
     */
    private String message;
    /**
     * 快递单当前状态，默认为0在途，1揽收，2疑难，3签收，4退签，5派件，8清关，14拒签等10个基础物流状态，如需要返回高级物流状态，请参考 resultv2 传值
     */
    private String state;
    /**
     * 通讯状态，请忽略
     */
    private String status;
    /**
     * 快递单明细状态标记，暂未实现，请忽略
     */
    private String condition;
    /**
     * 是否签收标记，0未签收，1已签收，请忽略，明细状态请参考state字段
     */
    private String ischeck;
    /**
     * 快递公司编码,一律用小写字母
     */
    private String com;
    /**
     * 单号
     */
    private String nu;
    /**
     * 最新查询结果，数组，包含多项，全量，倒序（即时间最新的在最前），每项都是对象，对象包含字段请展开
     */
    private List<TrailNodeData> data;


    @Data
    public static class TrailNodeData implements Serializable {
        /**
         * 时间，原始格式
         */
        private String time;
        /**
         * 格式化后时间
         */
        private String ftime;
        /**
         * 内容
         */
        private String context;
        /**
         * 本数据元对应的快件当前地点，实时查询接口中提交resultv2=4标记后才会出现
         */
        private String location;
        /**
         * 本数据元对应的行政区域的编码，实时查询接口中提交resultv2=1或者resultv2=4标记后才会出现
         */
        private String areaCode;
        /**
         * 本数据元对应的行政区域的名称，实时查询接口中提交resultv2=1或者resultv2=4标记后才会出现
         */
        private String areaName;
        /**
         * 本数据元对应的行政区域拼音，实时查询接口中提交resultv2=4标记后才会出现
         */
        private String areaPinYin;
        /**
         * 本数据元对应的行政区域经纬度，实时查询接口中提交resultv2=4标记后才会出现
         */
        private String areaCenter;
        /**
         * 本数据元对应的高级物流状态值，实时查询接口中提交resultv2=4标记后才会出现
         */
        private String statusCode;
        /**
         * 本数据元对应的物流状态名称或者高级状态名称，实时查询接口中提交resultv2=1或者resultv2=4标记后才会出现
         */
        private String status;
    }
}
