

-- ----------------------------
-- Records of t_cron_task
-- ----------------------------
INSERT INTO `t_cron_task` VALUES ('e66377ba2c724627895ead05ebcbb925', 'SignatureAccessToken', '0 0 0/1 * * ?', '定时获取公众号的全局唯一接口调用凭据', 'subscribeAccessTokenJob', 'Y', null, '2019-10-10 05:04:47', null, '2019-10-10 05:04:47');
INSERT INTO `t_cron_task` VALUES ('95c4c9a9fa964cb1b67eb12d0bdf8b5c', 'ImageMenuRead', '0 0/30 * * * ?', '图片菜单读取', 'imageMenuReadJob', 'Y', NULL, '2019-10-10 05:04:47', NULL, '2019-10-10 05:04:47');


-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('16556097056f45d4814e15234c3f1d75', 'Menu10051012', '文件版本信息', '/svnLocalInfo', 'Menu1005', '2', null, '2019-10-10 03:09:01', null, '2019-10-30 23:30:13', null);
INSERT INTO `t_menu` VALUES ('3a902dec01514a78b0cfaff165fc693c', 'Menu10011018', '用户信息', '/user', 'Menu1001', '2', null, '2019-10-10 04:59:23', null, '2019-10-10 05:00:01', null);
INSERT INTO `t_menu` VALUES ('40e27ad7ecdd4dfeae2d3088d0fcc4b8', 'Menu10021017', '还款计划试算', '/loanPlan', 'Menu1002', '2', null, '2019-10-10 03:38:48', null, '2019-10-10 05:00:57', null);
INSERT INTO `t_menu` VALUES ('546ee2cd4bf04488ad7a15f1b1b1c6ee', 'Menu10071019', '批处理运行', '/batchRun', 'Menu1007', '2', null, '2019-10-21 20:29:20', null, '2019-10-21 20:30:05', null);
INSERT INTO `t_menu` VALUES ('568ee85b2bb44f9aba2143d3347b0c91', 'Menu10061014', '自动回复', '/wechat', 'Menu1006', '2', null, '2019-10-10 03:38:43', null, '2019-11-21 20:28:55', null);
INSERT INTO `t_menu` VALUES ('767ee5cb8e524f04a5710788cff23a9d', 'Menu1001', '用户管理', null, null, '1', null, '2019-10-10 03:06:33', null, '2019-10-10 04:49:39', null);
INSERT INTO `t_menu` VALUES ('7ab4553d7a924b63a01e1f4ad862b93f', 'Menu10081020', '参数配置', '/schedule', 'Menu1008', '2', null, '2019-12-27 06:20:55', null, '2019-12-27 06:21:38', null);
INSERT INTO `t_menu` VALUES ('8e6aa0c9702c4710bc5141b52defda1f', 'Menu10021016', '利率计算', '/loanIntRate', 'Menu1002', '2', null, '2019-10-10 03:38:46', null, '2019-10-10 05:01:19', null);
INSERT INTO `t_menu` VALUES ('934c9d09f1244d92a60abae5ea1a77f4', 'Menu1002', '贷款测试', null, null, '1', null, '2019-10-10 03:06:46', null, '2019-10-10 05:00:27', null);
INSERT INTO `t_menu` VALUES ('9787e6d8ae4047c4a5fa8de3a1699ee2', 'Menu10011001', '菜单管理', '/menu', 'Menu1001', '2', null, '2019-10-10 03:06:54', null, '2019-10-10 04:50:00', null);
INSERT INTO `t_menu` VALUES ('9f926899f9734d91bd8299aef20ef27e', 'Menu1006', '聊天机器人', null, null, '1', null, '2019-10-10 03:38:36', null, '2019-10-10 05:01:40', null);
INSERT INTO `t_menu` VALUES ('a64ab330d9bf4e31a7db47c42c28e9bf', 'Menu1005', 'SVN工具', null, null, '1', null, '2019-10-10 03:08:54', null, '2019-10-10 05:02:55', null);
INSERT INTO `t_menu` VALUES ('a794aac8714f40c9bf60e38770d885c1', 'Menu10051010', '文件检出', '/svnCheckout', 'Menu1005', '2', null, '2019-10-10 03:08:58', null, '2019-10-10 05:04:47', null);
INSERT INTO `t_menu` VALUES ('be2691052d8f4414b5d495f9f7f33163', 'Menu1007', '批处理', null, null, '1', null, '2019-10-21 20:29:07', null, '2019-10-21 20:29:37', null);
INSERT INTO `t_menu` VALUES ('c230a07336014ec0bab157dc73cdebc8', 'Menu1008', '定时任务', null, null, '1', null, '2019-12-27 06:20:36', null, '2019-12-27 06:21:10', null);
INSERT INTO `t_menu` VALUES ('f9f59fc4ec9d47608f140372559eae83', 'Menu10011002', '权限管理', '/role', 'Menu1001', '2', null, '2019-10-10 03:06:58', null, '2019-12-06 03:28:48', null);
INSERT INTO `t_menu` VALUES ('10b2b0d3bb2c4f16bc30671257a55416', 'Menu10091021', '挡板列表', '/mock', 'Menu1009', 2, NULL, '2020-10-07 12:16:04', 'admin', '2020-10-07 12:16:19', 'admin');
INSERT INTO `t_menu` VALUES ('e0deff6e41be4ac199051e78e8dc9c37', 'Menu1009', '挡板管理', '', NULL, 1, NULL, '2020-10-07 12:15:27', 'admin', '2020-10-07 12:15:48', 'admin');


