package org.geely.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 67091
 */
@Data
public class OrgChildDTO {

    @ApiModelProperty("组织名称")
    private String organizationName;

    @ApiModelProperty("外部id")
    private String externalId;

    @ApiModelProperty("组织uuid")
    private String uuid;
}
