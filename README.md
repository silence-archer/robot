# robot
后台管理系统服务端
## H2连接字符串参数
DB_CLOSE_DELAY=-1：要求最后一个正在连接的连接断开后，不要关闭数据库
MODE=MySQL：兼容模式，H2兼容多种数据库，该值可以为：DB2、Derby、HSQLDB、MSSQLServer、MySQL、Oracle、PostgreSQL
AUTO_RECONNECT=TRUE：连接丢失后自动重新连接
AUTO_SERVER=TRUE：启动自动混合模式，允许开启多个连接，该参数不支持在内存中运行模式
TRACE_LEVEL_SYSTEM_OUT、TRACE_LEVEL_FILE：输出跟踪日志到控制台或文件， 取值0为OFF，1为ERROR（默认值），2为INFO，3为DEBUG
SET TRACE_MAX_FILE_SIZE mb：设置跟踪日志文件的大小，默认为16M