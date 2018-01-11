DROP TABLE IF EXISTS `river_flow`;

CREATE TABLE `river_flow` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL COMMENT '日期',
  `river_name` varchar(225) DEFAULT NULL COMMENT '河流名称',
  `flow` float DEFAULT NULL COMMENT '流量（m3/s）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;