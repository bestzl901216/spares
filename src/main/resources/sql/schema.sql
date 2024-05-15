DROP TABLE IF EXISTS `account`;
create table account
(
    id               int auto_increment comment 'id' primary key,
    name             varchar(64)         not null comment '名称',
    encoded_password varchar(256)        not null comment '编码后的密码',
    phone            varchar(16)         not null comment '手机号码',
    state            int(11)             Not NULL DEFAULT 0 COMMENT '状态，0=禁用，1=启用',
    icon             varchar(256)        not null DEFAULT '' comment '头像',
    deleted_flag     int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator          varchar(64)         not null comment '创建者',
    create_time      datetime            not null comment '创建时间',
    updater          varchar(64)         not null comment '更新者',
    update_time      datetime            not null comment '更新时间',
    version          int                 not null comment '版本号',
    constraint uk_name unique (name,deleted_flag),
    constraint uk_phone unique (phone,deleted_flag)
) COMMENT = '账号表';

DROP TABLE IF EXISTS `mall`;
CREATE TABLE `mall`
(
    `id`                       int auto_increment comment 'id' primary key,
    `name`                     varchar(64)         NOT NULL COMMENT '租户名称',
    `site_name`                varchar(64)         NOT NULL COMMENT '商城名称',
    `motto`                    varchar(256)        NOT NULL Default '' COMMENT '商城标语',
    `service_phone`            varchar(16)         NOT NULL Default '' COMMENT '服务热线',
    `logo`                     varchar(256)        NOT NULL Default '' COMMENT 'logo路径',
    `state`                    int(11)             Not NULL DEFAULT 0 COMMENT '状态，0=禁用，1=启用',
    `order_pay_expiration`     int(11)             Not NULL DEFAULT 30 COMMENT '订单支付超时（分钟）',
    `order_receive_expiration` int(11)             Not NULL DEFAULT 7 COMMENT '确认收货超时（天）',
    `order_return_expiration`  int(11)             Not NULL DEFAULT 7 COMMENT '售后超时超时（天）',
    `tax_point`                decimal(10, 2)      Not NULL DEFAULT 0.05 COMMENT '税点',
    `style_mode`               int(11)             Not NULL DEFAULT 1 COMMENT '商城样式：模式1=1，模式2=2',
    deleted_flag               int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator                    varchar(64)         not null comment '创建者',
    create_time                datetime            not null comment '创建时间',
    updater                    varchar(64)         not null comment '更新者',
    update_time                datetime            not null comment '更新时间',
    version                    int                 not null comment '版本号',
    constraint uk_name unique (name,deleted_flag)
) COMMENT = '租户表';

DROP TABLE IF EXISTS `market_channel_rel`;
CREATE TABLE `market_channel_rel`
(
    `id`              int auto_increment comment 'id' primary key,
    platform_type     int                 Not NULL DEFAULT 0 COMMENT '0商城，1管理平台，2租户平台，3商家平台，4店铺',
    platform_id       int                 Not NULL DEFAULT 0 COMMENT '平台Id',
    market_channel_id int                 Not NULL DEFAULT 0 COMMENT '渠道id',
    markup_rate       decimal(10, 2)      NOT NULL default 0 COMMENT '加价率，计算时需除100',
    deleted_flag      int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator           varchar(64)         not null comment '创建者',
    create_time       datetime            not null comment '创建时间',
    updater           varchar(64)         not null comment '更新者',
    update_time       datetime            not null comment '更新时间',
    version           int                 not null comment '版本号',
    constraint uk unique (platform_type,platform_id,market_channel_id,deleted_flag)
) COMMENT = '渠道关系表';

DROP TABLE IF EXISTS `role`;
create table role
(
    id            int auto_increment comment 'id' primary key,
    name          varchar(64)         not null comment '名称',
    remark        varchar(128)        not null comment '描述',
    platform_type int                 not null comment '0商城，1管理平台，2租户平台，3商家平台，4店铺',
    platform_id   int                 not null comment '平台Id',
    permissions   varchar(2000)       not null default '[]' comment '权限集合',
    deleted_flag  int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator       varchar(64)         not null comment '创建者',
    create_time   datetime            not null comment '创建时间',
    updater       varchar(64)         not null comment '更新者',
    update_time   datetime            not null comment '更新时间',
    version       int                 not null comment '版本号',
    constraint uk_platform_type_id_name_deletedFlag unique (platform_type, platform_id, name, deleted_flag)
) COMMENT = '角色表';

DROP TABLE IF EXISTS `account_role_rel`;
create table account_role_rel
(
    id           int auto_increment comment 'id' primary key,
    account_id   int                 Not NULL COMMENT '账号id',
    role_id      int                 not null comment '角色id',
    state        int                 Not NULL COMMENT '状态，0=禁用，1=启用',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    index idx_account_id (account_id),
    index idx_role_id (role_id)
) COMMENT = '账号角色关联表';

DROP TABLE IF EXISTS `market_channel`;
create table market_channel
(
    id           int auto_increment comment 'id' primary key,
    name         varchar(64)         Not NULL COMMENT '渠道名称',
    remark       varchar(128)        not null comment '备注',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint uk unique (name, deleted_flag)
) COMMENT = '渠道表';


