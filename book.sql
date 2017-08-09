/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : novel

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2017-08-06 15:27:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '小说列表自增主键',
  `name` varchar(100) NOT NULL COMMENT '书名',
  `author` varchar(100) NOT NULL COMMENT '作者',
  `url` varchar(200) NOT NULL COMMENT '作者',
  `type` varchar(30) DEFAULT NULL COMMENT '小说类别',
  `new_chapter` varchar(200) DEFAULT NULL COMMENT '最新一章的章节名',
  `new_chapter_url` varchar(200) DEFAULT NULL COMMENT '最新一章的地址',
  `last_update_time` datetime DEFAULT NULL COMMENT '小说的最后更新时间',
  `status` int(5) DEFAULT NULL COMMENT '小说状态 1:连载 2:完结',
  `source` varchar(50) DEFAULT NULL COMMENT '小说来源',
  `add_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '添加到数据库的时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `yueshu` (`name`,`author`)
) ENGINE=InnoDB AUTO_INCREMENT=4227940 DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;
