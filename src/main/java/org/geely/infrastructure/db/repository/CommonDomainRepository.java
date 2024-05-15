package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.domain.common.data.*;
import org.geely.infrastructure.db.*;
import org.geely.infrastructure.db.convert.CommonDomainDataConvert;
import org.geely.infrastructure.db.service.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 通用域数据仓库
 *
 * @author ricardo zhou
 */
@Repository
public class CommonDomainRepository {

    @Resource
    private NoticeDbService noticeDbService;
    @Resource
    private MarketChannelDbService marketChannelDbService;
    @Resource
    private MarketChannelRelDbService marketChannelRelDbService;
    @Resource
    private MessageProduceLogDbService messageProduceLogDbService;
    @Resource
    private MessageConsumeLogDbService messageConsumeLogDbService;
    @Resource
    private SearchHistoryDbService searchHistoryDbService;

    public NoticeData saveNotice(NoticeData noticeData) {
        NoticeDO noticeDO = CommonDomainDataConvert.INSTANCE.convert(noticeData);
        boolean result = noticeDbService.saveOrUpdate(noticeDO);
        Assert.isTrue(result, "通知数据保存失败");
        return CommonDomainDataConvert.INSTANCE.convert(noticeDO);
    }

    public void batchReadNotice(PlatformTypeEnum platformType, Integer platformId) {
        LambdaUpdateWrapper<NoticeDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(NoticeDO::getPlatformType, platformType.getId());
        updateWrapper.eq(NoticeDO::getPlatformId, platformId);
        updateWrapper.eq(NoticeDO::getState, NoticeData.StateEnum.UNREAD.getId());
        updateWrapper.set(NoticeDO::getState, NoticeData.StateEnum.READ.getId());
        noticeDbService.update(updateWrapper);
    }

    public MarketChannelData saveMarketChannel(MarketChannelData marketChannelData) {
        MarketChannelDO marketChannelDO = CommonDomainDataConvert.INSTANCE.convert(marketChannelData);
        boolean result = marketChannelDbService.saveOrUpdate(marketChannelDO);
        Assert.isTrue(result, "渠道数据保存失败");
        return CommonDomainDataConvert.INSTANCE.convert(marketChannelDO);
    }

    public MarketChannelData getMarketChannelById(Integer marketChannelId) {
        Assert.notNull(marketChannelId, "渠道id不能为空");
        MarketChannelDO marketChannelDO = marketChannelDbService.getById(marketChannelId);
        Assert.notNull(marketChannelDO, "渠道数据不存在");
        return CommonDomainDataConvert.INSTANCE.convert(marketChannelDO);
    }

    public Set<MarketChannelData> getMarketChannelByIds(Set<Integer> ids) {
        Assert.notEmpty(ids, "渠道ids不能为空");
        List<MarketChannelDO> marketChannelDOList = marketChannelDbService.listByIds(ids);
        Assert.isTrue(marketChannelDOList.size() == ids.size(), "渠道数据不存在");
        return marketChannelDOList.stream().map(CommonDomainDataConvert.INSTANCE::convert).collect(Collectors.toSet());
    }

    public void deleteMarketChannel(Integer id) {
        Assert.notNull(id, "渠道id不能为空");
        boolean result = marketChannelDbService.removeById(id);
        Assert.isTrue(result, "渠道数据软删除失败");
    }

    public MarketChannelRelData saveMarketChannelRel(MarketChannelRelData marketChannelRelData) {
        MarketChannelRelDO marketChannelRelDO = CommonDomainDataConvert.INSTANCE.convert(marketChannelRelData);
        boolean result = marketChannelRelDbService.saveOrUpdate(marketChannelRelDO);
        Assert.isTrue(result, "渠道关联数据保存失败");
        return CommonDomainDataConvert.INSTANCE.convert(marketChannelRelDO);
    }

