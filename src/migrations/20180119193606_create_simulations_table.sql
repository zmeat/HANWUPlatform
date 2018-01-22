DROP TABLE IF EXISTS `simulations`;

CREATE TABLE `simulations` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `simT` varchar(225) DEFAULT NULL COMMENT '模拟周期',
  `timestep` varchar(225) DEFAULT NULL COMMENT '模拟间隔',
  `starttime` date DEFAULT NULL COMMENT '模拟起始时间',
  `endtime` date DEFAULT NULL COMMENT '模拟结束时间',
  `component` varchar(225) DEFAULT NULL COMMENT '模拟模块',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
