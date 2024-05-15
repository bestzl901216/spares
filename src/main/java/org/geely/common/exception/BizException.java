package org.geely.common.exception;

import lombok.Getter;
import org.geely.common.enums.IErrorCode;

/**
 * @author wanbing.shi
 * @date 2023-09-04
 * @desc 统一业务异常定义
 **/
@Getter
public class BizException extends RuntimeException {

    /**
     * 状态码
     */
    private final String code;

    /**
     * 信息
     */
    private final String message;

    /**
     * 异常信息
     */
    private final Throwable throwable;

    public BizException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.throwable = null;
    }

    public BizException(IErrorCode errorCode, Object ... args) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getFormattedMessage(args);
        this.throwable = null;
    }

    public BizException(IErrorCode errorCode, Throwable throwable) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.throwable = throwable;
    }

    public BizException(IErrorCode errorCode, Throwable throwable, Object ... args) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getFormattedMessage(args);
        this.throwable = throwable;
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.throwable = null;
    }

    public BizException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
        this.throwable = throwable;
    }
}