DROP TABLE IF EXISTS `customer`;
create table customer
(
    id                int auto_increment comment 'id' primary key,
    name              varchar(64)         not null comment '客户名称',
    type              int                 not null comment '客户类型，1=企业，2=个人',
    identity_sn       varchar(128)        not null comment '公司税号或个人身份证',
    pca_code          varchar(64)         null     default '' comment '省市区编码',
    province          varchar(256)        not null default '' comment '省',
    city              varchar(256)        not null default '' comment '市',
    area              varchar(256)        not null default '' comment '区',
    address           varchar(256)        not null comment '详细地址',
    identity_image    varchar(256)        not null comment '营业执照或身份证照片url',
    shop_image        varchar(256)        not null comment '门头照片url',
    workroom_image    varchar(256)        not null comment '工作间照片url',
    market_channel_id int                 not null comment '渠道id',
    pay_password      varchar(256)        not null comment '支付密码',
    state             int                 not null comment '状态，0=禁用，1=启用',
    certify_state     int                 not null comment '认证状态，0=未认证，1=已认证，2=待审核，3=审核失败',
    deleted_flag      int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator           varchar(64)         not null comment '创建者',
    create_time       datetime            not null comment '创建时间',
    updater           varchar(64)         not null comment '更新者',
    update_time       datetime            not null comment '更新时间',
    version           int                 not null comment '版本号',
    constraint uk unique (name, deleted_flag)
) COMMENT = '客户表';

DROP TABLE IF EXISTS `customer_address`;
create table customer_address
(
    id           int auto_increment comment 'id' primary key,
    customer_id  int                 not null comment '客户Id',
    mall_id      int                 not null comment '商城编号',
    pca_Code     varchar(64)         null     default '' comment '省市区编码',
    province     varchar(256)        not null default '' comment '省',
    city         varchar(256)        not null default '' comment '市',
    area         varchar(256)        not null default '' comment '区',
    address      varchar(256)        not null comment '详细地址',
    address_full varchar(256)        not null comment '省市区+详细地址',
    receiver     varchar(64)         not null comment '收货人',
    phone        varchar(64)         not null comment '收货人手机',
    is_default   bit                 not null default 0 comment '默认地址',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号'
) COMMENT = '客户收货地址表';

create unique index unique_1 on customer_address (customer_id, mall_id, receiver, phone, address_full, deleted_flag);

DROP TABLE IF EXISTS `customer_invoice`;
create table customer_invoice
(
    id               int auto_increment comment 'id' primary key,
    customer_id      int                 not null comment '客户Id',
    invoiceType      int                 not null default 1 comment '发票类型, 1公司，2个人',
    title            varchar(64)         not null comment '发票抬头',
    taxSn            varchar(64)         not null default '' comment '税号',
    address          varchar(256)        not null default '' comment '注册地址',
    phone            varchar(64)         not null default '' comment '注册电话',
    bank             varchar(64)         not null default '' comment '开户银行',
    bank_account     varchar(64)         not null default '' comment '银行账号',
    receiver         varchar(64)         not null default '' comment '收票人',
    receiver_phone   varchar(64)         not null default '' comment '收票人手机',
    receiver_address varchar(256)        not null default '' comment '收票人地址',
    deleted_flag     int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator          varchar(64)         not null comment '创建者',
    create_time      datetime            not null comment '创建时间',
    updater          varchar(64)         not null comment '更新者',
    update_time      datetime            not null comment '更新时间',
    version          int                 not null comment '版本号'
) COMMENT = '客户发票表';

DROP TABLE IF EXISTS `mall_sap`;
create table mall_sap
(
    id           int auto_increment comment 'id' primary key,
    mall_id      int                 not null comment '租户id',
    name         varchar(64)         not null comment 'sap名称',
    state        int                 not null default 0 comment '状态，0=禁用，1=启用',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号'
) comment = 'sap类型表';

DROP TABLE IF EXISTS `mall_bank_account`;
create table mall_bank_account
(
    id           int auto_increment comment 'id' primary key,
    mall_id      int                 not null comment '租户id',
    name         varchar(64)         not null comment '企业名称',
    bank         varchar(64)         not null comment '开户行',
    bank_account varchar(64)         not null comment '银行账户',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号'
) comment = '收款账户表';

DROP TABLE IF EXISTS `mall_banner`;
create table mall_banner
(
    id           int auto_increment comment 'id' primary key,
    mall_id      int                 not null comment '租户id,0代表平台',
    title        varchar(64)         not null comment '标题',
    image_url    varchar(128)        not null comment '图片url',
    action_url   varchar(128)        not null comment '跳转url',
    state        int                 not null default 0 comment '状态，0=禁用，1=启用',
    sort         int                 not null default 0 comment '排序',
    position     int                 not null comment '0.登录页 1.首页',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号'
) comment = '商城轮播广告表';


