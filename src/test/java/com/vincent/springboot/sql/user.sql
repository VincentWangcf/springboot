/*
Navicat MySQL Data Transfer

Source Server         : DockerMysqlDev01
Source Server Version : 50645
Source Host           : 192.168.99.100:3307
Source Database       : test01

Target Server Type    : MYSQL
Target Server Version : 50645
File Encoding         : 65001

Date: 2019-08-23 19:37:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `customerid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'lilei', '2', null);
INSERT INTO `user` VALUES ('2', 'Vincent', '66', null);

-- 20191207 for test commit    master
