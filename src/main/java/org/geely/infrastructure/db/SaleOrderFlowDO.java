package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 账号
 *
 * @author ricardo zhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sale_order_flow", autoResultMap = true)
public class SaleOrderFlowDO extends BaseDO {
    private Integer saleOrderId;
    private Integer accountId;
    private String roleName;
    private String content;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> images;
}