DROP TABLE IF EXISTS `supplier`;
create table supplier
(
    id              int auto_increment comment 'id' primary key,
    mall_id         int                 not null comment '租户id',
    name            varchar(64)         not null comment '商家名称',
    sap_id          int                 not null comment 'sap类型',
    state           int                 not null default 0 comment '状态，0=禁用，1=启用',
    bank_account_id int                 not null comment '收款账户',
    merchant_no     varchar(64)         not null comment '商户号',
    deleted_flag    int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator         varchar(64)         not null comment '创建者',
    create_time     datetime            not null comment '创建时间',
    updater         varchar(64)         not null comment '更新者',
    update_time     datetime            not null comment '更新时间',
    version         int                 not null comment '版本号',
    constraint uk unique (mall_id, name, deleted_flag)
) comment = '商家表';

DROP TABLE IF EXISTS `product_category`;
create table product_category
(
    id           int auto_increment comment 'id' primary key,
    mall_id      int                 not null comment '租户id',
    parent_id    int                 not null default 0 comment '父节点id',
    parent_path  varchar(256)        not null default '' comment '父节点路径',
    name         varchar(64)         not null comment '类目名称',
    sort         int                 not null comment '排序',
    level        int                 not null comment '层级',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint uk unique (mall_id, parent_id, name, deleted_flag)
) comment = '前台商品类目表';

DROP TABLE IF EXISTS `product_quality`;
create table product_quality
(
    id           int auto_increment comment 'id' primary key,
    mall_id      int                 not null comment '租户id',
    name         varchar(64)         not null comment '品质名称',
    remark       varchar(64)         not null default '' comment '备注',
    sort         int                 not null default 0 comment '排序',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint uk unique (mall_id, name, deleted_flag)
) comment = '商品品质表';

DROP TABLE IF EXISTS `product_brand`;
create table product_brand
(
    id           int auto_increment comment 'id' primary key,
    mall_id      int                 not null comment '租户id',
    name         varchar(64)         not null comment '品牌名称',
    sort         int                 not null default 0 comment '排序',
    logo         varchar(256)        not null default '' comment 'logo url',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    UNIQUE INDEX `uq` (`mall_id`, `name`, `deleted_flag`) USING BTREE
) comment = '商品品牌表';

DROP TABLE IF EXISTS `product_tag`;
create table product_tag
(
    id               int auto_increment comment 'id' primary key,
    mall_id          int                 not null comment '租户id',
    name             varchar(64)         not null comment '标签名称',
    font_color       varchar(64)         not null default '' comment '字体颜色',
    background_color varchar(64)         not null default '' comment '背景颜色',
    remark           varchar(64)         not null default '' comment '备注',
    sort             int                 not null default 0 comment '排序',
    state            int                 not null default 1 comment '状态，0=禁用，1=启用',
    deleted_flag     int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator          varchar(64)         not null comment '创建者',
    create_time      datetime            not null comment '创建时间',
    updater          varchar(64)         not null comment '更新者',
    update_time      datetime            not null comment '更新时间',
    version          int                 not null comment '版本号',
    constraint uk unique (mall_id, name, deleted_flag)
) comment = '商品标签表';

DROP TABLE IF EXISTS `sale_order`;
create table sale_order
(
    id                  int auto_increment comment 'id' primary key,
    mall_id             int                 not null comment '租户id',
    supplier_id         int                 not null comment '商家id',
    shop_id             int                 not null comment '店铺id',
    customer_id         int                 not null comment '客户id',
    customer_account_id int                 not null comment '客户账号id',
    sn                  varchar(64)         not null comment '订单编号',
    receiver            varchar(64)         not null comment '收货人',
    receiver_province   varchar(64)         not null comment '收货地址省',
    receiver_city       varchar(64)         not null comment '收货地址市',
    receiver_area       varchar(64)         not null comment '收货地址区',
    receiver_address    varchar(256)        not null comment '收货地址详情',
    receiver_phone      varchar(64)         not null comment '收货人手机号',
    state               int                 not null comment '状态，-2=已退款，-1=已取消， 1=待支付，2=待审批，3=待同步，4=同步失败，5=待发货，6=待收货，7=已收货，8=已完成',
    amount              decimal(10, 2)      not null comment '金额',
    pay_amount          decimal(10, 2)      not null comment '实付金额',
    freight_type        int                 not null comment '运费支付方式，1=包邮，2=到付',
    delivery_type       int                 not null default 1 comment '发货方式，1=物流',
    pay_time            datetime            null comment '支付时间',
    approved_time       datetime            null comment '审批时间',
    delivery_time       datetime            null comment '发货完成时间',
    received_time       datetime            null comment '收货完成时间',
    finished_time       datetime            null comment '订单完成时间',
    cancel_time         datetime            null comment '取消时间',
    cancel_reason       varchar(256)        not null default '' comment '取消原因',
    cancel_by           int                 not null default 0 comment '取消账户id',
    cancel_from         int                 not null default 0 comment '取消方，0=无，1=系统，2=买家，3=卖家',
    pay_deadline        datetime            null comment '自动取消时间',
    received_deadline   datetime            null comment '自动收货时间',
    finished_deadline   datetime            null comment '自动完成时间',
    pay_type            int                 not null default 0 comment '支付方式，0=无，1=支付宝，2=微信，3=企业网银，4=线下转账，5挂账，6余额',
    invoice_id          int                 not null default 0 comment '发票id',
    tax_rate            decimal(10, 2)      not null default 0 comment '税率',
    remark              varchar(256)        not null default '' comment '备注',
    sap_sn              varchar(64)         not null default '' comment 'sap订单号',
    is_reverse          bit(1)              not null default 0 comment '是否反向订单',
    creator_id          int                 not null default 0 comment '反向订单制单账号id',
    deleted_flag        int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator             varchar(64)         not null comment '创建者',
    create_time         datetime            not null comment '创建时间',
    updater             varchar(64)         not null comment '更新者',
    update_time         datetime            not null comment '更新时间',
    version             int                 not null comment '版本号',
    constraint uk unique (sn),
    index k_mid_cid (mall_id, customer_id) USING BTREE
) comment = '订单表';

