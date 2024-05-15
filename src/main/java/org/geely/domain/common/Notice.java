package org.geely.domain.common;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.domain.common.data.NoticeData;
import org.geely.infrastructure.db.repository.CommonDomainRepository;

import java.time.LocalDateTime;

/**
 * 通知
 *
 * @author ricardo zhou
 */
public class Notice {
    private NoticeData data;

    private Notice(NoticeData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static Notice newInstance(String title, String content) {
        NoticeData data = new NoticeData();
        data.setTitle(title);
        data.setContent(content);
        data.setTime(LocalDateTime.now());
        data.setState(NoticeData.StateEnum.UNREAD);
        data.setVersion(0);
        Notice notice = new Notice(data);
        notice.save();
        return notice;
    }

    public static void batchRead(PlatformTypeEnum platformType, Integer platformId) {
        SpringUtil.getBean(CommonDomainRepository.class).batchReadNotice(platformType, platformId);
    }

    public void save() {
        Assert.notBlank(this.data.getTitle(), "消息标题不能为空");
        Assert.notBlank(this.data.getContent(), "消息内容不能为空");
        this.data = SpringUtil.getBean(CommonDomainRepository.class).saveNotice(this.data);
    }

}
