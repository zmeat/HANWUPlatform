DROP TABLE IF EXISTS `simulations`;

CREATE TABLE `simulations` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `timestep` varchar(225) DEFAULT NULL COMMENT '模拟间隔',
  `starttime` date DEFAULT NULL COMMENT '模拟起始时间',
  `endtime` date DEFAULT NULL COMMENT '模拟结束时间',
  `components` TEXT(2000) DEFAULT NULL COMMENT '模拟模块',
  `anchortime` varchar(255) DEFAULT NULL COMMENT '锚点时间',
  `mu` float(8, 2) DEFAULT NULL COMMENT '旱地占比',
  `learn` float(8, 2) DEFAULT NULL COMMENT '学习因子',
  `radius` float(8, 2) DEFAULT NULL COMMENT '信息半径',
  `sense` float(8, 2) DEFAULT NULL COMMENT '信息敏感度',
  `cv` float(8, 2) DEFAULT NULL COMMENT '变异系数',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
