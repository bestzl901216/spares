truncate table mall;
/* 创建租户 */
INSERT INTO mall (id, name, site_name, motto, service_phone, logo, state, order_pay_expiration,
                  order_receive_expiration, order_return_expiration, tax_point, style_mode, creator, create_time,
                  updater, update_time, version)
VALUES (1, '备品公司', '吉利备品企业采购平台', '一站式企业集采平台：吉利原厂，正品保证', '0571-28096451', 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/mall_beipin_logo.png', 1, 30, 7, 7, 0.05, 1, 'system', CURRENT_TIMESTAMP, 'system',
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

truncate table platform_setting;
/*平台设置*/
insert into platform_setting(id, name, motto, service_phone, service_time, logo, copyright, filings_icp, filings_icp_url,
                             filings_record, filings_record_url, deleted_flag, creator, create_time, updater,
                             update_time, version)
values (1, '备采平台', '质惠之选', '0571-28096458', '', 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/paltform_logo.png',
        '2024-现在 版权所有：吉利科技有限公司', 'ICP证:沪ICP备15084413号-1', 'https://beian.miit.gov.cn',
        '浙公网安备 33010802006317号', 'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010802006317', 0,
        'system', current_timestamp, 'system', current_timestamp, 0);

truncate table market_channel;
/* 渠道 */
INSERT INTO market_channel (id, name, remark, creator, create_time, updater, update_time, version)
VALUES (1, '区域代理', '', 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO market_channel (id, name, remark, creator, create_time, updater, update_time, version)
VALUES (2, '区域二级代理', '', 'system', current_timestamp, 'system', current_timestamp, 0);


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

INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id,markup_rate, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (4, 4, 1, 2, 3.3,0, 'system', current_timestamp, 'system', current_timestamp, 0);


-- INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
--                                 create_time, updater, update_time, version)
-- VALUES (2, 0, 1, 1, 0, 'ricardo zhou', '2024-03-28 18:34:19', 'ricardo zhou', '2024-03-28 18:34:24', 0);
-- INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
--                                 create_time, updater, update_time, version)
-- VALUES (3, 0, 2, 2, 0, 'ricardo zhou', '2024-03-28 18:34:19', 'ricardo zhou', '2024-03-28 18:34:24', 0);
-- INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
--                                 create_time, updater, update_time, version)
-- VALUES (4, 0, 1, 2, 0, 'ricardo zhou', '2024-03-28 18:34:19', 'ricardo zhou', '2024-03-28 18:34:24', 0);
-- INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
--                                 create_time, updater, update_time, version)
-- VALUES (5, 0, 2, 1, 0, 'ricardo zhou', '2024-03-28 18:34:19', 'ricardo zhou', '2024-03-28 18:34:24', 0);
-- INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
--                                 create_time, updater, update_time, version)
-- VALUES (6, 4, 1, 1, 0, 'saber', CURRENT_TIMESTAMP, 'saber', CURRENT_TIMESTAMP, 0);


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
VALUES (3, '宁波吉润汽车部件有限公司杭州湾新区备件分公司', 1, '', '', '浙江省宁波杭州湾新区滨海二路', '', '', '[]', 2, '123456', 1, 1, 'system',
        current_timestamp, 'system', current_timestamp, 0);

INSERT INTO customer (id, name, type, identity_sn, pca_code, address, identity_image, shop_image, workroom_image,
                      market_channel_id, pay_password, state, certify_state, creator, create_time, updater, update_time,
                      version)
VALUES (4, '上海兆配科技有效公司', 1, '', '', '上海市杨浦区三门路200号', '', '', '[]', 2, '123456', 1, 1, 'system',
        current_timestamp, 'system', current_timestamp, 0);

truncate table role;
/* 角色 */
INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time, version)
VALUES (1, '管理员', '浙江吉利汽车备件有限公司最高权限', 0, 1, '["xxxxx"]', 'system', current_timestamp, 'system',current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time, version)
VALUES (2, '管理员', '黑龙江云枫汽车有限公司最高权限', 0, 2, '["xxxxx"]', 'system', current_timestamp, 'system',current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time, version)
VALUES (3, '管理员', '宁波吉润汽车部件有限公司杭州湾新区备件分公司最高权限', 0, 3, '["xxxxx"]', 'system', current_timestamp, 'system',current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time, version)
VALUES (7, '管理员', '上海兆配科技有效公司最高权限', 0, 4, '["xxxxx"]', 'system', current_timestamp, 'system',current_timestamp, 0);

truncate table account;
/* 创建账号 */
INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (1, '李嘉慧', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '18667927339', 1, 'system', 'system', current_timestamp, current_timestamp, 0);

