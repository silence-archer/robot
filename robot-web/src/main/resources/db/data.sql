
-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('16556097056f45d4814e15234c3f1d75', 'Menu10051012', '文件版本信息', '/svnLocalInfo', 'Menu1005', '2', null, '2019-10-10 03:09:01', null, '2019-10-30 23:30:13', null);
INSERT INTO `t_menu` VALUES ('3a902dec01514a78b0cfaff165fc693c', 'Menu10011018', '用户信息', '/user', 'Menu1001', '2', null, '2019-10-10 04:59:23', null, '2019-10-10 05:00:01', null);
INSERT INTO `t_menu` VALUES ('40e27ad7ecdd4dfeae2d3088d0fcc4b8', 'Menu10021017', '还款计划试算', '/loanPlan', 'Menu1002', '2', null, '2019-10-10 03:38:48', null, '2019-10-10 05:00:57', null);
INSERT INTO `t_menu` VALUES ('546ee2cd4bf04488ad7a15f1b1b1c6ee', 'Menu10071019', '批处理运行', '/batchRun', 'Menu1007', '2', null, '2019-10-21 20:29:20', null, '2019-10-21 20:30:05', null);
INSERT INTO `t_menu` VALUES ('568ee85b2bb44f9aba2143d3347b0c91', 'Menu10061014', '自动回复', '/wechat', 'Menu1006', '2', null, '2019-10-10 03:38:43', null, '2019-10-10 05:02:07', null);
INSERT INTO `t_menu` VALUES ('767ee5cb8e524f04a5710788cff23a9d', 'Menu1001', '用户管理', null, null, '1', null, '2019-10-10 03:06:33', null, '2019-10-10 04:49:39', null);
INSERT INTO `t_menu` VALUES ('8e6aa0c9702c4710bc5141b52defda1f', 'Menu10021016', '利率计算', '/loanIntRate', 'Menu1002', '2', null, '2019-10-10 03:38:46', null, '2019-10-10 05:01:19', null);
INSERT INTO `t_menu` VALUES ('934c9d09f1244d92a60abae5ea1a77f4', 'Menu1002', '贷款测试', null, null, '1', null, '2019-10-10 03:06:46', null, '2019-10-10 05:00:27', null);
INSERT INTO `t_menu` VALUES ('9787e6d8ae4047c4a5fa8de3a1699ee2', 'Menu10011001', '菜单管理', '/menu', 'Menu1001', '2', null, '2019-10-10 03:06:54', null, '2019-10-10 04:50:00', null);
INSERT INTO `t_menu` VALUES ('9f926899f9734d91bd8299aef20ef27e', 'Menu1006', '聊天机器人', null, null, '1', null, '2019-10-10 03:38:36', null, '2019-10-10 05:01:40', null);
INSERT INTO `t_menu` VALUES ('a64ab330d9bf4e31a7db47c42c28e9bf', 'Menu1005', 'SVN工具', null, null, '1', null, '2019-10-10 03:08:54', null, '2019-10-10 05:02:55', null);
INSERT INTO `t_menu` VALUES ('a794aac8714f40c9bf60e38770d885c1', 'Menu10051010', '文件检出', '/svnCheckout', 'Menu1005', '2', null, '2019-10-10 03:08:58', null, '2019-10-10 05:04:47', null);
INSERT INTO `t_menu` VALUES ('be2691052d8f4414b5d495f9f7f33163', 'Menu1007', '批处理', null, null, '1', null, '2019-10-21 20:29:07', null, '2019-10-21 20:29:37', null);
INSERT INTO `t_menu` VALUES ('f9f59fc4ec9d47608f140372559eae83', 'Menu10011002', '权限管理', '/auth', 'Menu1001', '2', null, '2019-10-10 03:06:58', null, '2019-10-10 04:59:21', null);

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('100000', 'admin', '管理员', 'admin', '1', '2019-10-08 15:19:24', 'admin', '2019-10-08 15:19:35', 'admin');
INSERT INTO `t_user` VALUES ('32c72423988f423a8e0cc5e0c370b088', 'archer', '弓兵', null, null, '2019-10-21 21:56:38', null, '2019-10-21 22:29:08', null);
INSERT INTO `t_user` VALUES ('100001', 'silence', '我是谁', 'silence', null, '2019-10-21 21:55:30', null, '2019-10-21 22:28:53', null);
INSERT INTO `t_user` VALUES ('cddafacf06344148aeaa9500e4493b93', 'lancer', '枪兵', null, null, '2019-10-20 22:35:46', null, '2019-10-21 22:29:26', null);
INSERT INTO `t_user` VALUES ('ed46e38afcde4eb8992c750659ce39d0', 'saber', '剑士', null, null, '2019-10-28 03:03:04', null, '2019-10-28 04:24:28', null);

