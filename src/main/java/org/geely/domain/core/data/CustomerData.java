package org.geely.domain.core.data;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.geely.common.enums.EnableStateEnum;
import org.geely.common.exception.BizException;

import java.io.Serializable;
import java.util.List;

/**
 * @author cong huang
 */
@Data
public class CustomerData implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 客户名称
     */
    private String name;
    /**
     * 客户类型，1=企业，2=个人
     */
    private TypeEnum type;
    /**
     * 公司税号或个人身份证
     */
    private String identitySn;
    /**
     * 省市区编码
     */
    private String pcaCode;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 营业执照或身份证照片url
     */
    private String identityImage;
    /**
     * 门头照片url
     */
    private String shopImage;
    /**
     * 工作室照片url
     */
    private List<String> workroomImage;
    /**
     * 渠道id
     */
    private Integer marketChannelId;
    /**
     * 支付密码
     */
    private String payPassword;
    /**
     * 状态，0=禁用，1=启用
     */
    private EnableStateEnum state;
    /**
     * 认证状态
     */
    private CertifyStateEnum certifyState;
    /**
     * 版本号
     */
    private Integer version;
    private String province;
    private String city;
    private String area;

    @Getter
    @AllArgsConstructor
    public enum CertifyStateEnum {
        UNFINISHED(0, "未认证"),
        FINISHED(1, "已认证"),
        PENDING(2, "待审核"),
        FAILED(2, "审核失败");

        @EnumValue
        private final Integer id;
        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum TypeEnum {
        /**
         * 个人
         */
        PERSONAL(0, "个人"),
        /**
         * 企业
         */
        ENTERPRISE(1, "企业");
        @EnumValue
        private final Integer id;
        private final String name;

        public static TypeEnum getById(Integer id) {
            for (TypeEnum value : values()) {
                if (value.id.equals(id)) {
                    return value;
                }
            }
            throw new BizException("error CustomerType", "客户类型不存在");
        }
    }
}
