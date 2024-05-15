package org.geely.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.geely.common.constants.BizConstants;
import org.geely.common.enums.IErrorCode;

import java.io.Serializable;

/**
 * @author wanbing.shi
 * @date 2023-09-04
 * @desc Restful response统一返回格式封装
 **/
@Getter
public class ResultJson<T> implements Serializable {

    private static final long serialVersionUID = 7521254814094321234L;

    /**
     * 状态码
     */
    @ApiModelProperty(value = "返回编码")
    private final String code;

    /**
     * 信息
     */
    @ApiModelProperty(value = "返回信息")
    private final String message;

    /**
     * 数据
     */
    @ApiModelProperty(value = "返回数据")
    private final T data;


    public ResultJson(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public  static <T> ResultJson<T> fail(IErrorCode errorCode) {
        return new ResultJson<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回
     *
     * @param code      返回编码
     * @param message   返回信息
     */
    public static <T> ResultJson<T> fail(String code, String message) {
        return new ResultJson<>(code, message, null);
    }

    /**
     * 失败返回
     *
     * @param message   返回信息
     */
    public  static <T> ResultJson<T> fail(String message) {
        return new ResultJson<>(BizConstants.FAIL_CODE, message, null);
    }

    /**
     * 失败返回
     */
    public static <T> ResultJson<T> fail() {
        return new ResultJson<>(BizConstants.FAIL_CODE, BizConstants.FAIL_MESSAGE, null);
    }

    /**
     * 成功返回
     */
    public static <T> ResultJson<T> success() {
        return new ResultJson<>(BizConstants.SUCCESS_CODE, BizConstants.SUCCESS_MESSAGE, null);
    }

    /**
     * 成功返回
     *
     * @param data  返回数据
     */
    public static <T> ResultJson<T> success(T data) {
        return new ResultJson<>(BizConstants.SUCCESS_CODE, BizConstants.SUCCESS_MESSAGE, data);
    }

    /**
     * 判断请求是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return BizConstants.SUCCESS_CODE.equals(this.getCode());
    }

}
