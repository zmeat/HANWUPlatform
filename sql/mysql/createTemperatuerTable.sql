DROP TABLE IF EXISTS `temperatuer`;

CREATE TABLE `temperatuer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `county` varchar(225) DEFAULT NULL COMMENT '县级区域',
  `date` date DEFAULT NULL COMMENT '日期',
  `degree` float DEFAULT NULL COMMENT '温度值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;