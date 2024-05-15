package org.geely.infrastructure.db.convert;

import org.geely.common.annotation.DataToDoIgnore;
import org.geely.domain.common.data.*;
import org.geely.infrastructure.db.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ricardo zhou
 */
@Mapper
public interface CommonDomainDataConvert {
    CommonDomainDataConvert INSTANCE = Mappers.getMapper(CommonDomainDataConvert.class);

    /**
     * 转换
     *
     * @param noticeData 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    NoticeDO convert(NoticeData noticeData);

    /**
     * 转换
     *
     * @param noticeDO 数据库映射对象
     * @return 领域对象
     */
    NoticeData convert(NoticeDO noticeDO);

    /**
     * @param marketChannelDO marketChannelDO
     * @return MarketChannelData
     */
    MarketChannelData convert(MarketChannelDO marketChannelDO);

    /**
     * @param marketChannelData marketChannelData
     * @return MarketChannelDO
     */
    @DataToDoIgnore
    MarketChannelDO convert(MarketChannelData marketChannelData);

    /**
     * @param marketChannelRelDO marketChannelRelDO
     * @return MarketChannelRelData
     */
    MarketChannelRelData convert(MarketChannelRelDO marketChannelRelDO);

    /**
     * @param marketChannelRelData marketChannelRelData
     * @return MarketChannelRelDO
     */
    @DataToDoIgnore
    MarketChannelRelDO convert(MarketChannelRelData marketChannelRelData);

    /**
     * @param messageProduceLogDO messageProduceLogDO
     * @return MessageProduceLogData
     */
    MessageProduceLogData convert(MessageProduceLogDO messageProduceLogDO);

    /**
     * @param messageProduceLogData messageProduceLogData
     * @return MessageProduceLogDO
     */
    @DataToDoIgnore
    MessageProduceLogDO convert(MessageProduceLogData messageProduceLogData);

    /**
     * 转化
     *
     * @param messageConsumeLogDO messageConsumeLogDO
     * @return MessageConsumeLogData
     */
    MessageConsumeLogData convert(MessageConsumeLogDO messageConsumeLogDO);

    /**
     * 转换
     *
     * @param messageConsumeLogData messageConsumeLogData
     * @return MessageConsumeLogDO
     */
    @DataToDoIgnore
    MessageConsumeLogDO convert(MessageConsumeLogData messageConsumeLogData);

    /**
     * 转换
     *
     * @param searchHistoryData searchHistoryData
     * @return SearchHistoryDO
     */
    @DataToDoIgnore
    SearchHistoryDO convert(SearchHistoryData searchHistoryData);

    /**
     * 转换
     *
     * @param searchHistoryDO searchHistoryDO
     * @return SearchHistoryData
     */
    SearchHistoryData convert(SearchHistoryDO searchHistoryDO);
}
