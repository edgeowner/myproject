/*
Navicat MySQL Data Transfer

Source Server         : 10.200.157.139
Source Server Version : 50635
Source Host           : 10.200.157.139:3306
Source Database       : xiehua-uat

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-12-14 11:01:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for xiehua_weixin_public_setting
-- ----------------------------
DROP TABLE IF EXISTS `xiehua_weixin_public_setting`;
CREATE TABLE `xiehua_weixin_public_setting` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `creator_id` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人',
  `modifier_id` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `generate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（不能用于业务）',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间（不能用于业务）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `weixin_uid` varchar(50) NOT NULL DEFAULT '' COMMENT '微信公众号审核通过后生成的唯一标识,对外暴露',
  `set_type` smallint(4) NOT NULL DEFAULT '0' COMMENT '设置类型',
  `set_parameter` varchar(1000) NOT NULL DEFAULT '' COMMENT '设置参数',
  `status` smallint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `set_result` varchar(1000) NOT NULL DEFAULT '' COMMENT '设置结果',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='微信公众号设置信息表';

-- ----------------------------
-- Records of xiehua_weixin_public_setting
-- ----------------------------
INSERT INTO `xiehua_weixin_public_setting` VALUES ('19', '', '', '2018-08-16 15:57:08', '2018-08-16 15:57:08', '2018-08-16 15:57:09', '2018-08-16 15:57:09', 'c2cb46ce684a4cf6906612b2b3dc58e8', '1', '{\n  \"requestdomain\" : [ \"https://uat-gateway.zchz.com\" ],\n  \"downloaddomain\" : [ \"https://uat-gateway.zchz.com\" ],\n  \"wsrequestdomain\" : [ \"https://uat-gateway.zchz.com\" ],\n  \"action\" : \"set\",\n  \"uploaddomain\" : [ \"https://uat-gateway.zchz.com\" ]\n}', '1', '{\"errcode\":0,\"errmsg\":\"ok\",\"requestdomain\":[\"https:\\/\\/uat-gateway.zchz.com\"],\"wsrequestdomain\":[],\"uploaddomain\":[\"https:\\/\\/uat-gateway.zchz.com\"],\"downloaddomain\":[\"https:\\/\\/uat-gateway.zchz.com\"]}');
INSERT INTO `xiehua_weixin_public_setting` VALUES ('20', '', '', '2018-08-16 15:57:08', '2018-08-16 15:57:08', '2018-08-16 15:57:09', '2018-08-16 15:57:09', 'c2cb46ce684a4cf6906612b2b3dc58e8', '2', '{\n  \"action\" : \"set\",\n  \"webviewdomain\" : [ \"https://uat-c.zchz.com\" ]\n}', '1', '{\"errcode\":0,\"errmsg\":\"ok\",\"webviewdomain\":[\"https:\\/\\/uat-c.zchz.com\"]}');
INSERT INTO `xiehua_weixin_public_setting` VALUES ('21', '', '', '2018-08-16 15:58:04', '2018-08-16 15:58:04', '2018-08-16 15:58:05', '2018-08-16 15:58:05', 'c2cb46ce684a4cf6906612b2b3dc58e8', '3', '{\n  \"version\" : \"u.1.056\"\n}', '2', '{\"errcode\":89014,\"errmsg\":\"support version error hint: [1Si4302842364]\"}');
INSERT INTO `xiehua_weixin_public_setting` VALUES ('22', '', '', '2018-08-16 15:58:34', '2018-08-16 15:58:34', '2018-08-16 15:58:35', '2018-08-16 15:58:35', 'c2cb46ce684a4cf6906612b2b3dc58e8', '3', '{\n  \"version\" : \"u.1.056\"\n}', '2', '{\"errcode\":89014,\"errmsg\":\"support version error hint: [nF50Ga03143125]\"}');
INSERT INTO `xiehua_weixin_public_setting` VALUES ('23', '', '', '2018-08-16 16:03:12', '2018-08-16 16:03:12', '2018-08-16 16:03:13', '2018-08-16 16:03:13', 'c2cb46ce684a4cf6906612b2b3dc58e8', '3', '{\n  \"version\" : \"1.6.4\"\n}', '1', '{\"errcode\":0,\"errmsg\":\"ok\"}');
INSERT INTO `xiehua_weixin_public_setting` VALUES ('24', '', '', '2018-09-26 15:36:49', '2018-09-26 15:36:49', '2018-09-26 15:36:50', '2018-09-26 15:36:50', '4575935e1fc1400aa8bcaf3bb277435c', '1', '{\n  \"requestdomain\" : [ \"https://uat-gateway.zchz.com\" ],\n  \"downloaddomain\" : [ \"https://uat-gateway.zchz.com\" ],\n  \"wsrequestdomain\" : [ \"https://uat-gateway.zchz.com\" ],\n  \"action\" : \"set\",\n  \"uploaddomain\" : [ \"https://uat-gateway.zchz.com\" ]\n}', '1', '{\"errcode\":0,\"errmsg\":\"ok\",\"requestdomain\":[\"https:\\/\\/uat-gateway.zchz.com\"],\"wsrequestdomain\":[],\"uploaddomain\":[\"https:\\/\\/uat-gateway.zchz.com\"],\"downloaddomain\":[\"https:\\/\\/uat-gateway.zchz.com\"]}');
INSERT INTO `xiehua_weixin_public_setting` VALUES ('25', '', '', '2018-09-26 15:36:49', '2018-09-26 15:36:49', '2018-09-26 15:36:50', '2018-09-26 15:36:50', '4575935e1fc1400aa8bcaf3bb277435c', '2', '{\n  \"action\" : \"set\",\n  \"webviewdomain\" : [ \"https://uat-c.zchz.com\" ]\n}', '1', '{\"errcode\":0,\"errmsg\":\"ok\",\"webviewdomain\":[\"https:\\/\\/uat-c.zchz.com\"]}');
INSERT INTO `xiehua_weixin_public_setting` VALUES ('26', '', '', '2018-09-26 15:40:21', '2018-09-26 15:40:21', '2018-09-26 15:40:22', '2018-09-26 15:40:22', '4575935e1fc1400aa8bcaf3bb277435c', '3', '{\n  \"version\" : \"1.6.4\"\n}', '1', '{\"errcode\":0,\"errmsg\":\"ok\"}');
INSERT INTO `xiehua_weixin_public_setting` VALUES ('27', '', '', '2018-11-23 17:01:52', '2018-11-23 17:01:52', '2018-11-23 17:01:53', '2018-11-23 17:01:53', 'c2cb46ce684a4cf6906612b2b3dc58e8', '1', '{\n  \"requestdomain\" : [ \"https://uat-gateway.zchz.com\", \"https://wxapi.growingio.com\" ],\n  \"downloaddomain\" : [ \"https://uat-gateway.zchz.com\" ],\n  \"wsrequestdomain\" : [ \"https://uat-gateway.zchz.com\" ],\n  \"action\" : \"set\",\n  \"uploaddomain\" : [ \"https://uat-gateway.zchz.com\" ]\n}', '1', '{\"errcode\":0,\"errmsg\":\"ok\",\"requestdomain\":[\"https:\\/\\/uat-gateway.zchz.com\",\"https:\\/\\/wxapi.growingio.com\"],\"wsrequestdomain\":[],\"uploaddomain\":[\"https:\\/\\/uat-gateway.zchz.com\"],\"downloaddomain\":[\"https:\\/\\/uat-gateway.zchz.com\"]}');
INSERT INTO `xiehua_weixin_public_setting` VALUES ('28', '', '', '2018-11-23 17:01:53', '2018-11-23 17:01:53', '2018-11-23 17:01:53', '2018-11-23 17:01:53', '4575935e1fc1400aa8bcaf3bb277435c', '1', '{\n  \"requestdomain\" : [ \"https://uat-gateway.zchz.com\", \"https://wxapi.growingio.com\" ],\n  \"downloaddomain\" : [ \"https://uat-gateway.zchz.com\" ],\n  \"wsrequestdomain\" : [ \"https://uat-gateway.zchz.com\" ],\n  \"action\" : \"set\",\n  \"uploaddomain\" : [ \"https://uat-gateway.zchz.com\" ]\n}', '1', '{\"errcode\":0,\"errmsg\":\"ok\",\"requestdomain\":[\"https:\\/\\/uat-gateway.zchz.com\",\"https:\\/\\/wxapi.growingio.com\"],\"wsrequestdomain\":[],\"uploaddomain\":[\"https:\\/\\/uat-gateway.zchz.com\"],\"downloaddomain\":[\"https:\\/\\/uat-gateway.zchz.com\"]}');
INSERT INTO `xiehua_weixin_public_setting` VALUES ('29', '', '', '2018-11-23 17:02:15', '2018-11-23 17:02:15', '2018-11-23 17:02:15', '2018-11-23 17:02:15', 'c2cb46ce684a4cf6906612b2b3dc58e8', '2', '{\n  \"action\" : \"set\",\n  \"webviewdomain\" : [ \"https://uat-c.zchz.com\", \"https://p-erro.zchz.com\" ]\n}', '1', '{\"errcode\":0,\"errmsg\":\"ok\",\"webviewdomain\":[\"https:\\/\\/uat-c.zchz.com\",\"https:\\/\\/p-erro.zchz.com\"]}');
INSERT INTO `xiehua_weixin_public_setting` VALUES ('30', '', '', '2018-11-23 17:02:15', '2018-11-23 17:02:15', '2018-11-23 17:02:15', '2018-11-23 17:02:15', '4575935e1fc1400aa8bcaf3bb277435c', '2', '{\n  \"action\" : \"set\",\n  \"webviewdomain\" : [ \"https://uat-c.zchz.com\", \"https://p-erro.zchz.com\" ]\n}', '1', '{\"errcode\":0,\"errmsg\":\"ok\",\"webviewdomain\":[\"https:\\/\\/uat-c.zchz.com\",\"https:\\/\\/p-erro.zchz.com\"]}');
