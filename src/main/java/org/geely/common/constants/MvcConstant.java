package org.geely.common.constants;

/**
 * @author ricardo zhou
 */
public class MvcConstant {

    private MvcConstant() {
    }

    /**
     * 请求附带的header，用于确定请求来自哪个平台类型
     */
    public static final String PLATFORM_HEADER = "platformNow";

    /**
     * 请求附带的header，用于确定请求来自哪个平台的id
     */
    public static final String PLATFORM_ID_HEADER = "platformIdNow";

    /**
     * 请求附带的header，用于确定请求来自哪个商城的id
     */
    public static final String MALL_ID = "mallId";

    /**
     * 管理后台通用接口前缀
     */
    public static final String CMS = "/cms";

    /**
     * 平台管理后台接口前缀
     */
    public static final String PLATFORM = "/cms/platform";

    /**
     * 租户管理后台口前缀
     */
    public static final String MALL = "/cms/mall";

    /**
     * 商家管理后台接口前缀
     */
    public static final String SUPPLIER = "/cms/supplier";

    /**
     * 店铺管理后台接口前缀
     */
    public static final String SHOP = "/cms/shop";

    /**
     * 商城接口前缀
     */
    public static final String CUSTOMER = "/site/customer";

    public static final String SITE_COMMON = "/site/common";

    public static final String SITE_PRODUCT = "/site/product";

    public static final String SITE = "/site";

}
