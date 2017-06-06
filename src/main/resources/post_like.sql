/*
Navicat MySQL Data Transfer

Source Server         : zhiwei
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : emeng

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-06-06 20:56:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for post_like
-- ----------------------------
DROP TABLE IF EXISTS `post_like`;
CREATE TABLE `post_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `post_id` int(11) NOT NULL COMMENT '论坛PK',
  `user_id` int(11) NOT NULL COMMENT '点赞人PK',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of post_like
-- ----------------------------
