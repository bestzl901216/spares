package org.geely.common.model;

import lombok.Data;

import java.util.Date;

@Data
public class OperateLogDTO {


    // 用户名
    private String username;

    // 显示名
    private String displayName;

    private String appId;

    // 所属模块 用户管理
    private String module;

    // 操作名称：如用户删除
    private String operateName;

    // 请求参数
    private String requestBody;

    // 操作结果 success-成功 fail-失败
    private String result;

    // 响应参数
    private String responseBody;

    // @See OperateType
    private String type;

    private Date createTime;
}
