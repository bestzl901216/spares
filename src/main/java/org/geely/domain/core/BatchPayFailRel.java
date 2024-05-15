package org.geely.domain.core;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.domain.core.data.BatchPayFailRelData;
import org.geely.infrastructure.db.repository.BatchPayFailRelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author cong huang
 */
public class BatchPayFailRel {
    private BatchPayFailRelData data;

    private BatchPayFailRel(BatchPayFailRelData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static BatchPayFailRel newInstance(BatchPayFailRelData data) {
        BatchPayFailRel instance = new BatchPayFailRel(data);
        instance.data.setId(null);
        instance.data.setVersion(0);
        instance.save();
        return instance;
    }

    public static List<BatchPayFailRel> newInstances(Integer batchId, Set<Integer> saleOrderIds) {
        if(saleOrderIds == null || saleOrderIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<BatchPayFailRel> result = new ArrayList<>();
        for (Integer saleOrderId : saleOrderIds) {
            BatchPayFailRelData data = new BatchPayFailRelData();
            data.setBatchId(batchId);
            data.setSaleOrderId(saleOrderId);
            result.add(newInstance(data));
        }
        return result;
    }

    public void save() {
        this.data = SpringUtil.getBean(BatchPayFailRelRepository.class).save(this.data);
    }

    public BatchPayFailRelData getData() {
        return ObjectUtil.clone(this.data);
    }
}
