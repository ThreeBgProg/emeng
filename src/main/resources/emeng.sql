/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : emeng

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-05-25 17:52:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for advertisement
-- ----------------------------
DROP TABLE IF EXISTS `advertisement`;
CREATE TABLE `advertisement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `slogan` varchar(64) NOT NULL DEFAULT '' COMMENT '广告语',
  `link` varchar(128) NOT NULL DEFAULT '' COMMENT '广告链接',
  `pic` varchar(255) NOT NULL DEFAULT '' COMMENT '广告图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of advertisement
-- ----------------------------

-- ----------------------------
-- Table structure for apply
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '报名用户的id（邀请码报名默认0）',
  `meeting_id` int(11) NOT NULL DEFAULT '0' COMMENT '会议id',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '报名者姓名',
  `company` varchar(32) NOT NULL DEFAULT '' COMMENT '报名者所在单位',
  `duty` varchar(32) NOT NULL DEFAULT '' COMMENT '报名者职务',
  `phone` varchar(32) NOT NULL DEFAULT '' COMMENT '报名者电话',
  `mail` varchar(32) NOT NULL COMMENT '报名者邮箱',
  `job_title` varchar(32) NOT NULL DEFAULT '' COMMENT '报名者职称',
  PRIMARY KEY (`id`),
  KEY `uid` (`user_id`),
  KEY `mid` (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of apply
-- ----------------------------

-- ----------------------------
-- Table structure for chapter
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lesson` int(11) NOT NULL DEFAULT '0' COMMENT '课程所属：0-其他，1-思修，2-毛概，3-马克思，4-近代史，5-7保留',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '章节名称',
  `number` int(11) NOT NULL DEFAULT '0' COMMENT '章节序号（第几章）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_charpterNo` (`lesson`,`number`),
  KEY `lesson` (`lesson`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter
-- ----------------------------

-- ----------------------------
-- Table structure for lesson
-- ----------------------------
DROP TABLE IF EXISTS `lesson`;
CREATE TABLE `lesson` (
  `id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `pic` varchar(255) NOT NULL DEFAULT '' COMMENT '课程图片',
  `introduction` varchar(255) DEFAULT '' COMMENT '课程介绍',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lesson
-- ----------------------------
INSERT INTO `lesson` VALUES ('1', '思修', '', '');
INSERT INTO `lesson` VALUES ('2', '毛概', '', '');
INSERT INTO `lesson` VALUES ('3', '马克思', '', '');
INSERT INTO `lesson` VALUES ('4', '近代史', '', '');

-- ----------------------------
-- Table structure for links
-- ----------------------------
DROP TABLE IF EXISTS `links`;
CREATE TABLE `links` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '友情链接文案',
  `link` varchar(128) NOT NULL DEFAULT '' COMMENT '友情链接',
  `order` int(11) NOT NULL DEFAULT '999' COMMENT '友情链接展示顺序，序号越小则越前',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of links
-- ----------------------------

-- ----------------------------
-- Table structure for location
-- ----------------------------
DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '地区名称',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '类型：0-保留（可能使用国家或地区），1-省，2-市，3-区',
  `parent` int(11) NOT NULL DEFAULT '0' COMMENT '父级地区id，无则0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of location
-- ----------------------------
INSERT INTO `location` VALUES ('1', '北京市', '1', '0');
INSERT INTO `location` VALUES ('2', '天津市', '1', '0');
INSERT INTO `location` VALUES ('3', '上海市', '1', '0');
INSERT INTO `location` VALUES ('4', '重庆市', '1', '0');
INSERT INTO `location` VALUES ('5', '河北省', '1', '0');
INSERT INTO `location` VALUES ('6', '山西省', '1', '0');
INSERT INTO `location` VALUES ('7', '辽宁省', '1', '0');
INSERT INTO `location` VALUES ('8', '吉林省', '1', '0');
INSERT INTO `location` VALUES ('9', '黑龙江省', '1', '0');
INSERT INTO `location` VALUES ('10', '江苏省', '1', '0');
INSERT INTO `location` VALUES ('11', '浙江省', '1', '0');
INSERT INTO `location` VALUES ('12', '安徽省', '1', '0');
INSERT INTO `location` VALUES ('13', '福建省', '1', '0');
INSERT INTO `location` VALUES ('14', '台湾省', '1', '0');
INSERT INTO `location` VALUES ('15', '江西省', '1', '0');
INSERT INTO `location` VALUES ('16', '山东省', '1', '0');
INSERT INTO `location` VALUES ('17', '河南省', '1', '0');
INSERT INTO `location` VALUES ('18', '湖北省', '1', '0');
INSERT INTO `location` VALUES ('19', '湖南省', '1', '0');
INSERT INTO `location` VALUES ('20', '广东省', '1', '0');
INSERT INTO `location` VALUES ('21', '海南省', '1', '0');
INSERT INTO `location` VALUES ('22', '四川省', '1', '0');
INSERT INTO `location` VALUES ('23', '贵州省', '1', '0');
INSERT INTO `location` VALUES ('24', '云南省', '1', '0');
INSERT INTO `location` VALUES ('25', '陕西省', '1', '0');
INSERT INTO `location` VALUES ('26', '甘肃省', '1', '0');
INSERT INTO `location` VALUES ('27', '青海省', '1', '0');
INSERT INTO `location` VALUES ('28', '内蒙古自治区', '1', '0');
INSERT INTO `location` VALUES ('29', '广西壮族自治区', '1', '0');
INSERT INTO `location` VALUES ('30', '西藏自治区', '1', '0');
INSERT INTO `location` VALUES ('31', '宁夏回族自治区', '1', '0');
INSERT INTO `location` VALUES ('32', '新疆维吾尔自治区', '1', '0');
INSERT INTO `location` VALUES ('33', '香港特别行政区', '1', '0');
INSERT INTO `location` VALUES ('34', '澳门特别行政区', '1', '0');

-- ----------------------------
-- Table structure for meeting
-- ----------------------------
DROP TABLE IF EXISTS `meeting`;
CREATE TABLE `meeting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL DEFAULT '' COMMENT '会议标题（名称）',
  `link` varchar(128) DEFAULT '' COMMENT '会议对应原官方发布链接',
  `content` text NOT NULL COMMENT '会议通知内容实体',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '会议类型：0-外部会议（不可报名），1-自举办会议（可报名）',
  `release_date` date NOT NULL COMMENT '发布日期',
  `code` varchar(16) NOT NULL DEFAULT '' COMMENT '报名邀请码',
  `annex` varchar(255) DEFAULT '' COMMENT '附件路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of meeting
-- ----------------------------

-- ----------------------------
-- Table structure for navigation
-- ----------------------------
DROP TABLE IF EXISTS `navigation`;
CREATE TABLE `navigation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position` tinyint(6) NOT NULL DEFAULT '0' COMMENT '坑位（代表首页的哪个位置）',
  `document` varchar(16) NOT NULL DEFAULT '' COMMENT '文案',
  `link` varchar(128) NOT NULL DEFAULT '' COMMENT '对应链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of navigation
-- ----------------------------

-- ----------------------------
-- Table structure for passage
-- ----------------------------
DROP TABLE IF EXISTS `passage`;
CREATE TABLE `passage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titile` varchar(64) NOT NULL DEFAULT '' COMMENT '标题',
  `author` varchar(16) NOT NULL DEFAULT '' COMMENT '作者或投稿者或名师',
  `link` varchar(128) DEFAULT '' COMMENT '文章来源链接（原文链接）',
  `publish_time` datetime NOT NULL COMMENT '发布时间',
  `content` text NOT NULL COMMENT '文章内容',
  `type` tinyint(5) NOT NULL DEFAULT '0' COMMENT '文章所属类别：\r\n0-其他（未知），\r\n1-最新资料，\r\n2-思政动态，\r\n3-马院头条，\r\n4-经典作家，\r\n5-领导讲话，\r\n6-厅部文件，\r\n7-通知公告，\r\n8-改革动态，\r\n9学科建设，\r\n10-评估排行，\r\n11-科研前沿，\r\n12-热点推荐，\r\n13-课程基础，\r\n14-课程概论，\r\n15-课程纲要，\r\n16-理论剖析，\r\n17-参考资料，\r\n18-案例资源，\r\n19-教案推荐，\r\n20-精品课件，\r\n21-阅读书目，\r\n22-精品在线',
  `state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '文章状态：0-待审核，1已通过，2-后台发布文章（默认通过），3：保留',
  `annex` varchar(255) DEFAULT '' COMMENT '附件路径',
  `recommend` int(11) DEFAULT '0' COMMENT '推荐（展示按序号从小到大排序）：0-不推荐，1-999-推荐等级',
  `lesson` int(11) DEFAULT '0' COMMENT '课程所属：0-其他（不属于课程资源），1-思修，2-毛概，3-马克思，4-近代史，5-7保留',
  `chapter` int(11) DEFAULT '0' COMMENT '所属章节id（非课程资源则为0）',
  PRIMARY KEY (`id`),
  KEY `idx_state_type` (`state`,`type`) USING BTREE,
  KEY `idx_lesson_chapter` (`lesson`,`chapter`) USING BTREE,
  KEY `chapter` (`chapter`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of passage
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mapping` varchar(64) NOT NULL COMMENT '请求url',
  `description` varchar(64) NOT NULL COMMENT 'url功能描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '/image/getRandNum', '获取验证码');
INSERT INTO `permission` VALUES ('2', '/test', '未设置解释');
INSERT INTO `permission` VALUES ('3', 'testException', '未设置解释');
INSERT INTO `permission` VALUES ('4', '/image/getRandNum', '获取验证码');
INSERT INTO `permission` VALUES ('5', '/test', '未设置解释');
INSERT INTO `permission` VALUES ('6', 'testException', '未设置解释');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titile` varchar(64) NOT NULL DEFAULT '' COMMENT '帖子标题',
  `content` text NOT NULL COMMENT '帖子正文',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '发帖者Id',
  `release_time` datetime NOT NULL COMMENT '发表时间',
  `like` int(11) DEFAULT '0' COMMENT '点赞次数',
  `reply` text COMMENT '官方回复',
  `visit` int(11) DEFAULT '0' COMMENT '点击量',
  `username` varchar(64) DEFAULT '' COMMENT '发帖者名称（冗余属性）',
  PRIMARY KEY (`id`),
  KEY `uid` (`user_id`),
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of post
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(64) NOT NULL COMMENT '用户角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL COMMENT '决死id',
  `permission_id` int(11) NOT NULL COMMENT '功能id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '高校名称',
  `link` varchar(128) NOT NULL DEFAULT '' COMMENT '高校对应链接',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '高校类型：0-未知，1-本科学院，2-独立学院，3-高职院校',
  `pic` varchar(255) NOT NULL DEFAULT '' COMMENT '高校展示预览图（小图）图片路径',
  `province_id` int(11) NOT NULL DEFAULT '0' COMMENT '高校所在省id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school
-- ----------------------------

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL DEFAULT '' COMMENT '名师名称',
  `direction` varchar(32) NOT NULL DEFAULT '' COMMENT '主讲方向',
  `subject` varchar(32) NOT NULL DEFAULT '' COMMENT '主讲科目',
  `introduction` text NOT NULL COMMENT '名师介绍',
  `pic` varchar(255) NOT NULL DEFAULT '' COMMENT '名师照片图片路径',
  `school_id` int(11) NOT NULL DEFAULT '0' COMMENT '名师对应学校id，未知或不存在注册该学校填0',
  PRIMARY KEY (`id`),
  KEY `school_id` (`school_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `job_id` varchar(64) NOT NULL DEFAULT '' COMMENT '工号（仅登录使用）',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
  `school_id` int(11) NOT NULL DEFAULT '0' COMMENT '所属学校id（对应school表id）',
  `phone` varchar(64) DEFAULT '' COMMENT '电话',
  `mail` varchar(64) DEFAULT '' COMMENT '邮箱地址',
  `state` tinyint(2) DEFAULT '0' COMMENT '用户状态：0-未审核，1-正常，2-3：保留',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_username` (`username`),
  UNIQUE KEY `unique_jobId` (`job_id`),
  KEY `fk` (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', 'admin', '0', '111', '', '0');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '视频名称',
  `pic` varchar(255) NOT NULL DEFAULT '' COMMENT '视频预览图',
  `link` varchar(128) NOT NULL DEFAULT '' COMMENT '视频链接',
  `lesson` int(11) DEFAULT '0' COMMENT '课程所属：0-其他，1-思修，2-毛概，3-马克思，4-近代史',
  `chapter` int(11) DEFAULT '0' COMMENT '所属章节id',
  PRIMARY KEY (`id`),
  KEY `chapter` (`chapter`),
  KEY `lesson` (`lesson`,`chapter`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video
-- ----------------------------
