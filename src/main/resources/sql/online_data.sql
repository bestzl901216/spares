truncate table mall;
truncate table account_role_rel;
truncate table role;
/* 创建租户 */
INSERT INTO mall (id, name, site_name, motto, service_phone, logo, state, order_pay_expiration,
                  order_receive_expiration, order_return_expiration, tax_point, style_mode, creator, create_time,
                  updater, update_time, version)
VALUES (1, '备品公司', '吉利备品企业采购平台', '一站式企业集采平台：吉利原厂，正品保证', '0571-28096451',
        'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/mall_beipin_logo.png', 1, 30, 7, 7, 0.05, 1, 'system',
        CURRENT_TIMESTAMP, 'system',
        CURRENT_TIMESTAMP, 0);

INSERT INTO mall (id, name, site_name, motto, service_phone, logo, state, order_pay_expiration,
                  order_receive_expiration, order_return_expiration, tax_point, style_mode, creator, create_time,
                  updater, update_time, version)
VALUES (2, '重庆领悟', '利宁服务平台', '一站式备件集采平台：质惠之选', '0571-28096458',
        'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/mall_ln_logo.png', 1, 30, 7, 7, 0.05, 1, 'system',
        CURRENT_TIMESTAMP, 'system',
        CURRENT_TIMESTAMP, 0);


truncate table mall_banner;
/* 轮播图 */
INSERT INTO mall_banner (id, mall_id, title, image_url, action_url, state, sort, position, deleted_flag, creator,
                         create_time, updater, update_time, version)
