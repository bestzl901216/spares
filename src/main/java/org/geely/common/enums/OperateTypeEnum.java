package org.geely.common.enums;

import lombok.Getter;

@Getter
public enum OperateTypeEnum {
    LOGIN("登录"),
    LOGOUT("登出"),
    ADD("新增"),
    UPDATE("修改"),
    ENABLE("启用"),
    DISABLE("停用"),
    APPROVE("审批"),
    UPLOAD("上传"),
    DOWNLOAD("下载"),
    SHARE("分享"),
    DELETE("删除"),
    OTHER("其他"),
    ;

    private String desc;


    private OperateTypeEnum(String desc) {
        this.desc = desc;
    }

}