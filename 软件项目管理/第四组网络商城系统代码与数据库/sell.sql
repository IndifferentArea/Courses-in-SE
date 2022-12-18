/*
Navicat MySQL Data Transfer

Source Server         : ro1
Source Server Version : 80028
Source Host           : localhost:3306
Source Database       : sell

Target Server Type    : MYSQL
Target Server Version : 80028
File Encoding         : 65001

Date: 2022-11-20 20:43:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` varchar(32) NOT NULL,
  `pwd` varchar(45) NOT NULL,
  `province` varchar(45) NOT NULL,
  `type` int NOT NULL COMMENT '0超级管理员1省级签收2站点签收3站点管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', '123456', '上海', '1');
INSERT INTO `admin` VALUES ('2', '123456', '上海', '2');
INSERT INTO `admin` VALUES ('3', '123456', '上海', '3');
INSERT INTO `admin` VALUES ('4', '123456', '北京', '1');
INSERT INTO `admin` VALUES ('5', '123456', '北京', '2');
INSERT INTO `admin` VALUES ('6', '123456', '北京', '3');
INSERT INTO `admin` VALUES ('892805828', '123456', '总部', '0');

-- ----------------------------
-- Table structure for defect_registy
-- ----------------------------
DROP TABLE IF EXISTS `defect_registy`;
CREATE TABLE `defect_registy` (
  `order_id` int NOT NULL,
  `product` int NOT NULL,
  `count` int NOT NULL,
  PRIMARY KEY (`order_id`,`product`),
  KEY `fk_defect_registy_order1_idx` (`order_id`),
  KEY `fk_defect_registy_product1_idx` (`product`),
  CONSTRAINT `fk_defect_registy_order1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`),
  CONSTRAINT `fk_defect_registy_product1` FOREIGN KEY (`product`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of defect_registy
-- ----------------------------
INSERT INTO `defect_registy` VALUES ('26', '3', '1');
INSERT INTO `defect_registy` VALUES ('28', '2', '1');
INSERT INTO `defect_registy` VALUES ('36', '9', '1');

-- ----------------------------
-- Table structure for drivers
-- ----------------------------
DROP TABLE IF EXISTS `drivers`;
CREATE TABLE `drivers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `car_id` int NOT NULL DEFAULT '0',
  `province` varchar(45) NOT NULL,
  `priority` int DEFAULT NULL,
  `busy` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'true已经发车false未发车，可加货',
  PRIMARY KEY (`id`),
  KEY `fk_drivers_transprt1_idx` (`province`),
  CONSTRAINT `fk_drivers_transprt1` FOREIGN KEY (`province`) REFERENCES `transprt` (`province`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of drivers
-- ----------------------------
INSERT INTO `drivers` VALUES ('1', '123456', '上海', '5', '0');
INSERT INTO `drivers` VALUES ('2', '666666', '上海', '4', '0');
INSERT INTO `drivers` VALUES ('3', '888888', '上海', '3', '0');
INSERT INTO `drivers` VALUES ('4', '321123', '上海', '2', '0');

-- ----------------------------
-- Table structure for lack_registy
-- ----------------------------
DROP TABLE IF EXISTS `lack_registy`;
CREATE TABLE `lack_registy` (
  `product` int NOT NULL,
  `count` int NOT NULL,
  `user_id` varchar(45) NOT NULL,
  PRIMARY KEY (`product`,`user_id`),
  CONSTRAINT `fk_lackRegisty_product1` FOREIGN KEY (`product`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of lack_registy
-- ----------------------------
INSERT INTO `lack_registy` VALUES ('1', '3', '123456');
INSERT INTO `lack_registy` VALUES ('9', '3', '123457');
INSERT INTO `lack_registy` VALUES ('10', '3', '123457');
INSERT INTO `lack_registy` VALUES ('19', '3', '123457');
INSERT INTO `lack_registy` VALUES ('23', '3', '123456');
INSERT INTO `lack_registy` VALUES ('23', '3', '123457');
INSERT INTO `lack_registy` VALUES ('27', '3', '123457');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `dest_province` varchar(45) NOT NULL,
  `user` varchar(45) DEFAULT NULL,
  `car_id` int DEFAULT NULL,
  `end_status` int NOT NULL DEFAULT '0' COMMENT '0:正常完成1：有缺损，并完成正常部分的支付2：退货',
  `should_pay` decimal(11,2) NOT NULL DEFAULT '0.00',
  `actually_pay` decimal(11,2) NOT NULL DEFAULT '0.00',
  `complete_satus` int NOT NULL DEFAULT '0' COMMENT '0：创建订单；1：估价；2：完成预付；3：省级配送到货确认；4：到达签收站点；5：检查缺损或者退货并完成付款。本列的值代表列值的数字对应的事件已完成，而数字加一对应的事件还未完成。',
  `create_time` timestamp NULL DEFAULT NULL,
  `province_sign_time` timestamp NULL DEFAULT NULL,
  `spot_sign_time` timestamp NULL DEFAULT NULL,
  `finish_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_order_transprt1_idx` (`dest_province`),
  KEY `fk_order_user_idx` (`user`),
  CONSTRAINT `fk_order_transprt1` FOREIGN KEY (`dest_province`) REFERENCES `transprt` (`province`),
  CONSTRAINT `fk_order_user` FOREIGN KEY (`user`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('1', '上海', '123456', '666666', '2', '20.00', '20.00', '5', null, '2022-07-20 11:04:01', '2022-07-20 11:04:05', '2022-07-20 11:04:23');
INSERT INTO `order` VALUES ('3', '上海', '123456', '888888', '1', '164.00', '164.00', '5', '2022-07-18 13:43:38', '2022-07-20 11:46:27', '2022-07-20 17:08:35', '2022-07-20 17:08:40');
INSERT INTO `order` VALUES ('4', '上海', '123456', '666666', '2', '20.00', '20.00', '5', '2022-07-18 15:20:51', '2022-07-20 11:46:29', '2022-07-20 17:12:37', '2022-11-04 22:25:56');
INSERT INTO `order` VALUES ('5', '上海', '123456', '888888', '2', '20.00', '9.00', '3', '2022-07-18 15:49:09', '2022-11-08 18:59:53', '2022-07-20 09:11:58', '2022-07-20 09:51:39');
INSERT INTO `order` VALUES ('23', '上海', '123456', '123456', '2', '20.00', '20.00', '5', '2022-07-22 09:28:31', '2022-07-22 09:29:36', '2022-07-22 09:29:44', '2022-07-22 09:29:49');
INSERT INTO `order` VALUES ('24', '上海', '123456', '123456', '2', '20.00', '20.00', '5', '2022-07-22 09:42:12', '2022-07-22 09:42:36', '2022-07-22 09:42:38', '2022-07-22 09:42:43');
INSERT INTO `order` VALUES ('25', '上海', '123456', null, '2', '0.00', '0.00', '5', '2022-07-22 09:43:57', null, null, '2022-07-22 09:44:21');
INSERT INTO `order` VALUES ('26', '上海', '123456', '123456', '1', '16.00', '16.00', '5', '2022-07-22 09:44:45', '2022-07-22 09:45:58', '2022-07-22 09:46:00', '2022-07-22 09:46:03');
INSERT INTO `order` VALUES ('27', '上海', '123456', '123456', '0', '104.00', '104.00', '5', '2022-07-22 09:44:49', '2022-07-22 09:46:04', '2022-07-22 09:46:07', '2022-07-22 09:46:10');
INSERT INTO `order` VALUES ('28', '上海', '123456', '123456', '1', '140.80', '140.80', '5', '2022-07-22 10:17:14', '2022-07-22 10:18:48', '2022-07-22 10:18:57', '2022-07-22 10:19:09');
INSERT INTO `order` VALUES ('29', '上海', '123456', null, '2', '0.00', '0.00', '5', '2022-07-22 10:19:56', null, null, '2022-07-22 10:20:20');
INSERT INTO `order` VALUES ('30', '上海', '123456', '123456', '2', '20.00', '20.00', '5', '2022-07-22 10:20:41', '2022-07-22 10:21:10', '2022-07-22 10:21:11', '2022-07-22 10:21:19');
INSERT INTO `order` VALUES ('31', '上海', '123456', '123456', '0', '6.00', '4.00', '2', '2022-09-27 12:03:39', '2022-11-06 22:06:03', null, null);
INSERT INTO `order` VALUES ('32', '上海', '123456', '123456', '0', '236.24', '10.00', '4', '2022-09-27 12:13:31', '2022-11-07 10:54:08', '2022-11-07 10:55:29', null);
INSERT INTO `order` VALUES ('33', '上海', '123456', '123456', '2', '6.00', '0.00', '3', '2022-10-15 21:52:29', '2022-11-07 10:55:07', null, null);
INSERT INTO `order` VALUES ('34', '上海', '123456', '123456', '2', '21.00', '6.00', '3', '2022-10-16 16:21:17', '2022-11-07 10:44:24', null, null);
INSERT INTO `order` VALUES ('35', '上海', '123456', '123456', '0', '21.00', '10.00', '3', '2022-10-16 16:21:28', '2022-11-07 10:41:18', null, null);
INSERT INTO `order` VALUES ('36', '上海', '123456', '123456', '1', '38.90', '38.90', '5', '2022-10-16 21:50:18', '2022-11-05 14:09:43', '2022-11-05 14:09:47', '2022-11-05 14:11:03');
INSERT INTO `order` VALUES ('37', '上海', '123456', '123456', '0', '343.00', '343.00', '5', '2022-10-16 21:51:33', '2022-11-05 14:14:46', '2022-11-05 14:14:48', '2022-11-05 14:15:17');
INSERT INTO `order` VALUES ('38', '上海', '123456', '123456', '0', '301.12', '4.00', '3', '2022-11-01 20:25:47', '2022-11-07 10:35:46', null, null);
INSERT INTO `order` VALUES ('39', '上海', '123456', '123456', '0', '188.00', '21.00', '3', '2022-11-01 20:33:28', '2022-11-07 10:32:01', null, null);
INSERT INTO `order` VALUES ('40', '上海', '123456', '123456', '0', '121.00', '5.00', '3', '2022-11-01 20:34:30', '2022-11-07 10:34:47', null, null);
INSERT INTO `order` VALUES ('41', '上海', '123456', '123456', '0', '40.00', '4.00', '3', '2022-11-04 16:24:49', '2022-11-07 10:31:57', null, null);
INSERT INTO `order` VALUES ('42', '上海', '123456', '123456', '0', '88.00', '6.00', '4', '2022-11-04 16:27:03', '2022-11-07 10:31:14', '2022-11-07 10:55:35', null);
INSERT INTO `order` VALUES ('43', '上海', '123456', '123456', '0', '22.12', '3.00', '2', '2022-11-07 13:04:44', null, null, null);
INSERT INTO `order` VALUES ('44', '北京', '123457', null, '2', '77.00', '9.00', '2', '2022-11-07 13:06:43', null, null, null);
INSERT INTO `order` VALUES ('45', '北京', '123457', null, '2', '0.00', '0.00', '5', '2022-11-07 13:08:06', null, null, '2022-11-07 13:16:38');
INSERT INTO `order` VALUES ('46', '北京', '123457', null, '0', '21.00', '0.00', '0', '2022-11-07 13:23:19', null, null, null);
INSERT INTO `order` VALUES ('47', '北京', '123457', null, '0', '42.00', '0.00', '0', '2022-11-07 13:24:55', null, null, null);
INSERT INTO `order` VALUES ('48', '北京', '123457', null, '0', '42.00', '0.00', '0', '2022-11-07 13:25:42', null, null, null);
INSERT INTO `order` VALUES ('49', '北京', '123457', null, '0', '15.00', '0.00', '0', '2022-11-07 13:26:21', null, null, null);
INSERT INTO `order` VALUES ('50', '北京', '123457', null, '0', '80.00', '0.00', '0', '2022-11-07 13:58:37', null, null, null);
INSERT INTO `order` VALUES ('51', '北京', '123457', null, '0', '47.00', '0.00', '0', '2022-11-07 13:59:11', null, null, null);
INSERT INTO `order` VALUES ('52', '北京', '123457', null, '0', '182.00', '0.00', '0', '2022-11-07 13:59:39', null, null, null);
INSERT INTO `order` VALUES ('53', '北京', '123457', null, '0', '187.00', '0.00', '0', '2022-11-07 14:00:29', null, null, null);
INSERT INTO `order` VALUES ('54', '北京', '123457', null, '0', '88.00', '0.00', '0', '2022-11-07 14:01:12', null, null, null);
INSERT INTO `order` VALUES ('55', '北京', '123457', null, '0', '335.00', '0.00', '0', '2022-11-07 14:02:09', null, null, null);
INSERT INTO `order` VALUES ('56', '北京', '123457', null, '0', '160.12', '0.00', '0', '2022-11-07 14:20:41', null, null, null);
INSERT INTO `order` VALUES ('57', '上海', '123456', null, '0', '135.80', '0.00', '0', '2022-11-07 15:37:15', null, null, null);
INSERT INTO `order` VALUES ('58', '上海', '123456', null, '0', '15.00', '0.00', '0', '2022-11-20 20:01:06', null, null, null);
INSERT INTO `order` VALUES ('59', '上海', '123456', null, '0', '0.00', '0.00', '0', '2022-11-20 20:25:58', null, null, null);
INSERT INTO `order` VALUES ('60', '上海', '123456', null, '0', '48.00', '0.00', '1', '2022-11-20 20:30:34', null, null, null);

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `product` int NOT NULL,
  `count` int NOT NULL,
  `price` decimal(11,2) NOT NULL,
  `order_id` int NOT NULL,
  PRIMARY KEY (`item_id`),
  KEY `fk_order_item_order_idx` (`order_id`),
  KEY `fk_order_item_product1_idx` (`product`),
  CONSTRAINT `fk_order_item_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`),
  CONSTRAINT `fk_order_item_product1` FOREIGN KEY (`product`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES ('2', '1', '1', '13.00', '1');
INSERT INTO `order_item` VALUES ('3', '2', '1', '15.00', '1');
INSERT INTO `order_item` VALUES ('6', '6', '2', '46.00', '3');
INSERT INTO `order_item` VALUES ('7', '7', '2', '89.80', '3');
INSERT INTO `order_item` VALUES ('8', '6', '2', '46.00', '4');
INSERT INTO `order_item` VALUES ('9', '7', '2', '89.80', '4');
INSERT INTO `order_item` VALUES ('10', '6', '2', '46.00', '5');
INSERT INTO `order_item` VALUES ('11', '7', '2', '89.80', '5');
INSERT INTO `order_item` VALUES ('47', '2', '2', '30.00', '23');
INSERT INTO `order_item` VALUES ('48', '4', '2', '26.00', '23');
INSERT INTO `order_item` VALUES ('49', '3', '1', '22.12', '24');
INSERT INTO `order_item` VALUES ('50', '2', '1', '15.00', '25');
INSERT INTO `order_item` VALUES ('51', '3', '1', '22.12', '26');
INSERT INTO `order_item` VALUES ('52', '3', '1', '22.12', '27');
INSERT INTO `order_item` VALUES ('53', '5', '1', '88.00', '27');
INSERT INTO `order_item` VALUES ('54', '2', '3', '45.00', '28');
INSERT INTO `order_item` VALUES ('55', '9', '1', '94.80', '28');
INSERT INTO `order_item` VALUES ('56', '2', '1', '15.00', '29');
INSERT INTO `order_item` VALUES ('57', '3', '2', '44.24', '30');
INSERT INTO `order_item` VALUES ('58', '3', '2', '44.24', '32');
INSERT INTO `order_item` VALUES ('59', '5', '2', '176.00', '32');
INSERT INTO `order_item` VALUES ('60', '2', '0', '0.00', '33');
INSERT INTO `order_item` VALUES ('61', '2', '1', '15.00', '34');
INSERT INTO `order_item` VALUES ('62', '2', '1', '15.00', '34');
INSERT INTO `order_item` VALUES ('63', '2', '1', '15.00', '34');
INSERT INTO `order_item` VALUES ('64', '2', '1', '15.00', '35');
INSERT INTO `order_item` VALUES ('65', '2', '1', '15.00', '35');
INSERT INTO `order_item` VALUES ('66', '2', '1', '15.00', '35');
INSERT INTO `order_item` VALUES ('67', '2', '1', '15.00', '35');
INSERT INTO `order_item` VALUES ('68', '9', '1', '94.80', '36');
INSERT INTO `order_item` VALUES ('69', '10', '1', '22.90', '36');
INSERT INTO `order_item` VALUES ('70', '9', '2', '189.60', '37');
INSERT INTO `order_item` VALUES ('71', '10', '6', '137.40', '37');
INSERT INTO `order_item` VALUES ('72', '2', '1', '15.00', '38');
INSERT INTO `order_item` VALUES ('73', '3', '1', '22.12', '38');
INSERT INTO `order_item` VALUES ('74', '4', '1', '34.00', '38');
INSERT INTO `order_item` VALUES ('75', '5', '1', '42.00', '38');
INSERT INTO `order_item` VALUES ('76', '6', '1', '47.00', '38');
INSERT INTO `order_item` VALUES ('77', '7', '1', '63.00', '38');
INSERT INTO `order_item` VALUES ('78', '8', '1', '72.00', '38');
INSERT INTO `order_item` VALUES ('79', '6', '1', '47.00', '39');
INSERT INTO `order_item` VALUES ('80', '7', '1', '63.00', '39');
INSERT INTO `order_item` VALUES ('81', '8', '1', '72.00', '39');
INSERT INTO `order_item` VALUES ('82', '21', '1', '29.00', '40');
INSERT INTO `order_item` VALUES ('83', '22', '1', '29.00', '40');
INSERT INTO `order_item` VALUES ('84', '24', '1', '57.00', '40');
INSERT INTO `order_item` VALUES ('85', '4', '1', '34.00', '41');
INSERT INTO `order_item` VALUES ('86', '8', '1', '72.00', '42');
INSERT INTO `order_item` VALUES ('87', '3', '1', '22.12', '43');
INSERT INTO `order_item` VALUES ('88', '8', '1', '72.00', '44');
INSERT INTO `order_item` VALUES ('89', '8', '1', '72.00', '45');
INSERT INTO `order_item` VALUES ('90', '8', '1', '72.00', '45');
INSERT INTO `order_item` VALUES ('91', '13', '1', '21.00', '46');
INSERT INTO `order_item` VALUES ('92', '5', '1', '42.00', '47');
INSERT INTO `order_item` VALUES ('93', '5', '1', '42.00', '48');
INSERT INTO `order_item` VALUES ('94', '2', '1', '15.00', '49');
INSERT INTO `order_item` VALUES ('95', '11', '1', '80.00', '50');
INSERT INTO `order_item` VALUES ('96', '6', '1', '47.00', '51');
INSERT INTO `order_item` VALUES ('97', '7', '1', '63.00', '52');
INSERT INTO `order_item` VALUES ('98', '6', '1', '47.00', '52');
INSERT INTO `order_item` VALUES ('99', '8', '1', '72.00', '52');
INSERT INTO `order_item` VALUES ('100', '15', '1', '99.00', '53');
INSERT INTO `order_item` VALUES ('101', '20', '1', '88.00', '53');
INSERT INTO `order_item` VALUES ('102', '20', '1', '88.00', '54');
INSERT INTO `order_item` VALUES ('103', '24', '1', '57.00', '55');
INSERT INTO `order_item` VALUES ('104', '25', '1', '77.00', '55');
INSERT INTO `order_item` VALUES ('105', '26', '1', '89.00', '55');
INSERT INTO `order_item` VALUES ('106', '28', '1', '112.00', '55');
INSERT INTO `order_item` VALUES ('107', '2', '1', '15.00', '56');
INSERT INTO `order_item` VALUES ('108', '3', '1', '22.12', '56');
INSERT INTO `order_item` VALUES ('109', '4', '1', '34.00', '56');
INSERT INTO `order_item` VALUES ('110', '5', '1', '42.00', '56');
INSERT INTO `order_item` VALUES ('111', '6', '1', '47.00', '56');
INSERT INTO `order_item` VALUES ('112', '6', '2', '46.00', '57');
INSERT INTO `order_item` VALUES ('113', '7', '2', '89.80', '57');
INSERT INTO `order_item` VALUES ('114', '2', '1', '15.00', '58');
INSERT INTO `order_item` VALUES ('115', '13', '2', '42.00', '60');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` decimal(11,2) NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  `category` int NOT NULL COMMENT '0书籍1电子产品2课程',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', 'JAVA入门到放弃', '13.00', '0', '0');
INSERT INTO `product` VALUES ('2', 'Dubbo入门到放弃', '15.00', '11', '0');
INSERT INTO `product` VALUES ('3', 'Netty入门到放弃', '22.12', '7', '0');
INSERT INTO `product` VALUES ('4', 'Nginx入门到放弃', '34.00', '48', '0');
INSERT INTO `product` VALUES ('5', 'Zookeeper入门到放弃', '42.00', '48', '0');
INSERT INTO `product` VALUES ('6', 'SpringCloud入门到放弃', '47.00', '27', '0');
INSERT INTO `product` VALUES ('7', 'Git入门到放弃', '63.00', '21', '0');
INSERT INTO `product` VALUES ('8', 'RabbitMQ入门到放弃', '72.00', '61', '0');
INSERT INTO `product` VALUES ('9', 'Vue入门到放弃', '94.80', '0', '0');
INSERT INTO `product` VALUES ('10', 'RocketMQ从入门到放弃', '101.00', '0', '0');
INSERT INTO `product` VALUES ('11', '英菲克鼠标', '80.00', '12', '1');
INSERT INTO `product` VALUES ('12', '罗技电竞鼠标', '118.00', '56', '1');
INSERT INTO `product` VALUES ('13', '联想鼠标', '21.00', '8', '1');
INSERT INTO `product` VALUES ('14', '雷神游戏键盘', '56.00', '55', '1');
INSERT INTO `product` VALUES ('15', '小米机械键盘', '99.00', '76', '1');
INSERT INTO `product` VALUES ('16', '飞利浦键盘', '111.00', '0', '1');
INSERT INTO `product` VALUES ('17', '小米耳机', '57.00', '3', '1');
INSERT INTO `product` VALUES ('18', '苹果充电器', '88.00', '4', '1');
INSERT INTO `product` VALUES ('19', '绿联充电器', '26.00', '0', '1');
INSERT INTO `product` VALUES ('20', '安克充电器', '88.00', '6', '1');
INSERT INTO `product` VALUES ('21', 'C++语言程序设计基础', '29.00', '5', '2');
INSERT INTO `product` VALUES ('22', '数据结构基础', '29.00', '3', '2');
INSERT INTO `product` VALUES ('23', '数据结构高级', '45.00', '0', '2');
INSERT INTO `product` VALUES ('24', '操作系统', '57.00', '42', '2');
INSERT INTO `product` VALUES ('25', 'JAVA程序设计', '77.00', '5', '2');
INSERT INTO `product` VALUES ('26', '网络技术与应用', '89.00', '43', '2');
INSERT INTO `product` VALUES ('27', 'C++语言程序设计进阶', '100.00', '0', '2');
INSERT INTO `product` VALUES ('28', 'C程序设计案例基础', '112.00', '10', '2');
INSERT INTO `product` VALUES ('29', 'C程序设计案例进阶', '23.00', '3', '2');
INSERT INTO `product` VALUES ('30', '软件工程', '45.00', '44', '2');

-- ----------------------------
-- Table structure for recommend
-- ----------------------------
DROP TABLE IF EXISTS `recommend`;
CREATE TABLE `recommend` (
  `user_id` varchar(45) NOT NULL,
  `pointer` int NOT NULL DEFAULT '0',
  `products` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_recommend_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of recommend
-- ----------------------------
INSERT INTO `recommend` VALUES ('123456', '2', '2,13,4,8,3,6,7');
INSERT INTO `recommend` VALUES ('123457', '3', '4,5,6,26,28,2,3');

-- ----------------------------
-- Table structure for return_registy
-- ----------------------------
DROP TABLE IF EXISTS `return_registy`;
CREATE TABLE `return_registy` (
  `order_id` int NOT NULL,
  `finish_or_not` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'true为未发货直接退款false为收获后退款，后续可扩展。',
  PRIMARY KEY (`order_id`),
  CONSTRAINT `fk_returnRegisty_order1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of return_registy
-- ----------------------------
INSERT INTO `return_registy` VALUES ('4', '0');
INSERT INTO `return_registy` VALUES ('23', '0');
INSERT INTO `return_registy` VALUES ('24', '0');
INSERT INTO `return_registy` VALUES ('25', '1');
INSERT INTO `return_registy` VALUES ('29', '1');
INSERT INTO `return_registy` VALUES ('30', '0');
INSERT INTO `return_registy` VALUES ('45', '1');

-- ----------------------------
-- Table structure for transprt
-- ----------------------------
DROP TABLE IF EXISTS `transprt`;
CREATE TABLE `transprt` (
  `province` varchar(45) NOT NULL,
  `cost` decimal(11,2) NOT NULL DEFAULT '5.00',
  PRIMARY KEY (`province`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of transprt
-- ----------------------------
INSERT INTO `transprt` VALUES ('上海', '6.00');
INSERT INTO `transprt` VALUES ('北京', '5.00');
INSERT INTO `transprt` VALUES ('广州', '7.00');
INSERT INTO `transprt` VALUES ('深圳', '8.00');
INSERT INTO `transprt` VALUES ('西安', '4.00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(45) NOT NULL,
  `user_pwd` varchar(45) NOT NULL,
  `user_balance` decimal(11,2) NOT NULL,
  `province` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `fk_user_transprt1_idx` (`province`),
  CONSTRAINT `fk_user_transprt1` FOREIGN KEY (`province`) REFERENCES `transprt` (`province`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('123456', '654321', '2696.00', '上海');
INSERT INTO `user` VALUES ('123457', '654321', '5690.00', '北京');
