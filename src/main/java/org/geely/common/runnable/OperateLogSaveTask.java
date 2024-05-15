package org.geely.common.runnable;

import com.alibaba.fastjson2.JSON;
import org.geely.common.constants.BaseServiceApi;
import org.geely.common.model.OperateLogDTO;
import org.geely.common.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperateLogSaveTask implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(OperateLogSaveTask.class);

    private OperateLogDTO operateLogDTO;

    private String host;

    public OperateLogSaveTask(OperateLogDTO operateLogDTO, String host) {
        this.operateLogDTO = operateLogDTO;
        this.host = host;
    }

    @Override
    public void run() {
        try {
            String body = JSON.toJSONString(operateLogDTO);
            LOGGER.info(body);
            HttpUtil.doPostJson(host, BaseServiceApi.OPERATE_LOG_SAVE, null, null, body);
        } catch (Exception e) {
            LOGGER.error("operateLog save exception", e);
        }
    }
}