-- ----------------------------
-- Records of t_user_talk_friend
-- ----------------------------
INSERT INTO `t_user_talk_friend` VALUES ('13b7a3a42832498c8253ba3227b33548', '0', 'silence', 'admin');
INSERT INTO `t_user_talk_friend` VALUES ('63dcdcd642cf403db76536ee67b3d42a', '0', 'saber', 'admin');
INSERT INTO `t_user_talk_friend` VALUES ('74579b02b0794796958830b28b36c4c5', '0', 'archer', 'admin');
INSERT INTO `t_user_talk_friend` VALUES ('d017cc4d0a0941c291f31a77b0d94e7c', '0', 'lancer', 'admin');

-- ----------------------------
-- Records of t_user_talk_friend_group
-- ----------------------------
INSERT INTO `t_user_talk_friend_group` VALUES ('1', 'silence', '0', '机器人组');
INSERT INTO `t_user_talk_friend_group` VALUES ('2', 'saber', '0', '机器人组');
INSERT INTO `t_user_talk_friend_group` VALUES ('3', 'lancer', '0', '机器人组');
INSERT INTO `t_user_talk_friend_group` VALUES ('4', 'archer', '0', '机器人组');

-- ----------------------------
-- Records of t_user_talk_group
-- ----------------------------
INSERT INTO `t_user_talk_group` VALUES ('1', '0', 'silence', '大家庭', 'image/avatar/30.jpg');
INSERT INTO `t_user_talk_group` VALUES ('2', '0', 'saber', '大家庭', 'image/avatar/30.jpg');
INSERT INTO `t_user_talk_group` VALUES ('3', '0', 'lancer', '大家庭', 'image/avatar/30.jpg');
INSERT INTO `t_user_talk_group` VALUES ('4', '0', 'archer', '大家庭', 'image/avatar/30.jpg');

-- ----------------------------
-- Records of t_user_talk_info
-- ----------------------------
INSERT INTO `t_user_talk_info` VALUES ('admin', '管理员', 'online', '我是机器人', 'image/touxiang.png');
INSERT INTO `t_user_talk_info` VALUES ('archer', '弓兵', 'hide', '世间万物皆系于一箭之上', 'image/avatar/4.jpg');
INSERT INTO `t_user_talk_info` VALUES ('lancer', '枪兵', 'hide', '自古枪兵幸运E', 'image/avatar/3.jpg');
INSERT INTO `t_user_talk_info` VALUES ('saber', '剑士', 'hide', '断剑重铸之日，骑士归来之时', 'image/avatar/2.jpg');
INSERT INTO `t_user_talk_info` VALUES ('silence', '我是谁', 'hide', '死亡如风，常伴吾生', 'image/avatar/1.jpg');

-- ----------------------------
-- Records of t_user_talk_members
-- ----------------------------
INSERT INTO `t_user_talk_members` VALUES ('1', '0', 'silence');
INSERT INTO `t_user_talk_members` VALUES ('2', '0', 'archer');
INSERT INTO `t_user_talk_members` VALUES ('3', '0', 'saber');
INSERT INTO `t_user_talk_members` VALUES ('4', '0', 'lancer');