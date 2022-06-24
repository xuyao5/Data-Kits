/*
 Navicat Premium Data Transfer

 Source Server         : MySQL-Binlog
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 24/06/2022 14:19:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for OMS_ORDER
-- ----------------------------
DROP TABLE IF EXISTS `OMS_ORDER`;
CREATE TABLE `OMS_ORDER` (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CUST_NO` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户号',
  `ORDER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号',
  `LINE_NO` int NOT NULL COMMENT '交易文件行号',
  `LINE_RECORD` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '交易文件记录',
  `CREATE_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_BIZ_PK` (`CUST_NO`,`ORDER_ID`),
  KEY `IDX_LINE_NO` (`LINE_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单交易表';

SET FOREIGN_KEY_CHECKS = 1;
