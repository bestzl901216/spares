package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户收货地址
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "customer_address", autoResultMap = true)
public class CustomerAddressDO extends BaseDO {
    private Integer customerId;
    private Integer mallId;
    private String pcaCode;
    private String address;
    private String receiver;
    private String phone;
    private Boolean isDefault;
    private String province;
    private String city;
    private String area;
    private String addressFull;
}
