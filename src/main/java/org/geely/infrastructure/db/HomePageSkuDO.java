package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户收货地址
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "home_page_sku", autoResultMap = true)
public class HomePageSkuDO extends BaseDO {
    private Integer mallId;
    private Integer skuId;
    private String c1Id;
    private String sort;
}
