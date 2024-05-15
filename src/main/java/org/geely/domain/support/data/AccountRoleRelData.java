package org.geely.domain.support.data;

import lombok.Data;
import org.geely.common.enums.EnableStateEnum;

import java.io.Serializable;

/**
 * @author ricardo zhou
 */
@Data
public class AccountRoleRelData implements Serializable {
    private Integer id;
    private Integer accountId;
    private Integer roleId;
    private EnableStateEnum state;
    private Integer version;
}
