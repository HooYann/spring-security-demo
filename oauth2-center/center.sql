# 用户表
DROP TABLE IF EXISTS `z_user`;
CREATE TABLE `z_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'F女M男U未知',
  `phone_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态: 1-可用，0-禁用，-1-锁定',
  `deleted` tinyint NULL DEFAULT 0 COMMENT '删除状态: 1-已删除,0-未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户登录表' ROW_FORMAT = Dynamic;

#phone_number 加 索引 email + 索引

# 测试数据
INSERT INTO z_user (username, nickname, password, name, sex, phone_number, email, avatar, create_at, update_at, status, deleted) VALUES ('Yann', '胡大拿', '$2a$10$USsqXFfue.To1CYDoQ6hUeY2g9sTGA9iuTI9mjeG7IpQGFlDwpDxq', '胡大牛', 'M', '15917377535', null, 'https://img.beautybase.cn/file/img/avatar.png', '2019-01-24 00:00:00', '2019-01-24 00:00:00', '1', 0);



# 客户端信息
DROP TABLE IF EXISTS `z_oauth2_client`;
CREATE TABLE `z_oauth2_client`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端标识',
  `client_secret` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端密钥',
  `secret_required` tinyint NULL DEFAULT 1 COMMENT '默认需要密码',
  `grant_types` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权类型',
  `scoped` tinyint NULL DEFAULT 0 COMMENT '是否有范围',
  `scope` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '范围',
  `resource_ids` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源ID集',
  `redirect_uri` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重定向地址',
  `auto_approve` tinyint NULL DEFAULT 0 COMMENT '是否自动授权',
  `validity_seconds` int NULL DEFAULT -1 COMMENT 'token有效期',
  `refresh_validity_seconds` int NULL DEFAULT -1 COMMENT 'refresh_token有效期',
  `authorities` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '权限集',
  `additional_information` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '追加信息',
  `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户登录表' ROW_FORMAT = Dynamic;

INSERT INTO z_oauth2_client (client_id, client_secret, secret_required, grant_types, scoped, scope, resource_ids, redirect_uri, auto_approve, validity_seconds, refresh_validity_seconds, authorities, additional_information, create_at, update_at) VALUES ('client_test', '$2a$10$USsqXFfue.To1CYDoQ6hUeY2g9sTGA9iuTI9mjeG7IpQGFlDwpDxq', 1, 'authorization_code,implicit,password,wechat_miniapp,client_credentials,refresh_token', 1, 'user', null, 'http://localhost:8088', 1, 7200, 86400, null, '', '2019-01-24 15:49:34', '2019-01-24 15:49:34');
INSERT INTO z_oauth2_client (client_id, client_secret, secret_required, grant_types, scoped, scope, resource_ids, redirect_uri, auto_approve, validity_seconds, refresh_validity_seconds, authorities, additional_information, create_at, update_at) VALUES ('wechat_miniapp', '$2a$10$USsqXFfue.To1CYDoQ6hUeY2g9sTGA9iuTI9mjeG7IpQGFlDwpDxq', 1, 'password,wechat_miniapp', 1, 'user', null, '', 1, 7200, 86400, null, '', current_date, null);

# 用户社会表
DROP TABLE IF EXISTS `z_user_social`;
CREATE TABLE `z_user_social`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `provider_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方app标识',
  `provider_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方app用户标识',
  `sign_up` tinyint NULL DEFAULT 1 COMMENT '第三方授权，是否需要去注册，0不需要1需要',
  unique index `uni_user_social_provider`(`provider_id`, `provider_user_id`) USING BTREE ,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户社会表' ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `z_user_social_data`;
CREATE TABLE `z_user_social_data`  (
  `id` bigint NOT NULL primary key COMMENT 'ID，同user_social.id',
  `ext_data` json null
) charset = utf8;


SET FOREIGN_KEY_CHECKS = 1;