DROP TABLE IF EXISTS `sale_order_item`;
create table sale_order_item
(
    id                int auto_increment comment 'id' primary key,
    sale_order_id     int                 not null comment '订单id',
    product_id        int                 not null comment '商品id',
    sku_id            int                 not null comment 'skuId',
    product_name      varchar(64)         not null comment '商品名称',
    product_code      varchar(64)         not null comment '商品编号',
    sku_code          varchar(64)         not null comment 'SKU编号',
    image             varchar(256)        not null default '' comment 'SKU图片',
    material_code     varchar(64)         not null comment '物料编号',
    product_quality   varchar(64)         not null default '' comment '商品品质',
    product_brand     varchar(64)         not null comment '商品品牌',
    sku_specs         varchar(256)        not null comment 'SKU规格',
    origin_price      decimal(10, 2)      not null comment '原单价',
    price             decimal(10, 2)      not null comment '单价',
    quantity          int                 not null comment '数量',
    is_free_shipping  bit                 not null comment '是否包邮',
    support_return    bit                 not null comment '是否支持退货',
    unit              varchar(64)         not null default '' comment '单位',
    delivery_quantity int                 not null comment '发货数量',
    received_quantity int                 not null comment '收货数量',
    deleted_flag      int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator           varchar(64)         not null comment '创建者',
    create_time       datetime            not null comment '创建时间',
    updater           varchar(64)         not null comment '更新者',
    update_time       datetime            not null comment '更新时间',
    version           int                 not null comment '版本号',
    index k_sid (sale_order_id) USING BTREE
) comment = '订单商品表';


DROP TABLE IF EXISTS `sale_order_flow`;
create table sale_order_flow
(
    id            int auto_increment comment 'id' primary key,
    sale_order_id int                 not null comment '订单id',
    account_id    varchar(64)         not null comment '操作人账号id',
    role_name     varchar(64)         not null comment '操作人角色名称',
    content       varchar(1024)       not null comment '内容',
    images        text                null comment '图片',
    deleted_flag  int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator       varchar(64)         not null comment '创建者',
    create_time   datetime            not null comment '创建时间',
    updater       varchar(64)         not null comment '更新者',
    update_time   datetime            not null comment '更新时间',
    version       int                 not null comment '版本号',
    index k_sid (sale_order_id) USING BTREE
) comment = '订单操作流水表';

DROP TABLE IF EXISTS `sale_order_pay`;
create table sale_order_pay
(
    id            int auto_increment comment 'id' primary key,
    sn            varchar(64)         not null comment '批次号',
    sale_order_id int                 not null comment '订单id',
    parent_id     int                 not null default 0 comment '父id',
    pay_type      int                 not null comment '支付方式，0=无，1=支付宝，2=微信，3=企业网银，4=线下转账，5挂账，6余额',
    amount        decimal(10, 2)      not null comment '支付金额',
    pay_sn        varchar(64)         not null default '' comment '支付流水号',
    state         int                 not null comment '支付方式，-1失败，0=待支付，1=待审核，2=成功',
    pay_time      datetime            null comment '支付时间',
    deleted_flag  int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator       varchar(64)         not null comment '创建者',
    create_time   datetime            not null comment '创建时间',
    updater       varchar(64)         not null comment '更新者',
    update_time   datetime            not null comment '更新时间',
    version       int                 not null comment '版本号',
    UNIQUE INDEX `uk_sn` (`sn`) USING BTREE,
    INDEX `k_pid` (`parent_id`) USING BTREE,
    INDEX `k_sid` (`sale_order_id`) USING BTREE
) comment = '订单支付表';

DROP TABLE IF EXISTS `sale_order_pay_approve`;
create table sale_order_pay_approve
(
    id             int auto_increment comment 'id' primary key,
    batch_id       int                 not null comment '总批次id',
    supplier_id    int                 not null comment '商家id',
    amount         decimal(10, 2)      not null comment '支付金额',
    pay_sn         varchar(64)         not null default '' comment '支付流水号',
    state          int                 not null comment '支付方式，-1失败，1=待审核，2=成功',
    remark         varchar(256)        not null default '' comment '支付备注',
    approve_remark varchar(256)        not null default '' comment '审核备注',
    approve_time  datetime             null comment '审核时间',
    vouchers       varchar(1024)       not null default '' comment '支付凭证图片url',
    deleted_flag   int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator        varchar(64)         not null comment '创建者',
    create_time    datetime            not null comment '创建时间',
    updater        varchar(64)         not null comment '更新者',
    update_time    datetime            not null comment '更新时间',
    version        int                 not null comment '版本号',
    index k_bid (batch_id) USING BTREE
) comment = '订单支付审核表';

