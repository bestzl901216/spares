package org.geely.domain.common.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ricardo zhou
 */
@Data
public class SearchHistoryData implements Serializable {
    private Integer id;
    private Integer mallId;
    private Integer customerId;
    private Integer accountId;
    private String keyword;
    private Integer version;
}
