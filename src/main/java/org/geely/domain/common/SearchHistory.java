package org.geely.domain.common;

import cn.hutool.core.lang.Assert;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.domain.common.data.SearchHistoryData;
import org.geely.infrastructure.db.repository.CommonDomainRepository;


/**
 * 搜索历史
 *
 * @author ricardo zhou
 */
public class SearchHistory {
    private SearchHistoryData data;

    private SearchHistory(SearchHistoryData data) {
        this.data = data;
    }

    /**
     * 创建搜索记录
     */
    public static SearchHistory newInstance(Integer mallId, Integer customerId, Integer accountId, String keyword) {
        Assert.notBlank(keyword, "搜索关键字不能为空");
        SearchHistoryData data = new SearchHistoryData();
        data.setMallId(mallId);
        data.setCustomerId(customerId);
        data.setAccountId(accountId);
        data.setKeyword(keyword);
        data.setVersion(0);

        SearchHistory searchHistory = new SearchHistory(data);
        searchHistory.save();
        return searchHistory;
    }

    public static SearchHistory instanceOf(Integer mallId, Integer customerId, Integer accountId, String keyword) {
        SearchHistoryData searchHistoryData = SpringUtil.getBean(CommonDomainRepository.class).getSearchHistory(mallId, customerId, accountId, keyword);
        return new SearchHistory(searchHistoryData);
    }

    /**
     * 保存账户
     */
    public void save() {
        this.data = SpringUtil.getBean(CommonDomainRepository.class).saveSearchHistory(this.data);
    }
}
