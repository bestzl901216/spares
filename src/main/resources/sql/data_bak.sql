/* 创建账号 */
INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (1, 'ricardo_zhou', '38240185104f7c121112eeeb5b1096f0f54e1267f9d94a39d90440c1fcaca5fe',
        '15827068111', 1, 'ricardo_zhou', 'ricardo_zhou', '2023-03-08 12:00:00', '2023-03-08 12:00:00', 0);
INSERT INTO `account` (id, name, encoded_password, phone, creator, updater, create_time, update_time, version)
VALUES (2, 'Jordan', '38240185104f7c121112eeeb5b1096f0f54e1267f9d94a39d90440c1fcaca5fe',
        '15827068110', 'ricardo_zhou', 'ricardo_zhou', '2023-03-08 12:00:00', '2023-03-08 12:00:00', 0);
INSERT INTO `account` (id, name, encoded_password, phone, state, creator, updater, create_time, update_time, version)
VALUES (3, 'Saber', '3f2923d55502953caa2c08ed7b5ebe0f7c6a1ac6f0a7b07fab7a35e3b17e3a0f',
        '13402067150', 1, 'ricardo_zhou', 'ricardo_zhou', '2023-03-08 12:00:00', '2023-03-08 12:00:00', 0);

/* 创建租户 */
INSERT INTO mall (id, name, site_name, motto, service_phone, logo, state, order_pay_expiration,
                  order_receive_expiration, order_return_expiration, tax_point, style_mode, creator, create_time,
                  updater, update_time, version)
VALUES (1, '备品', '备品商城', '', '', '', 1, 30, 7, 7, 0.05, 1, 'ricardo_zhou', '2024-03-26 09:28:15', 'ricardo_zhou',
        '2024-03-26 09:28:19', 0);

/* 创建商家 */
INSERT INTO supplier (id, mall_id, name, sap_id, state, bank_account_id, merchant_no, creator, create_time, updater,
                      update_time, version)
VALUES (1, 1, '领悟商家', 1, 0, 0, '', 'ricardo zhou', '2024-03-26 09:34:20', 'ricardo zhou', '2024-03-26 09:34:34', 0);

INSERT INTO supplier (id, mall_id, name, sap_id, state, bank_account_id, merchant_no, creator, create_time, updater,
                      update_time, version)
VALUES (2, 2, '备品商家', 1, 0, 0, '', 'ricardo zhou', '2024-03-26 09:34:20', 'ricardo zhou', '2024-03-26 09:34:34', 0);

INSERT INTO `mall_sap`
VALUES (1, 1, '备品SAP', 1, 0, 'ricardo_zhou', '2024-04-02 18:25:57', 'ricardo_zhou', '2024-04-02 18:26:00', 0);
/* 创建店铺 */
INSERT INTO shop(id, mall_id, supplier_id, name, state, product_audit, creator, create_time,
                 updater, update_time, version, admin_phone, admin_name)
VALUES (1, 1, 1, '店铺A', 1, 0, 'ricardo_zhou', '2024-03-26 09:28:15', 'ricardo_zhou',
        '2024-03-26 09:28:19', 0, '1340xxxxxxx', 'saber');

INSERT INTO shop(id, mall_id, supplier_id, name, state, product_audit, creator, create_time,
                 updater, update_time, version, admin_phone, admin_name)
VALUES (2, 1, 1, '店铺B', 1, 0, 'ricardo_zhou', '2024-03-26 09:28:15', 'ricardo_zhou',
        '2024-03-26 09:28:19', 0, '1340xxxxxxx', 'saber');

/* 创建管理后台管理员角色 */
INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (1, '管理员', '最高权限', 1, 0, '["管理后台"]', 'ricardo zhou', '2024-03-26 09:36:38', 'ricardo zhou',
        '2024-03-26 09:36:40', 0);
INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (2, '管理员', '最高权限', 2, 1, '["领悟"]', 'ricardo zhou', '2024-03-26 09:36:38', 'ricardo zhou',
        '2024-03-26 09:36:40', 0);
INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (3, '管理员', '最高权限', 3, 1, '["领悟商城"]', 'ricardo zhou', '2024-03-26 09:36:38', 'ricardo zhou',
        '2024-03-26 09:36:40', 0);
INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (4, '管理员', '最高权限', 2, 2, '["备品"]', 'ricardo zhou', '2024-03-26 09:36:38', 'ricardo zhou',
        '2024-03-26 09:36:40', 0);
INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (5, '管理员', '最高权限', 0, 1, '["曹操专车"]', 'ricardo zhou', '2024-03-26 09:36:38', 'ricardo zhou',
        '2024-03-26 09:36:40', 0);
INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (6, '管理员', '最高权限', 0, 2, '["DIDI出行"]', 'ricardo zhou', '2024-03-26 09:36:38', 'ricardo zhou',
        '2024-03-26 09:36:40', 0);
INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (7, '测试人员', '最高权限', 1, 0, '["测试人员"]', 'ricardo zhou', '2024-03-26 09:36:38', 'ricardo zhou',
        '2024-03-26 09:36:40', 0);
INSERT INTO role (id, name, remark, platform_type, platform_id, permissions, creator, create_time, updater, update_time,
                  version)
VALUES (8, '管理员', '最高权限', 4, 1, '["测试人员"]', 'ricardo zhou', '2024-03-26 09:36:38', 'ricardo zhou',
        '2024-03-26 09:36:40', 0);

