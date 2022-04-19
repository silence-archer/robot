# robot
后台管理系统服务端
## 打包方式
在robot-web目录下进行mvn package
## 部署方式
将robot-web的jar，restart.sh，robot-web的resource目录下的ftl一并上传至服务器
## 启动方式
修改restart.sh脚本中的数据库链接，日志存放位置，端口等参数后，执行该shell脚本即可
### mysql
以mysql作为数据源，需要在restart.sh脚本中修改数据源为mysql，并将数据库链接地址进行修改，然后将robot-web的resource目录下的db脚本导入数据库中
### h2
以h2作为数据源，只需要在restart.sh脚本中修改数据源为h2即可
### sqlite
以mysql作为数据源，需要在restart.sh脚本中修改数据源为sqlite，并将数据库链接地址进行修改，然后将robot-web的resource目录下的db脚本导入数据库中