DROP TABLE IF EXISTS batch_pay_fail_rel;
create table batch_pay_fail_rel
(
    id            int auto_increment comment 'id' primary key,
    batch_id      int                 not null comment '批次号',
    sale_order_id int                 not null comment '订单id',
    deleted_flag  int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator       varchar(64)         not null comment '创建者',
    create_time   datetime            not null comment '创建时间',
    updater       varchar(64)         not null comment '更新者',
    update_time   datetime            not null comment '更新时间',
    version       int                 not null comment '版本号',
    index k_bid (batch_id) USING BTREE
) comment = '支付失败';

DROP TABLE IF EXISTS shop;
create table shop
(
    id            int auto_increment comment 'id' primary key,
    mall_id       int                 not null comment '租户id',
    supplier_id   int                 not null comment '商家id',
    name          varchar(64)         not null comment '店铺名称',
    state         int                 not null default 0 comment '状态，0=禁用，1=启用',
    product_audit int                 not null default 1 comment '商品上架审核，0=需要审核，1=不需要审核',
    admin_phone   varchar(64)         null comment '管理员手机号',
    admin_name    varchar(64)         null comment '管理员姓名',
    deleted_flag  int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator       varchar(64)         not null comment '创建者',
    create_time   datetime            not null comment '创建时间',
    updater       varchar(64)         not null comment '更新者',
    update_time   datetime            not null comment '更新时间',
    version       int                 not null comment '版本号',
    constraint uk unique (supplier_id, name, deleted_flag)
) comment = '店铺表';

DROP TABLE IF EXISTS notice;
create table notice
(
    id            int auto_increment comment 'id' primary key,
    platform_type int                 not null comment '0商城，1管理平台，2租户平台，3商家平台，4店铺',
    platform_id   int                 not null comment '平台Id',
    title         varchar(64)         not null comment '标题',
    content       varchar(64)         not null comment '内容',
    time          datetime            not null comment '发布时间',
    state         int                 not null comment '状态 0:未读 1:已读',
    deleted_flag  int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator       varchar(64)         not null comment '创建者',
    create_time   datetime            not null comment '创建时间',
    updater       varchar(64)         not null comment '更新者',
    update_time   datetime            not null comment '更新时间',
    version       int                 not null comment '版本号',
    index k_ptype_pid (platform_type, platform_id) USING BTREE
) comment = '消息表';

DROP TABLE IF EXISTS `after_sale_order`;
create table after_sale_order
(
    id                 int auto_increment comment 'id' primary key,
    sale_order_id      int                 not null comment '订单id',
    sn                 varchar(64)         not null comment '售后单号',
    after_sale_type    int                 not null comment '售后类型，1=仅退款，2=退货退款',
    state              int                 not null comment '状态，-2=已拒绝，-1=已取消，1=待处理，2=待收货，3=待退款，4=已退款',
    amount             decimal(10, 2)      not null comment '申请金额',
    amount_return      decimal(10, 2)      not null default 0 comment '实际退款金额',
    memo               varchar(256)        not null default '' comment '买家备注',
    approve_time       datetime            null comment '商家处理时间',
    amount_return_time datetime            null comment '退款时间',
    finished_time      datetime            null comment '完成时间',
    images             varchar(1024)       not null default '' comment '附件图片url，逗号分隔',
    consignee          varchar(64)         not null default '' comment '收货人',
    consignee_address  varchar(256)        not null default '' comment '收货人地址',
    consignee_phone    varchar(32)         not null default '' comment '收货人手机',
    refuse_reason      varchar(256)        not null default '' comment '拒绝理由',
    is_reverse         bit                 not null default 0 comment '是否为反向售后',
    creator_id         int                 not null comment '制单人id',
    approver_id        int                 not null default 0 comment '审批人id',
    approver           varchar(64)         not null default '' comment '审批人',
    deleted_flag       int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator            varchar(64)         not null comment '创建者',
    create_time        datetime            not null comment '创建时间',
    updater            varchar(64)         not null comment '更新者',
    update_time        datetime            not null comment '更新时间',
    version            int                 not null comment '版本号',
    constraint uk unique (sn)
) comment = '售后单表';

DROP TABLE IF EXISTS `after_sale_order_item`;
create table after_sale_order_item
(
    id                  int auto_increment comment 'id' primary key,
    alter_sale_order_id int                 not null comment '售后单id',
    sale_order_item_id  int                 not null comment '订单明细id',
    quantity            int                 not null comment '申请数量',
    amount              decimal(10, 2)      not null comment '申请金额',
    deleted_flag        int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator             varchar(64)         not null comment '创建者',
    create_time         datetime            not null comment '创建时间',
    updater             varchar(64)         not null comment '更新者',
    update_time         datetime            not null comment '更新时间',
    version             int                 not null comment '版本号',
    constraint uk unique (alter_sale_order_id, sale_order_item_id)
) comment = '售后明细表';