/* 账号关联管理员角色 */
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (1, 1, 1, 1, 'ricardo zhou', '2024-03-26 09:40:01', 'ricardo zhou', '2024-03-26 09:40:02', 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (2, 1, 2, 1, 'ricardo zhou', '2024-03-26 09:40:01', 'ricardo zhou', '2024-03-26 09:40:02', 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (3, 1, 3, 1, 'ricardo zhou', '2024-03-26 09:40:01', 'ricardo zhou', '2024-03-26 09:40:02', 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (4, 1, 4, 1, 'ricardo zhou', '2024-03-26 09:40:01', 'ricardo zhou', '2024-03-26 09:40:02', 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (5, 1, 5, 1, 'ricardo zhou', '2024-03-26 09:40:01', 'ricardo zhou', '2024-03-26 09:40:02', 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (6, 1, 6, 1, 'ricardo zhou', '2024-03-26 09:40:01', 'ricardo zhou', '2024-03-26 09:40:02', 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (7, 2, 7, 1, 'ricardo zhou', '2024-03-26 09:40:01', 'ricardo zhou', '2024-03-26 09:40:02', 0);
INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (8, 1, 8, 1, 'ricardo zhou', '2024-03-26 09:40:01', 'ricardo zhou', '2024-03-26 09:40:02', 0);

INSERT INTO account_role_rel (id, account_id, role_id, state, creator, create_time, updater, update_time,
                              version)
VALUES (9, 3, 5, 1, 'ricardo zhou', '2024-03-26 09:40:01', 'ricardo zhou', '2024-03-26 09:40:02', 0);

/* 平台消息 */
INSERT INTO notice (id, platform_type, platform_id, title, content, time, state, creator, create_time, updater,
                    update_time, version)
VALUES (1, 1, 0, '认证', '客户小王已提交认证资料，请及时审批', '2024-03-26 15:57:21', 1, 'ricardo zhou',
        '2024-03-26 15:57:43', 'ricardo zhou', '2024-03-26 15:57:45', 0);
INSERT INTO notice (id, platform_type, platform_id, title, content, time, state, creator, create_time, updater,
                    update_time, version)
VALUES (2, 1, 0, '认证', '客户小刘已提交认证资料，请及时审批', '2024-03-26 15:57:21', 0, 'ricardo zhou',
        '2024-03-26 15:57:43', 'ricardo zhou', '2024-03-26 15:57:45', 0);
INSERT INTO notice (id, platform_type, platform_id, title, content, time, state, creator, create_time, updater,
                    update_time, version)
VALUES (3, 1, 0, '认证', '客户小张已提交认证资料，请及时审批', '2024-03-26 16:57:21', 0, 'ricardo zhou',
        '2024-03-26 16:57:43', 'ricardo zhou', '2024-03-26 16:57:45', 0);

/* 商品标签 */
INSERT INTO `product_tag` (id, mall_id, name, remark, sort, state, creator, create_time, updater, update_time, version)
VALUES (1, 1, '包邮', '', 0, 1, 'conghuang', '2024-03-27 18:14:13', 'conghuang', '2024-03-27 18:14:19', 0);
INSERT INTO `product_tag`
VALUES (2, 1, '包邮', '', '', '', 0, 1, 0, 'ricardo_zhou', '2024-04-12 18:50:01', 'ricardo_zhou', '2024-04-12 18:50:01',
        0);
INSERT INTO `product_tag`
VALUES (3, 2, '包邮', '#169BD5', '#169BD5', '', 0, 1, 0, 'ricardo_zhou', '2024-04-12 18:50:01', 'ricardo_zhou',
        '2024-04-12 18:50:01', 0);
/* 客户 */
INSERT INTO customer (id, name, type, identity_sn, pca_code, address, identity_image, shop_image, workroom_image,
                      market_channel_id, pay_password, state, certify_state, creator, create_time, updater, update_time,
                      version)
VALUES (1, '曹操出行', 1, '123123123', '12345', '杭州吉利大厦', '', '', '[]', 1, '123123', 1, 1, 'ricardo zhou',
        '2024-03-28 15:28:45', 'ricardo zhou', '2024-03-28 15:28:47', 0);

INSERT INTO customer (id, name, type, identity_sn, pca_code, address, identity_image, shop_image, workroom_image,
                      market_channel_id, pay_password, state, certify_state, creator, create_time, updater, update_time,
                      version)
VALUES (2, 'DIDI专车', 1, '123123123', '12345', '杭州吉利大厦', '', '', '[]', 1, '123123', 1, 1, 'ricardo zhou',
        '2024-03-28 15:28:45', 'ricardo zhou', '2024-03-28 15:28:47', 0);


/* 渠道 */
INSERT INTO market_channel (id, name, remark, creator, create_time, updater, update_time, version)
VALUES (1, '渠道A', '测试渠道', 'ricardo zhou', '2024-03-28 15:31:16', 'ricardo zhou', '2024-03-28 15:31:18', 0);

INSERT INTO market_channel (id, name, remark, creator, create_time, updater, update_time, version)
VALUES (2, '渠道B', '测试渠道', 'ricardo zhou', '2024-03-28 15:31:16', 'ricardo zhou', '2024-03-28 15:31:18', 0);

/* 租户渠道 */
INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (1, 2, 1, 1, 0, 'ricardo zhou', '2024-03-28 18:34:19', 'ricardo zhou', '2024-03-28 18:34:24', 0);
INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (2, 0, 1, 1, 0, 'ricardo zhou', '2024-03-28 18:34:19', 'ricardo zhou', '2024-03-28 18:34:24', 0);
INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (3, 0, 2, 2, 0, 'ricardo zhou', '2024-03-28 18:34:19', 'ricardo zhou', '2024-03-28 18:34:24', 0);
INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (4, 0, 1, 2, 0, 'ricardo zhou', '2024-03-28 18:34:19', 'ricardo zhou', '2024-03-28 18:34:24', 0);
INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (5, 0, 2, 1, 0, 'ricardo zhou', '2024-03-28 18:34:19', 'ricardo zhou', '2024-03-28 18:34:24', 0);
INSERT INTO market_channel_rel (id, platform_type, platform_id, market_channel_id, deleted_flag, creator,
                                create_time, updater, update_time, version)
VALUES (6, 4, 1, 1, 0, 'saber', CURRENT_TIMESTAMP, 'saber', CURRENT_TIMESTAMP, 0);

/* 轮播图 */
INSERT INTO mall_banner (id, mall_id, title, image_url, action_url, state, sort, position, deleted_flag, creator,
                         create_time, updater, update_time, version)
VALUES (1, 1, '广告推广1',
        'https://t11.baidu.com/it/u=1525384081,844571676&fm=98&app=97&f=JPEG?s=F97B0ED08E1032CCE4B00F00030060F7', ' ',
        1, 0, 1, 0, 'ricardo zhou', '2024-04-06 12:34:55', 'ricardo zhou', '2024-04-06 12:34:57', 0);

INSERT INTO mall_banner (id, mall_id, title, image_url, action_url, state, sort, position, deleted_flag, creator,
                         create_time, updater, update_time, version)
VALUES (2, 0, '登录banner1',
        'https://t11.baidu.com/it/u=1525384081,844571676&fm=98&app=97&f=JPEG?s=F97B0ED08E1032CCE4B00F00030060F7', ' ',
        1, 0, 0, 0, 'ricardo zhou', '2024-04-06 12:34:55', 'ricardo zhou', '2024-04-06 12:34:57', 0);

INSERT INTO mall_banner (id, mall_id, title, image_url, action_url, state, sort, position, deleted_flag, creator,
                         create_time, updater, update_time, version)
VALUES (3, 2, '广告推广1',
        'https://t11.baidu.com/it/u=1525384081,844571676&fm=98&app=97&f=JPEG?s=F97B0ED08E1032CCE4B00F00030060F7', ' ',
        1, 0, 1, 0, 'ricardo zhou', '2024-04-06 12:34:55', 'ricardo zhou', '2024-04-06 12:34:57', 0);

/*物料*/
insert into material(id, mall_id, supplier_id, code, name, oe_no, category, stock, unit, price, deleted_flag, creator,
                     create_time, updater, update_time, version)
values (1, 1, 1, 'sap123', '机油5w30', '304854,dg3411', '机油', 12, '桶', '1230', 0, 'saber', current_timestamp,
        'saber', current_timestamp, 0);

insert into material_shop_rel(id, material_id, shop_id, deleted_flag, creator, create_time, updater, update_time,
                              version)
values (1, 1, 1, 0, 'saber', current_timestamp, 'saber', current_timestamp, 0);

insert into material_shop_rel(id, material_id, shop_id, deleted_flag, creator, create_time, updater, update_time,
                              version)
values (2, 1, 2, 0, 'saber', current_timestamp, 'saber', current_timestamp, 0);

/*平台设置*/
insert into platform_setting(id, name, motto, service_phone, logo, copyright, filings_icp, filings_icp_url,
                             filings_record, filings_record_url, deleted_flag, creator, create_time, updater,
                             update_time, version)
values (1, 'xx汽配平台', '厂家直销，品牌保证', 'xxx-xxx-xxxx', 'xxxxxx.png',
        'Copyright © 2024-现在 版权所有：领悟科技有限公司', 'ICP证:沪ICP备15084413号-1', 'https://beian.miit.gov.cn',
        '浙公网安备 33010802006317号', 'http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010802006317', 0,
        'saber', current_timestamp, 'saber', current_timestamp, 0);
/* 销售订单 */
INSERT INTO sale_order (id, mall_id, supplier_id, shop_id, customer_id, customer_account_id, sn, receiver,
                        receiver_province,
                        receiver_city, receiver_area,
                        receiver_address, receiver_phone, state, amount, pay_amount, freight_type, delivery_type,
                        pay_time, approved_time, delivery_time, received_time, finished_time, cancel_time,
                        cancel_reason, cancel_by, cancel_from, pay_deadline, received_deadline, pay_type,
                        invoice_id, tax_rate, remark, sap_sn, is_reverse, creator_id, deleted_flag, creator,
                        create_time, updater, update_time, version)
VALUES (1, 1, 1, 1, 1, 1, '123456789', '收货小哥', '', '', '', '上海东方明珠', '13177321956', 5, 200.22, 189.88, 1, 1,
        '2024-04-06 17:12:26', '2024-04-06 17:12:31', null, null, null, null, '', 0, 0, '2024-04-06 20:13:34',
        '2024-04-12 17:13:48', 1, 0, 0, '', '', false, 0, 0, 'ricardo zhou', '2024-04-06 17:14:23',
        'ricardo zhou', '2024-04-06 17:14:25', 0);
INSERT INTO sale_order_item (id, sale_order_id, product_id, sku_id, product_name, product_code, sku_specs, sku_code,
                             image, is_free_shipping,
                             material_code, product_quality, product_brand, origin_price, price, quantity,
                             support_return, unit, delivery_quantity, received_quantity,
                             deleted_flag, creator, create_time, updater, update_time, version)
VALUES (1, 1, 1, 1, '测试商品1', '123_123_123', '黑色/大瓶', '', '', 1, '234_234_234', '原厂件', '吉利', 100, 100, 1,
        true,
        '件', 0, 0, 0, 'ricardo zhou', '2024-04-06 17:19:29', 'ricardo zhou', '2024-04-06 17:19:31', 0);
INSERT INTO sale_order_item (id, sale_order_id, product_id, sku_id, product_name, product_code, sku_specs, sku_code,
                             image, is_free_shipping,
                             material_code, product_quality, product_brand, origin_price, price, quantity,
                             support_return, unit, delivery_quantity, received_quantity,
                             deleted_flag, creator, create_time, updater, update_time, version)
VALUES (2, 1, 2, 2, '测试商品2', '223_223_223', '白色/小瓶', '', '', 1, '334_334_334', '原厂件', '吉利', 100.22, 89.88,
        1,
        true, '件', 0, 0, 0, 'ricardo zhou', '2024-04-06 17:19:29', 'ricardo zhou', '2024-04-06 17:19:31', 0);

/*前台类目*/
INSERT INTO product_category (id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (1, 2, 0, '办公用品', 1, 1, 0,
        'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);
INSERT INTO product_category (id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (2, 2, 0, '电器', 1, 1, 0,
        'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);

INSERT INTO product_category (id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (3, 2, 1, '笔类', 1, 2, 0,
        'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);

INSERT INTO product_category (id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (4, 2, 0, '服饰', 1, 1, 0,
        'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);
INSERT INTO product_category (id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (5, 2, 4, '周年文化衫', 1, 2, 0,
        'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);
INSERT INTO product_category (id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (6, 2, 5, '35周年文化衫', 1, 3, 0,
        'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);

INSERT INTO product_category (id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                              update_time, version)
VALUES (7, 2, 2, '电视', 1, 2, 0,
        'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);

-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` (id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                                update_time, version)
VALUES (8, 1, 0, '办公用品', 1, 1, 0, 'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);
INSERT INTO `product_category` (id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                                update_time, version)
VALUES (9, 1, 0, '电器', 1, 1, 0, 'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);
INSERT INTO `product_category`(id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                               update_time, version)
VALUES (10, 1, 8, '笔类', 1, 2, 0, 'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);
INSERT INTO `product_category`(id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                               update_time, version)
VALUES (11, 1, 0, '服饰', 1, 1, 0, 'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);
INSERT INTO `product_category`(id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                               update_time, version)
VALUES (12, 1, 11, '周年文化衫', 1, 2, 0, 'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);
INSERT INTO `product_category`(id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                               update_time, version)
VALUES (13, 1, 12, '35周年文化衫', 1, 3, 0, 'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);
INSERT INTO `product_category`(id, mall_id, parent_id, name, sort, level, deleted_flag, creator, create_time, updater,
                               update_time, version)
VALUES (14, 1, 9, '电视', 1, 2, 0, 'saber', '2024-04-06 17:19:29', 'saber', '2024-04-06 17:19:31', 0);


/*收货地址*/
insert into customer_address(id, customer_id, mall_id, pca_code, address, receiver, phone, is_default, deleted_flag,
                             address_full, creator,
                             create_time, updater, update_time, version)
values (1, 1, 1, '110101', 'cccccxxxxx', 'xxx收件人', 'xxx-xxx-xxxx', 1, 0, '', 'saber', current_timestamp, 'saber',
        current_timestamp, 0);
insert into customer_address(id, customer_id, mall_id, pca_code, address, receiver, phone, is_default, deleted_flag,
                             address_full, creator,
                             create_time, updater, update_time, version)
values (2, 1, 2, '110111', 'cccccxxxxx', 'xxx2收件人', 'xxx-xxx-xxxx', 1, 0, '', 'saber', current_timestamp, 'saber',
        current_timestamp, 0);

/*首页类目广告位*/
insert into home_page_category(id, mall_id, c1_id, img_url, sort, deleted_flag, creator, create_time, updater,
                               update_time, version)
values (1, 2, 1, 'http://xxxxxxx', 0, 0, 'saber', current_timestamp, 'saber', current_timestamp, 0);

insert into home_page_category(id, mall_id, c1_id, img_url, sort, deleted_flag, creator, create_time, updater,
                               update_time, version)
values (2, 2, 2, 'http://xxxxxxx', 0, 0, 'saber', current_timestamp, 'saber', current_timestamp, 0);

insert into home_page_category(id, mall_id, c1_id, img_url, sort, deleted_flag, creator, create_time, updater,
                               update_time, version)
values (3, 2, 4, 'http://xxxxxxx', 0, 0, 'saber', current_timestamp, 'saber', current_timestamp, 0);

-- Records of product_brand
-- ----------------------------
INSERT INTO `product_brand`
VALUES (1, 1, '吉利', 0, '', 0, 'ricardo_zhou', '2024-04-12 18:16:31', 'ricardo_zhou', '2024-04-12 18:16:31', 0);
INSERT INTO `product_brand`
VALUES (2, 1, '领克', 0, '', 0, 'ricardo_zhou', '2024-04-12 18:16:55', 'ricardo_zhou', '2024-04-12 18:16:55', 0);
INSERT INTO `product_brand`
VALUES (3, 1, '极氪', 0, '', 0, 'ricardo_zhou', '2024-04-12 18:17:30', 'ricardo_zhou', '2024-04-12 18:17:30', 0);
-- Records of sku_specs_type
-- ----------------------------
INSERT INTO `sku_specs_type`
VALUES (1, 1, '型号', 0, 0, 'ricardo_zhou', '2024-04-12 18:40:21', 'ricardo_zhou', '2024-04-12 18:40:21', 0);

-- Records of sku_specs
-- ----------------------------
INSERT INTO `sku_specs`
VALUES (1, 1, '大瓶', 0, b'1', 0, 'ricardo_zhou', '2024-04-12 18:40:49', 'ricardo_zhou', '2024-04-12 18:40:49', 0);
INSERT INTO `sku_specs`
VALUES (2, 1, '小瓶', 0, b'1', 0, 'ricardo_zhou', '2024-04-12 18:40:54', 'ricardo_zhou', '2024-04-12 18:40:54', 0);

INSERT INTO sku_specs_rel (id, sku_id, specs_type_id, specs, deleted_flag, creator, create_time, updater,
                           update_time, version)
VALUES (1, 1, 1, '大瓶', 0, 'ricardo', '2024-04-16 11:34:45', 'ricardo', '2024-04-16 11:34:53', 0);
INSERT INTO sku_specs_rel (id, sku_id, specs_type_id, specs, deleted_flag, creator, create_time, updater,
                           update_time, version)
VALUES (2, 2, 1, '小瓶', 0, 'ricardo', '2024-04-16 11:34:45', 'ricardo', '2024-04-16 11:34:53', 0);


INSERT INTO `product`
VALUES (1, 2, 2, 2, 'P010005', 2, 0, 1, '发动机冷却液', '吉利长效冷却液1500ML', 1, 0, 0, 'ricardo_zhou',
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

INSERT INTO `home_page_sku`
VALUES (1, 2, 1, 2, 1, 0, 'saber', '2024-04-15 14:38:16', 'saber', '2024-04-15 14:38:16', 0);
INSERT INTO `home_page_sku`
VALUES (2, 2, 2, 1, 1, 0, 'saber', '2024-04-15 14:38:16', 'saber', '2024-04-15 14:38:16', 0);

INSERT INTO `product_tag_rel`
VALUES (1, 1, 3, 0, 'saber', '2024-04-12 18:50:01', 'saber', '2024-04-12 18:50:01', 0);

INSERT INTO sale_order_pay (id, sn, sale_order_id, parent_id, pay_type, amount, pay_sn, state, pay_time,
                            deleted_flag, creator, create_time, updater, update_time, version)
VALUES (1, '1231231', 1, 0, 1, 1002.12, '123123123', 0, '2024-04-24 14:06:17', 0, '123', '2024-04-24 14:06:29', '123',
        '2024-04-24 14:06:34', 0);
