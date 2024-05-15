package org.geely.domain.support.data;

import lombok.Data;

import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
public class SaleOrderFlowData {
    private Integer id;
    private Integer saleOrderId;
    private Integer accountId;
    private String roleName;
    private String content;
    private List<String> images;
    private Integer version;
}
