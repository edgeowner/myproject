/*
Navicat MySQL Data Transfer

Source Server         : 10.200.157.139
Source Server Version : 50635
Source Host           : 10.200.157.139:3306
Source Database       : xiehua-uat

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-12-14 11:01:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for xiehua_weixin_public_template
-- ----------------------------
DROP TABLE IF EXISTS `xiehua_weixin_public_template`;
CREATE TABLE `xiehua_weixin_public_template` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `creator_id` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人',
  `modifier_id` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `generate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（不能用于业务）',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间（不能用于业务）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `weixin_uid` varchar(50) NOT NULL DEFAULT '' COMMENT '微信公众号审核通过后生成的唯一标识,对外暴露',
  `template_id` varchar(50) NOT NULL DEFAULT '' COMMENT '公众号模板id',
  `rela_template_id` int(10) NOT NULL DEFAULT '0' COMMENT '关联模板id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COMMENT='微信公众号节点模板消息关系表';

-- ----------------------------
-- Records of xiehua_weixin_public_template
-- ----------------------------
INSERT INTO `xiehua_weixin_public_template` VALUES ('43', '', '', '2018-04-18 15:25:38', '2018-04-18 15:25:38', '2018-04-18 15:25:38', '2018-04-18 15:25:38', '67a53957af1148618474f59ced4d62d9', 'lSCdqcOy06BPNvezq0-FSWCImrklUhSqFRLDvj13PmA', '4');
INSERT INTO `xiehua_weixin_public_template` VALUES ('44', '', '', '2018-04-18 15:25:38', '2018-04-18 15:25:38', '2018-04-18 15:25:39', '2018-04-18 15:25:39', '67a53957af1148618474f59ced4d62d9', 'w4BJasQtMmAcSQULLrX_Abp1HiJd81QciviJcAO8pJc', '5');
INSERT INTO `xiehua_weixin_public_template` VALUES ('45', '', '', '2018-04-18 15:25:39', '2018-04-18 15:25:39', '2018-04-18 15:25:40', '2018-04-18 15:25:40', '67a53957af1148618474f59ced4d62d9', 'HXgaiyXELoBjevkPM2_MKg7u1Ipf5KOF5CH75YdMSj0', '6');
INSERT INTO `xiehua_weixin_public_template` VALUES ('46', '', '', '2018-04-18 15:25:40', '2018-04-18 15:25:40', '2018-04-18 15:25:40', '2018-04-18 15:25:40', '67a53957af1148618474f59ced4d62d9', 'V7mWbdAt-fflXvY_KJxyIuSW2TuXz-zJgtO1lfl1pxw', '7');
INSERT INTO `xiehua_weixin_public_template` VALUES ('47', '', '', '2018-04-18 15:25:40', '2018-04-18 15:25:40', '2018-04-18 15:25:41', '2018-04-18 15:25:41', '67a53957af1148618474f59ced4d62d9', 'sD2nKnGEKZTjuCb_da8fAfdTQqSq1zqb2uW61iTRhTY', '8');
INSERT INTO `xiehua_weixin_public_template` VALUES ('48', '', '', '2018-04-18 15:25:40', '2018-04-18 15:25:40', '2018-04-18 15:25:41', '2018-04-18 15:25:41', '67a53957af1148618474f59ced4d62d9', 'yQRtbCXJTPHd8Cpqyfis05RmO-lOfNasgtwH7guJ-T8', '9');
INSERT INTO `xiehua_weixin_public_template` VALUES ('49', '', '', '2018-04-18 15:25:41', '2018-04-18 15:25:41', '2018-04-18 15:25:41', '2018-04-18 15:25:41', '67a53957af1148618474f59ced4d62d9', 'hIxJub-MGF-eq1XOhSwJWVa59g2m1I5V-8yzb2TSKFM', '10');
INSERT INTO `xiehua_weixin_public_template` VALUES ('50', '', '', '2018-04-18 15:25:41', '2018-04-18 15:25:41', '2018-04-18 15:25:42', '2018-04-18 15:25:42', '67a53957af1148618474f59ced4d62d9', 'bY4R0hrqoCW1Me4RWWfFMQHXfBlXstmSxYQnpoJ-jak', '11');
INSERT INTO `xiehua_weixin_public_template` VALUES ('51', '', '', '2018-04-18 15:25:42', '2018-04-18 15:25:42', '2018-04-18 15:25:42', '2018-04-18 15:25:42', '67a53957af1148618474f59ced4d62d9', 'ljnOKdxupJcJUddofKp-ESoPYDsV9vezoKuIUjTtjFQ', '12');
INSERT INTO `xiehua_weixin_public_template` VALUES ('52', '', '', '2018-04-18 15:25:42', '2018-04-18 15:25:42', '2018-04-18 15:25:42', '2018-04-18 15:25:42', '67a53957af1148618474f59ced4d62d9', 'vZbtXznhJ240KJ6xkOkfR1Rkv3sQyZI1XAWlHnP6vkw', '13');
INSERT INTO `xiehua_weixin_public_template` VALUES ('53', '', '', '2018-07-23 19:40:45', '2018-07-23 19:40:45', '2018-07-23 19:40:46', '2018-07-23 19:40:46', '67a53957af1148618474f59ced4d62d9', 'lSCdqcOy06BPNvezq0-FSWCImrklUhSqFRLDvj13PmA', '14');
INSERT INTO `xiehua_weixin_public_template` VALUES ('54', '', '', '2018-07-23 19:41:38', '2018-07-23 19:41:38', '2018-07-23 19:41:39', '2018-07-23 19:41:39', '67a53957af1148618474f59ced4d62d9', 'w4BJasQtMmAcSQULLrX_Abp1HiJd81QciviJcAO8pJc', '15');
INSERT INTO `xiehua_weixin_public_template` VALUES ('57', '', '', '2018-07-23 19:42:21', '2018-07-23 19:42:21', '2018-07-23 19:42:22', '2018-07-23 19:42:22', '67a53957af1148618474f59ced4d62d9', 'sD2nKnGEKZTjuCb_da8fAfdTQqSq1zqb2uW61iTRhTY', '19');
INSERT INTO `xiehua_weixin_public_template` VALUES ('58', '', '', '2018-07-24 14:58:35', '2018-07-24 14:58:35', '2018-07-24 14:58:36', '2018-07-24 14:58:36', '67a53957af1148618474f59ced4d62d9', 'SC2UeNxTOEJeZDGWPb0qnfxbWnt2WXz3t1GZ0F1QvCE', '20');
INSERT INTO `xiehua_weixin_public_template` VALUES ('60', '', '', '2018-07-24 14:59:54', '2018-07-24 14:59:54', '2018-07-24 14:59:54', '2018-07-24 14:59:54', '67a53957af1148618474f59ced4d62d9', 'SC2UeNxTOEJeZDGWPb0qnfxbWnt2WXz3t1GZ0F1QvCE', '22');
INSERT INTO `xiehua_weixin_public_template` VALUES ('61', '', '', '2018-08-16 14:18:54', '2018-08-16 14:18:54', '2018-08-16 14:18:54', '2018-08-16 14:18:54', '67a53957af1148618474f59ced4d62d9', 'lSCdqcOy06BPNvezq0-FSWCImrklUhSqFRLDvj13PmA', '23');
INSERT INTO `xiehua_weixin_public_template` VALUES ('62', '', '', '2018-08-16 14:19:18', '2018-08-16 14:19:18', '2018-08-16 14:19:19', '2018-08-16 14:19:19', '67a53957af1148618474f59ced4d62d9', 'CXWsxCYhPh1VrGbaRc2FD5wlFtC8Vgxfgof39k8Bs9w', '24');
INSERT INTO `xiehua_weixin_public_template` VALUES ('63', '', '', '2018-08-16 14:19:27', '2018-08-16 14:19:27', '2018-08-16 14:19:27', '2018-08-16 14:19:27', '67a53957af1148618474f59ced4d62d9', 'fd1Jb4CKgjMPyOAufo3ODf7O7Pj2uiOswxFkfcP7QiY', '25');
INSERT INTO `xiehua_weixin_public_template` VALUES ('64', '', '', '2018-08-16 14:19:38', '2018-08-16 14:19:38', '2018-08-16 14:19:39', '2018-08-16 14:19:39', '67a53957af1148618474f59ced4d62d9', 'Yn2dtmayGuYaKwxxU9ov_wIiiKNRTC2uTPW6v2V6Di0', '26');
INSERT INTO `xiehua_weixin_public_template` VALUES ('65', '', '', '2018-08-16 14:19:46', '2018-08-16 14:19:46', '2018-08-16 14:19:47', '2018-08-16 14:19:47', '67a53957af1148618474f59ced4d62d9', 'rEkQfHXDMyeepVvQ4LjsUnKHmu19vu7Lrt6aod2oePE', '27');
INSERT INTO `xiehua_weixin_public_template` VALUES ('66', '', '', '2018-10-29 17:11:24', '2018-10-29 17:11:24', '2018-10-29 17:11:25', '2018-10-29 17:11:25', '67a53957af1148618474f59ced4d62d9', 'Wj1Xc6kO_ByRVu3HJUIw-T5udQya_FOFK2ahU5zbEag', '28');
INSERT INTO `xiehua_weixin_public_template` VALUES ('67', '', '', '2018-10-29 17:11:49', '2018-10-29 17:11:49', '2018-10-29 17:11:49', '2018-10-29 17:11:49', '67a53957af1148618474f59ced4d62d9', '8skbpXCz_rGy7bWPA4cKtneXRxxAbdYAm3oSDoBVyxM', '29');
INSERT INTO `xiehua_weixin_public_template` VALUES ('68', '', '', '2018-11-06 15:10:06', '2018-11-06 15:10:06', '2018-11-06 15:10:06', '2018-11-06 15:10:06', '67a53957af1148618474f59ced4d62d9', 'r_bpuUAbYAecvfZc_kvTskJWkYdPpB_hpeZOlQw5VDU', '30');
INSERT INTO `xiehua_weixin_public_template` VALUES ('69', '', '', '2018-11-07 14:35:38', '2018-11-07 14:35:38', '2018-11-07 14:35:39', '2018-11-07 14:35:39', '67a53957af1148618474f59ced4d62d9', '-qGqlJ0pfipkAY_qTKOVUcC39I4zoqhNxQWReJpd4jY', '31');
INSERT INTO `xiehua_weixin_public_template` VALUES ('70', '', '', '2018-11-07 15:27:56', '2018-11-07 15:27:56', '2018-11-07 15:27:57', '2018-11-07 15:27:57', '67a53957af1148618474f59ced4d62d9', 'hIxJub-MGF-eq1XOhSwJWVa59g2m1I5V-8yzb2TSKFM', '32');
