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

 Date: 10/11/2022 22:51:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attention
-- ----------------------------
DROP TABLE IF EXISTS `attention`;
CREATE TABLE `attention`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `opposite_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `attention_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `employee_id123213`(`opposite_id`) USING BTREE,
  INDEX `user_id12323`(`user_id`) USING BTREE,
  CONSTRAINT `employee_id123213` FOREIGN KEY (`opposite_id`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_id12323` FOREIGN KEY (`user_id`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attention
-- ----------------------------
INSERT INTO `attention` VALUES (1, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'sssss', '2022-11-10 21:54:41');
INSERT INTO `attention` VALUES (3, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '2022-11-10 21:54:41');

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_bigtype
-- ----------------------------
INSERT INTO `t_bigtype` VALUES (1, '连麦', '连麦', '20221022045741000000756.jpg');
INSERT INTO `t_bigtype` VALUES (2, '游戏', '游戏', '20221011103547000000806.jpg');
INSERT INTO `t_bigtype` VALUES (3, '聊天', '聊天', '20221011103900000000961.jpg');
INSERT INTO `t_bigtype` VALUES (4, '娱乐', 'LoL', '20221011103559000000983.jpg');
INSERT INTO `t_bigtype` VALUES (44, '其他', '其他', '20221011103722000000103.jpg');

-- ----------------------------
-- Table structure for t_conplain
-- ----------------------------
DROP TABLE IF EXISTS `t_conplain`;
CREATE TABLE `t_conplain`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `openId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `result_msg` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `conplain_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `conplain_type` int(11) NOT NULL COMMENT '投诉类型',
  `publish_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `openid_conplain`(`openId`) USING BTREE,
  INDEX `conplain_type111`(`conplain_type`) USING BTREE,
  INDEX `publish_id222`(`publish_id`) USING BTREE,
  CONSTRAINT `conplain_type111` FOREIGN KEY (`conplain_type`) REFERENCES `t_conplain_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `openid_conplain` FOREIGN KEY (`openId`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `publish_id222` FOREIGN KEY (`publish_id`) REFERENCES `t_publish` (`pid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_conplain
-- ----------------------------
INSERT INTO `t_conplain` VALUES (1, 'guoud', 'ohWWn5CNE3xxfWj72e_5-043_qsk', NULL, '2022-11-02 10:07:59', 5, 50);

-- ----------------------------
-- Table structure for t_conplain_type
-- ----------------------------
DROP TABLE IF EXISTS `t_conplain_type`;
CREATE TABLE `t_conplain_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_conplain_type
-- ----------------------------
INSERT INTO `t_conplain_type` VALUES (1, '涉嫌色情');
INSERT INTO `t_conplain_type` VALUES (2, '涉嫌赌博');
INSERT INTO `t_conplain_type` VALUES (3, '利诱（含返利/刷单/抽奖等）');
INSERT INTO `t_conplain_type` VALUES (4, '冒充他人诈骗');
INSERT INTO `t_conplain_type` VALUES (5, '其它');

-- ----------------------------
-- Table structure for t_extra_payitem
-- ----------------------------
DROP TABLE IF EXISTS `t_extra_payitem`;
CREATE TABLE `t_extra_payitem`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payitem_id` int(11) NOT NULL COMMENT '支付选项id',
  `employee_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `employee_id`(`employee_id`) USING BTREE,
  INDEX `payitem_id22`(`payitem_id`) USING BTREE,
  CONSTRAINT `employee_id` FOREIGN KEY (`employee_id`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `payitem_id22` FOREIGN KEY (`payitem_id`) REFERENCES `t_payitem` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_extra_payitem
-- ----------------------------
INSERT INTO `t_extra_payitem` VALUES (3, 2, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');
INSERT INTO `t_extra_payitem` VALUES (4, 3, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');

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
  `isRead` int(11) NOT NULL DEFAULT 0 COMMENT '0:未读    1:已读',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ms_id22`(`ms_id`) USING BTREE,
  CONSTRAINT `ms_id_mesage` FOREIGN KEY (`ms_id`) REFERENCES `t_msgsession` (`ms_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_message
-- ----------------------------
INSERT INTO `t_message` VALUES (2, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 'hello', '2022-09-25 14:11:49', 1, 0);
INSERT INTO `t_message` VALUES (3, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'hello', '2022-09-25 14:31:34', 1, 0);
INSERT INTO `t_message` VALUES (4, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'hello', '2022-09-25 14:32:53', 1, 0);
INSERT INTO `t_message` VALUES (5, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'hello', '2022-09-25 14:37:11', 1, 0);
INSERT INTO `t_message` VALUES (6, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '333', '2022-09-25 14:52:24', 1, 0);
INSERT INTO `t_message` VALUES (7, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'on yes', '2022-09-25 14:52:45', 1, 0);
INSERT INTO `t_message` VALUES (8, 'sssss', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '44421232111111111111111111111111111111111111111111sadsafsafas', '2022-09-25 14:54:55', 1, 0);
INSERT INTO `t_message` VALUES (9, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5fWj72e_5-043_qsk', '111', '2022-09-25 14:55:15', 1, 0);
INSERT INTO `t_message` VALUES (10, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWsk', '111', '2022-09-25 14:55:24', 1, 0);
INSERT INTO `t_message` VALUES (11, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '1111', '2022-09-25 14:55:30', 1, 0);
INSERT INTO `t_message` VALUES (12, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'hello', '2022-09-25 14:57:29', 1, 0);
INSERT INTO `t_message` VALUES (13, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '111', '2022-09-25 14:58:20', 1, 0);
INSERT INTO `t_message` VALUES (14, 'sssss', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '11122', '2022-09-25 14:58:24', 1, 0);
INSERT INTO `t_message` VALUES (15, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '111223123', '2022-09-25 14:58:29', 1, 0);
INSERT INTO `t_message` VALUES (16, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '111223123123', '2022-09-25 14:58:34', 1, 0);
INSERT INTO `t_message` VALUES (17, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '11111ohWWn5CNE3xxfWj72e_5-043_qsk', '2222', '2022-09-25 14:58:48', 1, 0);
INSERT INTO `t_message` VALUES (18, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '11111ohWWn5CNE3xxfWj72e_5-043_qsk', '222222', '2022-09-25 14:58:51', 1, 0);
INSERT INTO `t_message` VALUES (19, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '11111ohWWn5CNE3xxfWj72e_5-043_qsk', '222222', '2022-09-25 15:01:14', 1, 0);
INSERT INTO `t_message` VALUES (20, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '11', '2022-09-25 15:01:26', 1, 0);
INSERT INTO `t_message` VALUES (21, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '1122', '2022-09-25 15:01:28', 1, 0);
INSERT INTO `t_message` VALUES (22, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '22', '2022-09-25 15:06:32', 1, 0);
INSERT INTO `t_message` VALUES (23, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '', '2022-09-25 15:55:46', 1, 0);
INSERT INTO `t_message` VALUES (24, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '22222', '2022-09-25 20:17:38', 1, 0);
INSERT INTO `t_message` VALUES (25, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '????', '2022-09-25 20:18:18', 1, 0);
INSERT INTO `t_message` VALUES (26, 'sssss', 'hello122222222222222222222222222222223sdfsadsadsfdsfdsfds', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '2022-09-25 20:20:35', 1, 0);
INSERT INTO `t_message` VALUES (27, 'sssss', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'hello Mr.Justice', '2022-09-25 20:21:41', 1, 0);
INSERT INTO `t_message` VALUES (28, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '11', '2022-09-25 20:34:50', 1, 0);
INSERT INTO `t_message` VALUES (29, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '11', '2022-09-25 20:41:31', 1, 0);
INSERT INTO `t_message` VALUES (30, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'asdsadddddddddddddddddddddddadsadsadsadsadsadasdas', '2022-09-25 20:59:15', 1, 0);
INSERT INTO `t_message` VALUES (31, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '11111111111111111111111111111111111111111sadsadsadsadsaaaaaaaaaaaaaaaaaa', '2022-09-25 21:02:24', 1, 0);
INSERT INTO `t_message` VALUES (45, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '11asda', '2022-09-25 21:29:06', 1, 0);
INSERT INTO `t_message` VALUES (46, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'myu12551', '2022-09-25 21:33:44', 1, 0);
INSERT INTO `t_message` VALUES (47, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '对象', '2022-09-26 14:23:28', 1, 0);
INSERT INTO `t_message` VALUES (48, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '是的', '2022-09-26 21:48:53', 1, 0);
INSERT INTO `t_message` VALUES (49, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'sure是的！！！！', '2022-09-26 21:49:06', 1, 0);
INSERT INTO `t_message` VALUES (50, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'halma卡', '2022-09-27 13:12:53', 1, 0);
INSERT INTO `t_message` VALUES (51, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '2dsdas', '2022-09-27 18:52:14', 1, 0);
INSERT INTO `t_message` VALUES (52, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '聊天', '2022-09-27 18:58:49', 1, 0);
INSERT INTO `t_message` VALUES (53, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '2222', '2022-09-27 19:03:01', 1, 0);
INSERT INTO `t_message` VALUES (54, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '刘备', '2022-09-27 19:13:34', 1, 0);
INSERT INTO `t_message` VALUES (55, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '刘强', '2022-09-27 21:10:41', 1, 0);
INSERT INTO `t_message` VALUES (56, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'yes', '2022-09-28 09:55:47', 1, 0);
INSERT INTO `t_message` VALUES (57, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 'youaha', '2022-09-28 09:59:56', 1, 0);
INSERT INTO `t_message` VALUES (58, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '545啊空间', '2022-09-28 13:29:45', 10, 0);
INSERT INTO `t_message` VALUES (59, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', 'yousa挺好', '2022-09-28 13:30:23', 10, 0);
INSERT INTO `t_message` VALUES (60, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'hhlle', '2022-10-02 17:28:04', 1, 0);
INSERT INTO `t_message` VALUES (61, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'hello', '2022-10-08 16:26:41', 1, 0);
INSERT INTO `t_message` VALUES (62, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', 'shide', '2022-10-08 16:26:52', 1, 0);
INSERT INTO `t_message` VALUES (63, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '1111111111111akdsjhsakdbksajdbksadbksajdbjsakbdjasjdksajndjksandksadnas', '2022-10-08 16:38:13', 1, 0);
INSERT INTO `t_message` VALUES (64, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '22', '2022-10-10 22:09:15', 1, 0);
INSERT INTO `t_message` VALUES (65, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '33', '2022-10-10 22:09:25', 10, 0);
INSERT INTO `t_message` VALUES (66, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '332', '2022-10-10 22:09:30', 10, 0);
INSERT INTO `t_message` VALUES (67, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '332', '2022-10-10 22:09:31', 10, 0);
INSERT INTO `t_message` VALUES (68, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '11', '2022-10-10 22:09:54', 10, 0);
INSERT INTO `t_message` VALUES (69, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '113', '2022-10-10 22:10:08', 10, 0);
INSERT INTO `t_message` VALUES (70, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '11', '2022-10-10 22:11:07', 10, 0);
INSERT INTO `t_message` VALUES (71, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '1133', '2022-10-10 22:11:15', 10, 0);
INSERT INTO `t_message` VALUES (72, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '1133333', '2022-10-10 22:11:52', 10, 0);
INSERT INTO `t_message` VALUES (73, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '1133333', '2022-10-10 22:11:54', 10, 0);
INSERT INTO `t_message` VALUES (74, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '1133333111', '2022-10-10 22:11:56', 10, 0);
INSERT INTO `t_message` VALUES (75, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '113333311133', '2022-10-10 22:11:58', 10, 0);
INSERT INTO `t_message` VALUES (76, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '222', '2022-10-22 17:43:54', 11, 0);
INSERT INTO `t_message` VALUES (77, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '22', '2022-10-22 17:53:07', 11, 0);
INSERT INTO `t_message` VALUES (78, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '2233', '2022-10-22 17:53:08', 11, 0);
INSERT INTO `t_message` VALUES (79, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '2233', '2022-10-22 17:53:09', 11, 0);
INSERT INTO `t_message` VALUES (80, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '2233333', '2022-10-22 17:53:11', 11, 0);
INSERT INTO `t_message` VALUES (81, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '223333', '2022-10-22 17:53:12', 11, 0);
INSERT INTO `t_message` VALUES (82, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '223333', '2022-10-22 17:53:13', 11, 0);
INSERT INTO `t_message` VALUES (83, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '2233', '2022-10-22 17:53:14', 11, 0);
INSERT INTO `t_message` VALUES (84, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '22', '2022-10-22 17:53:15', 11, 0);
INSERT INTO `t_message` VALUES (85, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '688', '2022-10-22 17:53:33', 11, 0);
INSERT INTO `t_message` VALUES (86, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '688666', '2022-10-22 17:53:34', 11, 0);
INSERT INTO `t_message` VALUES (87, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '11132', '2022-11-03 15:44:59', 11, 0);
INSERT INTO `t_message` VALUES (88, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '12123', '2022-11-03 15:45:02', 11, 0);
INSERT INTO `t_message` VALUES (89, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '213213', '2022-11-03 15:45:05', 11, 0);
INSERT INTO `t_message` VALUES (90, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '123213532', '2022-11-03 15:45:08', 11, 0);
INSERT INTO `t_message` VALUES (91, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'asdasd123', '2022-11-03 15:46:04', 11, 0);
INSERT INTO `t_message` VALUES (92, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'ss', '2022-11-03 15:46:07', 11, 0);
INSERT INTO `t_message` VALUES (93, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'hello', '2022-11-04 14:08:57', 17, 0);
INSERT INTO `t_message` VALUES (94, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'o5fzY4tawihCUzJhDivPkt6LvAUU', 'hello', '2022-11-04 22:56:19', 18, 0);
INSERT INTO `t_message` VALUES (95, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'o5fzY4tawihCUzJhDivPkt6LvAUU', '加微信zhy2658', '2022-11-04 22:56:38', 18, 1);
INSERT INTO `t_message` VALUES (97, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '1', '2022-11-07 16:36:16', 11, 1);

-- ----------------------------
-- Table structure for t_msgsession
-- ----------------------------
DROP TABLE IF EXISTS `t_msgsession`;
CREATE TABLE `t_msgsession`  (
  `ms_id` int(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `openId2` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ms_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isShow` int(11) NOT NULL DEFAULT 1 COMMENT '0:不显示   1：显示',
  PRIMARY KEY (`ms_id`) USING BTREE,
  INDEX `openid2_ms`(`openId2`) USING BTREE,
  INDEX `openId_ms`(`openId`) USING BTREE,
  CONSTRAINT `openId_ms` FOREIGN KEY (`openId`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `openid2_ms` FOREIGN KEY (`openId2`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_msgsession
-- ----------------------------
INSERT INTO `t_msgsession` VALUES (1, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'sssss', '2022-09-27 12:34:35', 1);
INSERT INTO `t_msgsession` VALUES (10, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '2022-09-28 09:59:55', 1);
INSERT INTO `t_msgsession` VALUES (11, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '2022-10-22 17:43:53', 1);
INSERT INTO `t_msgsession` VALUES (17, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '2022-11-04 14:08:57', 1);
INSERT INTO `t_msgsession` VALUES (18, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'o5fzY4tawihCUzJhDivPkt6LvAUU', '2022-11-04 22:56:19', 1);

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `message` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `n_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `top` int(11) NOT NULL DEFAULT 0 COMMENT '0：置顶    1:不置顶',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES (1, '节日通知', '节日通知节日节日通知节日通知节日通知节日通知节日通知节日通知', '2022-10-10 10:13:26', 0);
INSERT INTO `t_notice` VALUES (2, '新年通知1231', '新年通知新年通知新年通知新年通知新年通知新年通知新年通知罪行2222', '2022-10-10 17:13:26', 0);
INSERT INTO `t_notice` VALUES (5, '今日天气', '中国天气网讯 周末两天（10月22日至23日），广东大部地区晴天为主，气温回升，并且空气比较干燥，森林火险等级高，公众居家和外出时需注意防范火灾发生。\n', '2022-10-22 16:57:04', -1);

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `userId` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'openId   微信购买用户ID',
  `totalPrice` decimal(8, 2) NULL DEFAULT NULL COMMENT '总价',
  `address` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `consignee` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信号',
  `telNumber` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `createDate` datetime NULL DEFAULT NULL COMMENT '订单创建日期',
  `payDate` datetime NULL DEFAULT NULL COMMENT '订单支付日期',
  `status` int(11) NULL DEFAULT 0 COMMENT '0 未支付 1等待员工接单 2 正在服务  3完成服务，待确认，4完成订单  5请求退单 6：已退单 7：随机派单中  8：删除',
  `pm_id` int(11) NULL DEFAULT NULL COMMENT '支付选项id',
  `servant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务人',
  `order_count` int(11) NOT NULL DEFAULT 1 COMMENT '第几次下同样的单',
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '0:指定单，  1:随机单',
  `random_sex` int(11) NULL DEFAULT -1 COMMENT '性别可以接的随机单   1：女    2：男',
  `random_grade` int(11) NULL DEFAULT 1 COMMENT '至少要几级员工才可以接这个单子',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `orderNo`(`orderNo`) USING BTREE,
  INDEX `pm_id`(`pm_id`) USING BTREE,
  INDEX `savant_id`(`servant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 233 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (228, 'LY20221110061934000000337', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 20.00, NULL, '', '', '2022-11-10 18:19:34', '2022-11-10 18:19:34', 2, 3, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 1, 0, -1, 1);
INSERT INTO `t_order` VALUES (229, 'LY20221110062009000000725', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 22.00, NULL, '', '', '2022-11-10 18:20:10', '2022-11-10 18:20:10', 1, 22, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 2, 0, -1, 1);
INSERT INTO `t_order` VALUES (230, 'LY20221110062218000000167', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 60.00, NULL, NULL, NULL, '2022-11-10 18:22:18', '2022-11-10 18:22:18', 1, 3, NULL, 1, 1, 1, 1);
INSERT INTO `t_order` VALUES (231, 'LY20221110062303000000968', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 22.00, NULL, '', '', '2022-11-10 18:23:04', '2022-11-10 18:23:04', 1, 22, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 3, 0, -1, 1);
INSERT INTO `t_order` VALUES (232, 'LY20221110062347000000572', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 100.00, NULL, NULL, NULL, '2022-11-10 18:23:48', '2022-11-10 18:23:48', 1, 0, NULL, 1, 1, 1, 2);

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
  `serviceStart` datetime NULL DEFAULT NULL,
  `serviceEnd` datetime NULL DEFAULT NULL,
  `totalHours` int(11) NULL DEFAULT NULL COMMENT '总小时数',
  `itemHours` int(11) NULL DEFAULT NULL COMMENT '单小时数',
  `totalPrice` float NULL DEFAULT NULL COMMENT '总价',
  `servant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `mId`(`mId`) USING BTREE,
  CONSTRAINT `t_order_detail_ibfk_1` FOREIGN KEY (`mId`) REFERENCES `t_order` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 252 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_order_detail
-- ----------------------------
INSERT INTO `t_order_detail` VALUES (247, 228, NULL, 1, 20.00, 'LOL', '/image/product/20221106105658000000870.jpg', '2022-11-10 18:23:30', '2022-11-10 21:23:30', 3, 3, 20, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');
INSERT INTO `t_order_detail` VALUES (248, 229, NULL, 1, 22.00, '唱歌', '/image/product/20221106105658000000870.jpg', NULL, NULL, 2, 2, 22, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');
INSERT INTO `t_order_detail` VALUES (249, 230, NULL, 3, 20.00, 'LOL', NULL, NULL, NULL, 9, 3, 60, NULL);
INSERT INTO `t_order_detail` VALUES (250, 231, NULL, 1, 22.00, '唱歌', '/image/product/20221106105658000000870.jpg', NULL, NULL, 2, 2, 22, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');
INSERT INTO `t_order_detail` VALUES (251, 232, NULL, 1, 100.00, '语音', NULL, NULL, NULL, 10, 10, 100, NULL);

-- ----------------------------
-- Table structure for t_payitem
-- ----------------------------
DROP TABLE IF EXISTS `t_payitem`;
CREATE TABLE `t_payitem`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` float NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `itemName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `itemHours` int(10) NOT NULL COMMENT '小时数',
  `grade` int(11) NULL DEFAULT NULL COMMENT '1级雇员  ，2:2级雇员   ...以此类推',
  `required` int(11) NOT NULL DEFAULT 0 COMMENT '0:必选    1:可选',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_payitem
-- ----------------------------
INSERT INTO `t_payitem` VALUES (1, 10, '2022-10-23 22:50:51', '体验聊天', 1, 1, 0);
INSERT INTO `t_payitem` VALUES (2, 10, '2022-11-10 15:51:45', '游戏', 5, 1, 1);
INSERT INTO `t_payitem` VALUES (3, 20, '2022-11-10 15:52:08', 'LOL', 3, 1, 1);
INSERT INTO `t_payitem` VALUES (21, 20, '2022-10-24 14:50:51', '语音', 10, 1, 0);
INSERT INTO `t_payitem` VALUES (22, 22, '2022-10-24 14:54:43', '唱歌', 2, 1, 0);
INSERT INTO `t_payitem` VALUES (23, 49, '2022-10-24 06:55:08', '跳舞', 10, 1, 0);
INSERT INTO `t_payitem` VALUES (24, 100, '2022-10-23 22:56:04', '语音', 10, 2, 0);
INSERT INTO `t_payitem` VALUES (25, 200, '2022-10-23 22:56:32', '唱歌', 2, 2, 0);
INSERT INTO `t_payitem` VALUES (28, 50, '2022-11-02 14:12:20', '喝酒', 2, 2, 0);

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
  `audioTime` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `openId`(`openId`) USING BTREE,
  INDEX `typeId`(`typeId`) USING BTREE,
  CONSTRAINT `openId_product` FOREIGN KEY (`openId`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_product_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `t_smalltype` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES (9, '杰克Jack', 2999.00, 1111, 'default.jpg', b'1', b'0', '20221011103945000000703.jpg', 0, NULL, '2021-12-28 21:36:34', '<h2>天气冷了，要穿衣服呢</h2>', '<p>This is paramThis is paramThis is param</p><p>This is param</p><p>This is param</p><p>This is param</p><p>This is param</p><p>This is paramThis is param</p>', '最好的服务', 'sssss', '/uploads/audios/testaudio.mp3', 22);
INSERT INTO `t_product` VALUES (10, '冷鸟', 1399.00, 2222, '20220929081244000000369.jpg', b'1', b'1', '20221101113108000000384.jpg', -1, NULL, '2021-11-28 21:36:34', '<p>&nbsp;隔壁室友的朋友终于回去了,拥挤的两室一厅少了一个人还是一样的拥挤,听室友说,那个女孩子可能还会回上海,这次她回家好象去征求父母的意见,天哪!如果是真的,那我会疯掉的,原本像家的合住,现在看上去没有一点温暖,拜托假设不要实现,不然,我会重新点燃搬家的火苗.</p><p><br></p><p>&nbsp;&nbsp;老大回来了,本想说很多话的我,似乎没有和想象的一样.上班时间大家还是老样子.只是回到家\"捧\"着电视就不理电脑.呵!好象没有之前想的那样感情处的那么好.......</p><p><br></p><p>&nbsp;&nbsp;家里已经3天没有做饭了.不甘心只有我一个人在厨房折腾,是自私还是计较?不想多分辨.只是心里的平衡点让自己为自己讨公道.现在的大家,没有以前那样一起忙里忙外一起分担,是我计较了还是大家变了?</p><p style=\"text-align: center;\"><br></p>', '<p>23123lo 我是冷鸟</p>', '强大神秘', 'ohWWn5CNE3xxfWj72e_5-043_qsk', '/uploads/audios/20221008012216000000422.mp3', 3);
INSERT INTO `t_product` VALUES (11, '黑色', 2499.00, 3322, '7.png', b'1', b'0', '20221022085808000000025.jpg', 0, NULL, '2021-11-28 21:36:34', '<p> </p><p>\"</p>', NULL, 'yousa', 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '/uploads/audios/testaudio3.mp3', 44);
INSERT INTO `t_product` VALUES (12, NULL, NULL, NULL, 'default.jpg', b'1', b'1', '20221101113034000000553.jpg', 0, NULL, '2022-10-22 17:15:13', '<p>hhhhh怎么会不难过呢，一点也不开心啊，尽管毫无道理，但是，太难过了，好难过。还没等到你的生日啊说好了一起做饭的吧说好了一起跑步的对不对说好了做菜给我吃的对吧我也有答应学做饭的我们约好了要去看电影的你说带我去荡秋千的你说户口本就在身边，随时都可以用的你说得话我都记得的明明当初是你先说喜欢，先开始，先上头的我只是，慢了一点<strong>.</strong></p>', NULL, '要快乐鸭', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', NULL, 0);
INSERT INTO `t_product` VALUES (13, NULL, NULL, NULL, 'default.jpg', b'0', b'0', 'default.jpg', 0, NULL, NULL, NULL, NULL, NULL, 'o5fzY4p9Fo5B3MwnnbsZWpC1FMA4', NULL, 0);
INSERT INTO `t_product` VALUES (14, NULL, NULL, NULL, 'default.jpg', b'0', b'0', 'default.jpg', 0, NULL, NULL, NULL, NULL, NULL, 'o5fzY4jngjpThhytLGqpMk7lq73o', NULL, 0);
INSERT INTO `t_product` VALUES (15, NULL, NULL, NULL, 'default.jpg', b'0', b'0', 'default.jpg', 0, NULL, NULL, NULL, NULL, NULL, 'o5fzY4hooYpm0pEEyfJQvwdFHef8', NULL, 0);
INSERT INTO `t_product` VALUES (16, NULL, NULL, NULL, 'default.jpg', b'0', b'0', 'default.jpg', 0, NULL, NULL, NULL, NULL, NULL, 'o5fzY4mx1IOFvHEfy70OMnikVrdE', NULL, 0);
INSERT INTO `t_product` VALUES (17, NULL, NULL, NULL, 'default.jpg', b'0', b'0', 'default.jpg', 0, NULL, NULL, NULL, NULL, NULL, 'o5fzY4tawihCUzJhDivPkt6LvAUU', NULL, 0);
INSERT INTO `t_product` VALUES (18, NULL, NULL, NULL, 'default.jpg', b'0', b'0', 'default.jpg', 0, NULL, NULL, NULL, NULL, NULL, 'o5fzY4jxeP2_Th0r0hpAa_ht-fUE', NULL, 0);
INSERT INTO `t_product` VALUES (19, '22', NULL, NULL, 'default.jpg', b'0', b'0', 'default.jpg', 0, NULL, NULL, NULL, NULL, NULL, 'o5fzY4tF2wY3OWRscEWf8LssBAi0', NULL, 0);
INSERT INTO `t_product` VALUES (22, 'Yousa', 1199.00, 1111, 'default.jpg', b'1', b'0', 'default.jpg', 0, NULL, '2022-10-09 21:43:09', '<p>欢迎下单</p>', NULL, '欢迎下单', 'testopen', NULL, 50);
INSERT INTO `t_product` VALUES (23, NULL, NULL, NULL, 'default.jpg', b'0', b'0', 'default.jpg', 0, NULL, NULL, NULL, NULL, '爱吃鸡排', 'o5fzY4tkTpwTcsbdKBupOz2BM8U0', NULL, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 182 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `t_product_swiper_image` VALUES (13, '222.jpg', 2, 16);
INSERT INTO `t_product_swiper_image` VALUES (14, '333.jpg', 3, 16);
INSERT INTO `t_product_swiper_image` VALUES (138, '20221101113139000000898.jpg', 0, 10);
INSERT INTO `t_product_swiper_image` VALUES (139, '20221101113149000000355.jpg', 0, 10);
INSERT INTO `t_product_swiper_image` VALUES (180, '20221101112947000000748.jpg', NULL, 12);
INSERT INTO `t_product_swiper_image` VALUES (181, '20221106101104000000612.jpg', 0, 12);

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
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0:待审核  1:审核通过  2：审核失败   3:删除',
  `openId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`pid`) USING BTREE,
  INDEX `openid_publish`(`openId`) USING BTREE,
  CONSTRAINT `openid_publish` FOREIGN KEY (`openId`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_publish
-- ----------------------------
INSERT INTO `t_publish` VALUES (31, 'hello world', '你好,啊就是打算佛南萨非宁你好,啊就是打算佛南萨非宁静静', '湖北恩施', 0, '2022-09-28 21:40:39', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (32, 'hello world', '你好,啊就是打算佛南萨非你好,啊就是打算佛南萨非宁静宁静', '湖北恩施', 0, '2022-09-28 21:44:20', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (33, 'hello world', '你好,啊就是打算佛南萨你好,啊就是打算佛南萨非宁静非宁静', '湖北恩施', 0, '2022-09-28 21:44:45', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (34, 'hello world已过', '你好,啊就是打算佛南萨非宁静', '湖北恩施', 0, '2022-09-28 21:48:20', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (35, 'hello world也已过', '你好,啊就是打算佛南萨非宁静', '湖北恩施', 0, '2022-09-28 21:51:17', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (36, 'helo worldhello wworld', '你好,啊就是打算佛南萨非宁静', NULL, 0, '2022-09-28 21:51:55', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (37, '我是标题', '我是内容你好,啊就是打算佛南萨非宁静你好,啊就是打算佛南萨非宁静你好,啊就是打算佛南萨非宁静', NULL, 0, '2022-09-28 21:54:50', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (38, '我是标题啊啊啊', '我是内容', NULL, 0, '2022-09-28 21:55:40', 0, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (39, '我是标题', '我是内容', NULL, 0, '2022-09-28 21:57:38', 0, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (40, '2222asdas', '111asdas', NULL, 0, '2022-09-30 18:10:54', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (41, '我是白头标题', '内容内容我试试是', NULL, 0, '2022-09-30 18:19:25', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (42, '人物介绍标题', '我是内容内容呢飒飒大大', NULL, 1, '2022-09-30 18:21:11', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (43, '标题标题呢标题', '内容内容asdsadas\n\nasdsa\nd\nsa\ndsa\nd\nas', NULL, 1, '2022-09-30 18:23:30', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (44, '抬棺告示', '从2022年10月1号开始(国庆节)\n\n我们专业团队发布超级优惠服务：\n第二棺半价！！！ \n第二棺半价！！！', NULL, 1, '2022-09-30 18:30:51', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (45, 'test图片', '图片测试测试多张', NULL, 1, '2022-09-30 20:02:06', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (46, '图片测试2', '图片测试\n内容\n内日1', NULL, 1, '2022-09-30 20:04:18', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (47, 'headggg', 'hhhcfgc', NULL, 1, '2022-10-02 11:17:10', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (48, 'head2222edd', ' gdfgdfgfgfggf', NULL, 1, '2022-10-02 11:19:12', 2, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (49, '表情包”我冲死了“的背后，是一部催人泪下的短篇漫画', '对不起，我哭死了。现如今，表情包已经是我们上网冲浪的必备良品。表情包不仅能让聊天气氛变得更加轻松，还能以一种更加诙谐的方式，说出部分人平时不善于直接表达的话语。它可能来源于照片、影视剧、动画或漫画，样式千奇百怪，有时候仅仅只需要改编表情包里面的文字，就能赋予它不一样的含义。当然，更多时候，这种改编往往都和表情包最一开始表达的内容，相距甚远。\r\n\r\n像是如果你流连于涩图群，寻觅人性的真谛的话，经常就能看到这么一个表情包——“对不起，我冲死了”。\r\n\r\n表情包“我冲死了”的背后，是一部催人泪下的短篇漫画\r\n如果你穿梭于开黑群，和网友们酣畅淋漓地连跪九把游戏后，往往还能看到这么一个表情——“对不起，我菜死了”。\r\n\r\n表情包“我冲死了”的背后，是一部催人泪下的短篇漫画\r\n如果你徘徊在手游群，见识过各类欧皇的狗叫和非酋的命途多舛后，可能还能看到这样的一幕——“对不起，我非死了”。\r\n\r\n表情包“我冲死了”的背后，是一部催人泪下的短篇漫画\r\n除此之外，它还有更加多元化的版本，只需要稍稍涂掉几个字，它就能很快地融入各类圈子之中——适用范围之广，令人有些出乎意料。\r\n\r\n这个表情包，出自画师Mr.A的二创作品《贫民窟的晓》。虽然里面的人物都出自游戏《舰队Collection》，但实际上和游戏关联度并不高，属于典型的OOC同人漫画。因此，读者完全可以把它当作一部独立短篇漫画来看。\r\n\r\n表情包“我冲死了”的背后，是一部催人泪下的短篇漫画\r\n《贫民窟的晓》描述的是一群儿童，在底层中不断受到欺压、霸凌、剥削后，互相抱团取暖，在黑暗的环境中一同生活下去的故事。故事整体偏黑暗向，像是“好不容易赚了钱，却立马给人抢走”“被坏蛋绑去工厂做苦力”之类的剧情，层出不穷。', NULL, 0, '2022-10-02 22:07:38', 1, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish` VALUES (50, '越成功、越伟大，就越该谦恭行事', '“骄则满，满则溢。”在人生低谷时，我们往往小心翼翼，如履薄冰，自然不会有傲慢的情绪，反而是功成名就之时，很容易让人心绪不稳。但其实越成功、越伟大，就越该谦恭行事。\r\n\r\n', NULL, 1, '2022-11-01 23:23:54', 1, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');
INSERT INTO `t_publish` VALUES (51, '常见咖啡图', '我们经常会听到美式咖啡、拿铁、摩卡、卡布奇诺，这些咖啡有什么区别大家都了解吗?', NULL, 1, '2022-11-03 23:32:20', 1, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');
INSERT INTO `t_publish` VALUES (52, '4', '444', NULL, NULL, '2022-11-06 10:04:07', 0, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');
INSERT INTO `t_publish` VALUES (53, '常见咖啡图', '我们经常会听到美式咖啡、拿铁、摩卡、卡布奇诺，这些咖啡有什么区别大家都了解吗?', NULL, 1, '2022-11-06 19:45:18', 0, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');
INSERT INTO `t_publish` VALUES (54, '2123', 'adsadsad', NULL, NULL, '2022-11-06 21:47:51', 0, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');

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
) ENGINE = InnoDB AUTO_INCREMENT = 94 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `t_publish_image` VALUES (78, '/image/productIntroImgs/20221002111709000000312.jpg', 0, 47);
INSERT INTO `t_publish_image` VALUES (79, '/image/productIntroImgs/20221002111709000000312.jpg', 1, 47);
INSERT INTO `t_publish_image` VALUES (80, '/image/productIntroImgs/20221002111911000000705.jpg', 0, 48);
INSERT INTO `t_publish_image` VALUES (81, '/image/productIntroImgs/20221002111911000000712.jpg', 1, 49);
INSERT INTO `t_publish_image` VALUES (82, '/image/productIntroImgs/20221101112354000000351.jpg', 0, 50);
INSERT INTO `t_publish_image` VALUES (83, '/image/productIntroImgs/20221103113220000000572.jpg', 0, 51);
INSERT INTO `t_publish_image` VALUES (88, '/image/productIntroImgs/20221106092940000000712.jpg', 0, 52);
INSERT INTO `t_publish_image` VALUES (89, '/image/productIntroImgs/20221106093453000000881.jpg', 0, 52);
INSERT INTO `t_publish_image` VALUES (93, '/image/productIntroImgs/20221106094801000000703.jpg', 0, 54);

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
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_publish_like
-- ----------------------------
INSERT INTO `t_publish_like` VALUES (1, '2022-09-30 11:57:02', 35, 'sssss');
INSERT INTO `t_publish_like` VALUES (3, '2022-09-30 11:57:02', 34, 'sssss');
INSERT INTO `t_publish_like` VALUES (23, '2022-10-01 22:16:32', 35, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish_like` VALUES (30, '2022-10-02 21:43:44', 43, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish_like` VALUES (31, '2022-10-02 21:46:19', 46, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish_like` VALUES (37, '2022-10-03 14:40:12', 34, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish_like` VALUES (38, '2022-10-03 14:49:49', 44, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish_like` VALUES (39, '2022-10-03 16:16:20', 48, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish_like` VALUES (41, '2022-10-08 16:51:53', 32, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish_like` VALUES (44, '2022-10-08 17:32:52', 49, 'ohWWn5CNE3xxfWj72e_5-043_qsk');
INSERT INTO `t_publish_like` VALUES (45, '2022-10-23 18:27:47', 44, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');
INSERT INTO `t_publish_like` VALUES (53, '2022-10-23 18:32:14', 46, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');
INSERT INTO `t_publish_like` VALUES (69, '2022-11-04 11:10:40', 51, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');
INSERT INTO `t_publish_like` VALUES (70, '2022-11-04 11:32:40', 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');
INSERT INTO `t_publish_like` VALUES (71, '2022-11-04 19:46:54', 49, 'o5fzY4obICZKbk_RJOtOrzSDSnwc');

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
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `t_publish_reply` VALUES (19, 48, 'ohWWn5CNE3xxfWj72e_5-043_qsk', 'hello', '2022-10-02 11:20:14');
INSERT INTO `t_publish_reply` VALUES (20, 34, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '111', '2022-10-02 21:12:13');
INSERT INTO `t_publish_reply` VALUES (21, 49, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '1111', '2022-10-08 17:32:19');
INSERT INTO `t_publish_reply` VALUES (22, 49, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '22222', '2022-10-08 17:32:29');
INSERT INTO `t_publish_reply` VALUES (23, 49, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '22222', '2022-10-08 17:32:34');
INSERT INTO `t_publish_reply` VALUES (24, 49, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '22222', '2022-10-08 17:32:36');
INSERT INTO `t_publish_reply` VALUES (25, 49, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '22222', '2022-10-08 17:32:39');
INSERT INTO `t_publish_reply` VALUES (26, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '啊对对对！', '2022-11-01 23:34:19');
INSERT INTO `t_publish_reply` VALUES (27, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '11', '2022-11-03 15:37:10');
INSERT INTO `t_publish_reply` VALUES (28, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:40:47');
INSERT INTO `t_publish_reply` VALUES (29, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:40:48');
INSERT INTO `t_publish_reply` VALUES (30, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:40:49');
INSERT INTO `t_publish_reply` VALUES (31, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:40:49');
INSERT INTO `t_publish_reply` VALUES (32, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:41:01');
INSERT INTO `t_publish_reply` VALUES (33, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:41:01');
INSERT INTO `t_publish_reply` VALUES (34, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:41:01');
INSERT INTO `t_publish_reply` VALUES (35, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '1111', '2022-11-03 15:41:03');
INSERT INTO `t_publish_reply` VALUES (36, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:41:43');
INSERT INTO `t_publish_reply` VALUES (37, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:41:45');
INSERT INTO `t_publish_reply` VALUES (38, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:41:46');
INSERT INTO `t_publish_reply` VALUES (39, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:41:46');
INSERT INTO `t_publish_reply` VALUES (40, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '11', '2022-11-03 15:42:54');
INSERT INTO `t_publish_reply` VALUES (41, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:42:56');
INSERT INTO `t_publish_reply` VALUES (42, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111', '2022-11-03 15:43:05');
INSERT INTO `t_publish_reply` VALUES (43, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '111dsfsdf', '2022-11-03 15:43:17');
INSERT INTO `t_publish_reply` VALUES (44, 50, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '12321', '2022-11-03 15:47:02');
INSERT INTO `t_publish_reply` VALUES (45, 51, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', '妙啊', '2022-11-03 23:36:11');

-- ----------------------------
-- Table structure for t_random_order
-- ----------------------------
DROP TABLE IF EXISTS `t_random_order`;
CREATE TABLE `t_random_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sex` int(11) NOT NULL COMMENT '1:女   2:男',
  `grade` int(11) NOT NULL COMMENT '员工等级',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `payItem_id` int(11) NOT NULL COMMENT '服务选项id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `payItem_id`(`payItem_id`) USING BTREE,
  CONSTRAINT `payItem_id` FOREIGN KEY (`payItem_id`) REFERENCES `t_payitem` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_random_order
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_smalltype
-- ----------------------------
INSERT INTO `t_smalltype` VALUES (1, '连麦', '连麦分类1', 1);
INSERT INTO `t_smalltype` VALUES (5, 'LOL', 'LOL', 2);
INSERT INTO `t_smalltype` VALUES (10, '聊天', '聊天分类1', 3);
INSERT INTO `t_smalltype` VALUES (13, '娱乐', '娱乐分类1', 4);
INSERT INTO `t_smalltype` VALUES (51, '其他', '其他', 44);

-- ----------------------------
-- Table structure for t_topup_record
-- ----------------------------
DROP TABLE IF EXISTS `t_topup_record`;
CREATE TABLE `t_topup_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topupNo` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `openId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '充值用户',
  `coinNum` int(11) NOT NULL DEFAULT 0 COMMENT '充值米粒数量',
  `totalPrice` float NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0:未支付   1：支付成功',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `topuopNo`(`topupNo`) USING BTREE,
  INDEX `open_id_toup`(`openId`) USING BTREE,
  CONSTRAINT `open_id_toup` FOREIGN KEY (`openId`) REFERENCES `t_wxuserinfo` (`openid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_topup_record
-- ----------------------------
INSERT INTO `t_topup_record` VALUES (1, 'LYTOPUP20221106015244000000654', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 60, 6, '2022-11-06 13:52:44', 0);
INSERT INTO `t_topup_record` VALUES (2, 'LYTOPUP20221106020750000000989', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 60, 6, '2022-11-06 14:07:51', 0);
INSERT INTO `t_topup_record` VALUES (3, 'LYTOPUP20221106020814000000027', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 60, 6, '2022-11-06 14:08:14', 0);
INSERT INTO `t_topup_record` VALUES (4, 'LYTOPUP20221106020908000000652', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 60, 6, '2022-11-06 14:09:08', 0);
INSERT INTO `t_topup_record` VALUES (5, 'LYTOPUP20221106021138000000292', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 60, 6, '2022-11-06 14:11:38', 0);
INSERT INTO `t_topup_record` VALUES (6, 'LYTOPUP20221106021306000000454', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 60, 6, '2022-11-06 14:13:06', 0);
INSERT INTO `t_topup_record` VALUES (7, 'LYTOPUP20221106022432000000700', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 200, 0.02, '2022-11-06 14:24:32', 1);
INSERT INTO `t_topup_record` VALUES (8, 'LYTOPUP20221106023350000000301', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 200, 0.02, '2022-11-06 14:33:50', 1);
INSERT INTO `t_topup_record` VALUES (9, 'LYTOPUP20221106023620000000145', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 2000, 100, '2022-11-06 14:36:20', 0);
INSERT INTO `t_topup_record` VALUES (10, 'LYTOPUP20221106023755000000224', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 60, 0.01, '2022-11-06 14:37:55', 1);
INSERT INTO `t_topup_record` VALUES (11, 'LYTOPUP20221108073545000000905', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 60, 0.01, '2022-11-08 19:35:45', 0);
INSERT INTO `t_topup_record` VALUES (12, 'LYTOPUP20221108073554000000664', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 60, 0.01, '2022-11-08 19:35:54', 0);
INSERT INTO `t_topup_record` VALUES (13, 'LYTOPUP20221108073619000000499', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 60, 0.01, '2022-11-08 19:36:19', 0);
INSERT INTO `t_topup_record` VALUES (14, 'LYTOPUP20221108073833000000719', 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 60, 0.01, '2022-11-08 19:38:33', 0);

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
  `wxid` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '-',
  `ustatus` int(11) NULL DEFAULT 1 COMMENT '0离线   1:在线  ',
  `small_id` int(11) NULL DEFAULT NULL,
  `admin` int(11) NULL DEFAULT 0 COMMENT '0:普通成员 1：员工',
  `employee_grade` int(11) NULL DEFAULT 1 COMMENT '1级雇员  ，2:2级雇员   ...以此类推',
  `coin` float NOT NULL DEFAULT 0,
  `employee_id` int(11) NULL DEFAULT NULL COMMENT '员工号',
  `password` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外部登录密码',
  `tel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `openid`(`openid`) USING BTREE,
  UNIQUE INDEX `employ_id`(`employee_id`) USING BTREE,
  INDEX `small_id_user`(`small_id`) USING BTREE,
  CONSTRAINT `small_id_user` FOREIGN KEY (`small_id`) REFERENCES `t_smalltype` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_wxuserinfo
-- ----------------------------
INSERT INTO `t_wxuserinfo` VALUES (6, 'ohWWn5CNE3xxfWj72e_5-043_qsk', '猫猫不易', '/image/product/20221101113122000000052.jpg', '2022-09-23 21:33:08', '2022-10-20 22:32:43', 1, '爱吃饭,爱喝水', 1, 15, 'yousa2658', 1, 5, 1, 1, 0, 1006, NULL, NULL);
INSERT INTO `t_wxuserinfo` VALUES (7, 'sssss', 'Rose', '/image/product/20220930122212000000177.jpg', '2022-09-25 19:34:45', '2022-09-25 19:34:46', 2, '命运,魔法,诡秘', 1, 12, '-', 0, 10, 1, 1, 0, 1007, NULL, NULL);
INSERT INTO `t_wxuserinfo` VALUES (8, 'testopen', '张三', '/image/product/20220929101726000000503.jpg', '2022-09-29 17:36:22', '2022-09-29 17:36:24', 0, '垃圾老,逃不过命运', 1, NULL, '-', 0, 13, 1, 1, 0, 1008, NULL, NULL);
INSERT INTO `t_wxuserinfo` VALUES (9, 'o5fzY4obICZKbk_RJOtOrzSDSnwc', 'yosa', '/image/product/20221106105658000000870.jpg', '2022-10-22 14:47:36', '2022-11-10 16:07:34', 1, '干饭,爱喝饮料,游泳健将', 1, 19, 'zhy2658', 1, 51, 1, 1, 4734, 1009, '1234', '17362388926');
INSERT INTO `t_wxuserinfo` VALUES (10, 'o5fzY4p9Fo5B3MwnnbsZWpC1FMA4', '超_越梦', 'https://thirdwx.qlogo.cn/mmopen/vi_32/XAxTcGFpib3p316ak4Pdia0gFic2ykNwibDjpJsHicEyBw8Ahw9iafPMKoN0I7ouBUJibRPRKxgrRNXQT3COaFOjrjPSg/132', '2022-11-04 08:08:00', '2022-11-04 08:08:00', 0, NULL, 0, NULL, '-', 1, NULL, 0, 1, 0, NULL, NULL, NULL);
INSERT INTO `t_wxuserinfo` VALUES (11, 'o5fzY4jngjpThhytLGqpMk7lq73o', '辛福', 'https://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIARE6ibpu3FkwBJKRu1lZibicL0ReWcH2uq4bA70KZmiamiaibjLqL7maogyiaPhBbG1iao2o5PtAuE3AvAA/132', '2022-11-04 11:12:32', '2022-11-04 11:12:32', 0, NULL, 0, NULL, '-', 1, NULL, 0, 1, 0, NULL, NULL, NULL);
INSERT INTO `t_wxuserinfo` VALUES (12, 'o5fzY4hooYpm0pEEyfJQvwdFHef8', '蓝色妖姬', 'https://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEJ56LQpjibjCicrsLlZlHcj6VUb2bwjBBm9Rpk6eZajaGROZ0Wf6ogY3ttpbnf6ibOGlcXtibxPVpUE5A/132', '2022-11-04 12:53:27', '2022-11-04 12:53:27', 0, NULL, 0, NULL, '-', 1, NULL, 0, 1, 0, NULL, NULL, NULL);
INSERT INTO `t_wxuserinfo` VALUES (13, 'o5fzY4mx1IOFvHEfy70OMnikVrdE', 'Shin', 'https://thirdwx.qlogo.cn/mmopen/vi_32/XAxTcGFpib3p316ak4Pdia0heRS9PRaH1hWMMgGLmr47jXULYVKibS4L4ia23zFfca9DBbWicPqPoJZyTuTLjF8KCyQ/132', '2022-11-04 13:12:35', '2022-11-04 13:12:35', 0, NULL, 0, NULL, '-', 1, NULL, 0, 1, 0, NULL, NULL, '');
INSERT INTO `t_wxuserinfo` VALUES (14, 'o5fzY4tawihCUzJhDivPkt6LvAUU', '丶', 'https://thirdwx.qlogo.cn/mmopen/vi_32/9aqxseO00Yak0dj0lpC0vWFjoI23uw275ramQh6E3ib7WwzuHjBQDTKIqOgzXGvB0nGwD2aKGyic1JH3yxITPNWA/132', '2022-11-04 14:10:16', '2022-11-04 14:10:16', 0, NULL, 0, NULL, '-', 1, NULL, 0, 1, 0, NULL, NULL, NULL);
INSERT INTO `t_wxuserinfo` VALUES (15, 'o5fzY4jxeP2_Th0r0hpAa_ht-fUE', '小倩', 'https://thirdwx.qlogo.cn/mmopen/vi_32/4dBMq5ZIsiaqwHZDOV8z51JtRatBWmYYmYhx6zZEk0UKXSvWQrhPgbXwC3rN8pDeRCLRxG6RfvsqZ55PTueIVsQ/132', '2022-11-04 14:16:54', '2022-11-04 19:50:01', 0, NULL, 0, NULL, '-', 1, NULL, 0, 1, 0, NULL, NULL, NULL);
INSERT INTO `t_wxuserinfo` VALUES (16, 'o5fzY4tF2wY3OWRscEWf8LssBAi0', '超级大可', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKxBgRjib2ECXkvd8RhosJm1fTQial7HRZjbnGibQiavBltECdVknibSqFoUYvGvtVP2JBqUxKSclWlsOw/132', '2022-11-04 14:42:36', '2022-11-04 14:42:36', 0, NULL, 0, NULL, '-', 1, NULL, 0, 1, 0, NULL, NULL, NULL);
INSERT INTO `t_wxuserinfo` VALUES (17, 'o5fzY4sF5taFEGKUzOjxtTYWADfw', '渡', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83ep8ZZx6omRkOnOnoalnnkjh2Gun2dthfibvHZFhrgenQ3pOfBic8x8Fu9ANwia27dp6h7VXYdMP64fUQ/132', '2022-11-04 23:08:21', '2022-11-04 23:08:21', 0, NULL, 0, NULL, '-', 1, NULL, 0, 1, 100, NULL, NULL, NULL);
INSERT INTO `t_wxuserinfo` VALUES (19, 'o30ur5JpAsAUyGBkR0uW4IxvahR8', '小峰', '/image/product/20220929101806000000898.jpg', '2022-01-08 22:19:11', '2022-02-21 08:20:09', 0, '魔法,知识,命运', 1, 15, '-', 1, 1, 1, 1, 0, 1005, NULL, NULL);
INSERT INTO `t_wxuserinfo` VALUES (20, 'o5fzY4tkTpwTcsbdKBupOz2BM8U0', 'ikun', 'https://thirdwx.qlogo.cn/mmopen/vi_32/hnKp3DsVbdCmLEuUx3eiac6szOcpiblb8SicoPtbp0IxObcRlJkqyWm0URrGq0aiaBETuyNBzh7jXHFZLU5IzBfuGw/132', '2022-11-10 08:51:28', '2022-11-10 08:51:28', 2, '力大无穷', 1, 18, 'sos9234', 1, NULL, 1, 1, 0, 1019, NULL, NULL);

-- ----------------------------
-- Table structure for temp_file
-- ----------------------------
DROP TABLE IF EXISTS `temp_file`;
CREATE TABLE `temp_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `src` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of temp_file
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
