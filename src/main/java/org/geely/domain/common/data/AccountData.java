package org.geely.domain.common.data;

import lombok.Data;
import org.geely.common.enums.EnableStateEnum;

import java.io.Serializable;

/**
 * @author ricardo zhou
 */
@Data
public class AccountData implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 名字
     */
    private String name;
    /**
     * 密码（明文）
     */
    private String password;
    /**
     * 密码（密文）
     */
    private String encodedPassword;
    /**
     * 电话
     */
    private String phone;
    /**
     * 版本号
     */
    private Integer version;
    private String icon;
    private EnableStateEnum state;
}
