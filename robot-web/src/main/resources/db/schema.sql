create table t_cron_task
(
    ID varchar(64) not null comment '物理主键'
        primary key,
    job_name varchar(64) not null comment '任务名称',
    cron_expr varchar(30) not null comment 'cron表达式',
    job_desc varchar(200) null comment '任务描述',
    job_class varchar(60) null comment '任务执行类名',
    effect_flag varchar(1) not null comment '生效标志',
    create_user varchar(20) null comment '创建人',
    create_time datetime not null comment '创建时间',
    update_user varchar(20) null comment '更新人',
    update_time datetime not null comment '更新时间',
    constraint t_cron_task_job_name_uindex
        unique (job_name)
) comment='定时任务配置表';

create table t_cron_task_proc_log
(
    ID varchar(64) not null comment '物理主键'
        primary key,
    job_name varchar(64) not null comment '任务名称',
    job_desc varchar(256) null comment '任务描述',
    proc_status varchar(10) null comment '执行状态',
    error_msg varchar(256) null comment '错误信息',
    create_time datetime not null comment '创建时间',
    create_user varchar(64) null comment '创建人',
    update_time datetime not null comment '更新时间',
    update_user varchar(64) null comment '更新人',
    constraint t_cron_task_proc_log_job_name_uindex
        unique (job_name)
) comment='定时任务执行日志表';

create table t_data_dict
(
    id varchar(54) not null comment '物理主键'
        primary key,
    name varchar(20) null comment '名称',
    `desc` varchar(255) null comment '描述',
    enum_name varchar(20) null comment '枚举值',
    enum_desc varchar(255) null comment '枚举值描述',
    remark varchar(255) null comment '备注'
) comment='数据字典表';

create table t_database_info
(
    id varchar(50) not null comment '物理主键'
        primary key,
    type varchar(20) null comment '数据库类型',
    url varchar(32) null comment '数据库链接',
    user varchar(32) null comment '用户名',
    password varchar(32) null comment '密码',
    business_type varchar(32) null comment '业务类型'
) comment='数据库配置表';

create table t_file_config
(
    id varchar(64) not null comment '物理主键'
        primary key,
    filename varchar(32) not null comment '文件名',
    file_desc varchar(200) not null comment '文件描述',
    remote_filepath varchar(64) not null comment '远程文件路径',
    local_filepath varchar(64) not null comment '本地文件路径',
    remote_ip varchar(32) not null comment '远程服务器ip',
    remote_port int(10) not null comment '远程服务器端口',
    remote_username varchar(32) not null comment '远程服务器用户名',
    remote_password varchar(32) not null comment '远程服务器密码',
    remote_secret_key varchar(32) not null comment '远程服务器密钥',
    transfer_type varchar(20) not null comment '传输类型',
    business_type varchar(20) not null comment '业务类型',
    create_user varchar(20) null comment '创建人',
    create_time datetime not null comment '创建时间',
    update_user varchar(20) null comment '更新人',
    update_time datetime not null comment '更新时间'
) comment='文件配置表';

create table t_interface_scene
(
    id varchar(64) not null comment '物理主键'
        primary key,
    tran_code varchar(32) null comment '交易码',
    scene_id varchar(32) null comment '场景编号',
    scene_desc varchar(255) null comment '场景描述',
    scene_value longtext null comment '场景json报文',
    create_time datetime null comment '创建时间',
    create_user varchar(32) null comment '创建人',
    update_time datetime null comment '更新时间',
    update_user varchar(32) null comment '更新人'
) comment='接口场景配置表';

create table t_log_file
(
    id varchar(40) not null comment '物理主键'
        primary key,
    date_time datetime null comment '日志时间',
    service_name varchar(30) null comment '系统名称',
    trace_id varchar(50) null comment '流水号',
    tran_code varchar(30) null comment '交易码',
    thread_name varchar(20) null comment '线程名称',
    level varchar(10) null comment '日志级别',
    class_name varchar(30) null comment '类名称',
    line_num int null comment '行号',
    content longtext null comment '日志内容',
    sub_trace_id varchar(30) null comment '子流水号',
    business_type varchar(20) null comment '业务类型'
) comment='日志信息表';

create table t_menu
(
    ID varchar(32) not null comment '物理主键'
        primary key,
    MENU_NO varchar(64) default '' null comment '菜单编号',
    MENU_NAME varchar(64) default '' null comment '菜单名称',
    MENU_PATH varchar(60) default '' null comment '菜单路径',
    PARENT_MENU_NO varchar(64) default '' null comment '父菜单编号',
    MENU_LEVEL int(10) null comment '菜单级别',
    MENU_SEQ_NO int(10) null comment '菜单序号',
    CREATE_TIME datetime not null comment '创建时间',
    CREATE_USER varchar(32) default '' null comment '创建人',
    UPDATE_TIME datetime not null comment '修改时间',
    UPDATE_USER varchar(32) default '' null comment '修改人',
    constraint MENU_UQ_IDX
        unique (MENU_NO)
)comment='菜单信息表';

