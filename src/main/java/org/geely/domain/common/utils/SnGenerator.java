package org.geely.domain.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;

/**
 * @author cong huang
 */
@UtilityClass
public class SnGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    public static String get(String prefix)
    {
        int random = RANDOM.nextInt(1000);
        return prefix + System.currentTimeMillis() + StringUtils.leftPad(String.valueOf(random), 3, '0');
    }
}
