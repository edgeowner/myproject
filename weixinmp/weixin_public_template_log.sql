/*
Navicat MySQL Data Transfer

Source Server         : 10.200.157.139
Source Server Version : 50635
Source Host           : 10.200.157.139:3306
Source Database       : xiehua-uat

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-12-14 11:01:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for xiehua_weixin_public_template_log
-- ----------------------------
DROP TABLE IF EXISTS `xiehua_weixin_public_template_log`;
CREATE TABLE `xiehua_weixin_public_template_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `creator_id` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人',
  `modifier_id` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `generate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（不能用于业务）',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间（不能用于业务）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `system` smallint(2) NOT NULL DEFAULT '0' COMMENT '所属系统',
  `weixin_uid` varchar(50) NOT NULL DEFAULT '' COMMENT '微信公众号审核通过后生成的唯一标识,对外暴露',
  `open_id` varchar(128) NOT NULL DEFAULT '' COMMENT '用户openid',
  `node` smallint(4) NOT NULL DEFAULT '0' COMMENT '微信节点',
  `template_id` varchar(50) NOT NULL DEFAULT '' COMMENT '公众号消息模板id',
  `message_content` text NOT NULL COMMENT '消息内容更',
  `message_id` varchar(50) NOT NULL DEFAULT '' COMMENT '返回的消息id',
  `send_status` smallint(2) NOT NULL DEFAULT '0' COMMENT '发送状态（1-成功，2-失败）',
  `error_reason` varchar(500) NOT NULL DEFAULT '' COMMENT '错误原因',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5253 DEFAULT CHARSET=utf8 COMMENT='微信公众号发送模板消息日志表';

-- ----------------------------
-- Records of xiehua_weixin_public_template_log
-- ----------------------------

INSERT INTO `xiehua_weixin_public_template_log` VALUES ('704', '', '', '2018-04-26 17:49:27', '2018-04-26 17:49:27', '2018-04-26 17:49:27', '2018-04-26 17:49:27', '1', '67a53957af1148618474f59ced4d62d9', 'oVrfT1Zy7gLteD7s2q5Mfq1YKtCQ', '4', 'V7mWbdAt-fflXvY_KJxyIuSW2TuXz-zJgtO1lfl1pxw', '{\"touser\":\"oVrfT1Zy7gLteD7s2q5Mfq1YKtCQ\",\"template_id\":\"V7mWbdAt-fflXvY_KJxyIuSW2TuXz-zJgtO1lfl1pxw\",\"url\":\"https://uat-c.zchz.com/96309c109307453e9f17cca3a74175fb/orderdetails?ordersn=ZK439112475426684928&openType=wxtpl\",\"data\":{\"first\":{\"value\":\"你有订单已还车，如有尾款，请注意及时支付\",\"color\":\"#000000\"},\"keyword1\":{\"value\":\"奔驰C级 2017款 C 200 运动版 4MATIC\",\"color\":\"#003366\"},\"keyword2\":{\"value\":\"2018-04-27 18:00:00\",\"color\":\"#003366\"},\"keyword3\":{\"value\":\"上海自营业务租车科技有限公司\",\"color\":\"#003366\"},\"keyword4\":{\"value\":\"1\",\"color\":\"#003366\"},\"keyword5\":{\"value\":\"581.00\",\"color\":\"#003366\"},\"remark\":{\"value\":\"订单编号：ZK439112475426684928\",\"color\":\"#003366\"}}}', '254072420816797696', '1', '', '[96309c109307453e9f17cca3a74175fb, ZK439112475426684928]');
INSERT INTO `xiehua_weixin_public_template_log` VALUES ('705', '', '', '2018-04-26 17:52:25', '2018-04-26 17:52:25', '2018-04-26 17:52:25', '2018-04-26 17:52:25', '1', '67a53957af1148618474f59ced4d62d9', 'oVrfT1bU7BWlcQi4k3uwQYxSeEvQ', '3', 'HXgaiyXELoBjevkPM2_MKg7u1Ipf5KOF5CH75YdMSj0', '{\"touser\":\"oVrfT1bU7BWlcQi4k3uwQYxSeEvQ\",\"template_id\":\"HXgaiyXELoBjevkPM2_MKg7u1Ipf5KOF5CH75YdMSj0\",\"url\":\"https://uat-c.zchz.com/96309c109307453e9f17cca3a74175fb/orderdetails?ordersn=ZK438800505515278336&openType=wxtpl\",\"data\":{\"first\":{\"value\":\"你有订单已确认提车，请注意查看\",\"color\":\"#000000\"},\"keyword1\":{\"value\":\"ZK438800505515278336\",\"color\":\"#003366\"},\"keyword2\":{\"value\":\"奔驰C级 2017款 C 200 运动版 4MATIC\",\"color\":\"#003366\"},\"keyword3\":{\"value\":\"2018-04-25 20:36:33\",\"color\":\"#003366\"},\"keyword4\":{\"value\":\"42502其他浦东新区\",\"color\":\"#003366\"},\"remark\":{\"value\":\"提车时间：2018-04-25 21:00:00\",\"color\":\"#003366\"}}}', '254075399712030722', '1', '', '[96309c109307453e9f17cca3a74175fb, ZK438800505515278336]');
INSERT INTO `xiehua_weixin_public_template_log` VALUES ('706', '', '', '2018-04-26 17:52:37', '2018-04-26 17:52:37', '2018-04-26 17:52:38', '2018-04-26 17:52:38', '1', '67a53957af1148618474f59ced4d62d9', 'oVrfT1bU7BWlcQi4k3uwQYxSeEvQ', '4', 'V7mWbdAt-fflXvY_KJxyIuSW2TuXz-zJgtO1lfl1pxw', '{\"touser\":\"oVrfT1bU7BWlcQi4k3uwQYxSeEvQ\",\"template_id\":\"V7mWbdAt-fflXvY_KJxyIuSW2TuXz-zJgtO1lfl1pxw\",\"url\":\"https://uat-c.zchz.com/96309c109307453e9f17cca3a74175fb/orderdetails?ordersn=ZK438800505515278336&openType=wxtpl\",\"data\":{\"first\":{\"value\":\"你有订单已还车，如有尾款，请注意及时支付\",\"color\":\"#000000\"},\"keyword1\":{\"value\":\"奔驰C级 2017款 C 200 运动版 4MATIC\",\"color\":\"#003366\"},\"keyword2\":{\"value\":\"2018-04-26 21:00:00\",\"color\":\"#003366\"},\"keyword3\":{\"value\":\"上海自营业务租车科技有限公司\",\"color\":\"#003366\"},\"keyword4\":{\"value\":\"1\",\"color\":\"#003366\"},\"keyword5\":{\"value\":\"1600.00\",\"color\":\"#003366\"},\"remark\":{\"value\":\"订单编号：ZK438800505515278336\",\"color\":\"#003366\"}}}', '254075614259068930', '1', '', '[96309c109307453e9f17cca3a74175fb, ZK438800505515278336]');
INSERT INTO `xiehua_weixin_public_template_log` VALUES ('707', '', '', '2018-04-26 20:00:00', '2018-04-26 20:00:00', '2018-04-26 20:00:00', '2018-04-26 20:00:00', '1', '67a53957af1148618474f59ced4d62d9', '', '8', '', '', '', '2', '用户openid不存在，userGid=b9bd95c6a4af435abc4cc023c07f5bdc，WeixinUid=67a53957af1148618474f59ced4d62d9', '[96309c109307453e9f17cca3a74175fb, ZK438081193833922560]');
INSERT INTO `xiehua_weixin_public_template_log` VALUES ('708', '', '', '2018-04-26 20:12:43', '2018-04-26 20:12:43', '2018-04-26 20:12:43', '2018-04-26 20:12:43', '1', '28766544bad244d3907242c097d8f44a', 'oFmW31TqnobSWBp6hY3wIYQpaIhk', '1', '', '', '', '2', '公众号微信模板配置不存在', '[fbaac1b94a244a99bd263a5812de2238, ZK439156895672958976]');
INSERT INTO `xiehua_weixin_public_template_log` VALUES ('709', '', '', '2018-04-26 20:16:53', '2018-04-26 20:16:53', '2018-04-26 20:16:53', '2018-04-26 20:16:53', '1', '28766544bad244d3907242c097d8f44a', 'oFmW31TqnobSWBp6hY3wIYQpaIhk', '2', '', '', '', '2', '公众号微信模板配置不存在', '[fbaac1b94a244a99bd263a5812de2238, ZK439156895672958976]');
INSERT INTO `xiehua_weixin_public_template_log` VALUES ('710', '', '', '2018-04-26 20:17:23', '2018-04-26 20:17:23', '2018-04-26 20:17:24', '2018-04-26 20:17:24', '1', '28766544bad244d3907242c097d8f44a', 'oFmW31TqnobSWBp6hY3wIYQpaIhk', '8', '', '', '', '2', '公众号微信模板配置不存在', '[fbaac1b94a244a99bd263a5812de2238, ZK439156895672958976]');
INSERT INTO `xiehua_weixin_public_template_log` VALUES ('711', '', '', '2018-04-26 20:18:09', '2018-04-26 20:18:09', '2018-04-26 20:18:10', '2018-04-26 20:18:10', '1', '28766544bad244d3907242c097d8f44a', 'oFmW31TqnobSWBp6hY3wIYQpaIhk', '3', '', '', '', '2', '公众号微信模板配置不存在', '[fbaac1b94a244a99bd263a5812de2238, ZK439156895672958976]');
INSERT INTO `xiehua_weixin_public_template_log` VALUES ('712', '', '', '2018-04-26 20:18:25', '2018-04-26 20:18:25', '2018-04-26 20:18:25', '2018-04-26 20:18:25', '1', '28766544bad244d3907242c097d8f44a', 'oFmW31TqnobSWBp6hY3wIYQpaIhk', '4', '', '', '', '2', '公众号微信模板配置不存在', '[fbaac1b94a244a99bd263a5812de2238, ZK439156895672958976]');