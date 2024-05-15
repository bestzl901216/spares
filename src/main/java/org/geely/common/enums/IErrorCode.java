package org.geely.common.enums;

import org.geely.common.constants.BizConstants;
import org.springframework.util.ObjectUtils;

/**
 * @author wanbing.shi
 * @date 2023-09-04
 * @desc
 **/
public interface IErrorCode {
    /**
     * 返回错误码
     *
     * @return 错误码
     */
    String getCode();

    /**
     * 返回错误信息
     *
     * @return 错误信息
     */
    String getMessage();

    /**
     * 返回格式化的错误信息
     * @param args 格式化参数
     * @return 错误信息
     */
    default String getFormattedMessage(Object... args) {
        String message = getMessage();
        if (ObjectUtils.isEmpty(message)) {
            return message;
        }

        if (ObjectUtils.isEmpty(args)) {
            return message;
        }

        StringBuilder sb = new StringBuilder();
        int index;
        for (Object arg : args) {
            index = message.indexOf(BizConstants.LOG_DELIMITER);
            if (index == -1) {
                sb.append(message);
                break;
            } else {
                sb.append(message.substring(0, index)).append(arg);
                message = message.substring(index + BizConstants.LOG_DELIMITER.length());
            }
        }

        return sb.toString();
    }
}
