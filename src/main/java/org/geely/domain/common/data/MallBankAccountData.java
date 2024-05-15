package org.geely.domain.common.data;

import lombok.Data;
import org.geely.controller.dto.MallBankAccountCreateDTO;

import java.io.Serializable;

/**
 * @author cong huang
 */
@Data
public class MallBankAccountData implements Serializable {
    private Integer id;
    private Integer mallId;
    private String name;
    private String bank;
    private String bankAccount;
    private Integer version;

    public MallBankAccountData() {

    }

    public MallBankAccountData(MallBankAccountCreateDTO dto) {
        if (dto != null) {
            this.name = dto.getName();
            this.bank = dto.getBank();
            this.bankAccount = dto.getBankAccount();
        }
    }
}
