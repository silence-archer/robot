package com.silence.robot.interceptor;


import com.silence.robot.utils.DataSourceContextHelper;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Properties;
/**
 * 动态数据切换拦截器
 * @author silence
 * @date 2020/5/29 23:57
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class DynamicDataSourceInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        //获取是否存在事务
        boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
        switch (mappedStatement.getSqlCommandType()) {
            case SELECT:
                DataSourceContextHelper.read();
                break;
            case DELETE:
            case INSERT:
            case UPDATE:
                DataSourceContextHelper.write();
                break;
            default:
                break;
        }
        logger.info("当前执行的sql类型为[{}], ID为[{}]", mappedStatement.getSqlCommandType(), mappedStatement.getId());
        Object proceed = invocation.proceed();
        DataSourceContextHelper.clear();
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
