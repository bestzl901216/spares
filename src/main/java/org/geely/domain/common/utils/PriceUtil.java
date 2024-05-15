package org.geely.domain.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author cong huang
 */
public class PriceUtil {

    private PriceUtil() {
    }

    /**
     * @param originPrice 原始价格
     * @param markupRate  加价率
     * @return 加价后的价格
     */
    public static BigDecimal getMarkupPrice(BigDecimal originPrice, BigDecimal markupRate) {
        if (markupRate == null || markupRate.equals(BigDecimal.valueOf(0))) {
            return originPrice;
        }
        return markupRate.multiply(originPrice).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP).add(originPrice).setScale(2, RoundingMode.HALF_UP);
    }
}
