package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.geely.common.enums.EnableStateEnum;

/**
 * 商城轮播广告
 *
 * @author cong huang
 */
@Data
@TableName(value = "mall_banner", autoResultMap = true)
public class MallBannerDO extends BaseDO {
    private Integer mallId;
    private String title;
    private String imageUrl;
    private String actionUrl;
    private EnableStateEnum state;
    private Integer sort;
}
