CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '商品' COMMENT '商品名',
  `prince` double NOT NULL DEFAULT 0 COMMENT '价格',
  `count` int(11) NOT NULL DEFAULT 0 COMMENT '数量',
  `remark` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品信息';

CREATE TABLE `users` (
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'guest' COMMENT '用户名, 英文和数字, 不要有特殊字符',
  `user_type` enum('GUEST','NORMAL','ADMIN') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'GUEST' COMMENT '用户类型',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码, AES加密',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息';

INSERT INTO users (name,user_type,password) VALUES ('admin','ADMIN','d3igjPrf4/F0WY1RV5RnfA==');
INSERT INTO users (name,user_type,password) VALUES ('guest','GUEST','pRm5K9Y0xlYrWE8i6jBZmA==');
INSERT INTO users (name,user_type,password) VALUES ('normal','NORMAL','mlGyF+u2cRY+WCEyb2sK+Q==');
