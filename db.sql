/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : personmgr

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2018-12-17 14:02:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `CustomerID` int(11) NOT NULL AUTO_INCREMENT,
  `CustomerName` varchar(128) DEFAULT NULL,
  `Img` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '头像',
  `Region` varchar(20) DEFAULT NULL COMMENT '省份',
  `Lng` double DEFAULT NULL COMMENT '经度',
  `Lat` double DEFAULT NULL COMMENT '纬度',
  `SecurityLevel` varchar(128) DEFAULT NULL,
  `MaxVal` varchar(128) DEFAULT NULL,
  `Note` varchar(500) DEFAULT NULL COMMENT '备注',
  `Status` int(11) DEFAULT NULL,
  `CreateUser` varchar(64) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyUser` varchar(64) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`CustomerID`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for function
-- ----------------------------
DROP TABLE IF EXISTS `function`;
CREATE TABLE `function` (
  `functionID` int(11) NOT NULL AUTO_INCREMENT,
  `menuID` int(11) DEFAULT NULL,
  `FuncTypeID` int(11) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `CreateUser` varchar(64) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyUser` varchar(64) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`functionID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of function
-- ----------------------------

-- ----------------------------
-- Table structure for functype
-- ----------------------------
DROP TABLE IF EXISTS `functype`;
CREATE TABLE `functype` (
  `FuncTypeID` int(11) NOT NULL AUTO_INCREMENT,
  `FuncTypeName` varchar(64) DEFAULT NULL,
  `FuncTypeCode` varchar(64) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `CreateUser` varchar(64) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyUser` varchar(64) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`FuncTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of functype
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menuID` int(11) NOT NULL AUTO_INCREMENT,
  `menuName` varchar(64) DEFAULT NULL,
  `menuURL` varchar(128) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `CreateUser` varchar(64) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyUser` varchar(64) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`menuID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `personmgr`.`menu` (`menuName`, `menuURL`, `roles`, `Status`, `CreateUser`, `CreateTime`) VALUES ('任务', '/task/taskList', '1,', '1', 'sqb', '2019-1-3');
INSERT INTO `personmgr`.`menu` (`menuName`, `menuURL`, `roles`, `Status`, `CreateUser`, `CreateTime`) VALUES ('项目', '/project/projectList', '1,', '1', 'sqb', '2019-1-2');


-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `ProjectId` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(300) NOT NULL COMMENT '项目代号',
  `Name` varchar(300) NOT NULL COMMENT '项目名称',
  `StartTime` datetime DEFAULT NULL COMMENT '项目开始时间',
  `EndTime` datetime DEFAULT NULL COMMENT '项目结束时间',
  `Status` int(11) DEFAULT NULL,
  `CreateUser` varchar(64) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyUser` varchar(64) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ProjectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目表';

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `personmgr`.`project` (`ProjectId`, `Code`, `Name`, `StartTime`, `EndTime`, `Status`, `CreateUser`, `CreateTime`, `ModifyUser`, `ModifyTime`)
 VALUES ('1', 'law', '司法部', NULL, NULL, '1', 'sqb', '2018-12-19 00:00:00', NULL, NULL);


-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `RoleID` int(11) NOT NULL AUTO_INCREMENT,
  `RoleCode` varchar(64) DEFAULT NULL,
  `RoleName` varchar(64) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `CreateUser` varchar(64) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyUser` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`RoleID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
INSERT INTO `role` (`RoleID`, `RoleCode`, `RoleName`, `ModifyTime`, `Status`, `CreateUser`, `CreateTime`, `ModifyUser`) VALUES ('1', 'User', '客户', '2018-12-12 00:00:00', '1', 'Admin', '2018-12-12 00:00:00', 'Admin');
-- ----------------------------

-- ----------------------------
-- Table structure for rolefunc
-- ----------------------------
DROP TABLE IF EXISTS `rolefunc`;
CREATE TABLE `rolefunc` (
  `RoleFuncID` int(11) NOT NULL AUTO_INCREMENT,
  `RoleID` int(11) DEFAULT NULL,
  `functionID` varchar(128) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `CreateUser` varchar(64) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyUser` varchar(64) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`RoleFuncID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rolefunc
-- ----------------------------

-- ----------------------------
-- Table structure for sensor
-- ----------------------------
DROP TABLE IF EXISTS `sensor`;
CREATE TABLE `sensor` (
  `SensorID` int(11) NOT NULL AUTO_INCREMENT,
  `BaseMapID` int(11) DEFAULT NULL,
  `SensorName` varchar(64) DEFAULT NULL,
  `SensorCode` varchar(64) DEFAULT NULL,
  `CoordinateX` decimal(20,9) DEFAULT NULL,
  `CoordinateY` decimal(20,9) DEFAULT NULL,
  `Zoom` decimal(9,2) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `CreateUser` varchar(64) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyUser` varchar(64) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`SensorID`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sensor
-- ----------------------------

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `TaskId` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(100) NOT NULL COMMENT '任务标题',
  `TaskType` int(11) NOT NULL COMMENT '类型：0-需求 1-BUG',
  `TaskStatus` int(11) NOT NULL COMMENT '任务状态： 0-未开始，1-处理中，2-处理完成，3-处理不了',
  `ProjectCode` varchar(300) NULL COMMENT '所属项目',
  `ModuleId` int(11)  NULL COMMENT '所属模块',
  `Demandor` varchar(100) NULL COMMENT '提出人',
  `AssignTo` varchar(100) NULL COMMENT '分配人',
  `Solver` varchar(100) NULL COMMENT '解决人',
  `Content` varchar(5000) NOT NULL COMMENT '任务内容',
  `PutTime` datetime NULL COMMENT '提出时间',
  `ScheduledStart` datetime NULL COMMENT '预计开始时间',
  `ScheduledEnd` datetime NULL COMMENT '预计结束时间',
  `Status` int(11) DEFAULT NULL,
  `CreateUser` varchar(64) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyUser` varchar(64) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`TaskId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `personmgr`.`task` (`TaskId`, `Title`, `TaskType`, `TaskStatus`, `ProjectCode`, `ModuleId`, `Demandor`, `AssignTo`, `Solver`, `Content`, `PutTime`, `ScheduledStart`, `ScheduledEnd`, `Status`, `CreateUser`, `CreateTime`) VALUES ('1', '一件格式化', '1', '1', 'law', '1', '谈谈', 'me', 'me', '格式化内容', '2019/1/5', '2019/1/5', '2019/1/5', '1', 'sqb', '2019/1/5');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `UserCode` varchar(64) DEFAULT NULL,
  `UserName` varchar(64) DEFAULT NULL,
  `Password` varchar(64) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `CreateUser` varchar(64) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyUser` varchar(64) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('17', 'sqb', '申强宾', null, '1', 'sqb', '2018-12-17 00:00:00', null, null);

-- ----------------------------
-- Table structure for userrole
-- ----------------------------
DROP TABLE IF EXISTS `userrole`;
CREATE TABLE `userrole` (
  `UserRoleID` int(11) NOT NULL AUTO_INCREMENT,
  `RoleID` int(11) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `CreateUser` varchar(64) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyUser` varchar(64) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`UserRoleID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;



INSERT INTO `personmgr`.`userrole` (`RoleID`, `UserID`, `Status`, `CreateUser`, `CreateTime`) VALUES ('3', '17', '1', 'sqb', '2018/12/17');
