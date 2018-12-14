/*
Navicat MySQL Data Transfer

Source Server         : 10.200.157.139
Source Server Version : 50635
Source Host           : 10.200.157.139:3306
Source Database       : xiehua-uat

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-12-14 11:01:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for xiehua_weixin_public
-- ----------------------------
DROP TABLE IF EXISTS `xiehua_weixin_public`;
CREATE TABLE `xiehua_weixin_public` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `creator_id` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人',
  `modifier_id` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `generate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（不能用于业务）',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间（不能用于业务）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `weixin_uid` varchar(50) NOT NULL DEFAULT '' COMMENT '微信公众号审核通过后生成的唯一标识,对外暴露',
  `app_id` varchar(50) NOT NULL DEFAULT '' COMMENT '公众号appid',
  `secret` varchar(50) NOT NULL DEFAULT '' COMMENT '公众号secret',
  `token` varchar(50) NOT NULL DEFAULT '' COMMENT '公众号token',
  `aes_key` varchar(50) NOT NULL DEFAULT '' COMMENT '公众号aes_key',
  `original_id` varchar(50) NOT NULL DEFAULT '' COMMENT '原始id',
  `refresh_token` varchar(255) NOT NULL DEFAULT '' COMMENT 'refresh_token',
  `verify_type_info` smallint(4) NOT NULL DEFAULT '-1' COMMENT '认证信息',
  `status` smallint(2) NOT NULL DEFAULT '0' COMMENT '状态（0-待验证，1-服务器验证通过，2-账号验证通过，3-验证失败）',
  `system` smallint(2) NOT NULL DEFAULT '0' COMMENT '所属系统',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `qrcode_url` varchar(500) NOT NULL DEFAULT '' COMMENT '二维码图片路径',
  `nick_name` varchar(500) NOT NULL DEFAULT '' COMMENT '公众号昵称',
  `head_img` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `principal_name` varchar(255) NOT NULL DEFAULT '' COMMENT '公众号的主体名称',
  `alias` varchar(255) NOT NULL DEFAULT '' COMMENT '授权方公众号所设置的微信号，可能为空',
  `signature` varchar(255) NOT NULL DEFAULT '' COMMENT '账号介绍',
  `bind_type` smallint(4) NOT NULL DEFAULT '0' COMMENT '公众号绑定类型（1-微信，2-微信三方服务）',
  `func_info` varchar(255) NOT NULL DEFAULT '' COMMENT '权限集列表',
  `type` smallint(4) NOT NULL DEFAULT '0' COMMENT '类型（1-公众号，2-小程序）',
  `save_qrcode_url` varchar(500) DEFAULT '' COMMENT '长按保存二维码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='微信公众号';

-- ----------------------------
-- Records of xiehua_weixin_public
-- ----------------------------
INSERT INTO `xiehua_weixin_public` VALUES ('20', '', '', '2018-04-04 10:34:06', '2018-07-11 13:25:09', '2018-04-04 10:34:06', '2018-04-04 10:34:06', 'fc9d9b9aa5914354b672c1222bd5c542', 'wx28054e9ced0cb3d0', '9a3d33de92de7c0cf0a5d02b01fa9084', 'uat_token', 'euVFud1iu8l3kNWzLKc95E3Gau8TlJnoLWpu7JOwfca', 'gh_599637a07488', '', '-1', '1', '3', '同行调车公众号（直客收款使用）', '', '', '', '', '', '', '1', '', '1', '');
INSERT INTO `xiehua_weixin_public` VALUES ('26', '', '463', '2018-04-18 15:21:55', '2018-12-03 18:15:33', '2018-04-18 15:21:55', '2018-12-03 18:15:34', '67a53957af1148618474f59ced4d62d9', 'wxf82b5bfc53c3410b', '', '', '', 'gh_f4bf0eaa7168', '', '-1', '2', '1', '', 'http://mmbiz.qpic.cn/mmbiz_jpg/mib4tyUvnauMmS8n7yvTPA8iaVic5FF0ibVG9Z9xibTuHqFYHyF7JeNjbJ8F2JB9jTPcn2LWCvlOwY95j2shiaS3KakQ/0', 'ZKZC', '', '上海携华网络科技有限公司', '', '为国内汽车租赁公司提供综合服务和解决方案，致力于解决车源、司机、金融、风控等方面问题。', '2', '1,15,4,7,2,3,11,6,5,8,13,9,10,12,22,23,24,26,33,35', '1', 'http://img1.zchz.com/img/xiehua/qrcode/2018/31d/a97/470035997a57a66.png');
INSERT INTO `xiehua_weixin_public` VALUES ('27', '', '', '2018-04-22 10:03:26', '2018-12-10 17:53:01', '2018-04-22 10:03:27', '2018-12-10 17:53:02', 'c2cb46ce684a4cf6906612b2b3dc58e8', 'wx75022ffadbf568ad', '', '', '', 'gh_578c1c0404c0', 'refreshtoken@@@WL2A2h4AhY6TmSHspsNauSc5YhqkWufiDOEY6loRLxQ', '0', '2', '1', '', 'http://mmbiz.qpic.cn/mmbiz_jpg/7S5yTPEBtGIrfuPBwM2930o2R0KjEB4b75Oh4fd3k5HwphMTNpVQMWcKLFR6q4Hfa222vvtlc7jjw1iaX4zw81A/0', '直客uat测试01', '', '上海携华网络科技有限公司', '', '直客uat小程序测试01直客uat小程序测试01', '2', '17,18,19,25,30,31,36,37,40', '2', '');
INSERT INTO `xiehua_weixin_public` VALUES ('28', '', '', '2018-04-22 12:10:13', '2018-12-13 10:04:37', '2018-04-22 12:10:13', '2018-12-13 10:04:37', '4575935e1fc1400aa8bcaf3bb277435c', 'wx3a3ee94071f53f23', '', '', '', 'gh_232f813c825d', 'refreshtoken@@@sPjcgF7vA2xYabavLHT3-ID9pSjd4rM-ep5L-HMhnx8', '0', '2', '1', '', 'http://mmbiz.qpic.cn/mmbiz_jpg/aibG4eApAHzhyE0sMYJn9YnEsEGBnibPm0uDxl861ZxduftSWv3emelgG1pa3RbTicP13iaT62GGkJslick05NC0S1Q/0', '直客租车uat测试02', '', '上海携华网络科技有限公司', '', '直客租车uat测试02', '2', '17,18,19,25,30,31,36,37,40', '2', '');
INSERT INTO `xiehua_weixin_public` VALUES ('29', '', '410', '2018-04-25 15:56:39', '2018-12-07 21:20:36', '2018-04-25 15:56:39', '2018-12-07 21:20:36', '28766544bad244d3907242c097d8f44a', 'wx0414d1fa80ce7cc1', '07df51f9ee4f00c2ea05873be31e5b80', 'uat_token', 'string', 'gh_6d6f83b85143', '', '-1', '1', '1', '娇娇公众号', 'https://w.url.cn/s/AHAPBgZ', '上海好美家', '', '', '', '', '1', '', '1', 'http://img1.zchz.com/img/xiehua/qrcode/2018/f9b/c71/15924333a754be5.png');
INSERT INTO `xiehua_weixin_public` VALUES ('30', '', '', '2018-05-05 13:45:55', '2018-07-11 13:25:09', '2018-05-05 13:45:56', '2018-05-05 13:48:07', '89a8cd67b327461d91dbae3969405448', 'wx1692fda65634fd53', '9cb24e6900ad8b9fff1d8ddb3c02393b', 'string', 'string', 'gh_6676f304c64e', '', '-1', '1', '1', '骚兵公众号', 'https://w.url.cn/s/AEEHv69', '如月之升', '', '', '', '', '1', '', '1', '');
INSERT INTO `xiehua_weixin_public` VALUES ('31', '', '229', '2018-05-07 17:04:04', '2018-12-04 13:00:26', '2018-05-07 17:04:05', '2018-12-04 13:00:26', 'c05e6bd956b846069bd4a086ab598180', 'wx08f847e3f1b4d3bd', '2b71109fb2992136f9f8dee9ba007e1a', 'string', 'string', 'gh_b1a201f64327', '', '-1', '1', '1', '娇娇另一个微信号', 'https://w.url.cn/s/Aeh2Ntz', '黑白设计撮合', '', '', '', '', '1', '', '1', 'http://img1.zchz.com/img/xiehua/qrcode/2018/206/263/2dccb474068a08f.png');
INSERT INTO `xiehua_weixin_public` VALUES ('32', '', '', '2018-05-07 17:16:29', '2018-07-11 13:25:09', '2018-05-07 17:16:29', '2018-05-07 17:17:36', 'b710f0a81fd04db4bec70381c5ba0437', 'wx26eb0ca40bfe88df', '968ed57c6c789325adca734007021821', 'string', 'string', 'gh_9c1470639d2b', '', '-1', '1', '1', '大圣另一个微信号', 'https://w.url.cn/s/AxXmY56', '产品撮合有限公司', '', '', '', '', '1', '', '1', '');
INSERT INTO `xiehua_weixin_public` VALUES ('33', '', '', '2018-05-25 14:16:49', '2018-07-11 13:25:09', '2018-05-25 14:16:49', '2018-05-25 14:16:49', '50830c616a124792892f4cc6fa522197', 'wx40c4c7fea89d3230', '23575653e69eb48472e6f194e9de56f4', 'string', 'string', 'gh_909ccf0b43c4', '', '-1', '1', '1', 'uat直客收款小程序', '', '', '', '', '', '', '1', '', '2', '');
INSERT INTO `xiehua_weixin_public` VALUES ('34', '', '', '2018-05-31 18:05:40', '2018-07-11 13:25:09', '2018-05-31 18:05:41', '2018-05-31 18:05:41', '2dff7a14df2c443e8e95c582534fb9fc', 'wx283924353d15ea98', 'ccea01b86ea2d944ee29598237a34bee', 'string', 'string', 'gh_ebf9e1ec3410', '', '-1', '1', '1', 'uat开发小程序', '', '', '', '', '', '', '1', '', '2', '');