create table t_mock_info
(
    ID varchar(64) not null comment '物理主键'
        primary key,
    MOCK_URL varchar(64) null comment '挡板url',
    MOCK_NAME varchar(255) null comment '挡板名称',
    MOCK_MODULE varchar(20) null comment '挡板模块',
    MOCK_INPUT varchar(4000) null comment '挡板入参',
    MOCK_OUTPUT varchar(4000) null comment '挡板出参',
    CREATE_USER varchar(64) null comment '创建人',
    CREATE_TIME datetime null comment '创建时间',
    UPDATE_USER varchar(64) null comment '更新人',
    UPDATE_TIME datetime null comment '更新时间'
)comment='挡板信息';

create table t_role
(
    ID varchar(32) not null comment '物理主键'
        primary key,
    ROLE_NO varchar(64) null comment '角色编号',
    ROLE_NAME varchar(64) null comment '角色名称',
    MENU_NO varchar(60) null comment '菜单ID',
    CREATE_TIME datetime not null comment '创建时间',
    CREATE_USER varchar(32) null comment '创建人',
    UPDATE_TIME datetime not null comment '修改时间',
    UPDATE_USER varchar(32) null comment '修改人'
)comment='角色信息表';

create table t_sequence
(
    id varchar(32) not null comment '物理主键'
        primary key,
    seq_name varchar(255) null comment '序列名称',
    seq_value int(10) null comment '序列值',
    constraint SEQ_UQ_IDX
        unique (seq_name)
)comment='序列表';

create table t_subscribe_config_info
(
    ID varchar(64) not null comment '物理主键'
        primary key,
    config_name varchar(64) not null comment '配置名称',
    config_value varchar(256) not null comment '配置值',
    config_desc varchar(256) null comment '配置描述',
    expire_time int null comment '超时时间',
    create_time datetime not null comment '创建时间',
    create_user varchar(64) null comment '创建人',
    update_time datetime null comment '更新时间',
    update_user varchar(64) null comment '更新人',
    constraint t_subscribe_config_info_config_name_uindex
        unique (config_name)
)comment='微信订阅号配置信息表';

create table t_svn_info
(
    ID varchar(32) not null comment '物理主键'
        primary key,
    url varchar(255) null comment 'svn地址',
    local_url varchar(255) null comment '本地存储地址',
    remark varchar(255) null comment '备注',
    over_flag varchar(1) null comment '检出成功标志',
    constraint SVN_UQ_IDX
        unique (url)
)comment='SVN信息表';

create table t_user
(
    ID varchar(32) not null comment '物理主键'
        primary key,
    USERNAME varchar(64) not null comment '用户名',
    NICKNAME varchar(64) null comment '昵称',
    PASSWORD varchar(200) null comment '密码',
    ROLE_NO varchar(32) null comment '角色编号',
    CREATE_TIME datetime not null comment '创建时间',
    CREATE_USER varchar(32) null comment '创建人',
    UPDATE_TIME datetime not null comment '修改时间',
    UPDATE_USER varchar(32) null comment '修改人',
    IP_ADDR varchar(20) null comment 'ip地址',
    constraint UQ_IDX
        unique (USERNAME)
)comment='用户信息表';

create table t_user_talk_friend
(
    id varchar(64) not null comment '物理主键'
        primary key,
    group_id int null comment '分组id',
    mine_id varchar(64) null comment '我的id',
    friend_id varchar(64) null comment '朋友id'
)comment='用户即时聊天好友信息表';

create table t_user_talk_friend_group
(
    id varchar(64) not null comment '物理主键'
        primary key,
    mine_id varchar(64) null comment '我的ID',
    group_id int null comment '分组ID',
    groupname varchar(255) null comment '分组名称'
)comment='即时聊天好友分组信息表';

create table t_user_talk_group
(
    id varchar(64) not null comment '唯一标识'
        primary key,
    group_id varchar(64) null comment '群聊ID',
    mine_id varchar(64) null comment '我的id',
    groupname varchar(255) null comment '群组名称',
    avatar varchar(255) null comment '群组头像地址'
)comment='用户即时聊天群组信息表';

create table t_user_talk_info
(
    id varchar(64) default '' not null comment '用户编号'
        primary key,
    username varchar(255) null,
    status varchar(20) null comment '状态',
    sign varchar(255) null comment '签名',
    avatar varchar(255) null comment '头像地址'
)comment='用户即时聊天信息表';

create table t_user_talk_members
(
    id varchar(64) not null comment '唯一标识'
        primary key,
    group_id varchar(64) null comment '群组id',
    member_id varchar(64) null comment '成员Id'
) comment='用户即时聊天群组成员信息表';

