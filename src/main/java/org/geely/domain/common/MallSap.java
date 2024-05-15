package org.geely.domain.common;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.common.enums.EnableStateEnum;
import org.geely.controller.dto.MallSapDTO;
import org.geely.domain.common.data.MallSapData;
import org.geely.infrastructure.db.repository.MallSapRepository;

import java.util.List;

/**
 * Sap类型
 *
 * @author cong huang
 */
public class MallSap {

    private MallSapData data;

    private MallSap(MallSapData data) {
        this.data = ObjectUtil.clone(data);
    }
    public static MallSap instanceOf(Integer id) {
        MallSapData data = SpringUtil.getBean(MallSapRepository.class).getById(id);
        return new MallSap(data);
    }
    public static List<MallSapDTO> list(Integer mallId) {
        return SpringUtil.getBean(MallSapRepository.class).list(mallId);
    }
    public MallSapData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Integer getMallId() {
        return data.getMallId();
    }

    public EnableStateEnum getState() {
        return data.getState();
    }
}
