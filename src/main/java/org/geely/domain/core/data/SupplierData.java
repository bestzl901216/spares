package org.geely.domain.core.data;

import lombok.Data;
import org.geely.common.enums.EnableStateEnum;
import org.geely.controller.dto.SupplierCreateDTO;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class SupplierData implements Serializable {
    private Integer id;
    private Integer mallId;
    private String name;
    private Integer sapId;
    private EnableStateEnum state;
    private Integer bankAccountId;
    private String merchantNo;
    private Integer version;
    public SupplierData() {

    }
    public SupplierData(SupplierCreateDTO dto) {
        if(dto != null) {
            name = dto.getName();
            state = EnableStateEnum.ENABLE;
            merchantNo = dto.getMerchantNo();
            sapId = dto.getSapId();
            bankAccountId = dto.getBankAccountId();
        }
    }
}
