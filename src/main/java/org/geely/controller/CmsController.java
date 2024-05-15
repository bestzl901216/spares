package org.geely.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.annotation.NoLogin;
import org.geely.common.constants.MvcConstant;
import org.geely.common.model.ResultJson;
import org.geely.common.utils.OperatorUtil;
import org.geely.controller.dto.NoticeDTO;
import org.geely.controller.dto.PlatformDTO;
import org.geely.domain.common.Notice;
import org.geely.domain.support.data.RoleData;
import org.geely.infrastructure.db.gateway.ConsoleDbGateway;
import org.geely.infrastructure.oss.OssGateway;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * @author cong huang
 */
@Api(value = "平台通用接口", tags = "平台通用接口")
@Slf4j
@RestController
@NoLogin
@RequestMapping(MvcConstant.CMS)
public class CmsController {

    @Resource
    private ConsoleDbGateway consoleDbGateway;
    @Resource
    private OssGateway ossGateway;

    @ApiOperation("平台列表")
    @GetMapping("platformList")
    public ResultJson<List<PlatformDTO>> platformList() {
        Integer accountId = OperatorUtil.getAccountId();
        return ResultJson.success(consoleDbGateway.platformList(accountId));
    }

    @ApiOperation("消息分页")
    @GetMapping("notice")
    public ResultJson<Page<NoticeDTO>> noticePage(@RequestParam Long current,
                                                  @RequestParam Long size) {
        Page<NoticeDTO> page = Page.of(current, size);
        RoleData roleData = OperatorUtil.getRoleData();
        consoleDbGateway.noticePage(page, roleData.getPlatformType().getId(), roleData.getPlatformId());
        return ResultJson.success(page);
    }

    @ApiOperation("批量已读消息")
    @PutMapping("notice")
    public ResultJson<Integer> updateNoticeState() {
        RoleData roleData = OperatorUtil.getRoleData();
        Notice.batchRead(roleData.getPlatformType(), roleData.getPlatformId());
        return ResultJson.success(0);
    }

    @ApiOperation("上传图片")
    @PostMapping("imageUpload")
    public ResultJson<String> imageUpload(@RequestParam(required = false, defaultValue = "common")
                                          String category,
                                          @RequestPart(required = false)
                                          MultipartFile file) throws IOException {
        Assert.isTrue(file != null, "请选择要上传的图片");
        Assert.isTrue(file.getSize() <= 1024 * 1024 * 10, "图片大小不能大于10M");

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        Assert.isTrue("png,jpg,jpeg,gif".toUpperCase().contains(suffix.toUpperCase()), "上传的图片格式不支持【支持格式png, jpg, jpeg, gif");

        String fileName = UUID.randomUUID().toString() + "." + suffix;
        String imageUrl = ossGateway.upload(file.getInputStream(), category + "/" + fileName);

        return ResultJson.success(imageUrl);
    }
}
