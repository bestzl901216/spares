package org.geely.common.config;

import lombok.extern.slf4j.Slf4j;
import org.geely.common.exception.BizException;
import org.geely.common.model.ResultJson;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;

/**
 * @author ricardo zhou
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultJson<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[全局异常处理] [参数校验不通过]{}", e.getMessage(), e);
        return ResultJson.fail("参数校验不通过", e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse("")).findFirst().orElse(""));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResultJson<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("[全局异常处理] [参数校验类型不匹配]{}", e.getMessage(), e);
        return ResultJson.fail("参数校验类型不匹配");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultJson<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("[全局异常处理] [请求方式错误]{}", e.getMessage(), e);
        return ResultJson.fail("请求方式错误");
    }

    @ExceptionHandler(BizException.class)
    public ResultJson<String> handleBizException(BizException e) {
        log.error("[全局异常处理] [业务异常]{}", e.getMessage(), e);
        return ResultJson.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultJson<String> handleRuntimeException(RuntimeException e) {
        log.error("[全局异常处理] [运行异常]{}", e.getMessage(), e);
        return ResultJson.fail(e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResultJson<String> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("[全局异常处理] [唯一索引异常]{}", e.getMessage(), e);
        return ResultJson.fail("违反业务唯一约束");
    }
}
