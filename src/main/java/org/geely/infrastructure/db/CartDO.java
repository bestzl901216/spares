package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "cart", autoResultMap = true)
public class CartDO extends BaseDO {
    private Integer mallId;
    private Integer customerId;
    private Integer accountId;
    private Integer skuId;
    private Integer quantity;
    private Boolean selected;
}
