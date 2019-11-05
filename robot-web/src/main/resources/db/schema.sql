
-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `ID` varchar(32) NOT NULL COMMENT '物理主键',
  `MENU_NO` varchar(64) DEFAULT '' COMMENT '菜单编号',
  `MENU_NAME` varchar(64) DEFAULT '' COMMENT '菜单名称',
  `MENU_PATH` varchar(60) DEFAULT '' COMMENT '菜单路径',
  `PARENT_MENU_NO` varchar(64) DEFAULT '' COMMENT '父菜单编号',
  `MENU_LEVEL` int(10) DEFAULT NULL COMMENT '菜单级别',
  `MENU_SEQ_NO` int(10) DEFAULT NULL COMMENT '菜单序号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) DEFAULT '' COMMENT '创建人',
  `UPDATE_TIME` datetime NOT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(32) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `MENU_UQ_IDX` (`MENU_NO`)
) COMMENT='角色信息表';

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
-- Table structure for t_sequence
-- ----------------------------
DROP TABLE IF EXISTS `t_sequence`;
CREATE TABLE `t_sequence` (
  `id` varchar(32) NOT NULL COMMENT '物理主键',
  `seq_name` varchar(255) DEFAULT NULL COMMENT '序列名称',
  `seq_value` int(10) DEFAULT NULL COMMENT '序列值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `SEQUENCE_UQ_IDX` (`seq_name`)
) COMMENT='序列表';

-- ----------------------------
-- Table structure for t_svn_info
-- ----------------------------
DROP TABLE IF EXISTS `t_svn_info`;
CREATE TABLE `t_svn_info` (
  `ID` varchar(32) NOT NULL COMMENT '物理主键',
  `url` varchar(255) DEFAULT NULL COMMENT 'svn地址',
  `local_url` varchar(255) DEFAULT NULL COMMENT '本地存储地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `over_flag` varchar(1) DEFAULT NULL COMMENT '检出成功标志',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SVN_INFO_UQ_IDX` (`url`) USING BTREE
) COMMENT='SVN信息表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `ID` varchar(32) NOT NULL COMMENT '物理主键',
  `USERNAME` varchar(64) NOT NULL COMMENT '用户名',
  `NICKNAME` varchar(64) DEFAULT NULL COMMENT '昵称',
  `PASSWORD` varchar(60) DEFAULT NULL COMMENT '密码',
  `ROLE_NO` varchar(32) DEFAULT NULL COMMENT '角色编号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime NOT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_UQ_IDX` (`USERNAME`) USING BTREE
) COMMENT='用户信息表';

