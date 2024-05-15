package org.geely.controller.common;

import com.geely.gsapi.GSAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.geely.common.model.OrgChildDTO;
import org.geely.common.model.OrgDetailDTO;
import org.geely.common.model.ResultJson;
import org.geely.service.OrganizationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织机构控制器
 *
 * @author yancheng.guo
 */
@Api(value = "组织机构接口", tags = "组织机构接口")
@RestController
@RequestMapping("/org")
@Validated
public class OrganizationController {

    @Resource
    private OrganizationService organizationService;

    @ApiOperation("查询组织详情")
    @GetMapping(value = "/getOrgDetail")
    public ResultJson<OrgDetailDTO> getOrgDetail(

            @ApiParam(name = "externalId", required = true, value = "组织externalId")
            @RequestParam(name = "externalId", required = true) String externalId) {
        externalId = GSAPI.getSafeDirInstance().verifyNonNullString(externalId);
        OrgDetailDTO orgDetailDTO = organizationService.getOrgDetail(externalId);
        return ResultJson.success(orgDetailDTO);
    }

    @ApiOperation("查询子组织")
    @GetMapping(value = "/getChildOrg")
    public ResultJson<List<OrgChildDTO>> getChildOrg(

            @ApiParam(name = "ouUuid", required = false, value = "组织uuid")
            @RequestParam(name = "ouUuid", required = false) String ouUuid) {
        ouUuid = GSAPI.getSafeDirInstance().verifyNonNullString(ouUuid);
        List<OrgChildDTO> list = organizationService.getChildOrg(ouUuid);
        return ResultJson.success(list);
    }

}
