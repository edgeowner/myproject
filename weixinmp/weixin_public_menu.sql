/*
Navicat MySQL Data Transfer

Source Server         : 10.200.157.139
Source Server Version : 50635
Source Host           : 10.200.157.139:3306
Source Database       : xiehua-uat

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-12-14 11:01:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for xiehua_weixin_public_menu
-- ----------------------------
DROP TABLE IF EXISTS `xiehua_weixin_public_menu`;
CREATE TABLE `xiehua_weixin_public_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `creator_id` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人',
  `modifier_id` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `generate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（不能用于业务）',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间（不能用于业务）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `weixin_uid` varchar(50) NOT NULL DEFAULT '' COMMENT '微信公众号审核通过后生成的唯一标识,对外暴露',
  `level` smallint(4) NOT NULL DEFAULT '1' COMMENT '微信菜单级别（一级菜单数组，个数应为1~3个,二级菜单数组，个数应为1~5个），code=WECHAT_MENU_LEVEL',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单标题，不超过16个字节，子菜单不超过60个字节',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '微信菜单类型（菜单的响应动作类型）',
  `key` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单KEY值，用于消息接口推送，不超过128字节（click等点击类型必须）',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '网页链接，用户点击菜单可打开链接，不超过1024字节（view类型必须）',
  `parent_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '父级菜单id',
  `need_auth` smallint(2) NOT NULL DEFAULT '1' COMMENT '是否需要微信授权认证url',
  `sequence` smallint(4) NOT NULL DEFAULT '0' COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1385 DEFAULT CHARSET=utf8 COMMENT='微信公众号菜单';

-- ----------------------------
-- Records of xiehua_weixin_public_menu
-- ----------------------------
INSERT INTO `xiehua_weixin_public_menu` VALUES ('494', '', '', '2018-04-26 16:12:03', '2018-05-05 13:56:45', '2018-04-26 16:12:03', '2018-04-26 16:12:03', 'b710f0a81fd04db4bec70381c5ba0437', '1', '订单中心', 'view', '', 'https://uat-c.zchz.com/b7db1dc746cb4ccaa1fac387cbe65fba/orderlist', '0', '2', '1');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('495', '', '', '2018-04-26 16:12:03', '2018-05-05 13:56:46', '2018-04-26 16:12:03', '2018-04-26 16:12:03', 'b710f0a81fd04db4bec70381c5ba0437', '1', '个人中心', 'view', '', 'https://uat-c.zchz.com/b7db1dc746cb4ccaa1fac387cbe65fba/userHome', '0', '2', '2');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('496', '', '', '2018-04-26 16:12:03', '2018-05-05 13:56:49', '2018-04-26 16:12:03', '2018-04-26 16:12:03', 'b710f0a81fd04db4bec70381c5ba0437', '1', '店铺首页', 'view', '', 'https://uat-c.zchz.com/b7db1dc746cb4ccaa1fac387cbe65fba', '0', '2', '3');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('535', '', '', '2018-05-09 17:19:47', '2018-05-09 17:19:47', '2018-05-09 17:19:48', '2018-05-09 17:19:48', 'c05e6bd956b846069bd4a086ab598180', '1', '店铺首页', 'view', '', 'https://uat-c.zchz.com/231a31a3e2a645e7b56146e829694685', '0', '2', '1');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('536', '', '', '2018-05-09 17:19:47', '2018-05-09 17:19:47', '2018-05-09 17:19:48', '2018-05-09 17:19:48', 'c05e6bd956b846069bd4a086ab598180', '1', '个人中心', 'view', '', 'https://uat-c.zchz.com/231a31a3e2a645e7b56146e829694685/userHome', '0', '2', '2');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('537', '', '', '2018-05-09 17:19:47', '2018-05-09 17:19:47', '2018-05-09 17:19:48', '2018-05-09 17:19:48', 'c05e6bd956b846069bd4a086ab598180', '1', '订单中心', 'view', '', 'https://uat-c.zchz.com/231a31a3e2a645e7b56146e829694685/orderlist', '0', '2', '3');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('654', '', '', '2018-05-15 08:44:02', '2018-05-15 08:44:02', '2018-05-15 08:44:03', '2018-05-15 08:44:03', '89a8cd67b327461d91dbae3969405448', '1', '订单中心', 'view', '', 'https://uat-c.zchz.com/dce8a0dce4fc401ba4132974484cac48/orderlist', '0', '2', '1');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('655', '', '', '2018-05-15 08:44:02', '2018-05-15 08:44:02', '2018-05-15 08:44:03', '2018-05-15 08:44:03', '89a8cd67b327461d91dbae3969405448', '2', '二级菜单3', 'view', '', 'https://uat-c.zchz.com/dce8a0dce4fc401ba4132974484cac48', '654', '2', '1');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('656', '', '', '2018-05-15 08:44:02', '2018-05-15 08:44:02', '2018-05-15 08:44:03', '2018-05-15 08:44:03', '89a8cd67b327461d91dbae3969405448', '2', '二级菜单1', 'view', '', 'https://uat-c.zchz.com/dce8a0dce4fc401ba4132974484cac48', '654', '2', '2');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('657', '', '', '2018-05-15 08:44:02', '2018-05-15 08:44:02', '2018-05-15 08:44:03', '2018-05-15 08:44:03', '89a8cd67b327461d91dbae3969405448', '2', '二级菜单2', 'view', '', 'https://uat-c.zchz.com/dce8a0dce4fc401ba4132974484cac48', '654', '2', '3');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('658', '', '', '2018-05-15 08:44:02', '2018-05-15 08:44:02', '2018-05-15 08:44:03', '2018-05-15 08:44:03', '89a8cd67b327461d91dbae3969405448', '1', '个人中心', 'view', '', 'https://uat-c.zchz.com/dce8a0dce4fc401ba4132974484cac48/userHome', '0', '2', '2');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('659', '', '', '2018-05-15 08:44:02', '2018-05-15 08:44:02', '2018-05-15 08:44:03', '2018-05-15 08:44:03', '89a8cd67b327461d91dbae3969405448', '1', '店铺首页', 'view', '', 'https://uat-c.zchz.com/dce8a0dce4fc401ba4132974484cac48', '0', '2', '3');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('1360', '', '', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '28766544bad244d3907242c097d8f44a', '1', '进店逛逛', 'view', '', 'https://uat-c.zchz.com/fbaac1b94a244a99bd263a5812de2238', '0', '2', '1');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('1361', '', '', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '28766544bad244d3907242c097d8f44a', '1', '进店逛逛', 'view', '', 'https://uat-c.zchz.com/fbaac1b94a244a99bd263a5812de2238', '0', '2', '2');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('1362', '', '', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '28766544bad244d3907242c097d8f44a', '1', '本地必吃必', 'view', '', 'https://uat-c.zchz.com/fbaac1b94a244a99bd263a5812de2238/home?dianPing=true', '0', '2', '3');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('1363', '', '', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '28766544bad244d3907242c097d8f44a', '2', '本地必吃必玩', 'view', '', 'https://uat-c.zchz.com/fbaac1b94a244a99bd263a5812de2238/home?dianPing=true', '1362', '2', '1');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('1364', '', '', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '2018-11-28 10:37:40', '28766544bad244d3907242c097d8f44a', '2', '预定租车', 'view', '', 'https://uat-c.zchz.com/fbaac1b94a244a99bd263a5812de2238/Cars', '1362', '2', '2');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('1378', '', '', '2018-12-04 16:14:50', '2018-12-04 16:14:50', '2018-12-04 16:14:51', '2018-12-04 16:14:51', '67a53957af1148618474f59ced4d62d9', '1', '自驾游', 'view', '', 'https://uat-c.zchz.com/96309c109307453e9f17cca3a74175fb', '0', '2', '1');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('1379', '', '', '2018-12-04 16:14:50', '2018-12-04 16:14:50', '2018-12-04 16:14:51', '2018-12-04 16:14:51', '67a53957af1148618474f59ced4d62d9', '1', '租车业务', 'miniprogram', '', 'pages/index/index', '0', '2', '2');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('1380', '', '', '2018-12-04 16:14:50', '2018-12-04 16:14:50', '2018-12-04 16:14:51', '2018-12-04 16:14:51', '67a53957af1148618474f59ced4d62d9', '2', '违章无忧', 'view', '', 'https://uat-c.zchz.com/96309c109307453e9f17cca3a74175fb/peccancyQuery', '1379', '2', '1');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('1381', '', '', '2018-12-04 16:14:50', '2018-12-04 16:14:50', '2018-12-04 16:14:51', '2018-12-04 16:14:51', '67a53957af1148618474f59ced4d62d9', '2', '机场送机', 'view', '', 'https://uat-c.zchz.com/96309c109307453e9f17cca3a74175fb/home?jiesong=1', '1379', '2', '2');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('1382', '', '', '2018-12-04 16:14:50', '2018-12-04 16:14:50', '2018-12-04 16:14:51', '2018-12-04 16:14:51', '67a53957af1148618474f59ced4d62d9', '2', '租车神器', 'miniprogram', '', 'pages/index/index', '1379', '2', '3');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('1383', '', '', '2018-12-04 16:14:50', '2018-12-04 16:14:50', '2018-12-04 16:14:51', '2018-12-04 16:14:51', '67a53957af1148618474f59ced4d62d9', '2', '土著美食', 'view', '', 'https://uat-c.zchz.com/96309c109307453e9f17cca3a74175fb/home?dianPing=true', '1379', '2', '4');
INSERT INTO `xiehua_weixin_public_menu` VALUES ('1384', '', '', '2018-12-04 16:14:50', '2018-12-04 16:14:50', '2018-12-04 16:14:51', '2018-12-04 16:14:51', '67a53957af1148618474f59ced4d62d9', '1', '订单查询', 'view', '', 'https://uat-c.zchz.com/96309c109307453e9f17cca3a74175fb/orderlist', '0', '2', '3');
