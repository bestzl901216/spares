package org.geely.domain.common.data;

import lombok.Data;
import org.geely.domain.core.data.MallData;
import org.geely.domain.support.data.RoleData;

import java.io.Serializable;
import java.util.Set;

/**
 * @author ricardo zhou
 */
@Data
public class SessionData implements Serializable {
    private AccountData accountData;
    private Set<RoleData> roleDataSet;
    private Set<MallData> mallDataSet;
}