DROP TABLE IF EXISTS `message_produce_log`;
create table message_produce_log
(
    id           int auto_increment comment 'id' primary key,
    message_id   varchar(64)         not null comment '消息id',
    topic        varchar(64)         not null comment '消息主题',
    tag          varchar(64)         not null comment '消息标签',
    body         text                not null comment '消息体',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint uk_message_id unique (message_id)
) comment = '消息生产日志表';

DROP TABLE IF EXISTS `message_consume_log`;
create table message_consume_log
(
    id             int auto_increment comment 'id' primary key,
    message_id     varchar(64)         not null comment '消息id',
    topic          varchar(64)         not null comment '消息主题',
    tag            varchar(64)         not null comment '消息标签',
    body           text                not null comment '消息体',
    consumer_group varchar(64)         not null comment '消息者消费分组',
    deleted_flag   int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator        varchar(64)         not null comment '创建者',
    create_time    datetime            not null comment '创建时间',
    updater        varchar(64)         not null comment '更新者',
    update_time    datetime            not null comment '更新时间',
    version        int                 not null comment '版本号',
    constraint uk_message_id_consume_group unique (message_id, consumer_group)
) comment = '消息消费日志表';

DROP TABLE IF EXISTS `material`;
create table `material`
(
    id           int auto_increment comment 'id' primary key,
    mall_id      int                 not null comment '租户id',
    supplier_id  int                 not null comment '商家id',
    code         varchar(64)         not null comment '物料编码',
    name         varchar(256)        not null comment '物料名称',
    oe_no        varchar(64)         not null comment 'OE编码',
    category     varchar(64)         not null comment '后台类目',
    stock        integer             not null comment '库存',
    unit         varchar(64)         not null comment '单位',
    price        decimal(10, 2)      not null comment '基础价格',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint uk unique (supplier_id, code)
) comment = '物料表';

drop table if exists `material_shop_rel`;
create table `material_shop_rel`
(
    id           int auto_increment comment 'id' primary key,
    material_id  int                 not null comment '物料id',
    shop_id      int                 not null comment '店铺id',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint uk unique (`material_id`, `shop_id`, `deleted_flag`)
) comment = '物料店铺关联表';

drop table if exists `product`;
create table `product`
(
    id           int auto_increment comment 'id' primary key,
    mall_id      int                 not null comment '租户id',
    supplier_id  int                 not null comment '商家id',
    shop_id      int                 not null comment '店铺id',
    code         varchar(64) comment '商品编码',
    category_id  int                 not null comment '前台类目id',
    quality_id   int                 not null comment '品质id',
    brand_id     int                 not null comment '品牌id',
    name         varchar(64)         not null comment '商品名称',
    description  varchar(256)        not null default '' comment '商品描述',
    state        int                 not null comment '状态:-1审核失败,0待审核,1已下架,2已上架',
    sort         int                 not null default 0 comment '排序',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    index k_supplier_id (supplier_id) USING BTREE,
    index k_shop_id (shop_id) USING BTREE
) comment = '商品表';

drop table if exists `product_image`;
create table `product_image`
(
    id           int auto_increment comment 'id' primary key,
    product_id   int                 not null comment '商品id',
    url          varchar(256)        not null comment '图片链接',
    sort         int                 not null default 0 comment '排序',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    index k_pid (product_id) USING BTREE
) comment = '商品详情图片url表';

drop table if exists `product_vehicle_rel`;
create table `product_vehicle_rel`
(
    id               int auto_increment comment 'id' primary key,
    product_id       int                 not null comment '商品id',
    vehicle_model_id int                 not null comment '车型id',
    deleted_flag     int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator          varchar(64)         not null comment '创建者',
    create_time      datetime            not null comment '创建时间',
    updater          varchar(64)         not null comment '更新者',
    update_time      datetime            not null comment '更新时间',
    version          int                 not null comment '版本号',
    constraint uk unique (`product_id`, `vehicle_model_id`, `deleted_flag`)
) comment = '商品车型关联表';

drop table if exists `product_tag_rel`;
create table `product_tag_rel`
(
    id           int auto_increment comment 'id' primary key,
    product_id   int                 not null comment '商品id',
    tag_id       int                 not null comment '标签id',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint uk unique (`product_id`, `tag_id`, `deleted_flag`)
) comment = '商品标签关联表';

drop table if exists `vehicle`;
create table `vehicle`
(
    id           int auto_increment comment 'id' primary key,
    mallId       int                 not null comment '租户id',
    code         varchar(64)         not null comment '品牌编码',
    name         varchar(64)         not null comment '品牌名称',
    sort         int                 not null default 0 comment '排序',
    parentId     int                 not null default 0 comment '父id',
    type         int                 not null comment '类型：1品牌，2车系，3车型',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号'
) comment = '车型目录表';

