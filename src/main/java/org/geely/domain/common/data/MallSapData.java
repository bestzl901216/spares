package org.geely.domain.common.data;

import lombok.Data;
import org.geely.common.enums.EnableStateEnum;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class MallSapData implements Serializable {
    private Integer id;
    private Integer mallId;
    private String name;
    private EnableStateEnum state;
    private Integer version;
}
