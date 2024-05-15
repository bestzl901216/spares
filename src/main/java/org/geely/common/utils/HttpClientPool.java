package org.geely.common.utils;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


/**
 * http连接池
 *
 * @author 67091
 */
public class HttpClientPool {
    public static final Logger log = LoggerFactory.getLogger(HttpClientPool.class);

    private HttpClientPool() {
    }

    private static final AtomicReference<CloseableHttpClient> HTTP_CLIENT = new AtomicReference<>();

    // 单例模式获取httpClient
    public static CloseableHttpClient getHttpClient() {
        if (HTTP_CLIENT.get() == null) {
            synchronized (HttpClientPool.class) {
                if (HTTP_CLIENT.get() == null) {
                    HTTP_CLIENT.compareAndSet(null, createHttpClient());
                }
            }
        }
        return HTTP_CLIENT.get();
    }

    private static CloseableHttpClient createHttpClient() {
        // 连接池配置
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        // 最大连接数
        connManager.setMaxTotal(1000);
        // 默认的每个路由的最大连接数
        connManager.setDefaultMaxPerRoute(600);
        connManager.setValidateAfterInactivity(1000);
        connManager.setDefaultSocketConfig(SocketConfig.custom().setSoKeepAlive(true).build());
        // 操作连接配置
        RequestConfig requestConfig = RequestConfig.custom()
                // 连接超时时间
                .setConnectTimeout(5 * 1000)
                // 读超时时间
                .setSocketTimeout(20 * 1000)
                // 从连接池获取连接超时时间
                .setConnectionRequestTimeout(500)
                .build();

        // 保活配置
        ConnectionKeepAliveStrategy myKAStragegy = (response, context) -> {
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && "timeout".equalsIgnoreCase(param)) {
                    return Long.parseLong(value) * 1000;
                }
            }
            // 如果没有约定，则默认定义时长为60s
            return 60 * 1000;
        };
        return HttpClients.custom()
                .disableCookieManagement()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy(myKAStragegy)
                // 无用连接关闭策略
                .evictExpiredConnections()
                .evictIdleConnections(10L, TimeUnit.SECONDS)
                .build();
    }
}