-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('08d96a3d7a6c431da6404c1c100daa03', 'roleNo0001', '管理员', 'Menu10011002', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('0a70dc96d7444b8cb8083d72cbb0aa76', 'roleNo0001', '管理员', 'Menu1005', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('1077972523934cafa80067ba8fa621bf', 'roleNo0001', '管理员', 'Menu10071019', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('128d0398a22b4794affae2d8feca2b27', 'roleNo0001', '管理员', 'Menu1006', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('266c92b5367f4ccfa6838427546828fa', 'roleNo0001', '管理员', 'Menu10051010', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('2a2f3942845242beb813148a8f8f7ccb', 'roleNo0001', '管理员', 'Menu1002', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('2c65b877a2f849f489ab99517e818a86', 'roleNo0001', '管理员', 'Menu10051012', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('35fca8e749ed45c184cd40600389a92a', 'roleNo0002', '用户管理角色', 'Menu10011002', '2019-12-10 01:54:39', null, '2019-12-10 01:54:39', null);
INSERT INTO `t_role` VALUES ('3d76a38484204428a9a6d077e484c186', 'roleNo0002', '用户管理角色', 'Menu10011018', '2019-12-10 01:54:39', null, '2019-12-10 01:54:39', null);
INSERT INTO `t_role` VALUES ('42cedd57c6da414990a81589d33c2266', 'roleNo0001', '管理员', 'Menu1007', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('4bd613d04f8c44a1bd789fe33e7c729d', 'roleNo0002', '用户管理角色', 'Menu1001', '2019-12-10 01:54:39', null, '2019-12-10 01:54:39', null);
INSERT INTO `t_role` VALUES ('534776433fb344acb2ad3a3f1fb8ead0', 'roleNo0001', '管理员', 'Menu10021016', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('614b665e83994f078e858e652f45315e', 'roleNo0001', '管理员', 'Menu1008', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('74a72180dba44ee6962cb110be0a5f34', 'roleNo0001', '管理员', 'Menu10061014', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('9775717781084a56809627efffffc620', 'roleNo0001', '管理员', 'Menu10021017', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('b0af5b7009ae408b8808cfc3e465c199', 'roleNo0001', '管理员', 'Menu10011001', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('b50a7d840aa649008a8e0f76351acf9e', 'roleNo0001', '管理员', 'Menu10011018', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('bad7b3e7c19b474b821d043427b8f1e8', 'roleNo0009', '贷款专员', 'Menu10021017', '2019-12-17 01:28:40', null, '2019-12-17 01:28:40', null);
INSERT INTO `t_role` VALUES ('bb49c9bdfd4547d3a484d0a10ee5899c', 'roleNo0009', '贷款专员', 'Menu1002', '2019-12-17 01:28:40', null, '2019-12-17 01:28:40', null);
INSERT INTO `t_role` VALUES ('c397f4256b2e4e06a0251c83dd5cc544', 'roleNo0002', '用户管理角色', 'Menu10011001', '2019-12-10 01:54:39', null, '2019-12-10 01:54:39', null);
INSERT INTO `t_role` VALUES ('d0157b4882374236ac2613b42f00b2f5', 'roleNo0009', '贷款专员', 'Menu10021016', '2019-12-17 01:28:40', null, '2019-12-17 01:28:40', null);
INSERT INTO `t_role` VALUES ('d5030c431a6d4e1dbc86dcceb9e8503c', 'roleNo0001', '管理员', 'Menu10081020', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);
INSERT INTO `t_role` VALUES ('ea81ce317e9f42338c3b3d52dd44b21e', 'roleNo0001', '管理员', 'Menu1001', '2019-12-27 06:21:54', null, '2019-12-27 06:21:54', null);


-- ----------------------------
-- Records of t_sequence
-- ----------------------------
INSERT INTO `t_sequence` VALUES ('1e2cefcf47914374800a0cb7d23f692d', 'MenuLevel2', '20');
INSERT INTO `t_sequence` VALUES ('30bf1c8ceed4400685b4739658db4f1d', 'MenuLevel1', '8');
INSERT INTO `t_sequence` VALUES ('8960fcf6bada440a806594f88962f5ef', 'RoleNo', '9');


-- ----------------------------
-- Records of t_subscribe_config_info
-- ----------------------------
INSERT INTO `t_subscribe_config_info` VALUES ('1', 'access_token', '1', '获取到的微信公众号凭证', null, '2019-10-10 05:04:47', 'admin', '2019-10-10 05:04:47', 'admin');


-- ----------------------------
-- Records of t_svn_info
-- ----------------------------
INSERT INTO `t_svn_info` VALUES ('b17a679da9a94cf7b7881bc6287e7d82', 'https://52.80.187.241/svn/ZSF-Project/11_参考资料/', 'C:\\Users\\silence\\Desktop/robot/11_参考资料', '11_参考资料', '1');



-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('100000', 'admin', '管理员', '8715a4f66d9eeddba20b5453edc41bd7', 'roleNo0001', '2019-10-08 15:19:24', 'admin', '2019-12-17 01:18:51', 'admin');
INSERT INTO `t_user` VALUES ('43e938be87ff4dcd9ae2df7fed6ce132', 'ironman', '钢铁侠', 'f03f5bdcc405581f127502a48e4a8643', 'roleNo0009', '2019-12-17 01:27:40', null, '2019-12-17 01:28:53', null);
INSERT INTO `t_user` VALUES ('6dc34aba2fc9451ca3de56b870f76b77', 'saber', '剑士', 'd5c0f750fd263103f55c14cfd9a427bf', 'roleNo0002', '2019-11-22 01:32:09', null, '2019-12-17 01:24:16', null);
INSERT INTO `t_user` VALUES ('89cb1a600e004ef9bd3087d5c4e05d6b', 'archer', '弓兵', 'd7afe71568da8f9e78cac5afecd7cb78', 'roleNo0001', '2019-11-22 01:33:59', null, '2019-11-22 01:33:59', null);
INSERT INTO `t_user` VALUES ('a1b1412efd6144c5815c391b89d5b54c', 'lancer', '枪兵', '4195b8334e611bbf87a1362d3d61673a', 'roleNo0001', '2019-11-22 01:33:09', null, '2019-12-04 20:20:12', null);
INSERT INTO `t_user` VALUES ('a429f00579824aa490085deac52a3b8f', 'batman', '蝙蝠侠', '6b1f54d8c9d383ec2a4340bd9dc993ec', 'roleNo0001', '2019-12-04 20:26:02', null, '2019-12-17 01:14:49', null);
INSERT INTO `t_user` VALUES ('cf88ad9aa8fe44e4b535cc48aaa44539', 'silence', '我是谁', 'b1a46eeb63f51695aca3556c767ea34e', 'roleNo0001', '2019-11-22 01:30:31', null, '2019-11-22 01:30:31', null);


-- ----------------------------
-- Records of t_user_talk_friend
-- ----------------------------
INSERT INTO `t_user_talk_friend` VALUES ('13b7a3a42832498c8253ba3227b33548', '0', 'silence', 'admin');
INSERT INTO `t_user_talk_friend` VALUES ('2e96cdcab1c04747b0865e9140593cbe', '0', 'batman', 'admin');
INSERT INTO `t_user_talk_friend` VALUES ('63dcdcd642cf403db76536ee67b3d42a', '0', 'saber', 'admin');
INSERT INTO `t_user_talk_friend` VALUES ('74579b02b0794796958830b28b36c4c5', '0', 'archer', 'admin');
INSERT INTO `t_user_talk_friend` VALUES ('d017cc4d0a0941c291f31a77b0d94e7c', '0', 'lancer', 'admin');
INSERT INTO `t_user_talk_friend` VALUES ('d11c42f934fd48609e0c7ed480d2e5c4', '0', 'ironman', 'admin');


-- ----------------------------
-- Records of t_user_talk_friend_group
-- ----------------------------
INSERT INTO `t_user_talk_friend_group` VALUES ('1', 'silence', '0', '机器人组');
INSERT INTO `t_user_talk_friend_group` VALUES ('2', 'saber', '0', '机器人组');
INSERT INTO `t_user_talk_friend_group` VALUES ('3', 'lancer', '0', '机器人组');
INSERT INTO `t_user_talk_friend_group` VALUES ('4', 'archer', '0', '机器人组');
INSERT INTO `t_user_talk_friend_group` VALUES ('6abb78045f424d108cb4de483dae7eaa', 'ironman', '0', '机器人组');
INSERT INTO `t_user_talk_friend_group` VALUES ('8963d52e261c4ed98b16e592837f048c', 'batman', '0', '机器人组');


-- ----------------------------
-- Records of t_user_talk_group
-- ----------------------------
INSERT INTO `t_user_talk_group` VALUES ('1', '0', 'silence', '大家庭', 'image/avatar/30.jpg');
INSERT INTO `t_user_talk_group` VALUES ('2', '0', 'saber', '大家庭', 'image/avatar/30.jpg');
INSERT INTO `t_user_talk_group` VALUES ('3', '0', 'lancer', '大家庭', 'image/avatar/30.jpg');
INSERT INTO `t_user_talk_group` VALUES ('4', '0', 'archer', '大家庭', 'image/avatar/30.jpg');
INSERT INTO `t_user_talk_group` VALUES ('8e4f22d8ffcc47879ae82930e19f37c2', '0', 'ironman', '大家庭', 'image/avatar/30.jpg');
INSERT INTO `t_user_talk_group` VALUES ('ed5ce115618843d8b2d4b3c09d7612fd', '0', 'batman', '大家庭', 'image/avatar/30.jpg');


-- ----------------------------
-- Records of t_user_talk_info
-- ----------------------------
INSERT INTO `t_user_talk_info` VALUES ('admin', '管理员', 'online', '我是机器人', 'image/touxiang.png');
INSERT INTO `t_user_talk_info` VALUES ('archer', '弓兵', 'hide', '世间万物皆系于一箭之上', 'image/avatar/4.jpg');
INSERT INTO `t_user_talk_info` VALUES ('batman', '蝙蝠侠', 'hide', '我的超能力是有钱', 'image/avatar/5.jpg');
INSERT INTO `t_user_talk_info` VALUES ('ironman', '钢铁侠', 'hide', 'I am Ironman', 'image/avatar/6.jpg');
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