drop table if exists `product_props`;
create table `product_props`
(
    id           int auto_increment comment 'id' primary key,
    product_id   int                 not null comment '商品id',
    name         varchar(64)         not null comment '属性标题',
    value        varchar(256)         not null comment '属性值',
    sort         int                 not null default 0 comment '排序',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint uk unique (`product_id`, `name`, `deleted_flag`)
) comment = '商品属性表';

drop table if exists `sku`;
create table `sku`
(
    id               int auto_increment comment 'id' primary key,
    product_id       int                 not null comment '商品id',
    code             varchar(64)         not null comment '编码',
    stock            int                 not null comment '库存',
    is_free_shipping bit(1)              not null default 0 comment '是否包邮',
    moq              int                 not null comment '起订量',
    unit             varchar(64)         not null comment '单位',
    material_id      int                 not null comment '物料id',
    state            int                 not null default 0 comment '状态：0禁用，1启用',
    default_image    varchar(256)        not null default '' comment '第一张图片',
    sales_volume     int                 not null default 0 comment '销量',
    sort             int                 not null default 0 comment '排序',
    deleted_flag     int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator          varchar(64)         not null comment '创建者',
    create_time      datetime            not null comment '创建时间',
    updater          varchar(64)         not null comment '更新者',
    update_time      datetime            not null comment '更新时间',
    version          int                 not null comment '版本号',
    index k_pid (product_id) USING BTREE
) comment = 'sku表';

drop table if exists `sku_image`;
create table `sku_image`
(
    id           int auto_increment comment 'id' primary key,
    sku_id       int                 not null comment 'sku_id',
    url          varchar(256)        not null comment '图片链接',
    sort         int                 not null comment '排序',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    index k_sid (sku_id) USING BTREE
) comment = 'sku图片url表';

drop table if exists `sku_price`;
create table `sku_price`
(
    id                int auto_increment comment 'id' primary key,
    sku_id            int                 not null comment 'sku_id',
    market_channel_id int                 not null comment '渠道id',
    price             decimal(10, 2)      not null comment '固定价格',
    deleted_flag      int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator           varchar(64)         not null comment '创建者',
    create_time       datetime            not null comment '创建时间',
    updater           varchar(64)         not null comment '更新者',
    update_time       datetime            not null comment '更新时间',
    version           int                 not null comment '版本号',
    constraint `uk` unique (`sku_id`, `market_channel_id`, `deleted_flag`)
) comment = 'sku渠道固定价格表';

drop table if exists `sku_specs_type`;
create table `sku_specs_type`
(
    id           int auto_increment comment 'id' primary key,
    mall_id      int                 not null comment '租户id',
    name         varchar(64)         not null comment '规格类型名称',
    sort         int                 not null default 0 comment '排序',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    UNIQUE INDEX `uq` (`mall_id`, `name`, `deleted_flag`) USING BTREE
) comment = 'sku规格类型表';


drop table if exists `sku_specs`;
create table `sku_specs`
(
    id           int auto_increment comment 'id' primary key,
    type_id      int                 not null comment '规格类型',
    name         varchar(64)         not null comment '规格名称',
    sort         int                 not null default 0 comment '排序',
    selectable   bit                 not null default 0 comment '可在下拉框显示',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint `uk` unique (`type_id`, `name`, `deleted_flag`)
) comment = 'sku规格表';

drop table if exists `sku_specs_rel`;
create table `sku_specs_rel`
(
    id            int auto_increment comment 'id' primary key,
    sku_id        int                 not null comment 'sku_id',
    specs_type_id int                 not null comment '规格类型id',
    specs         varchar(64)         not null comment '规格值',
    deleted_flag  int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator       varchar(64)         not null comment '创建者',
    create_time   datetime            not null comment '创建时间',
    updater       varchar(64)         not null comment '更新者',
    update_time   datetime            not null comment '更新时间',
    version       int                 not null comment '版本号',
    constraint `uk` unique (`sku_id`, `specs_type_id`, `deleted_flag`)
) comment = 'sku规格关联表';

DROP TABLE IF EXISTS `platform_setting`;
CREATE TABLE `platform_setting`
(
    `id`               int auto_increment comment 'id' primary key,
    `name`             varchar(64)         NOT NULL COMMENT '商城平台名称',
    `motto`            varchar(256)        NOT NULL Default '' COMMENT '商城平台标语',
    `service_phone`    varchar(16)         NOT NULL Default '' COMMENT '商城平台服务热线',
    `service_time`     varchar(256)        NOT NULL Default '' COMMENT '服务时间',
    `logo`             varchar(256)        NOT NULL Default '' COMMENT 'logo路径',
    copyright          varchar(256)        NOT NULL Default '' COMMENT '版权',
    filings_icp        varchar(256)        NOT NULL Default '' COMMENT 'icp备案描述',
    filings_icp_url    varchar(256)        NOT NULL Default '' COMMENT 'icp备案跳转地址',
    filings_record     varchar(256)        NOT NULL Default '' COMMENT '网安备案',
    filings_record_url varchar(256)        NOT NULL Default '' COMMENT '网安备案跳转地址',
    deleted_flag       int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator            varchar(64)         not null comment '创建者',
    create_time        datetime            not null comment '创建时间',
    updater            varchar(64)         not null comment '更新者',
    update_time        datetime            not null comment '更新时间',
    version            int                 not null comment '版本号',
    constraint uk_name unique (name)
) COMMENT = '平台设置表';

