package org.geely.common.utils;

import lombok.experimental.UtilityClass;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池工具
 * @author yancheng.guo
 * @date 2023-05-30
 */
@UtilityClass
public class ThreadPoolUtils {

    /**
     * 操作日志上报
     */
    public static final ThreadPoolExecutor OPERATE_LOG_THREAD_POOL
            = new ThreadPoolExecutor(12,12,10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
}
