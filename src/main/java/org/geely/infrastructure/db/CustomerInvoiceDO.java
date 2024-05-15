package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.geely.common.enums.InvoiceTypeEnum;

/**
 * 客户发票
 *
 * @author cong huang
 */
@Data
@TableName(value = "customer_invoice", autoResultMap = true)
public class CustomerInvoiceDO extends BaseDO {
    private Integer customerId;
    private InvoiceTypeEnum invoiceType;
    private String title;
    private String taxSn;
    private String address;
    private String phone;
    private String bank;
    private String bankAccount;
    private String receiver;
    private String receiverPhone;
    private String receiverAddress;
}
