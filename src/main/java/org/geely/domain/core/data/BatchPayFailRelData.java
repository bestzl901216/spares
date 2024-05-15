package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class BatchPayFailRelData implements Serializable {
    private Integer id;
    private Integer batchId;
    private Integer saleOrderId;
    private Integer version;
}
