
-- ----------------------------
-- Table structure for t_cron_task
-- ----------------------------
DROP TABLE IF EXISTS `t_cron_task`;
CREATE TABLE `t_cron_task` (
  `ID` varchar(64) NOT NULL COMMENT '物理主键',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `cron_expr` varchar(30) NOT NULL COMMENT 'cron表达式',
  `job_desc` varchar(200) DEFAULT NULL COMMENT '任务描述',
  `job_class` varchar(60) DEFAULT NULL COMMENT '任务执行类名',
  `effect_flag` varchar(1) NOT NULL COMMENT '生效标志',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `t_cron_task_job_name_uindex` (`job_name`)
) COMMENT='定时任务配置表';

-- ----------------------------
-- Table structure for t_cron_task_proc_log
-- ----------------------------
DROP TABLE IF EXISTS `t_cron_task_proc_log`;
CREATE TABLE `t_cron_task_proc_log` (
  `ID` varchar(64) NOT NULL COMMENT '物理主键',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_desc` varchar(256) DEFAULT NULL COMMENT '任务描述',
  `proc_status` varchar(10) DEFAULT NULL COMMENT '执行状态',
  `error_msg` varchar(256) DEFAULT NULL COMMENT '错误信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `t_cron_task_proc_log_job_name_uindex` (`job_name`)
)  COMMENT='定时任务执行日志表';

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
)  COMMENT='菜单信息表';

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `ID` varchar(32) NOT NULL COMMENT '物理主键',
  `ROLE_NO` varchar(64) DEFAULT NULL COMMENT '角色编号',
  `ROLE_NAME` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `MENU_NO` varchar(60) DEFAULT NULL COMMENT '菜单ID',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime NOT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`)
)  COMMENT='角色信息表';

-- ----------------------------
-- Table structure for t_sequence
-- ----------------------------
DROP TABLE IF EXISTS `t_sequence`;
CREATE TABLE `t_sequence` (
  `id` varchar(32) NOT NULL COMMENT '物理主键',
  `seq_name` varchar(255) DEFAULT NULL COMMENT '序列名称',
  `seq_value` int(10) DEFAULT NULL COMMENT '序列值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `SEQ_UQ_IDX` (`seq_name`)
)  COMMENT='序列表';

-- ----------------------------
-- Table structure for t_subscribe_config_info
-- ----------------------------
DROP TABLE IF EXISTS `t_subscribe_config_info`;
CREATE TABLE `t_subscribe_config_info` (
  `ID` varchar(64) NOT NULL COMMENT '物理主键',
  `config_name` varchar(64) NOT NULL COMMENT '配置名称',
  `config_value` varchar(256) NOT NULL COMMENT '配置值',
  `config_desc` varchar(256) DEFAULT NULL COMMENT '配置描述',
  `expire_time` int(11) DEFAULT NULL COMMENT '超时时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `t_subscribe_config_info_config_name_uindex` (`config_name`)
)  COMMENT='微信订阅号配置信息表';

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
  UNIQUE KEY `SVN_UQ_IDX` (`url`)
)  COMMENT='SVN信息表';

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
  UNIQUE KEY `UQ_IDX` (`USERNAME`) 
)  COMMENT='用户信息表';

-- ----------------------------
-- Table structure for t_user_talk_friend
-- ----------------------------
DROP TABLE IF EXISTS `t_user_talk_friend`;
CREATE TABLE `t_user_talk_friend` (
  `id` varchar(64) NOT NULL COMMENT '物理主键',
  `group_id` int(11) DEFAULT NULL COMMENT '分组id',
  `mine_id` varchar(64) DEFAULT NULL COMMENT '我的id',
  `friend_id` varchar(64) DEFAULT NULL COMMENT '朋友id',
  PRIMARY KEY (`id`)
)  COMMENT='用户即时聊天好友信息表';

-- ----------------------------
-- Table structure for t_user_talk_friend_group
-- ----------------------------
DROP TABLE IF EXISTS `t_user_talk_friend_group`;
CREATE TABLE `t_user_talk_friend_group` (
  `id` varchar(64) NOT NULL COMMENT '物理主键',
  `mine_id` varchar(64) DEFAULT NULL COMMENT '我的ID',
  `group_id` int(11) DEFAULT NULL COMMENT '分组ID',
  `groupname` varchar(255) DEFAULT NULL COMMENT '分组名称',
  PRIMARY KEY (`id`)
)  COMMENT='即时聊天好友分组信息表';

-- ----------------------------
-- Table structure for t_user_talk_group
-- ----------------------------
DROP TABLE IF EXISTS `t_user_talk_group`;
CREATE TABLE `t_user_talk_group` (
  `id` varchar(64) NOT NULL COMMENT '唯一标识',
  `group_id` varchar(64) DEFAULT NULL COMMENT '群聊ID',
  `mine_id` varchar(64) DEFAULT NULL COMMENT '我的id',
  `groupname` varchar(255) DEFAULT NULL COMMENT '群组名称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '群组头像地址',
  PRIMARY KEY (`id`)
)  COMMENT='用户即时聊天群组信息表';

-- ----------------------------
-- Table structure for t_user_talk_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_talk_info`;
CREATE TABLE `t_user_talk_info` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户编号',
  `username` varchar(255) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL COMMENT '状态',
  `sign` varchar(255) DEFAULT NULL COMMENT '签名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  PRIMARY KEY (`id`)
)  COMMENT='用户即时聊天信息表';

-- ----------------------------
-- Table structure for t_user_talk_members
-- ----------------------------
DROP TABLE IF EXISTS `t_user_talk_members`;
CREATE TABLE `t_user_talk_members` (
  `id` varchar(64) NOT NULL COMMENT '唯一标识',
  `group_id` varchar(64) DEFAULT NULL COMMENT '群组id',
  `member_id` varchar(64) DEFAULT NULL COMMENT '成员Id',
  PRIMARY KEY (`id`)
)  COMMENT='用户即时聊天群组成员信息表';
