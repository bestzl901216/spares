package org.geely.infrastructure.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.exception.BizException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author ricardo zhou
 */
@Slf4j
@Component
public class OssGateway {
    @Value("${oss.endpoint}")
    private String endPoint;
    @Value("${oss.appKeyId}")
    private String appKeyId;
    @Value("${oss.appSecret}")
    private String appSecret;
    @Value("${oss.cdnDomain}")
    private String cdnDomain;
    @Value("${oss.bucket}")
    private String bucketName;

    /**
     * 上传文件
     *
     * @return 验证码
     */
    public String upload(InputStream fileStream, String savaKey) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, appKeyId, appSecret);
        String errorCode = "OssGateway Upload error";
        String errorMsg = "文件上传服务调用失败";
        try {
            ossClient.putObject(bucketName, savaKey, fileStream);
        } catch (OSSException oe) {
            log.error("文件上传服务调用失败，ossException", oe);
            throw new BizException(errorCode, errorMsg);
        } catch (ClientException ce) {
            log.error("文件上传服务调用失败,clientException", ce);
            throw new BizException(errorCode, errorMsg);
        } catch (Exception e) {
            log.error("文件上传服务调用失败,exception", e);
            throw new BizException(errorCode, errorMsg);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return cdnDomain + savaKey;
    }
}