VALUES (1, 0, '登录banner1',
        'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/login_banner1.png', '',
        1, 0, 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO mall_banner (id, mall_id, title, image_url, action_url, state, sort, position, deleted_flag, creator,
                         create_time, updater, update_time, version)
VALUES (2, 1, '首页banner1',
        'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/mall_beipin_banner1.png', '',
        1, 0, 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO mall_banner (id, mall_id, title, image_url, action_url, state, sort, position, deleted_flag, creator,
                         create_time, updater, update_time, version)
VALUES (3, 2, '首页banner1',
        'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/mall_ln_banner1.jpg', '',
        1, 0, 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

truncate table platform_setting;
/*平台设置*/
insert into platform_setting(id, name, motto, service_phone,service_time, logo, copyright, filings_icp, filings_icp_url,
                             filings_record, filings_record_url, deleted_flag, creator, create_time, updater,
                             update_time, version)
values (1, '备采平台', '质惠之选', '0571-28096458','',
        'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/paltform_logo.png',
        '2024-现在 版权所有：吉利科技有限公司', 'ICP证:沪ICP备15084413号-1', 'https://beian.miit.gov.cn',
        '浙公网安备 33010802006317号', 'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010802006317', 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

truncate table market_channel;
/* 渠道 */
INSERT INTO market_channel (id, name, remark, creator, create_time, updater, update_time, version)
VALUES (1, '区域代理', '', 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel (id, name, remark, creator, create_time, updater, update_time, version)
VALUES (2, '区域二级代理', '', 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel (id, name, remark, creator, create_time, updater, update_time, version)
VALUES (3, '出行平台', '', 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel (id, name, remark, creator, create_time, updater, update_time, version)
VALUES (4, '汽配代理商', '', 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel (id, name, remark, creator, create_time, updater, update_time, version)
VALUES (5, '交易平台', '', 'system', current_timestamp, 'system', current_timestamp, 0);

truncate table market_channel_rel;
/* 租户渠道 */
INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (1, 2, 1, 1, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (2, 2, 1, 2, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (3, 3, 1, 2, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, markup_rate, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (4, 4, 1, 2, 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (5, 2, 2, 3, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (6, 2, 2, 4, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (7, 2, 2, 5, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, markup_rate, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (8, 4, 2, 3, 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, markup_rate, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (9, 4, 2, 4, 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, markup_rate, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (10, 4, 2,5, 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (11, 3, 2, 3, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (12, 3, 2, 4, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (13, 3, 2, 5, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

truncate table customer;
/* 客户 */
INSERT INTO customer (id, name, type, identity_sn, pca_code, address, identity_image, shop_image, workroom_image,
                      market_channel_id, pay_password, state, certify_state, creator, create_time, updater, update_time,
                      version)
VALUES (1, '浙江吉利汽车备件有限公司', 1, '', '', '浙江省杭州市', '', '', '[]', 2, '123456', 1, 1, 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO customer (id, name, type, identity_sn, pca_code, address, identity_image, shop_image, workroom_image,
                      market_channel_id, pay_password, state, certify_state, creator, create_time, updater, update_time,
                      version)
VALUES (2, '黑龙江云枫汽车有限公司', 1, '', '', '黑龙江省哈尔滨市平房区', '', '', '[]', 2, '123456', 1, 1, 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO customer (id, name, type, identity_sn, pca_code, address, identity_image, shop_image, workroom_image,
                      market_channel_id, pay_password, state, certify_state, creator, create_time, updater, update_time,
                      version)
VALUES (3, '宁波吉润汽车部件有限公司杭州湾新区备件分公司', 1, '', '', '浙江省宁波杭州湾新区滨海二路', '', '', '[]', 2,
        '123456', 1, 1, 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO customer (id, name, type, identity_sn, pca_code, address, identity_image, shop_image, workroom_image,
                      market_channel_id, pay_password, state, certify_state, creator, create_time, updater, update_time,
                      version)
VALUES (4, '上海兆配科技有效公司', 1, '', '', '上海市杨浦区三门路200号', '', '', '[]', 2,
        '123456', 1, 1, 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO customer (id, name, type, identity_sn, pca_code, address, identity_image, shop_image, workroom_image,
                      market_channel_id, pay_password, state, certify_state, creator, create_time, updater, update_time,
                      version)
VALUES (5, '上海兆配科技有效公司-渠道1', 1, '', '', '上海市杨浦区三门路200号', '', '', '[]', 1,
        '123456', 1, 1, 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO customer (id, name, type, identity_sn, pca_code, address, identity_image, shop_image, workroom_image,
                      market_channel_id, pay_password, state, certify_state, creator, create_time, updater, update_time,
                      version)
VALUES (6, '杭州优行科技有限公司', 1, '', '', '浙江省杭州市', '', '', '[]', 3,
        '123456', 1, 1, 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO customer (id, name, type, identity_sn, pca_code, address, identity_image, shop_image, workroom_image,
                      market_channel_id, pay_password, state, certify_state, creator, create_time, updater, update_time,
                      version)
VALUES (7, '深圳开思有限公司', 1, '', '', '深圳市', '', '', '[]', 5,
        '123456', 1, 1, 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO customer (id, name, type, identity_sn, pca_code, address, identity_image, shop_image, workroom_image,
                      market_channel_id, pay_password, state, certify_state, creator, create_time, updater, update_time,
                      version)
VALUES (8, '重庆领悟有限公司', 1, '', '', '重庆市', '', '', '[]', 4,
        '123456', 1, 1, 'system',
        current_timestamp, 'system', current_timestamp, 0);


/* 角色 */
INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (1, '管理员', '浙江吉利汽车备件有限公司最高权限', 0, 1, '["xxxxx"]', 'system', current_timestamp, 'system',
        current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (2, '管理员', '黑龙江云枫汽车有限公司最高权限', 0, 2, '["xxxxx"]', 'system', current_timestamp, 'system',
        current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (3, '管理员', '宁波吉润汽车部件有限公司杭州湾新区备件分公司最高权限', 0, 3, '["xxxxx"]', 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (6, '管理员', '上海兆配科技有限公司最高权限', 0, 4, '["xxxxx"]', 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (8,'管理员', '上海兆配科技有效公司-渠道1', 0, 5, '["xxxxx"]', 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                   version)
VALUES (9,'管理员', '杭州优行科技有限公司', 0, 6, '["xxxxx"]', 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                   version)
VALUES (10,'管理员', '深圳开思有限公司', 0, 7, '["xxxxx"]', 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                   version)
VALUES (11,'管理员', '重庆领悟有限公司', 0, 8, '["xxxxx"]', 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (14,'管理员', '备品有限公司', 1, 1, '["xxxxx"]', 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (15, '备品租户后台', '最高权限', 2, 1, '["管理后台"]', 'system', current_timestamp, 'system',
        current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (4, 15, 1, 'system', current_timestamp, 'system', current_timestamp, 0);


truncate table account;
/* 创建账号 */
INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (1, '李嘉慧', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '18667927339', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (2, '何慧', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '15824389015', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (3, '黄世明', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '17783167687', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (4, '施国庆', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '13402067150', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (5, '黄聪', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '13916718554', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (6, '熊开玲', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '18521030064', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (7, '张会超', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '17521523235', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (8, '陈双双', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '18717703112', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (11, '王敏', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '15726955792', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (12, '金政伟', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '17621645606', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (13, '鲁加明', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '15158181380', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (14, '周林', '',
        '15827068112', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (15, '王飞', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '18226896643', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (16, '范豪辉', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '18568647713', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (17, '刘雅苏', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '13530199449', 1, 'system', 'system', current_timestamp, current_timestamp, 0);
		
INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (18, '汪琦', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '18268578829', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (19, '张琪', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '15957138639', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (20, '陈洋', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '18661216606', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (21, '陈剑', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '15158183469', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (22, '王闪', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '18667913359', 1, 'system', 'system', current_timestamp, current_timestamp, 0);


INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (23, '俞磊', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '18967612035', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (24, '牛琰琦', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '15036082895', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (25, '杨志杰', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '15871476857', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (26, '沈琰', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '13656669827', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

/* 账号关联管理员角色 */
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (1, 1, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (2, 2, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (3, 3, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (4, 6, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (5, 6, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (6, 6, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (7, 6, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (8, 6, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (11, 9, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (12, 10, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (13, 11, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (14, 14, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (14, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (14, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (15, 11, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (19, 11, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (17, 1, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (18, 1, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (20, 1, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (21, 11, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (22, 11, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

truncate table product_category;
/*前台类目*/
INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (1, 1, 0,'', '车主生活', 1, 1, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (2, 1, 0,'', '原厂备件', 2, 1, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (3, 1, 0,'', '原厂车品', 3, 1, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (4, 1, 1,'1', '生活百货', 1, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (5, 1, 1,'1', '饰品摆件', 2, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (6, 1, 1,'1', '数码电器', 3, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (7, 1, 2,'2', '变速箱系统', 1, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (8, 1, 2,'2', '动力系统', 2, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (9, 1, 2,'2', '空调系统', 3, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (10, 1, 2,'2', '冷却系统', 4, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (11, 1, 2,'2', '汽车轮胎', 5, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (12, 1, 2,'2', '蓄电池', 6, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (13, 1, 2,'2', '雨刮系统', 7, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (14, 1, 2,'2', '制动系统', 8, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (15, 1, 3,'3', '电子电器', 1, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (16, 1, 3,'3', '内装精品', 2, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (17, 1, 3,'3', '汽车贴膜', 3, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (18, 1, 3,'3', '外装精品', 4, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (19, 1, 3,'3', '洗美养护用品', 5, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (20, 1, 3,'3', '新能源专区', 6, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (60, 2, 0,'', '原厂备件', 1, 1, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (61, 2, 0,'', '利宁件', 1, 1, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (62, 2, 60,'60', '变速箱系统', 1, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (63, 2, 60,'60', '动力系统', 2, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (64, 2, 60,'60', '空调系统', 3, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (65, 2, 60,'60', '冷却系统', 4, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (66, 2, 60,'60', '汽车轮胎', 5, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (67, 2, 60,'60', '蓄电池', 6, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (68, 2, 60,'60', '雨刮系统',7, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (69, 2, 60,'60', '制动系统',8, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (70, 2, 61,'61', '变速箱系统',1, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (71, 2, 61,'61', '动力系统',2, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (72, 2, 61,'61', '空调系统',3, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (73, 2, 61,'61', '冷却系统',4, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (74, 2, 61,'61', '汽车轮胎',5, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (75, 2, 61,'61', '蓄电池',6, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (76, 2, 61,'61', '雨刮系统',7, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (77, 2, 61,'61', '制动系统',8, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO product_category (id, mall_id, parent_id, parent_path, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (78, 2, 61,'61', '油液类',9, 2, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);


truncate table home_page_category;
/*首页类目广告位*/
insert into home_page_category(id, mall_id, c1_id, img_url, sort, deleted_flag, creator, create_time, updater,
                               update_time, version)
values (1, 1, 1, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/home_page_category_czsh.jpeg', 0, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

insert into home_page_category(id, mall_id, c1_id, img_url, sort, deleted_flag, creator, create_time, updater,
                               update_time, version)
values (2, 1, 2, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/home_page_category_pjfw.jpeg', 0, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

insert into home_page_category(id, mall_id, c1_id, img_url, sort, deleted_flag, creator, create_time, updater,
                               update_time, version)
values (3, 1, 3, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/home_page_category_yccp.jpeg', 0, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

insert into home_page_category(mall_id, c1_id, img_url, sort, deleted_flag, creator, create_time, updater,
                               update_time, version)
values (2, 60, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/home_page_category_oil.png', 0, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

insert into home_page_category(mall_id, c1_id, img_url, sort, deleted_flag, creator, create_time, updater,
                               update_time, version)
values (2, 61, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/home_page_category_lx.png', 0, 0,
        'system', current_timestamp, 'system', current_timestamp, 0);


truncate table product_brand;
INSERT INTO `product_brand`
VALUES (1, 1, '吉利汽车', 0, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/brand_geely.png', 0, 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `product_brand`
VALUES (30, 2, '利宁', 0, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/brand_ln.png', 0, 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `product_brand`
VALUES (31, 2, '博世', 1, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/brand_ln.png', 0, 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `product_brand`
VALUES (32, 2, '壳牌', 2, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/brand_ln.png', 0, 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `product_brand`
VALUES (33, 2, '法雷奥', 4, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/brand_ln.png', 0, 'system',
        current_timestamp, 'system', current_timestamp, 0);

truncate table sku_specs_type;
-- Records of sku_specs_type
-- ----------------------------
INSERT INTO `sku_specs_type`(id, mall_id, name, sort, deleted_flag, creator, create_time, updater, update_time, version)
VALUES (1, 1, '颜色', 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs_type`(id, mall_id, name, sort, deleted_flag, creator, create_time, updater, update_time, version)
VALUES (2, 1, '车型', 1, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs_type`(id, mall_id, name, sort, deleted_flag, creator, create_time, updater, update_time, version)
VALUES (3, 1, '数量', 2, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs_type`(id, mall_id, name, sort, deleted_flag, creator, create_time, updater, update_time, version)
VALUES (10, 2, '颜色', 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs_type`(id, mall_id, name, sort, deleted_flag, creator, create_time, updater, update_time, version)
VALUES (11, 2, '车型', 1, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs_type`(id, mall_id, name, sort, deleted_flag, creator, create_time, updater, update_time, version)
VALUES (12, 2, '数量', 2, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs_type`(id, mall_id, name, sort, deleted_flag, creator, create_time, updater, update_time, version)
VALUES (13, 2, '规格', 3, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs_type`(id, mall_id, name, sort, deleted_flag, creator, create_time, updater, update_time, version)
VALUES (14, 2, '容量', 3, 0, 'system', current_timestamp, 'system', current_timestamp, 0);


truncate table sku_specs;
-- Records of sku_specs
-- ----------------------------
INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (1, 1, '红色', 0, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (2, 1, '黄色', 1, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (3, 1, '紫色', 2, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (4, 2, '星越L', 0, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (5, 2, '星瑞', 1, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (6, 3, '1个', 0, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (7, 3, '2个', 1, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);


INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (50, 10, '红色', 0, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (51, 10, '黄色', 1, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (52, 10, '紫色', 2, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (53, 11, '星越L', 0, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (54, 11, '星瑞', 1, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (55, 12, '1个', 0, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (56, 12, '2个', 1, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (57, 13, '205/55R16', 0, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (58, 13, '215/60R17', 1, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (59, 14, '1L', 0, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id, type_id, name, sort, selectable, deleted_flag, creator, create_time, updater, update_time,
                        version)
VALUES (60, 14, '4L', 1, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

truncate table product_tag;
/* 商品标签 */
INSERT INTO `product_tag` (id, mall_id, name, font_color, background_color, remark, sort, state, creator, create_time,
                           updater, update_time, version)
VALUES (1, 1, '原厂备品', '#169BD5', '#337ab7', '', 0, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO `product_tag` (id, mall_id, name, font_color, background_color, remark, sort, state, creator, create_time,
                           updater, update_time, version)
VALUES (2, 1, '专车专用', '#337ab7', '#169BD5', '', 1, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO `product_tag` (id, mall_id, name, font_color, background_color, remark, sort, state, creator, create_time,
                           updater, update_time, version)
VALUES (3, 1, '环保无味', '#169BD5', '#169BD5', '', 2, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO `product_tag` (id, mall_id, name, font_color, background_color, remark, sort, state, creator, create_time,
                           updater, update_time, version)
VALUES (4, 1, '热销产品', '#169BD5', '#169BD5', '', 3, 1, 'system', current_timestamp, 'system', current_timestamp, 0);


truncate table supplier;
/* 创建商家 */
INSERT INTO supplier (id, mall_id, name, sap_id, state, bank_account_id, merchant_no, creator, create_time, updater,
                      update_time, version)
VALUES (1, 1, '吉利备品企业采购平台', 1, 1, 1, '', 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO supplier (id, mall_id, name, sap_id, state, bank_account_id, merchant_no, creator, create_time, updater,
                      update_time, version)
VALUES (2, 2, '重庆领悟', 2, 1, 1, '', 'system', current_timestamp, 'system', current_timestamp, 0);


INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (4, '商家管理员', '最高权限', 3, 1, '["管理后台"]', 'system', current_timestamp, 'system',
        current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (1, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (2, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (3, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (4, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (14, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (16, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (17, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (18, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (20, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (23, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (24, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (25, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (26, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);


INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (12, '领悟商家管理员', '最高权限', 3, 2, '["管理后台"]', 'system', current_timestamp, 'system',
        current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (4, 12, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES ( 11, 12, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (12, 12, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (13, 12, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (14, 12, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (16, 12, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (15, 12, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (19, 12, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (21, 12, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (22, 12, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

truncate table mall_sap;
INSERT INTO `mall_sap`
VALUES (1, 1, '备品SAP', 1, 0, 'ricardo_zhou', '2024-04-02 18:25:57', 'ricardo_zhou', '2024-04-02 18:26:00', 0);

INSERT INTO `mall_sap`
VALUES (2, 2, '领悟SAP', 1, 0, 'ricardo_zhou', '2024-04-02 18:25:57', 'ricardo_zhou', '2024-04-02 18:26:00', 0);

truncate table mall_bank_account;
insert into mall_bank_account(id, mall_id, name, bank, bank_account, deleted_flag, creator, create_time, updater,
                              update_time, version)
values (1, 1, '浙江吉利汽车备件有限公司', '兴业银行宁波北仑支行', '388010100101263737', 0, 'system',
        current_timestamp, 'system', current_timestamp, 0);

insert into mall_bank_account(id, mall_id, name, bank, bank_account, deleted_flag, creator, create_time, updater,
                              update_time, version)
values (2, 2, '领悟汽车技术（重庆）有限公司', '中国工商银行股份有限公司重庆西彭支行', '3100082209200119111', 0, 'system',
        current_timestamp, 'system', current_timestamp, 0);


truncate table shop;
/* 创建店铺 */
INSERT INTO shop(id, mall_id, supplier_id, name, state, product_audit, creator, create_time,
                 updater, update_time, version, admin_phone, admin_name)
VALUES (1, 1, 1, '吉利备品企业采购平台', 1, 0, 'system', current_timestamp, 'system',
        current_timestamp, 0, '18667927339', '李嘉慧');

INSERT INTO shop(id, mall_id, supplier_id, name, state, product_audit, creator, create_time,
                 updater, update_time, version, admin_phone, admin_name)
VALUES (2, 2, 2, '利宁店铺', 1, 0, 'system', current_timestamp, 'system',
        current_timestamp, 0, '15158181380', '鲁加明');

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (5, '店铺管理员', '最高权限', 4, 1, '["管理后台"]', 'system', current_timestamp, 'system',
        current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (1, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (2, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (3, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (4, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (14, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (16, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (17, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (18, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (20, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (23, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (24, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (25, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (26, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (13, '利宁店铺管理员', '最高权限', 4, 2, '["管理后台"]', 'system', current_timestamp, 'system',
        current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (4, 13, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (11, 13, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (12, 13, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (14, 13, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (13, 13, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (16, 13, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (15, 13, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (19, 13, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (21, 13, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (22, 13, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

/*物料*/
truncate table material;
insert into material(id, mall_id, supplier_id, code, name, oe_no, category, stock, unit, price, deleted_flag, creator,
                     create_time, updater, update_time, version)
values (1, 1, 1, '4114410120', '星越L车模（1：18）', '', '', 999, '', 720, 0, 'system', current_timestamp,
        'system', current_timestamp, 0);

insert into material(id, mall_id, supplier_id, code, name, oe_no, category, stock, unit, price, deleted_flag, creator,
                     create_time, updater, update_time, version)
values (2, 1, 1, '4114410130', '星越L车模（1：18）', '', '', 999, '', 720, 0, 'system',
        current_timestamp,
        'system', current_timestamp, 0);



insert into material(id, mall_id, supplier_id, code, name, oe_no, category, stock, unit, price, deleted_flag, creator,
                     create_time, updater, update_time, version)
values (30, 2, 2, '6608102976', '利宁蓄电池46B24RS', '', '', 999, '', 230, 0, 'system', current_timestamp,
        'system', current_timestamp, 0);

insert into material(id, mall_id, supplier_id, code, name, oe_no, category, stock, unit, price, deleted_flag, creator,
                     create_time, updater, update_time, version)
values (31, 2, 2, '6608149428', '利宁冷却液（龙蟠-40℃）4L', '', '', 999, '', 80, 0, 'system', current_timestamp,
        'system', current_timestamp, 0);

insert into material(id, mall_id, supplier_id, code, name, oe_no, category, stock, unit, price, deleted_flag, creator,
                     create_time, updater, update_time, version)
values (32, 2, 2, '6608102988', '轮胎（215/60R16）', '', '', 999, '', 195, 0, 'system', current_timestamp,
        'system', current_timestamp, 0);


truncate table material_shop_rel;
insert into material_shop_rel(id, material_id, shop_id, deleted_flag, creator, create_time, updater, update_time,
                              version)
values (1, 1, 1, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

insert into material_shop_rel(id, material_id, shop_id, deleted_flag, creator, create_time, updater, update_time,
                              version)
values (2, 2, 1, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

insert into material_shop_rel(id, material_id, shop_id, deleted_flag, creator, create_time, updater, update_time,
                              version)
values (3, 3, 1, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

insert into material_shop_rel(id, material_id, shop_id, deleted_flag, creator, create_time, updater, update_time,
                              version)
values (4, 4, 1, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

insert into material_shop_rel(material_id, shop_id, deleted_flag, creator, create_time, updater, update_time,
                              version)
values (30, 2, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

insert into material_shop_rel(material_id, shop_id, deleted_flag, creator, create_time, updater, update_time,
                              version)
values (31, 2, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

insert into material_shop_rel(material_id, shop_id, deleted_flag, creator, create_time, updater, update_time,
                              version)
values (32, 2, 0, 'system', current_timestamp, 'system', current_timestamp, 0);



INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (6, 14, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (6, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (6, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

