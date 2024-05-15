package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class ProvinceCityAreaData implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * code
     */
    private String code;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
}
