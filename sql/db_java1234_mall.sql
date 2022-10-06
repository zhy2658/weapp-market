/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50736 (5.7.36)
 Source Host           : localhost:3306
 Source Schema         : db_java1234_mall

 Target Server Type    : MySQL
 Target Server Version : 50736 (5.7.36)
 File Encoding         : 65001

 Date: 02/10/2022 09:00:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES (1, 'yousa', '1234');

-- ----------------------------
-- Table structure for t_bigtype
-- ----------------------------
DROP TABLE IF EXISTS `t_bigtype`;
CREATE TABLE `t_bigtype`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品大类名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '大类封面图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_bigtype
-- ----------------------------
INSERT INTO `t_bigtype` VALUES (1, '男生', 'Man', '20220926025212000000239.jpg');
INSERT INTO `t_bigtype` VALUES (2, '女生', '??????', '20220926025929000000817.jpg');
INSERT INTO `t_bigtype` VALUES (3, '美声', '??????', '3.jpg');
INSERT INTO `t_bigtype` VALUES (4, 'LoL', 'LoL', '20220926030146000000981.jpg');
INSERT INTO `t_bigtype` VALUES (5, '国外', '?????', '20220926030247000000442.jpg');
INSERT INTO `t_bigtype` VALUES (6, '大陆', '?????', '20220926030547000000265.jpg');
INSERT INTO `t_bigtype` VALUES (7, '大学生', '??????', '20220926030617000000950.jpg');
INSERT INTO `t_bigtype` VALUES (8, '游戏', 'Game', '20220926030709000000041.jpg');
INSERT INTO `t_bigtype` VALUES (9, '连麦', 'comminicate', '20220926030730000000380.jpg');
INSERT INTO `t_bigtype` VALUES (10, '其他', '??????', '20220926030758000000915.jpg');
INSERT INTO `t_bigtype` VALUES (41, 'ds', 'ds', 'default.jpg');
INSERT INTO `t_bigtype` VALUES (42, 'dsds', 'ds', 'default.jpg');
INSERT INTO `t_bigtype` VALUES (43, '22', '22', 'default.jpg');

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sendOpenID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `receiverOpenId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ms_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ms_id22`(`ms_id`) USING BTREE,
  CONSTRAINT `ms_id22` FOREIGN KEY (`ms_id`) REFERENCES `t_msgsession` (`ms_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_message
-- ----------------------------
INSERT INTO `t_message` VALUES (2, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 'hello', '2022-09-25 14:11:49', 1);
INSERT INTO `t_message` VALUES (3, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'hello', '2022-09-25 14:31:34', 1);
INSERT INTO `t_message` VALUES (4, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'hello', '2022-09-25 14:32:53', 1);
INSERT INTO `t_message` VALUES (5, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'hello', '2022-09-25 14:37:11', 1);
INSERT INTO `t_message` VALUES (6, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '333', '2022-09-25 14:52:24', 1);
INSERT INTO `t_message` VALUES (7, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'on yes', '2022-09-25 14:52:45', 1);
INSERT INTO `t_message` VALUES (8, 'sssss', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '44421232111111111111111111111111111111111111111111sadsafsafas', '2022-09-25 14:54:55', 1);
INSERT INTO `t_message` VALUES (9, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5fWj72e_5-043_qsk', '111', '2022-09-25 14:55:15', 1);
INSERT INTO `t_message` VALUES (10, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWsk', '111', '2022-09-25 14:55:24', 1);
INSERT INTO `t_message` VALUES (11, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '1111', '2022-09-25 14:55:30', 1);
INSERT INTO `t_message` VALUES (12, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'hello', '2022-09-25 14:57:29', 1);
INSERT INTO `t_message` VALUES (13, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '111', '2022-09-25 14:58:20', 1);
INSERT INTO `t_message` VALUES (14, 'sssss', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '11122', '2022-09-25 14:58:24', 1);
INSERT INTO `t_message` VALUES (15, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '111223123', '2022-09-25 14:58:29', 1);
INSERT INTO `t_message` VALUES (16, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '111223123123', '2022-09-25 14:58:34', 1);
INSERT INTO `t_message` VALUES (17, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '11111ohWWn5CNE3xxfWj72e_5-043_qsk', '2222', '2022-09-25 14:58:48', 1);
INSERT INTO `t_message` VALUES (18, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '11111ohWWn5CNE3xxfWj72e_5-043_qsk', '222222', '2022-09-25 14:58:51', 1);
INSERT INTO `t_message` VALUES (19, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '11111ohWWn5CNE3xxfWj72e_5-043_qsk', '222222', '2022-09-25 15:01:14', 1);
INSERT INTO `t_message` VALUES (20, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '11', '2022-09-25 15:01:26', 1);
INSERT INTO `t_message` VALUES (21, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '1122', '2022-09-25 15:01:28', 1);
INSERT INTO `t_message` VALUES (22, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '22', '2022-09-25 15:06:32', 1);
INSERT INTO `t_message` VALUES (23, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '', '2022-09-25 15:55:46', 1);
INSERT INTO `t_message` VALUES (24, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '22222', '2022-09-25 20:17:38', 1);
INSERT INTO `t_message` VALUES (25, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '????', '2022-09-25 20:18:18', 1);
INSERT INTO `t_message` VALUES (26, 'sssss', 'hello122222222222222222222222222222223sdfsadsadsfdsfdsfds', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '2022-09-25 20:20:35', 1);
INSERT INTO `t_message` VALUES (27, 'sssss', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'hello Mr.Justice', '2022-09-25 20:21:41', 1);
INSERT INTO `t_message` VALUES (28, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '11', '2022-09-25 20:34:50', 1);
INSERT INTO `t_message` VALUES (29, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '11', '2022-09-25 20:41:31', 1);
INSERT INTO `t_message` VALUES (30, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'asdsadddddddddddddddddddddddadsadsadsadsadsadasdas', '2022-09-25 20:59:15', 1);
INSERT INTO `t_message` VALUES (31, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '11111111111111111111111111111111111111111sadsadsadsadsaaaaaaaaaaaaaaaaaa', '2022-09-25 21:02:24', 1);
INSERT INTO `t_message` VALUES (45, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '11asda', '2022-09-25 21:29:06', 1);
INSERT INTO `t_message` VALUES (46, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'myu12551', '2022-09-25 21:33:44', 1);
INSERT INTO `t_message` VALUES (47, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '对象', '2022-09-26 14:23:28', 1);
INSERT INTO `t_message` VALUES (48, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '是的', '2022-09-26 21:48:53', 1);
INSERT INTO `t_message` VALUES (49, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'sure是的！！！！', '2022-09-26 21:49:06', 1);
INSERT INTO `t_message` VALUES (50, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'halma卡', '2022-09-27 13:12:53', 1);
INSERT INTO `t_message` VALUES (51, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '2dsdas', '2022-09-27 18:52:14', 1);
INSERT INTO `t_message` VALUES (52, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '聊天', '2022-09-27 18:58:49', 1);
INSERT INTO `t_message` VALUES (53, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '2222', '2022-09-27 19:03:01', 1);
INSERT INTO `t_message` VALUES (54, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '刘备', '2022-09-27 19:13:34', 1);
INSERT INTO `t_message` VALUES (55, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '刘强', '2022-09-27 21:10:41', 1);
INSERT INTO `t_message` VALUES (56, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'yes', '2022-09-28 09:55:47', 1);
INSERT INTO `t_message` VALUES (57, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 'youaha', '2022-09-28 09:59:56', 1);
INSERT INTO `t_message` VALUES (58, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '545啊空间', '2022-09-28 13:29:45', 10);
INSERT INTO `t_message` VALUES (59, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 'yousa挺好', '2022-09-28 13:30:23', 10);

-- ----------------------------
-- Table structure for t_msgsession
-- ----------------------------
DROP TABLE IF EXISTS `t_msgsession`;
CREATE TABLE `t_msgsession`  (
  `ms_id` int(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `openId2` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ms_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ms_id`) USING BTREE,
  INDEX `openid2_ms`(`openId2`) USING BTREE,
  INDEX `openId_ms`(`openId`) USING BTREE,
  CONSTRAINT `openId_ms` FOREIGN KEY (`openId`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `openid2_ms` FOREIGN KEY (`openId2`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_msgsession
-- ----------------------------
INSERT INTO `t_msgsession` VALUES (1, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '2022-09-27 12:34:35');
INSERT INTO `t_msgsession` VALUES (10, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '2022-09-28 09:59:55');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `userId` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'openId微信用户ID',
  `totalPrice` decimal(8, 2) NULL DEFAULT NULL COMMENT '总价',
  `address` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `consignee` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `telNumber` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `createDate` datetime NULL DEFAULT NULL COMMENT '订单创建日期',
  `payDate` datetime NULL DEFAULT NULL COMMENT '订单支付日期',
  `status` int(11) NULL DEFAULT 1 COMMENT '订单状态 0 未支付 1 已经支付',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 117 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (82, 'JAVA20220127032849000000201', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 2599.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-27 15:28:49', '2022-01-27 15:29:06', 1);
INSERT INTO `t_order` VALUES (83, 'JAVA20220129103913000000494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 1);
INSERT INTO `t_order` VALUES (84, 'JAVA20220129103913000001494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 1);
INSERT INTO `t_order` VALUES (85, 'JAVA20220129103913000002494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 1);
INSERT INTO `t_order` VALUES (86, 'JAVA20220129103913000003494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 1);
INSERT INTO `t_order` VALUES (87, 'JAVA20220129103913000004494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 1);
INSERT INTO `t_order` VALUES (88, 'JAVA20220129103913000005494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 2);
INSERT INTO `t_order` VALUES (89, 'JAVA20220129103913000006494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 2);
INSERT INTO `t_order` VALUES (90, 'JAVA20220129103913000007494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 2);
INSERT INTO `t_order` VALUES (91, 'JAVA20220129103913000008494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 2);
INSERT INTO `t_order` VALUES (92, 'JAVA20220129103913000009494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 2);
INSERT INTO `t_order` VALUES (93, 'JAVA20220129103913000010494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 2);
INSERT INTO `t_order` VALUES (94, 'JAVA20220129103913000011494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 2);
INSERT INTO `t_order` VALUES (95, 'JAVA20220129103913000012494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 2);
INSERT INTO `t_order` VALUES (96, 'JAVA20220129103913000013494', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 10:39:13', '2022-01-27 15:29:06', 3);
INSERT INTO `t_order` VALUES (97, 'JAVA20220129035805000000052', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 2599.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 15:58:05', '2022-01-29 16:12:26', 2);
INSERT INTO `t_order` VALUES (98, 'JAVA20220129035946000000595', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 5198.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 15:59:47', '2022-01-29 16:14:07', 2);
INSERT INTO `t_order` VALUES (99, 'JAVA20220129040547000000824', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 3799.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 16:05:48', '2022-01-29 16:20:08', 2);
INSERT INTO `t_order` VALUES (100, 'JAVA20220129040836000000654', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 2599.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 16:08:37', '2022-01-29 16:12:52', 3);
INSERT INTO `t_order` VALUES (101, 'JAVA20220129041124000000758', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 2599.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 16:11:25', '2022-01-29 16:11:36', 2);
INSERT INTO `t_order` VALUES (102, 'JAVA20220129043412000000133', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 3799.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-01-29 16:34:12', '2022-01-29 16:34:26', 2);
INSERT INTO `t_order` VALUES (103, 'JAVA20220217071851000000421', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 2599.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-02-17 19:18:51', NULL, 1);
INSERT INTO `t_order` VALUES (104, 'JAVA20220217072440000000734', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 2599.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-02-17 19:24:41', NULL, 4);
INSERT INTO `t_order` VALUES (105, 'JAVA20220217072709000000080', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 17494.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-02-17 19:27:09', NULL, 3);
INSERT INTO `t_order` VALUES (106, 'JAVA20220221080859000000097', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 7497.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-02-21 08:08:59', '2022-02-21 08:09:24', 2);
INSERT INTO `t_order` VALUES (107, 'JAVA20220221082558000000449', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 31794.00, '广东省广州市海珠区新港中路397号', '张三', '020-81167888', '2022-02-21 08:25:58', '2022-02-21 08:26:15', 3);
INSERT INTO `t_order` VALUES (108, 'JAVA20220923102956000000005', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 2999.00, '?????????????397?', '??', '020-81167888', '2022-09-23 22:29:56', NULL, 1);
INSERT INTO `t_order` VALUES (109, 'JAVA20220923103000000000561', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 2999.00, '?????????????397?', '??', '020-81167888', '2022-09-23 22:30:01', NULL, 1);
INSERT INTO `t_order` VALUES (110, 'JAVA20220924084940000000537', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 1199.00, '?????????????397?', '??', '020-81167888', '2022-09-24 08:49:41', NULL, 1);
INSERT INTO `t_order` VALUES (111, 'JAVA20220924095928000000397', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 1199.00, '?????????????397?', '??', '020-81167888', '2022-09-24 09:59:28', NULL, 1);
INSERT INTO `t_order` VALUES (112, 'JAVA20220924103818000000795', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 1199.00, '?????????????397?', '??', '020-81167888', '2022-09-24 10:38:19', NULL, 1);
INSERT INTO `t_order` VALUES (113, 'JAVA20220924112648000000412', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 1199.00, '?????????????397?', '??', '020-81167888', '2022-09-24 11:26:48', NULL, 1);
INSERT INTO `t_order` VALUES (114, 'JAVA20220924115200000000281', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 1199.00, '?????????????397?', '??', '020-81167888', '2022-09-24 11:52:00', NULL, 1);
INSERT INTO `t_order` VALUES (115, 'JAVA20220924115309000000561', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 1199.00, '?????????????397?', '??', '020-81167888', '2022-09-24 11:53:10', NULL, 1);
INSERT INTO `t_order` VALUES (116, 'JAVA20220924121100000000381', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 1199.00, '?????????????397?', '??', '020-81167888', '2022-09-24 12:11:00', NULL, 1);

-- ----------------------------
-- Table structure for t_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `mId` int(11) NULL DEFAULT NULL COMMENT '订单主表ID',
  `goodsId` int(11) NULL DEFAULT NULL COMMENT '商品ID',
  `goodsNumber` int(11) NULL DEFAULT NULL COMMENT '商品购买数量',
  `goodsPrice` decimal(8, 2) NULL DEFAULT NULL COMMENT '商品单价',
  `goodsName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goodsPic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `mId`(`mId`) USING BTREE,
  CONSTRAINT `t_order_detail_ibfk_1` FOREIGN KEY (`mId`) REFERENCES `t_order` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 149 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_detail
-- ----------------------------
INSERT INTO `t_order_detail` VALUES (121, 82, 14, 1, 2599.00, 'Xiaomi Civi', '9.png');
INSERT INTO `t_order_detail` VALUES (122, 83, 14, 2, 2599.00, 'Xiaomi Civi', '9.png');
INSERT INTO `t_order_detail` VALUES (123, 97, 14, 1, 2599.00, 'Xiaomi Civi', '9.png');
INSERT INTO `t_order_detail` VALUES (124, 98, 14, 2, 2599.00, 'Xiaomi Civi', '9.png');
INSERT INTO `t_order_detail` VALUES (125, 99, 4, 1, 3799.00, 'Xiaomi 11', '6.png');
INSERT INTO `t_order_detail` VALUES (126, 100, 14, 1, 2599.00, 'Xiaomi Civi', '9.png');
INSERT INTO `t_order_detail` VALUES (127, 101, 14, 1, 2599.00, 'Xiaomi Civi', '9.png');
INSERT INTO `t_order_detail` VALUES (128, 102, 4, 1, 3799.00, 'Xiaomi 11', '6.png');
INSERT INTO `t_order_detail` VALUES (129, 103, 14, 1, 2599.00, 'Xiaomi Civi', '9.png');
INSERT INTO `t_order_detail` VALUES (130, 104, 14, 1, 2599.00, 'Xiaomi Civi', '9.png');
INSERT INTO `t_order_detail` VALUES (131, 105, 14, 2, 2599.00, 'Xiaomi Civi', '9.png');
INSERT INTO `t_order_detail` VALUES (132, 105, 4, 1, 3799.00, 'Xiaomi 11', '6.png');
INSERT INTO `t_order_detail` VALUES (133, 105, 16, 2, 1999.00, '小米平板5', '13.png');
INSERT INTO `t_order_detail` VALUES (134, 105, 6, 1, 4499.00, 'Xiaomi 11 Pro', '1.png');
INSERT INTO `t_order_detail` VALUES (135, 106, 5, 1, 2299.00, 'Redmi K40 游戏增强版', '11.png');
INSERT INTO `t_order_detail` VALUES (136, 106, 14, 2, 2599.00, 'Xiaomi Civi', '9.png');
INSERT INTO `t_order_detail` VALUES (137, 107, 14, 3, 2599.00, 'Xiaomi Civi', '9.png');
INSERT INTO `t_order_detail` VALUES (138, 107, 1, 2, 10999.00, '小米电视大师 82英寸', '21.png');
INSERT INTO `t_order_detail` VALUES (139, 107, 16, 1, 1999.00, '小米平板5', '13.png');
INSERT INTO `t_order_detail` VALUES (140, 108, 9, 1, 2999.00, 'Xiaomi 10S', '4.png');
INSERT INTO `t_order_detail` VALUES (141, 109, 9, 1, 2999.00, 'Xiaomi 10S', '4.png');
INSERT INTO `t_order_detail` VALUES (142, 110, 8, 1, 1199.00, 'Note 9 5G', '3.png');
INSERT INTO `t_order_detail` VALUES (143, 111, 8, 1, 1199.00, 'Note 9 5G', '3.png');
INSERT INTO `t_order_detail` VALUES (144, 112, 8, 1, 1199.00, 'Note 9 5G', '3.png');
INSERT INTO `t_order_detail` VALUES (145, 113, 8, 1, 1199.00, 'Note 9 5G', '3.png');
INSERT INTO `t_order_detail` VALUES (146, 114, 8, 1, 1199.00, 'Note 9 5G', '3.png');
INSERT INTO `t_order_detail` VALUES (147, 115, 8, 1, 1199.00, 'Note 9 5G', '3.png');
INSERT INTO `t_order_detail` VALUES (148, 116, 8, 1, 1199.00, 'Note 9 5G', '3.png');

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` decimal(8, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `stock` int(11) NULL DEFAULT NULL COMMENT '库存',
  `proPic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'default.jpg' COMMENT '商品图片',
  `isHot` bit(1) NULL DEFAULT b'0' COMMENT '是否热门推荐商品',
  `isSwiper` bit(1) NULL DEFAULT b'0' COMMENT '是否轮播图片商品',
  `swiperPic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'default.jpg' COMMENT '商品轮播图片',
  `swiperSort` int(11) NULL DEFAULT 0 COMMENT '轮播排序',
  `typeId` int(11) NULL DEFAULT NULL COMMENT '商品类别编号',
  `hotDateTime` datetime NULL DEFAULT NULL COMMENT '设置热卖日期',
  `productIntroImgs` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品介绍图片',
  `productParaImgs` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品规格参数图片',
  `description` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `openId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `audio` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '音频',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `typeId`(`typeId`) USING BTREE,
  INDEX `openId_product`(`openId`) USING BTREE,
  CONSTRAINT `openId_product` FOREIGN KEY (`openId`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_product_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `t_smalltype` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES (8, 'Yousa', 1199.00, 1111, 'default.jpg', b'1', b'0', 'default.jpg', 0, 40, '2021-09-28 21:36:34', '\"\"', NULL, 'xxx', 'testopen', NULL);
INSERT INTO `t_product` VALUES (9, '杰克Jack', 2999.00, 1111, 'default.jpg', b'1', b'1', '20220924094939000000043.jpg', 0, 2, '2021-12-28 21:36:34', '<h2><strong><u>我是杰克</u></strong></h2><p><br></p><ul><li>首先别人请我抬棺都会准备合适的音乐，没有合适的BGM我是不接单的</li><li>我的团队相当专业！！</li><li><br></li><li>进行抬棺仪式时请不相关的人员远离，这有助于提升仪式的神圣度。</li></ul><h3><br></h3><h2>黑人抬棺唯一电话号：<span style=\"color: rgb(230, 0, 0);\">0718-5551555</span></h2><p><br></p><h4>别的电话统统是诈骗</h4><p><br></p><h3><strong>感谢大家的关注！！</strong></h3><p>谢谢</p>', '<p>This is paramThis is paramThis is param</p><p>This is param</p><p>This is param</p><p>This is param</p><p>This is param</p><p>This is paramThis is param</p>', '我是杰克，专业团队专业抬棺，联系我们获取最新安葬一条龙优惠，黑人抬棺唯一电话号：0718-5551555', 'sssss', '/uploads/audios/testaudio.mp3');
INSERT INTO `t_product` VALUES (10, '冷鸟', 1399.00, 2222, '20220929081244000000369.jpg', b'1', b'1', '20220929102016000000812.jpg', 0, 40, '2021-11-28 21:36:34', '<p>javapublic文字介绍javapublic文</p><p class=\"ql-align-center\">javapublic文字介绍</p><p class=\"ql-align-center\">javapublic文字介绍</p><p class=\"ql-align-center\">javapublic文字介绍字介绍javapublic文字介绍javapublic文字介绍javapublic文字介绍javapublic文字介绍</p>', '<p>23123lo 我是冷鸟</p>', 'hello 我是冷鸟', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '/uploads/audios/testaudio2.mp3');
INSERT INTO `t_product` VALUES (11, '黑色', 2499.00, 3322, '7.png', b'1', b'0', 'default.jpg', 0, 41, '2021-11-28 21:36:34', '<p> </p><p>\"</p>', NULL, 'yousa', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '/uploads/audios/testaudio3.mp3');

-- ----------------------------
-- Table structure for t_product_swiper_image
-- ----------------------------
DROP TABLE IF EXISTS `t_product_swiper_image`;
CREATE TABLE `t_product_swiper_image`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `productId` int(11) NULL DEFAULT NULL COMMENT '所属商品id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_swiper_image
-- ----------------------------
INSERT INTO `t_product_swiper_image` VALUES (1, '1.jpg', 1, 14);
INSERT INTO `t_product_swiper_image` VALUES (2, '2.jpg', 2, 14);
INSERT INTO `t_product_swiper_image` VALUES (3, '3.jpg', 3, 14);
INSERT INTO `t_product_swiper_image` VALUES (4, '4.jpg', 4, 14);
INSERT INTO `t_product_swiper_image` VALUES (5, '5.jpg', 5, 14);
INSERT INTO `t_product_swiper_image` VALUES (6, '6.jpg', 6, 14);
INSERT INTO `t_product_swiper_image` VALUES (7, '7.jpg', 7, 14);
INSERT INTO `t_product_swiper_image` VALUES (8, '11.jpg', 1, 4);
INSERT INTO `t_product_swiper_image` VALUES (9, '22.jpg', 2, 4);
INSERT INTO `t_product_swiper_image` VALUES (10, '33.jpg', 3, 4);
INSERT INTO `t_product_swiper_image` VALUES (11, '44.jpg', 4, 4);
INSERT INTO `t_product_swiper_image` VALUES (12, '111.jpg', 1, 16);
INSERT INTO `t_product_swiper_image` VALUES (13, '222.jpg', 2, 16);
INSERT INTO `t_product_swiper_image` VALUES (14, '333.jpg', 3, 16);
INSERT INTO `t_product_swiper_image` VALUES (24, '20220926010903000000336.jpg', 0, 9);
INSERT INTO `t_product_swiper_image` VALUES (25, '20220926010908000000614.jpg', 0, 9);
INSERT INTO `t_product_swiper_image` VALUES (26, '20220926010921000000913.jpg', 0, 9);
INSERT INTO `t_product_swiper_image` VALUES (27, '20220929102033000000013.jpg', 0, 10);
INSERT INTO `t_product_swiper_image` VALUES (28, '20220929102039000000632.jpg', 0, 10);
INSERT INTO `t_product_swiper_image` VALUES (29, '20220929102043000000824.jpg', 0, 10);

-- ----------------------------
-- Table structure for t_publish
-- ----------------------------
DROP TABLE IF EXISTS `t_publish`;
CREATE TABLE `t_publish`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '无标题',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '无内容',
  `address` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isimg` int(11) NULL DEFAULT 0 COMMENT '0:无  1:有',
  `pubtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0:待审核  1:审核通过  2：审核失败',
  `openId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`pid`) USING BTREE,
  INDEX `openid_publish`(`openId`) USING BTREE,
  CONSTRAINT `openid_publish` FOREIGN KEY (`openId`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_publish
-- ----------------------------
INSERT INTO `t_publish` VALUES (31, 'hello world', '你好,啊就是打算佛南萨非宁你好,啊就是打算佛南萨非宁静静', '湖北恩施', 0, '2022-09-28 21:40:39', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (32, 'hello world', '你好,啊就是打算佛南萨非你好,啊就是打算佛南萨非宁静宁静', '湖北恩施', 0, '2022-09-28 21:44:20', 1, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (33, 'hello world', '你好,啊就是打算佛南萨你好,啊就是打算佛南萨非宁静非宁静', '湖北恩施', 0, '2022-09-28 21:44:45', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (34, 'hello world已过', '你好,啊就是打算佛南萨非宁静', '湖北恩施', 0, '2022-09-28 21:48:20', 1, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (35, 'hello world也已过', '你好,啊就是打算佛南萨非宁静', '湖北恩施', 0, '2022-09-28 21:51:17', 1, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (36, 'helo worldhello wworld', '你好,啊就是打算佛南萨非宁静', NULL, 0, '2022-09-28 21:51:55', 0, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (37, '我是标题', '我是内容你好,啊就是打算佛南萨非宁静你好,啊就是打算佛南萨非宁静你好,啊就是打算佛南萨非宁静', NULL, 0, '2022-09-28 21:54:50', 0, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (38, '我是标题啊啊啊', '我是内容', NULL, 0, '2022-09-28 21:55:40', 0, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (39, '我是标题', '我是内容', NULL, 0, '2022-09-28 21:57:38', 0, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (40, '2222asdas', '111asdas', NULL, 0, '2022-09-30 18:10:54', 0, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (41, '我是白头标题', '内容内容我试试是', NULL, 0, '2022-09-30 18:19:25', 0, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (42, '人物介绍标题', '我是内容内容呢飒飒大大', NULL, 1, '2022-09-30 18:21:11', 0, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (43, '标题标题呢标题', '内容内容asdsadas\n\nasdsa\nd\nsa\ndsa\nd\nas', NULL, 1, '2022-09-30 18:23:30', 1, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (44, '抬棺告示', '从2022年10月1号开始(国庆节)\n\n我们专业团队发布超级优惠服务：\n第二棺半价！！！ \n第二棺半价！！！', NULL, 1, '2022-09-30 18:30:51', 1, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (45, 'test图片', '图片测试测试多张', NULL, 1, '2022-09-30 20:02:06', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (46, '图片测试2', '图片测试\n内容\n内日1', NULL, 1, '2022-09-30 20:04:18', 1, 'ohWWn5CNE3xxfWj72e_5-043_qsk');

-- ----------------------------
-- Table structure for t_publish_image
-- ----------------------------
DROP TABLE IF EXISTS `t_publish_image`;
CREATE TABLE `t_publish_image`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `src` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sort` int(11) NOT NULL DEFAULT 1,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pid_11`(`pid`) USING BTREE,
  CONSTRAINT `pid_11` FOREIGN KEY (`pid`) REFERENCES `t_publish` (`pid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_publish_image
-- ----------------------------
INSERT INTO `t_publish_image` VALUES (51, '/image/productIntroImgs/20220930061053000000749.jpg', 0, 40);
INSERT INTO `t_publish_image` VALUES (52, '/image/productIntroImgs/20220930061053000000759.jpg', 1, 40);
INSERT INTO `t_publish_image` VALUES (53, '/image/productIntroImgs/20220930061053000000771.gif', 2, 40);
INSERT INTO `t_publish_image` VALUES (54, '/image/productIntroImgs/20220930062110000000892.jpg', 0, 42);
INSERT INTO `t_publish_image` VALUES (55, '/image/productIntroImgs/20220930062110000000901.jpg', 1, 42);
INSERT INTO `t_publish_image` VALUES (56, '/image/productIntroImgs/20220930062110000000907.jpg', 2, 42);
INSERT INTO `t_publish_image` VALUES (57, '/image/productIntroImgs/20220930062110000000920.gif', 3, 42);
INSERT INTO `t_publish_image` VALUES (58, '/image/productIntroImgs/20220930062329000000176.jpg', 0, 43);
INSERT INTO `t_publish_image` VALUES (59, '/image/productIntroImgs/20220930062329000000184.jpg', 1, 43);
INSERT INTO `t_publish_image` VALUES (60, '/image/productIntroImgs/20220930062329000000192.jpg', 2, 43);
INSERT INTO `t_publish_image` VALUES (61, '/image/productIntroImgs/20220930063050000000862.jpg', 0, 44);
INSERT INTO `t_publish_image` VALUES (62, '/image/productIntroImgs/20220930063050000000870.jpg', 1, 44);
INSERT INTO `t_publish_image` VALUES (63, '/image/productIntroImgs/20220930063050000000878.jpg', 2, 44);
INSERT INTO `t_publish_image` VALUES (64, '/image/productIntroImgs/20220930080205000000290.jpg', 0, 45);
INSERT INTO `t_publish_image` VALUES (65, '/image/productIntroImgs/20220930080205000000290.jpg', 1, 45);
INSERT INTO `t_publish_image` VALUES (66, '/image/productIntroImgs/20220930080205000000291.jpg', 2, 45);
INSERT INTO `t_publish_image` VALUES (67, '/image/productIntroImgs/20220930080205000000290.jpg', 3, 45);
INSERT INTO `t_publish_image` VALUES (68, '/image/productIntroImgs/20220930080205000000290.jpg', 4, 45);
INSERT INTO `t_publish_image` VALUES (69, '/image/productIntroImgs/20220930080205000000290.jpg', 5, 45);
INSERT INTO `t_publish_image` VALUES (70, '/image/productIntroImgs/20220930080205000000290.jpg', 6, 45);
INSERT INTO `t_publish_image` VALUES (71, '/image/productIntroImgs/20220930080417000000703.jpg', 0, 46);
INSERT INTO `t_publish_image` VALUES (72, '/image/productIntroImgs/20220930080417000000715.jpg', 1, 46);
INSERT INTO `t_publish_image` VALUES (73, '/image/productIntroImgs/20220930080417000000722.jpg', 2, 46);
INSERT INTO `t_publish_image` VALUES (74, '/image/productIntroImgs/20220930080417000000731.jpg', 3, 46);
INSERT INTO `t_publish_image` VALUES (75, '/image/productIntroImgs/20220930080417000000741.jpg', 4, 46);
INSERT INTO `t_publish_image` VALUES (76, '/image/productIntroImgs/20220930080417000000749.jpg', 5, 46);
INSERT INTO `t_publish_image` VALUES (77, '/image/productIntroImgs/20220930080417000000756.jpg', 6, 46);

-- ----------------------------
-- Table structure for t_publish_like
-- ----------------------------
DROP TABLE IF EXISTS `t_publish_like`;
CREATE TABLE `t_publish_like`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ltime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pid` int(11) NOT NULL,
  `openId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pid_like`(`pid`) USING BTREE,
  INDEX `open_like`(`openId`) USING BTREE,
  CONSTRAINT `open_like` FOREIGN KEY (`openId`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pid_like` FOREIGN KEY (`pid`) REFERENCES `t_publish` (`pid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_publish_like
-- ----------------------------
INSERT INTO `t_publish_like` VALUES (1, '2022-09-30 11:57:02', 35, 'sssss');
INSERT INTO `t_publish_like` VALUES (3, '2022-09-30 11:57:02', 34, 'sssss');
INSERT INTO `t_publish_like` VALUES (18, '2022-09-30 18:32:32', 44, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish_like` VALUES (19, '2022-09-30 18:34:52', 43, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish_like` VALUES (21, '2022-09-30 20:05:23', 46, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish_like` VALUES (23, '2022-10-01 22:16:32', 35, 'ohWWn5CNE3xxfWj72e_5-043_qsk');

-- ----------------------------
-- Table structure for t_publish_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_publish_reply`;
CREATE TABLE `t_publish_reply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `openId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `msg` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `retime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pid_publish`(`pid`) USING BTREE,
  INDEX `openId_reply`(`openId`) USING BTREE,
  CONSTRAINT `openId_reply` FOREIGN KEY (`openId`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pid_publish` FOREIGN KEY (`pid`) REFERENCES `t_publish` (`pid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_publish_reply
-- ----------------------------
INSERT INTO `t_publish_reply` VALUES (1, 34, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '你好111', '2022-09-30 11:25:57');
INSERT INTO `t_publish_reply` VALUES (2, 34, 'sssss', '你好2222', '2022-09-30 11:25:57');
INSERT INTO `t_publish_reply` VALUES (3, 34, 'sssss', '你好333333', '2022-09-30 11:25:57');
INSERT INTO `t_publish_reply` VALUES (4, 34, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '你好4444', '2022-09-30 11:25:57');
INSERT INTO `t_publish_reply` VALUES (5, 35, 'sssss', '你好777777', '2022-09-30 11:25:57');
INSERT INTO `t_publish_reply` VALUES (6, 35, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '你好88888', '2022-09-30 11:25:57');
INSERT INTO `t_publish_reply` VALUES (7, 35, 'sssss', '你好888888', '2022-09-30 11:25:57');
INSERT INTO `t_publish_reply` VALUES (8, 35, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '说的号', '2022-09-30 14:21:59');
INSERT INTO `t_publish_reply` VALUES (9, 35, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '12321', '2022-09-30 14:32:31');
INSERT INTO `t_publish_reply` VALUES (10, 32, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '打破', '2022-09-30 14:32:54');
INSERT INTO `t_publish_reply` VALUES (11, 32, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '11', '2022-09-30 14:35:03');
INSERT INTO `t_publish_reply` VALUES (12, 32, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '222', '2022-09-30 14:35:31');
INSERT INTO `t_publish_reply` VALUES (13, 34, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '321go', '2022-09-30 14:37:48');
INSERT INTO `t_publish_reply` VALUES (14, 35, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '我准备好了我准备好了', '2022-09-30 14:41:59');
INSERT INTO `t_publish_reply` VALUES (15, 32, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'yes', '2022-09-30 15:31:17');
INSERT INTO `t_publish_reply` VALUES (16, 43, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '干的漂亮', '2022-09-30 18:25:41');
INSERT INTO `t_publish_reply` VALUES (17, 44, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '怎么联系，我朋友需要', '2022-09-30 18:32:04');
INSERT INTO `t_publish_reply` VALUES (18, 46, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '棒棒', '2022-09-30 20:05:19');

-- ----------------------------
-- Table structure for t_smalltype
-- ----------------------------
DROP TABLE IF EXISTS `t_smalltype`;
CREATE TABLE `t_smalltype`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品小类',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `bigTypeId` int(11) NULL DEFAULT NULL COMMENT '商品大类编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bigTypeId`(`bigTypeId`) USING BTREE,
  CONSTRAINT `t_smalltype_ibfk_1` FOREIGN KEY (`bigTypeId`) REFERENCES `t_bigtype` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_smalltype
-- ----------------------------
INSERT INTO `t_smalltype` VALUES (1, 'Xiaomi Civi', 'Xiaomi Civi', 1);
INSERT INTO `t_smalltype` VALUES (2, 'Xiaomi 数字系列', 'Xiaomi 数字系列', 1);
INSERT INTO `t_smalltype` VALUES (3, 'Xiaomi MIX系列', 'Xiaomi MIX系列', 1);
INSERT INTO `t_smalltype` VALUES (5, '小米平板', '小米平板', 2);
INSERT INTO `t_smalltype` VALUES (6, 'RedmiBook', 'RedmiBook', 2);
INSERT INTO `t_smalltype` VALUES (7, '小米笔记本', '小米笔记本', 2);
INSERT INTO `t_smalltype` VALUES (10, '耳机', '耳机', 3);
INSERT INTO `t_smalltype` VALUES (11, '手表', '手表', 3);
INSERT INTO `t_smalltype` VALUES (12, '手环', '手环', 3);
INSERT INTO `t_smalltype` VALUES (13, '电视', '电视', 4);
INSERT INTO `t_smalltype` VALUES (14, '小米电视大师', '小米电视大师', 4);
INSERT INTO `t_smalltype` VALUES (15, '电视配件', '电视配件', 4);
INSERT INTO `t_smalltype` VALUES (16, '空调', '空调', 5);
INSERT INTO `t_smalltype` VALUES (17, '洗衣机', '洗衣机', 5);
INSERT INTO `t_smalltype` VALUES (18, '冰箱', '冰箱', 5);
INSERT INTO `t_smalltype` VALUES (19, '厨房大电', '厨房大电', 5);
INSERT INTO `t_smalltype` VALUES (20, '厨房小电', '厨房小电', 6);
INSERT INTO `t_smalltype` VALUES (21, '清洁电器', '清洁电器', 6);
INSERT INTO `t_smalltype` VALUES (22, '环境电器', '环境电器', 6);
INSERT INTO `t_smalltype` VALUES (23, '生活电器', '生活电器', 6);
INSERT INTO `t_smalltype` VALUES (24, '小爱音箱', '小爱音箱', 7);
INSERT INTO `t_smalltype` VALUES (25, '路由器', '路由器', 7);
INSERT INTO `t_smalltype` VALUES (26, '智能安防', '智能安防', 7);
INSERT INTO `t_smalltype` VALUES (27, '智能控制', '智能控制', 7);
INSERT INTO `t_smalltype` VALUES (28, '户外出行', '户外出行', 8);
INSERT INTO `t_smalltype` VALUES (29, '箱包', '箱包', 8);
INSERT INTO `t_smalltype` VALUES (30, '家具日用', '家具日用', 9);
INSERT INTO `t_smalltype` VALUES (31, '防护清洁', '防护清洁', 9);
INSERT INTO `t_smalltype` VALUES (32, '会员定制', '会员定制', 9);
INSERT INTO `t_smalltype` VALUES (33, '个人护理', '个人护理', 9);
INSERT INTO `t_smalltype` VALUES (34, '健康', '健康', 9);
INSERT INTO `t_smalltype` VALUES (35, '鞋服配饰', '鞋服配饰', 9);
INSERT INTO `t_smalltype` VALUES (36, '床品家居', '床品家居', 9);
INSERT INTO `t_smalltype` VALUES (37, '礼品周边', '礼品周边', 9);
INSERT INTO `t_smalltype` VALUES (38, '儿童用品', '儿童用品', 10);
INSERT INTO `t_smalltype` VALUES (39, 'Redmi K系列', 'K系列', 1);
INSERT INTO `t_smalltype` VALUES (40, 'Redmi Note系列', 'Note系列', 1);
INSERT INTO `t_smalltype` VALUES (41, '游戏手机', '游戏手机', 1);
INSERT INTO `t_smalltype` VALUES (44, '22', '22', 3);
INSERT INTO `t_smalltype` VALUES (45, '是', '3 是', 2);
INSERT INTO `t_smalltype` VALUES (46, 'ewew', 'ew', 3);
INSERT INTO `t_smalltype` VALUES (47, 'rrr', 'rrr', 2);
INSERT INTO `t_smalltype` VALUES (48, '43', '4343', 2);
INSERT INTO `t_smalltype` VALUES (49, '5', '55', 4);
INSERT INTO `t_smalltype` VALUES (50, '12', '22', 7);

-- ----------------------------
-- Table structure for t_wxuserinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_wxuserinfo`;
CREATE TABLE `t_wxuserinfo`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `openid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户唯一标识',
  `nickName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatarUrl` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像图片的 URL',
  `registerDate` datetime NULL DEFAULT NULL COMMENT '注册日期',
  `lastLoginDate` datetime NULL DEFAULT NULL COMMENT '最后登录日期',
  `sex` int(11) NULL DEFAULT 0 COMMENT '1:女，2:男  0:不明',
  `tags` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isshow` int(11) NOT NULL DEFAULT 0 COMMENT '0:不公开 1:公开',
  `age` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `openid`(`openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_wxuserinfo
-- ----------------------------
INSERT INTO `t_wxuserinfo` VALUES (5, 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '我是小峰Jvav大佬', '/image/product/20220929101806000000898.jpg', '2022-01-08 22:19:11', '2022-02-21 08:20:09', 0, '#魔法 ,#知识,#命运', 1, NULL);
INSERT INTO `t_wxuserinfo` VALUES (6, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '             冷鸟s', '/image/product/20221001104059000000289.jpg', '2022-09-23 21:33:08', '2022-10-01 23:13:52', 0, '             #欺诈 ,#愚弄,#诡秘', 1, 17);
INSERT INTO `t_wxuserinfo` VALUES (7, 'sssss', 'Jack杰克', '/image/product/20220930122212000000177.jpg', '2022-09-25 19:34:45', '2022-09-25 19:34:46', 0, '#命运 ,#魔法,#诡秘', 1, 12);
INSERT INTO `t_wxuserinfo` VALUES (8, 'testopen', '张三', '/image/product/20220929101726000000503.jpg', '2022-09-29 17:36:22', '2022-09-29 17:36:24', 0, '#垃圾老,#逃不过命运', 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