DROP TABLE IF EXISTS `home_page_category`;
CREATE TABLE `home_page_category`
(
    `id`         int auto_increment comment 'id' primary key,
    `mall_id`    int                 NOT NULL COMMENT '商城编号',
    `c1_id`      int                 NOT NULL COMMENT '一级前台类目编号',
    `img_url`    varchar(256)        NOT NULL Default '' COMMENT '类目图片',
    sort         int                 NOT NULL Default 0 COMMENT '排序',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint uk_name unique (c1_id)
) COMMENT = '首页类目广告位';

DROP TABLE IF EXISTS `home_page_sku`;
CREATE TABLE `home_page_sku`
(
    `id`         int auto_increment comment 'id' primary key,
    `mall_id`    int                 NOT NULL COMMENT '商城编号',
    `sku_id`     int                 NOT NULL COMMENT 'sku编号',
    c1_id        int                 NOT NULL COMMENT '一级前台类目编号',
    sort         int                 NOT NULL Default 0 COMMENT '排序',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint uk unique (sku_id)
) COMMENT = '首页商品广告位';

DROP TABLE IF EXISTS `province_city_area`;
CREATE TABLE `province_city_area`
(
    `id`         int auto_increment comment 'id' primary key,
    `code`       varchar(64)         NOT NULL COMMENT '商城平台名称',
    `province`   varchar(256)        NOT NULL Default '' COMMENT '省',
    `city`       varchar(256)        NOT NULL Default '' COMMENT '市',
    `area`       varchar(256)        NOT NULL Default '' COMMENT '区',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号'
) COMMENT = '省市区表';

DROP TABLE IF EXISTS `delivery_note`;
create table delivery_note
(
    id                int auto_increment comment 'id' primary key,
    sale_order_id     int          not null comment '订单id',
    sn                varchar(64)  not null comment '发货单号',
    express_company   varchar(128) not null comment '物流公司',
    express_sn        varchar(64)  not null comment '物流单号',
    receiver_phone    varchar(64)  not null comment '收货人手机号',
    express_state     varchar(8)   not NULL COMMENT '物流状态',
    data              text         not null comment '物流信息',
    sync_time         datetime     not null comment '同步时间',
    send_time         datetime     not null comment '发货时间',
    received_time     datetime null comment '收货时间',
    received_deadline datetime     not null comment '收货截止时间',
    state             int          Not NULL COMMENT '状态',
    deleted_flag      int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator           varchar(64)  not null comment '创建者',
    create_time       datetime     not null comment '创建时间',
    updater           varchar(64)  not null comment '更新者',
    update_time       datetime     not null comment '更新时间',
    version           int          not null comment '版本号',
    constraint uk unique (sn),
    index k_sid (sale_order_id) USING BTREE
) COMMENT = '发货单';

DROP TABLE IF EXISTS `delivery_package`;
create table delivery_package
(
    id                 int auto_increment comment 'id' primary key,
    delivery_note_id   int                 not null comment '发货单id',
    sale_order_id      int                 not null comment '订单id',
    sale_order_item_id int                 not null comment '订单子项id',
    quantity           int                 not null comment '数量',
    deleted_flag       int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator            varchar(64)         not null comment '创建者',
    create_time        datetime            not null comment '创建时间',
    updater            varchar(64)         not null comment '更新者',
    update_time        datetime            not null comment '更新时间',
    version            int                 not null comment '版本号',
    constraint uk unique (delivery_note_id, sale_order_item_id, deleted_flag)
) COMMENT = '发货包裹';

DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`
(
    id           int auto_increment comment 'id' primary key,
    mall_id      int                 not null COMMENT '商城id',
    customer_id  int                 not null COMMENT '客户id',
    account_id   int                 not null COMMENT '账号id',
    sku_id       int                 not null COMMENT 'sku_id',
    selected     bit                 not null COMMENT '是否选中',
    quantity     int                 not null COMMENT '数量',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint uk unique (mall_id, customer_id, account_id, sku_id, deleted_flag)
) COMMENT = '购物车';

DROP TABLE IF EXISTS `search_history`;
CREATE TABLE `search_history`
(
    id           int auto_increment comment 'id' primary key,
    mall_id      int                 not null COMMENT '商城id',
    customer_id  int                 not null COMMENT '客户id',
    account_id   int                 not null COMMENT '账号id',
    keyword      varchar(64)         not null COMMENT '关键词',
    deleted_flag int NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
    creator      varchar(64)         not null comment '创建者',
    create_time  datetime            not null comment '创建时间',
    updater      varchar(64)         not null comment '更新者',
    update_time  datetime            not null comment '更新时间',
    version      int                 not null comment '版本号',
    constraint uk_mall_customer_account_keyword unique (mall_id, customer_id, account_id, keyword, deleted_flag)
) COMMENT = '搜索记录';