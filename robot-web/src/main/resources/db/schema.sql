-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `ID` varchar(32) NOT NULL COMMENT '物理主键',
  `USERNAME` varchar(64) DEFAULT NULL COMMENT '用户名',
  `NICKNAME` varchar(64) DEFAULT NULL COMMENT '昵称',
  `PASSWORD` varchar(60) DEFAULT NULL COMMENT '密码',
  `ROLE_NO` varchar(32) DEFAULT NULL COMMENT '角色编号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime NOT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`)
) COMMENT='用户信息表';

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `ID` varchar(32) NOT NULL COMMENT '物理主键',
  `ROLE_NO` varchar(64) DEFAULT NULL COMMENT '角色编号',
  `ROLE_NAME` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `MENU_ID` varchar(60) DEFAULT NULL COMMENT '菜单ID',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime NOT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`)
) COMMENT='角色信息表';

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `ID` varchar(32) NOT NULL COMMENT '物理主键',
  `MENU_NO` varchar(64) DEFAULT NULL COMMENT '菜单编号',
  `MENU_NAME` varchar(64) DEFAULT NULL COMMENT '菜单名称',
  `MENU_PATH` varchar(60) DEFAULT NULL COMMENT '菜单路径',
  `PARENT_MENU_NO` varchar(64) DEFAULT NULL COMMENT '父菜单编号',
  `MENU_LEVEL` int(10) DEFAULT NULL COMMENT '菜单级别',
  `MENU_SEQ_NO` int(10) DEFAULT NULL COMMENT '菜单序号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime NOT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`)
) COMMENT='菜单信息表';

-- ----------------------------
-- Table structure for t_sequence
-- ----------------------------
DROP TABLE IF EXISTS `t_sequence`;
CREATE TABLE `t_sequence` (
  `ID` varchar(32) NOT NULL COMMENT '物理主键',
  `SEQ_NAME` varchar(64) DEFAULT NULL COMMENT '序列名称',
  `SEQ_VALUE` varchar(64) DEFAULT NULL COMMENT '序列值',
  PRIMARY KEY (`ID`)
) COMMENT='菜单信息表';