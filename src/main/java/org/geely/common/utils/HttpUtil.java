package org.geely.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.HtmlUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

@Slf4j
public class HttpUtil {

    /**
     * get
     *
     * @param host
     * @param path
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static String doGet(String host, String path,
                               Map<String, String> headers,
                               Map<String, String> querys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpGet request = new HttpGet(buildUrl(host, path, querys));
        if (headers != null) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }

        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String resultStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            closeHttpClient(response);
            return HtmlUtils.htmlEscape(resultStr);
        }
        closeHttpClient(response);
        throw new Exception("http request fail " + response.toString());
    }

    private static void closeHttpClient(HttpResponse response) throws IOException {
        // 5.回收链接到连接池
        if (null != response) {
            try {
                EntityUtils.consume(response.getEntity());
            } catch (IOException e) {
                log.error("response convert error:{}", e.getMessage(), e);
            } finally {
                CloseableHttpResponse closeableHttpResponse = (CloseableHttpResponse) response;
                closeableHttpResponse.close();
                log.debug("CloseableHttpResponse closed,status:{}", response.getStatusLine().getStatusCode());
            }
        }
    }

    /**
     * post json
     *
     * @param host
     * @param path
     * @param headers
     * @param querys
     * @param bodys
     * @return
     * @throws Exception
     */
    public static String doPostJson(String host, String path,
                                    Map<String, String> headers,
                                    Map<String, String> querys,
                                    String bodys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);
        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        if (headers != null) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }

        if (bodys != null) {
            StringEntity requestEntity = new StringEntity(bodys, "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            requestEntity.setContentType("application/json");
            request.setEntity(requestEntity);
        }
        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String resutlStr = EntityUtils.toString(entity, "UTF-8");
            closeHttpClient(response);
            return resutlStr;
        }
        closeHttpClient(response);
        throw new Exception("http request fail " + response.toString());
    }

    public static String doPost(String host, String path,
                                Map<String, String> headers,
                                Map<String, String> querys,
                                String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }

        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String resutlStr = EntityUtils.toString(entity, "UTF-8");
            closeHttpClient(response);
            return resutlStr;
        }
        closeHttpClient(response);
        throw new Exception("http request fail " + response.toString());
    }

    /**
     * post json
     *
     * @param host
     * @param path
     * @param headers
     * @param querys
     * @param bodys
     * @return
     * @throws Exception
     */
    public static String doPutJson(String host, String path,
                                   Map<String, String> headers,
                                   Map<String, String> querys,
                                   String bodys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);
        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        if (headers != null) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }

        if (StringUtils.isNotBlank(bodys)) {
            StringEntity requestEntity = new StringEntity(bodys, "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            requestEntity.setContentType("application/json");
            request.setEntity(requestEntity);
        }
        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String resutlStr = EntityUtils.toString(entity, "UTF-8");
            closeHttpClient(response);
            return resutlStr;
        }
        closeHttpClient(response);
        throw new Exception("http request fail " + response.toString());
    }

    /**
     * Delete
     *
     * @param host
     * @param path
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static String doDelete(String host, String path,
                                  Map<String, String> headers,
                                  Map<String, String> querys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String resutlStr = EntityUtils.toString(entity, "UTF-8");
            closeHttpClient(response);
            return resutlStr;
        }
        closeHttpClient(response);
        throw new Exception("http request fail " + response.toString());
    }

    private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = HttpClientPool.getHttpClient();
        /*if (host.startsWith("https://")) {
            sslClient(httpClient);
        }*/

        return httpClient;
    }

    private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] xcs, String str) {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String str) {

                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
}