INSERT INTO `account` (id, name, encoded_password, phone, state,creator, updater, create_time, update_time, version)
VALUES (2, '何慧', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '15827068111',1, 'system', 'system', current_timestamp, current_timestamp, 0);

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

truncate table account_role_rel;
/* 账号关联管理员角色 */
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (1, 1, 1, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (2, 2, 2, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (3, 3, 3, 1, 'system', current_timestamp, 'system', current_timestamp, 0);


INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (11, 4, 7, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (12, 5, 7, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (13, 6, 7, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (20, 7, 7, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (23, 8, 7, 1, 'system', current_timestamp, 'system', current_timestamp, 0);


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


truncate table home_page_category;
/*首页类目广告位*/
insert into home_page_category(id, mall_id, c1_id, img_url, sort, deleted_flag, creator, create_time, updater,
                               update_time, version)
values (1, 1, 1, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/home_page_category_czsh.jpeg', 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

insert into home_page_category(id, mall_id, c1_id, img_url, sort, deleted_flag, creator, create_time, updater,
                               update_time, version)
values (2, 1, 2, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/home_page_category_pjfw.jpeg', 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

insert into home_page_category(id, mall_id, c1_id, img_url, sort, deleted_flag, creator, create_time, updater,
                               update_time, version)
values (3, 1, 3, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/home_page_category_yccp.jpeg', 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);


truncate table product_brand;
INSERT INTO `product_brand`
VALUES (1, 1, '吉利汽车', 0, 'https://gbdp-oss-prod.oss-cn-hangzhou.aliyuncs.com/image/brand_geely.png', 0, 'system', current_timestamp, 'system', current_timestamp, 0);


truncate table sku_specs_type;
-- Records of sku_specs_type
-- ----------------------------
INSERT INTO `sku_specs_type`(id,mall_id,name,sort,deleted_flag,creator,create_time,updater,update_time,version)
VALUES (1, 1, '颜色', 0, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs_type`(id,mall_id,name,sort,deleted_flag,creator,create_time,updater,update_time,version)
VALUES (2, 1, '车型', 1, 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs_type`(id,mall_id,name,sort,deleted_flag,creator,create_time,updater,update_time,version)
VALUES (3, 1, '数量', 2, 0, 'system', current_timestamp, 'system', current_timestamp, 0);


truncate table sku_specs;
-- Records of sku_specs
-- ----------------------------
INSERT INTO `sku_specs`(id,type_id,name,sort,selectable, deleted_flag,creator,create_time,updater,update_time,version)
VALUES (1, 1, '红色', 0, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id,type_id,name,sort,selectable, deleted_flag,creator,create_time,updater,update_time,version)
VALUES (2, 1, '黄色', 1, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id,type_id,name,sort,selectable, deleted_flag,creator,create_time,updater,update_time,version)
VALUES (3, 1, '紫色', 2, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id,type_id,name,sort,selectable, deleted_flag,creator,create_time,updater,update_time,version)
VALUES (4, 2, '星越L', 0, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id,type_id,name,sort,selectable, deleted_flag,creator,create_time,updater,update_time,version)
VALUES (5, 2, '星瑞', 1, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id,type_id,name,sort,selectable, deleted_flag,creator,create_time,updater,update_time,version)
VALUES (6, 3, '1个', 0, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO `sku_specs`(id,type_id,name,sort,selectable, deleted_flag,creator,create_time,updater,update_time,version)
VALUES (7, 3, '2个', 1, b'1', 0, 'system', current_timestamp, 'system', current_timestamp, 0);

truncate table product_tag;
/* 商品标签 */
INSERT INTO `product_tag` (id, mall_id, name,font_color,background_color, remark, sort, state, creator, create_time, updater, update_time, version)
VALUES (1, 1, '原厂备品', '#F64041','#FFEEEE','', 0, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO `product_tag` (id, mall_id, name,font_color,background_color, remark, sort, state, creator, create_time, updater, update_time, version)
VALUES (2, 1, '专车专用', '#FF7800','#FFF5EC','', 1, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO `product_tag` (id, mall_id, name,font_color,background_color, remark, sort, state, creator, create_time, updater, update_time, version)
VALUES (3, 1, '环保无味', '#0053DB','#EBF2FF','', 2, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO `product_tag` (id, mall_id, name,font_color,background_color, remark, sort, state, creator, create_time, updater, update_time, version)
VALUES (4, 1, '热销产品', '#F64041','#FFEEEE','', 3, 1, 'system', current_timestamp, 'system', current_timestamp, 0);


truncate table supplier;
/* 创建商家 */
INSERT INTO supplier (id, mall_id, name, sap_id, state, bank_account_id, merchant_no, creator, create_time, updater,
                      update_time, version)
VALUES (1, 1, '吉利备品企业采购平台', 1, 1, 1, '', 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (4, '商家管理员', '最高权限', 3, 1, '["管理后台"]', 'system', current_timestamp, 'system',
        current_timestamp, 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (4, 1, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (5, 2, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (6, 3, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (14, 4, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (15, 5, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (16, 6, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (21, 7, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (24, 8, 4, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

truncate table mall_sap;
INSERT INTO `mall_sap`
VALUES (1, 1, '备品SAP', 1, 0, 'ricardo_zhou', '2024-04-02 18:25:57', 'ricardo_zhou', '2024-04-02 18:26:00', 0);


truncate table mall_bank_account;
insert into mall_bank_account(id,mall_id,name,bank,bank_account,deleted_flag,creator,create_time,updater,update_time,version)
values(1,1,'浙江吉利汽车备件有限公司','兴业银行宁波北仑支行','''388010100101263737',0,'system',current_timestamp,'system',current_timestamp,0);


truncate table shop;
/* 创建店铺 */
INSERT INTO shop(id, mall_id, supplier_id, name, state, product_audit, creator, create_time,
                 updater, update_time, version, admin_phone, admin_name)
VALUES (1, 1, 1, '吉利备品企业采购平台', 1, 0, 'system', current_timestamp, 'system',
        current_timestamp, 0, '18667927339', '李嘉慧');

INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (5, '店铺管理员', '最高权限', 4, 1, '["管理后台"]', 'system', current_timestamp, 'system',
        current_timestamp, 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (7, 1, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (8, 2,5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (9, 3, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (17, 4, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (18, 5, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (19, 6, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (22, 7, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (25, 8, 5, 1, 'system', current_timestamp, 'system', current_timestamp, 0);


/*物料*/
insert into material(id, mall_id, supplier_id, code, name, oe_no, category, stock, unit, price, deleted_flag, creator,
                     create_time, updater, update_time, version)
values (1, 1, 1, '4114009920', '星越L TPE脚垫套装（四驱、棕）', '', '', 999, '', 558, 0, 'system', current_timestamp,
        'system', current_timestamp, 0);

insert into material(id, mall_id, supplier_id, code, name, oe_no, category, stock, unit, price, deleted_flag, creator,
                     create_time, updater, update_time, version)
values (2, 1, 1, '4114008580', '21款星越L TPE后备箱垫（两驱舒适、两驱豪华、两驱尊贵）', '', '', 999, '', 198, 0, 'system', current_timestamp,
        'system', current_timestamp, 0);

insert into material(id, mall_id, supplier_id, code, name, oe_no, category, stock, unit, price, deleted_flag, creator,
                     create_time, updater, update_time, version)
values (3, 1, 1, '4114008270', '21款星越L 3D皮质包围脚垫(四驱、燃油版）', '', '', 999, '', 358, 0, 'system', current_timestamp,
        'system', current_timestamp, 0);

insert into material(id, mall_id, supplier_id, code, name, oe_no, category, stock, unit, price, deleted_flag, creator,
                     create_time, updater, update_time, version)
values (4, 1, 1, '4114212020', '星瑞贯穿式LED尾灯', '', '', 999, '', 1288, 0, 'system', current_timestamp,
        'system', current_timestamp, 0);

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

/* 销售订单 */
INSERT INTO sale_order (id, mall_id, supplier_id, shop_id, customer_id, customer_account_id, sn, receiver,
                        receiver_address, receiver_phone, state, amount, pay_amount, freight_type, delivery_type,
                        pay_time, approved_time, delivery_time, received_time, finished_time, cancel_time,
                        cancel_reason, cancel_by, cancel_from, pay_deadline, received_deadline, pay_type,
                        invoice_id, tax_rate, remark, sap_sn, is_reverse, creator_id, deleted_flag, creator,
                        create_time, updater, update_time, version)
VALUES (1, 1, 1, 1, 1, 1, '123456789', '收货小哥', '上海东方明珠', '13177321956', 5, 200.22, 189.88, 1, 1,
        '2024-04-06 17:12:26', '2024-04-06 17:12:31', null, null, null, null, '', 0, 0, '2024-04-06 20:13:34',
        '2024-04-12 17:13:48', 1, 0, 0, '', '', false, 0, 0, 'ricardo zhou', '2024-04-06 17:14:23',
        'ricardo zhou', '2024-04-06 17:14:25', 0);
INSERT INTO sale_order_item (id, sale_order_id, product_id, sku_id, product_name, product_code, sku_specs,
                             material_code, product_quality, product_brand, origin_price, price, quantity,
                             support_return, unit, delivery_quantity, received_quantity,
                             deleted_flag, creator, create_time, updater, update_time, version)
VALUES (1, 1, 1, 1, '测试商品1', '123_123_123', '黑色/大瓶', '234_234_234', '原厂件', '吉利', 100, 100, 1, true,
        '件', 0, 0, 0, 'ricardo zhou', '2024-04-06 17:19:29', 'ricardo zhou', '2024-04-06 17:19:31', 0);
INSERT INTO sale_order_item (id, sale_order_id, product_id, sku_id, product_name, product_code, sku_specs,
                             material_code, product_quality, product_brand, origin_price, price, quantity,
                             support_return, unit, delivery_quantity, received_quantity,
                             deleted_flag, creator, create_time, updater, update_time, version)
VALUES (2, 1, 2, 2, '测试商品2', '223_223_223', '白色/小瓶', '334_334_334', '原厂件', '吉利', 100.22, 89.88, 1,
        true, '件', 0, 0, 0, 'ricardo zhou', '2024-04-06 17:19:29', 'ricardo zhou', '2024-04-06 17:19:31', 0);

/*收货地址*/
insert into customer_address(id, customer_id, mall_id, pca_code, address, receiver, phone, is_default, deleted_flag,
                             creator,
                             create_time, updater, update_time, version)
values (1, 1, 1, '110101', 'cccccxxxxx', 'xxx收件人', 'xxx-xxx-xxxx', 1, 0, 'saber', current_timestamp, 'saber',
        current_timestamp, 0);
insert into customer_address(id, customer_id, mall_id, pca_code, address, receiver, phone, is_default, deleted_flag,
                             creator,
                             create_time, updater, update_time, version)
values (2, 1, 2, '110111', 'cccccxxxxx', 'xxx2收件人', 'xxx-xxx-xxxx', 1, 0, 'saber', current_timestamp, 'saber',
        current_timestamp, 0);





INSERT INTO sku_specs_rel (id, sku_id, specs_type_id, specs, deleted_flag, creator, create_time, updater,
                           update_time, version)
VALUES (1, 1, 1, '大瓶', 0, 'ricardo', '2024-04-16 11:34:45', 'ricardo', '2024-04-16 11:34:53', 0);
INSERT INTO sku_specs_rel (id, sku_id, specs_type_id, specs, deleted_flag, creator, create_time, updater,
                           update_time, version)
VALUES (2, 2, 1, '小瓶', 0, 'ricardo', '2024-04-16 11:34:45', 'ricardo', '2024-04-16 11:34:53', 0);


INSERT INTO `product`
VALUES (1, 1, 1, 1, 'P010005', 2, 0, 1, '发动机冷却液', '吉利长效冷却液1500ML', 1, 0, 0, 'ricardo_zhou',
        '2024-04-15 14:38:16', 'ricardo_zhou', '2024-04-15 14:38:16', 1);

INSERT INTO `sku`
VALUES (1, 1, '111111', 100, b'1', 1, '瓶', 1, 1, '', 0, 0, 'ricardo_zhou', '2024-04-15 14:38:16', 'ricardo_zhou',
        '2024-04-15 14:38:16', 0);
INSERT INTO `sku`
VALUES (2, 1, '222222', 100, b'1', 1, '瓶', 1, 1, '', 0, 0, 'ricardo_zhou', '2024-04-15 14:38:16', 'ricardo_zhou',
        '2024-04-15 14:38:16', 0);

insert into `sku_image`
values (1, 1, 'https://xxx.jpg', 0, 0, 'saber', '2024-04-15 14:38:16', 'saber', '2024-04-15 14:38:16', 0);
insert into `sku_image`
values (2, 1, 'https://222xxx.jpg', 0, 0, 'saber', '2024-04-15 14:38:16', 'saber', '2024-04-15 14:38:16', 0);
insert into `sku_image`
values (3, 2, 'https://333xxx.jpg', 0, 0, 'saber', '2024-04-15 14:38:16', 'saber', '2024-04-15 14:38:16', 0);

INSERT INTO `home_page_sku`(id,mall_id,sku_id,c1_id,sort,deleted_flag,creator,create_time,updater,update_time,version)
VALUES (1, 1, 1, 2, 1, 0, 'saber', '2024-04-15 14:38:16', 'saber', '2024-04-15 14:38:16', 0);
INSERT INTO `home_page_sku`(id,mall_id,sku_id,c1_id,sort,deleted_flag,creator,create_time,updater,update_time,version)
VALUES (2, 2, 2, 1, 1, 0, 'saber', '2024-04-15 14:38:16', 'saber', '2024-04-15 14:38:16', 0);

INSERT INTO `product_tag_rel`
VALUES (1, 1, 3, 0, 'saber', '2024-04-12 18:50:01', 'saber', '2024-04-12 18:50:01', 0);


