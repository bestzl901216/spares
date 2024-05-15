package org.geely.common.model;

import lombok.Data;

@Data
public class OrgUserDTO {

    private String postName;

    private String postNo;

    private String postType;

    /**
     * 职位：目前仅用于dms
     */
    private String post;

    /**
     * 是否管理cep
     * */
    private String isAdmin;

    /**
     * 主管id
     */
    private String supervisorId;

    /**
     * 用户外部id
     */
    private String externalId;

    private String username;

    private String phoneNumber;

    private String email;

    private int locked;

    /**
     * 启用状态 （1，0）
     */
    private String enabled;

    private String belongs;

    private String createTime;

    private String updatedTime;

    private Boolean archived;

    /**
     * 组织 id
     */
    private  String ouId;

    private String displayName;

    /**
     * 用户uuid
     */
    private String uuid;

    /**
     * 域帐号
     */
    private String fieldAccount;

    /**
     * 是否删除
     */
    private int isDelete;
}
