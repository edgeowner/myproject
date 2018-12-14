/*
Navicat MySQL Data Transfer

Source Server         : 10.200.157.139
Source Server Version : 50635
Source Host           : 10.200.157.139:3306
Source Database       : xiehua-dev

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-12-14 15:38:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for zb_system_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `zb_system_sms_template`;
CREATE TABLE `zb_system_sms_template` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `creator_id` varchar(20) DEFAULT '' COMMENT '创建人',
  `modifier_id` varchar(20) DEFAULT '' COMMENT '修改人',
  `generate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（不能用于业务）',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间（不能用于业务）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `node` tinyint(4) NOT NULL DEFAULT '0' COMMENT '短信节点',
  `template_id` varchar(32) NOT NULL DEFAULT '' COMMENT '模板id',
  `template` varchar(255) NOT NULL COMMENT '内容',
  `system` varchar(32) NOT NULL COMMENT '系统',
  `status` smallint(1) DEFAULT '0' COMMENT '状态:0初始化,1,成功,2失败',
  `shop_uid` varchar(32) DEFAULT NULL COMMENT '直客店铺id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1827 DEFAULT CHARSET=utf8 COMMENT='短信模板表';

-- ----------------------------
-- Records of zb_system_sms_template
-- ----------------------------
INSERT INTO `zb_system_sms_template` VALUES ('1824', '', '', '2018-06-14 14:46:44', '2018-06-14 14:49:16', '2018-06-14 14:46:57', '2018-06-14 14:49:29', '101', '570ffd9d66574b3096a070b1700fb816', '{activityName},租车遇上双重礼!{shopName}为您奉上{value}元立减劵，{activityNote}，抢先领取请猛戳{url}，退订回TD', '1', '0', '1216b6eac408476a9223cc3358853adb');
INSERT INTO `zb_system_sms_template` VALUES ('1825', '', '', '2018-06-14 14:49:27', '2018-06-14 15:10:50', '2018-06-14 14:49:41', '2018-06-14 14:49:41', '101', '476326fb2dc6488080b4008d62bb8003', '{activityName},租车遇上双重礼!{shopName}为您奉上{value}元立减劵，{activityNote}，抢先领取请猛戳{url}，退订回TD', '1', '0', '1216b6eac408476a9223cc3358853adb');
INSERT INTO `zb_system_sms_template` VALUES ('1826', '', '', '2018-06-14 15:12:15', '2018-06-14 15:12:15', '2018-06-14 15:12:29', '2018-06-14 15:12:29', '100', '2af4c614e78e45baa7d0f317c87fc1a3', '{activityTheme}!{shopName}为您奉上{value}元立减劵,{activityNote}，抢先领取请猛戳{url}，退订回TD', '1', '1', '1216b6eac408476a9223cc3358853adb');
