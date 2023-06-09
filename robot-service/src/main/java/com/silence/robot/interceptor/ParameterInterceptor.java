/**
 * @title ParameterInterceptor.java
 * @author oe_machaohui
 * @since 2020/3/26 18:20
 * @copyright 2020 XXX有限公司版权所有
 */
package com.silence.robot.interceptor;

import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Properties;

/**
 * mybatis 插入更新操作拦截器
 * @author oe_machaohui
 * @since 2020/3/26 18:20
 */
@Intercepts({@Signature(type= Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class ParameterInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(ParameterInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor) invocation.getTarget();
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        if(SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
            Object id = BeanUtils.getProperty(args[1], "id");
            if (id == null) {
                BeanUtils.setProperty(args[1], "id", CommonUtils.getUuid());
            }
            BeanUtils.setProperty(args[1], "createTime", new Date());
            BeanUtils.setProperty(args[1], "createUser", HttpUtils.getLoginUserName());
            BeanUtils.setProperty(args[1], "updateTime", new Date());
            BeanUtils.setProperty(args[1], "updateUser", HttpUtils.getLoginUserName());
        }else if(SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType())) {
            BeanUtils.setProperty(args[1], "updateTime", new Date());
            BeanUtils.setProperty(args[1], "updateUser", HttpUtils.getLoginUserName());
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }

    }

    @Override
    public void setProperties(Properties properties) {

    }
}
