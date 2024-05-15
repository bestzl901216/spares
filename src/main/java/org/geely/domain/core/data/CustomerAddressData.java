package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class CustomerAddressData implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 客户编号
     */
    private Integer customerId;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 是否默认收货地址
     */
    private Boolean isDefault;
    /**
     * 版本号
     */
    private Integer version;
    private Integer mallId;
    private String province;
    private String city;
    private String area;
}
