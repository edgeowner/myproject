/*
Navicat MySQL Data Transfer

Source Server         : 10.200.157.139
Source Server Version : 50635
Source Host           : 10.200.157.139:3306
Source Database       : xiehua-uat

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-12-14 11:01:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for xiehua_weixin_tempalte
-- ----------------------------
DROP TABLE IF EXISTS `xiehua_weixin_tempalte`;
CREATE TABLE `xiehua_weixin_tempalte` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `creator_id` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人',
  `modifier_id` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `generate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（不能用于业务）',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间（不能用于业务）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `node` smallint(4) NOT NULL DEFAULT '0' COMMENT '微信节点',
  `template_id_short` varchar(255) NOT NULL DEFAULT '' COMMENT '模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `click_url` varchar(500) NOT NULL DEFAULT '' COMMENT '点击模板跳转页面',
  `status` smallint(2) NOT NULL DEFAULT '0' COMMENT '状态（0-无，1-模板保存，2-公众号模板添加完成）',
  `system` smallint(2) NOT NULL DEFAULT '0' COMMENT '应用系统',
  `need_auth` smallint(2) NOT NULL DEFAULT '1' COMMENT '是否需要微信授权认证url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='微信模板信息表';

-- ----------------------------
-- Records of xiehua_weixin_tempalte
-- ----------------------------
INSERT INTO `xiehua_weixin_tempalte` VALUES ('4', '', '', '2018-02-05 19:55:40', '2018-11-27 09:15:38', '2018-02-05 19:56:01', '2018-03-12 10:24:10', '1', 'OPENTM406173979', '下单成功提醒', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('5', '', '', '2018-02-05 19:55:40', '2018-11-27 09:15:38', '2018-02-05 19:56:01', '2018-03-12 10:24:10', '2', 'OPENTM202374389', '订单确认提醒', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('6', '', '', '2018-02-05 19:55:40', '2018-11-27 09:15:38', '2018-02-05 19:56:01', '2018-03-12 10:24:10', '3', 'OPENTM409398754', '提车通知', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('7', '', '', '2018-02-05 19:55:40', '2018-11-27 09:15:38', '2018-02-05 19:56:01', '2018-03-12 10:24:11', '4', 'OPENTM407428436', '还车成功通知', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('8', '', '', '2018-02-05 19:55:40', '2018-11-27 09:15:38', '2018-02-05 19:56:01', '2018-03-12 10:24:11', '5', 'OPENTM405958370', '订单取消通知', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('9', '', '', '2018-02-05 19:55:40', '2018-11-27 09:15:38', '2018-02-05 19:56:01', '2018-03-12 10:24:12', '6', 'OPENTM408894858', '审核结果通知', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('10', '', '', '2018-02-05 19:55:40', '2018-11-27 09:15:38', '2018-02-05 19:56:01', '2018-03-12 10:24:12', '7', 'OPENTM407085138', '还车提醒', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('11', '', '', '2018-02-05 19:55:40', '2018-11-26 17:29:45', '2018-02-05 19:56:01', '2018-03-12 10:24:12', '8', 'OPENTM409398754', '提车通知', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('12', '', '', '2018-02-05 19:55:40', '2018-11-26 17:29:51', '2018-02-05 19:56:01', '2018-03-12 10:24:13', '9', 'OPENTM411464105', '订单待支付提醒', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('13', '', '', '2018-03-22 10:06:16', '2018-11-26 17:29:57', '2018-03-22 10:06:17', '2018-03-22 10:09:07', '10', 'OPENTM402207057', '押金退还提醒', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('14', '', '', '2018-07-23 19:03:23', '2018-11-26 17:30:03', '2018-07-23 19:03:23', '2018-07-23 19:03:23', '11', 'OPENTM406173979', '接送机下单成功提醒', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/relayOrderDetail\"}', '1', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('15', '', '', '2018-07-23 19:07:10', '2018-11-26 17:30:10', '2018-07-23 19:07:10', '2018-07-23 19:07:10', '12', 'OPENTM202374389', '接送机订单确认提醒', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/relayOrderDetail\"}', '1', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('19', '', '', '2018-07-23 19:42:21', '2018-11-26 17:30:16', '2018-07-23 19:42:22', '2018-07-23 19:42:22', '15', 'OPENTM405958370', '接送机订单取消通知', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/relayOrderDetail\"}', '1', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('20', '', '', '2018-07-24 14:58:34', '2018-11-26 17:30:22', '2018-07-24 14:58:35', '2018-07-24 14:58:36', '13', 'OPENTM415476664', '派车通知', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/relayOrderDetail\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('22', '', '', '2018-07-24 14:59:54', '2018-11-26 17:30:27', '2018-07-24 14:59:54', '2018-07-24 14:59:54', '14', 'OPENTM415476664', '确认派车通知', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/relayOrderDetail\"}', '1', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('23', '', '', '2018-08-16 14:18:54', '2018-11-26 17:30:33', '2018-08-16 14:18:54', '2018-08-16 14:18:54', '16', 'OPENTM406173979', '待付款通知', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderGoodsDetails\"}', '1', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('24', '', '', '2018-08-16 14:19:18', '2018-11-26 17:30:39', '2018-08-16 14:19:18', '2018-08-16 14:19:19', '17', 'OPENTM410404351', '待发货通知通知', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderGoodsDetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('25', '', '', '2018-08-16 14:19:27', '2018-11-26 17:30:46', '2018-08-16 14:19:27', '2018-08-16 14:19:27', '18', 'OPENTM414251563', '收货通知', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderGoodsDetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('26', '', '', '2018-08-16 14:19:38', '2018-11-26 17:30:53', '2018-08-16 14:19:39', '2018-08-16 14:19:39', '19', 'OPENTM406411654', '订单取消', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderGoodsDetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('27', '', '', '2018-08-16 14:19:46', '2018-11-26 17:31:00', '2018-08-16 14:19:46', '2018-08-16 14:19:47', '20', 'OPENTM408279243', '订单关闭', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderGoodsDetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('28', '', '', '2018-10-29 17:11:24', '2018-11-23 16:51:34', '2018-10-29 17:11:24', '2018-10-29 17:11:25', '21', 'OPENTM408905197', '处理结果', 'pages/index/index?link={\"path\":\"{0}/{1}/queryList\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('29', '', '', '2018-10-29 17:11:49', '2018-11-26 17:31:07', '2018-10-29 17:11:49', '2018-10-29 17:11:49', '22', 'OPENTM410544052', '违章通知', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}', '2', '1', '2');
INSERT INTO `xiehua_weixin_tempalte` VALUES ('32', '', '', '2018-11-07 15:27:56', '2018-11-26 17:31:14', '2018-11-07 15:27:57', '2018-11-07 15:27:57', '23', 'OPENTM407085138', '续租提醒', 'pages/index/index?link={\"ordersn\":\"{2}\",\"openType\":\"wxtpl\",\"path\":\"{0}/{1}/orderdetails\"}', '1', '1', '2');
