/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : emeng

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-06-16 00:22:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mapping` varchar(64) NOT NULL COMMENT '请求url',
  `description` varchar(255) NOT NULL COMMENT 'url功能描述',
  `function` varchar(255) DEFAULT NULL COMMENT '功能描述',
  `state` tinyint(4) DEFAULT '1' COMMENT '权限状态:0-失效，1-正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3008 DEFAULT CHARSET=utf8;