    public Set<MarketChannelRelData> getMarketChannelRel(PlatformTypeEnum platformType, Integer platformId) {
        Assert.notNull(platformType, "平台类型不能为空");
        Assert.notNull(platformId, "平台id不能为空");
        LambdaQueryWrapper<MarketChannelRelDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MarketChannelRelDO::getPlatformType, platformType.getId());
        queryWrapper.eq(MarketChannelRelDO::getPlatformId, platformId);
        List<MarketChannelRelDO> marketChannelRelDOList = marketChannelRelDbService.list(queryWrapper);
        return marketChannelRelDOList.stream().map(CommonDomainDataConvert.INSTANCE::convert).collect(Collectors.toSet());
    }

    public Set<MarketChannelRelData> listMarketChannelRel(PlatformTypeEnum platformType, Integer channelId, Set<Integer> platformIds) {
        LambdaQueryWrapper<MarketChannelRelDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MarketChannelRelDO::getPlatformType, platformType.getId());
        if(channelId != null && !channelId.equals(0)) {
            queryWrapper.eq(MarketChannelRelDO::getMarketChannelId, channelId);
        }
        if(platformIds != null && !platformIds.isEmpty()) {
            queryWrapper.in(MarketChannelRelDO::getPlatformId, platformIds);
        }
        List<MarketChannelRelDO> marketChannelRelDOList = marketChannelRelDbService.list(queryWrapper);
        return marketChannelRelDOList.stream().map(CommonDomainDataConvert.INSTANCE::convert).collect(Collectors.toSet());
    }

    public void deleteMarketChannelRel(Integer id) {
        Assert.notNull(id, "渠道关联id不能为空");
        boolean result = marketChannelRelDbService.removeById(id);
        Assert.isTrue(result, "渠道关联数据软删除失败");
    }

    public MessageProduceLogData saveMessageProduceLog(MessageProduceLogData data) {
        MessageProduceLogDO messageProduceLogDO = CommonDomainDataConvert.INSTANCE.convert(data);
        boolean result = messageProduceLogDbService.saveOrUpdate(messageProduceLogDO);
        Assert.isTrue(result, "数据保存失败");
        return CommonDomainDataConvert.INSTANCE.convert(messageProduceLogDO);
    }

    public MessageProduceLogData getMessageProduceLog(String messageId) {
        Assert.notNull(messageId, "消息ID不能为空");
        LambdaQueryWrapper<MessageProduceLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessageProduceLogDO::getMessageId, messageId);
        MessageProduceLogDO messageProduceLogDO = messageProduceLogDbService.getOne(wrapper);
        Assert.notNull(messageProduceLogDO, "消息生产日志不存在");
        return CommonDomainDataConvert.INSTANCE.convert(messageProduceLogDO);
    }

    public MessageConsumeLogData saveMessageConsumeLog(MessageConsumeLogData data) {
        MessageConsumeLogDO messageConsumeLogDO = CommonDomainDataConvert.INSTANCE.convert(data);
        boolean result = messageConsumeLogDbService.saveOrUpdate(messageConsumeLogDO);
        Assert.isTrue(result, "数据保存失败");
        return CommonDomainDataConvert.INSTANCE.convert(messageConsumeLogDO);
    }

    public MessageConsumeLogData getMessageConsumeLog(String messageId, String consumerGroup) {
        Assert.notNull(messageId, "消息ID不能为空");
        Assert.notNull(consumerGroup, "消息消费分组不能为空");
        LambdaQueryWrapper<MessageConsumeLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessageConsumeLogDO::getMessageId, messageId);
        wrapper.eq(MessageConsumeLogDO::getConsumerGroup, consumerGroup);
        MessageConsumeLogDO messageProduceLogDO = messageConsumeLogDbService.getOne(wrapper);
        Assert.notNull(messageProduceLogDO, "消息消费日志不存在");
        return CommonDomainDataConvert.INSTANCE.convert(messageProduceLogDO);
    }

    public List<MarketChannelRelData> listMarketChannelByShopId(Integer shopId) {
        LambdaQueryWrapper<MarketChannelRelDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MarketChannelRelDO::getPlatformType, PlatformTypeEnum.SHOP.getId())
                .eq(MarketChannelRelDO::getPlatformId, shopId);
        List<MarketChannelRelData> relDataList = marketChannelRelDbService.list(wrapper).stream().map(CommonDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
        if (relDataList.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Integer> channelIds = relDataList.stream().map(MarketChannelRelData::getMarketChannelId).collect(Collectors.toSet());
        Map<Integer, String> channelMap = marketChannelDbService.listByIds(channelIds).stream().collect(Collectors.toMap(BaseDO::getId, MarketChannelDO::getName));
        for (MarketChannelRelData rel : relDataList) {
            rel.setMarketChannel(channelMap.get(rel.getMarketChannelId()));
        }
        return relDataList;
    }

    public SearchHistoryData saveSearchHistory(SearchHistoryData data) {
        SearchHistoryDO searchHistoryDO = CommonDomainDataConvert.INSTANCE.convert(data);
        try {
            boolean result = searchHistoryDbService.saveOrUpdate(searchHistoryDO);
            Assert.isTrue(result, "搜索历史数据保存失败");
        } catch (DuplicateKeyException e) {
            LambdaQueryWrapper<SearchHistoryDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SearchHistoryDO::getMallId, data.getMallId());
            queryWrapper.eq(SearchHistoryDO::getCustomerId, data.getCustomerId());
            queryWrapper.eq(SearchHistoryDO::getAccountId, data.getAccountId());
            queryWrapper.eq(SearchHistoryDO::getKeyword, data.getKeyword());
            searchHistoryDO = searchHistoryDbService.getOne(queryWrapper);
            searchHistoryDO.setUpdater(null);
            searchHistoryDO.setUpdateTime(null);
            boolean result = searchHistoryDbService.updateById(searchHistoryDO);
            Assert.isTrue(result, "搜索历史数据保存失败");
        }
        return CommonDomainDataConvert.INSTANCE.convert(searchHistoryDO);
    }

    public SearchHistoryData getSearchHistory(Integer mallId, Integer customerId, Integer accountId, String keyword) {
        Assert.notNull(mallId, "租户id不能为空");
        Assert.notNull(customerId, "客户id不能为空");
        Assert.notNull(accountId, "账户id不能为空");
        Assert.notBlank(keyword, "搜索关键字不能为空");
        LambdaQueryWrapper<SearchHistoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SearchHistoryDO::getMallId, mallId);
        queryWrapper.eq(SearchHistoryDO::getCustomerId, customerId);
        queryWrapper.eq(SearchHistoryDO::getAccountId, accountId);
        queryWrapper.eq(SearchHistoryDO::getKeyword, keyword);
        SearchHistoryDO searchHistoryDO = searchHistoryDbService.getOne(queryWrapper);
        Assert.notNull(searchHistoryDO, "搜索历史数据不存在");
        return CommonDomainDataConvert.INSTANCE.convert(searchHistoryDO);
    }

}
