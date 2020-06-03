/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TransactionAdviceConfig
 * Author:   silence
 * Date:     2019/10/10 17:01
 * Description: 全局事务控制
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.aop;

import com.silence.robot.config.DynamicDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈全局事务控制〉
 *
 * @author silence
 * @create 2019/10/10
 * @since 1.0.0
 */
@Configuration
public class TransactionAdviceConfig {

    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.silence.robot.service.*.*(..))";

    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSourceProperties getWriteDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean("writeDataSource")
    public DataSource getWriteDataSource(){
        return getWriteDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSourceProperties getReadDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean("readDataSource")
    public DataSource getReadDataSource(){
        return getReadDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public DynamicDataSource getDynamicDataSource(@Qualifier("writeDataSource") DataSource writeDataSource, @Qualifier("readDataSource") DataSource readDataSource){
        List<DataSource> readDataSources = new ArrayList<>(2);
        readDataSources.add(readDataSource);
        readDataSources.add(readDataSource);
        DynamicDataSource dynamicDataSource = new DynamicDataSource(writeDataSource, readDataSources);
        return dynamicDataSource;
    }

    @Bean
    public TransactionInterceptor txAdvice(){
        DefaultTransactionAttribute txAttrRequired = new DefaultTransactionAttribute();
        DefaultTransactionAttribute txAttrReadOnly = new DefaultTransactionAttribute();
        txAttrRequired.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttrReadOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttrReadOnly.setReadOnly(true);
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("add*",txAttrRequired);
        source.addTransactionalMethod("update*",txAttrRequired);
        source.addTransactionalMethod("delete*",txAttrRequired);
        source.addTransactionalMethod("insert*",txAttrRequired);
        source.addTransactionalMethod("modify*",txAttrRequired);
        source.addTransactionalMethod("reset*",txAttrRequired);

        source.addTransactionalMethod("select*",txAttrReadOnly);
        source.addTransactionalMethod("find*",txAttrReadOnly);
        source.addTransactionalMethod("get*",txAttrReadOnly);
        source.addTransactionalMethod("query*",txAttrReadOnly);

        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(platformTransactionManager, source);
        return transactionInterceptor;

    }

    @Bean
    public Advisor txAdviceAdvisor(){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        Advisor advisor = new DefaultPointcutAdvisor(pointcut,txAdvice());
        return advisor;
    }

}