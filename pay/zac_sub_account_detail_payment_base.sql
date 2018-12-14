/*
Navicat MySQL Data Transfer

Source Server         : 10.200.157.139
Source Server Version : 50635
Source Host           : 10.200.157.139:3306
Source Database       : huboot-dev

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-12-14 14:48:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for zac_sub_account_detail_payment_base
-- ----------------------------
DROP TABLE IF EXISTS `zac_sub_account_detail_payment_base`;
CREATE TABLE `zac_sub_account_detail_payment_base` (
  `id` bigint(20) unsigned NOT NULL COMMENT '唯一标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `type` tinyint(4) NOT NULL COMMENT '子账户明细支付类型,code=SUB_ACCOUNT_DETAIL_PAYMENT_TYPE',
  `platform` tinyint(4) NOT NULL DEFAULT '0' COMMENT '子账户明细支付平台,code=SUB_ACCOUNT_DETAIL_PAYMENT_PLATFORM',
  `method` tinyint(4) NOT NULL DEFAULT '0' COMMENT '子账户明细支付方式,code=SUB_ACCOUNT_DETAIL_PAYMENT_METHOD',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '备注',
  `version` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '更新版本号',
  `creator_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '创建人',
  `modifier_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '修改人',
  `record_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(0无效1有效)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户中心-子账户明细支付基础信息表';

-- ----------------------------
-- Records of zac_sub_account_detail_payment_base
-- ----------------------------
INSERT INTO `zac_sub_account_detail_payment_base` VALUES ('1', '2016-12-13 09:54:13', '2017-03-25 16:57:35', '1', '1', '2', '线下转账', '1', '0', '0', '1');
INSERT INTO `zac_sub_account_detail_payment_base` VALUES ('2', '2017-02-16 14:11:57', '2017-02-16 14:40:22', '2', '2', '1', 'Pingpp通道', '1', '0', '0', '1');
INSERT INTO `zac_sub_account_detail_payment_base` VALUES ('3', '2017-02-16 14:12:00', '2017-02-16 14:40:23', '3', '2', '1', 'Pingpp通道', '1', '0', '0', '1');
INSERT INTO `zac_sub_account_detail_payment_base` VALUES ('4', '2017-02-16 14:12:03', '2017-02-16 14:40:24', '4', '2', '1', 'Pingpp通道', '1', '0', '0', '1');
INSERT INTO `zac_sub_account_detail_payment_base` VALUES ('5', '2017-03-09 19:18:06', '2017-03-11 16:20:50', '5', '4', '1', 'Pingpp通道', '1', '0', '0', '1');
INSERT INTO `zac_sub_account_detail_payment_base` VALUES ('6', '2017-03-08 13:57:04', '2017-03-25 16:57:26', '6', '1', '2', '线下支付卖家代操作', '1', '0', '0', '1');
INSERT INTO `zac_sub_account_detail_payment_base` VALUES ('7', '2017-03-14 13:36:16', '2017-03-25 16:56:31', '7', '5', '1', '系统计算', '1', '0', '0', '0');
INSERT INTO `zac_sub_account_detail_payment_base` VALUES ('8', '2017-06-02 10:24:51', '2017-06-02 10:24:51', '8', '1', '1', '财务核销', '1', '0', '0', '1');
INSERT INTO `zac_sub_account_detail_payment_base` VALUES ('9', '2017-07-03 10:13:29', '2017-07-03 10:13:29', '9', '3', '1', '支付宝手机网站支付', '1', '0', '0', '1');
INSERT INTO `zac_sub_account_detail_payment_base` VALUES ('10', '2017-06-02 10:24:51', '2017-09-09 15:37:42', '10', '5', '1', '携华平台支付', '1', '0', '0', '1');
INSERT INTO `zac_sub_account_detail_payment_base` VALUES ('11', '2017-06-02 10:24:51', '2017-09-09 15:37:42', '11', '5', '1', '携华平台支付', '1', '0', '0', '1');
INSERT INTO `zac_sub_account_detail_payment_base` VALUES ('12', '2017-06-02 10:24:51', '2018-04-10 12:59:42', '12', '2', '1', 'Pingpp通道', '1', '0', '0', '1');
