package org.geely.infrastructure.db.repository;

import org.geely.domain.core.data.BatchPayFailRelData;
import org.geely.infrastructure.db.BatchPayFailRelDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.service.BatchPayFailRelDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author cong huang
 */
@Repository
public class BatchPayFailRelRepository {
    @Resource
    private BatchPayFailRelDbService service;
    public BatchPayFailRelData save(BatchPayFailRelData data) {
        BatchPayFailRelDO dbModel = SupportDomainDataConvert.INSTANCE.convert(data);
        service.saveOrUpdate(dbModel);
        return SupportDomainDataConvert.INSTANCE.convert(dbModel);
    }
}
