package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.EnableStateEnum;
import org.geely.domain.core.data.CustomerData;

import java.util.List;

/**
 * 客户
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "customer", autoResultMap = true)
public class CustomerDO extends BaseDO {
    private String name;
    private CustomerData.TypeEnum type;
    private String identitySn;
    private String pcaCode;
    private String address;
    private String identityImage;
    private String shopImage;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> workroomImage;
    private Integer marketChannelId;
    private String payPassword;
    private EnableStateEnum state;
    private CustomerData.CertifyStateEnum certifyState;
    private String province;
    private String city;
    private String area;
}
