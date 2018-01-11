DROP TABLE IF EXISTS `rain`;

CREATE TABLE `rain` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL COMMENT '日期',
  `station` varchar(225) DEFAULT NULL COMMENT '检测站点',
  `rainfall` float DEFAULT NULL COMMENT '雨量值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
