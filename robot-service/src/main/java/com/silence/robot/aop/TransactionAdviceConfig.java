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
import com.silence.robot.interceptor.DynamicDataSourceInterceptor;
import com.silence.robot.interceptor.ParameterInterceptor;
import com.silence.robot.utils.SpringContextHelper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Bean("hikariConfig")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig getHikariConfig(){
        return new HikariConfig();
    }


    @Bean("writeDataSourceProperties")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSourceProperties getWriteDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean("writeDataSource")
    @Primary
    public DataSource getWriteDataSource(@Qualifier("writeDataSourceProperties") DataSourceProperties dataSourceProperties, @Qualifier("hikariConfig") HikariConfig hikariConfig){
        HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setConnectionTimeout(hikariConfig.getConnectionTimeout());
        dataSource.setMaximumPoolSize(hikariConfig.getMaximumPoolSize());
        return dataSource;
    }

    @Bean("readDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSourceProperties getReadDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean("readDataSource")
    public DataSource getReadDataSource(@Qualifier("readDataSourceProperties") DataSourceProperties dataSourceProperties, @Qualifier("hikariConfig") HikariConfig hikariConfig){
        HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setConnectionTimeout(hikariConfig.getConnectionTimeout());
        dataSource.setMaximumPoolSize(hikariConfig.getMaximumPoolSize());
        return dataSource;
    }

    @Bean("dynamicDataSource")
    public DynamicDataSource getDynamicDataSource(@Qualifier("writeDataSource") DataSource writeDataSource, @Qualifier("readDataSource") DataSource readDataSource){
        List<DataSource> readDataSources = new ArrayList<>(2);
        readDataSources.add(readDataSource);
        readDataSources.add(readDataSource);
        return new DynamicDataSource(writeDataSource, readDataSources);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource, MybatisProperties properties) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setPlugins(new DynamicDataSourceInterceptor(), new ParameterInterceptor());
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(properties.getConfigLocation())) {
            factory.setConfigLocation(SpringContextHelper.getResource(properties.getConfigLocation()));
        }
        org.apache.ibatis.session.Configuration configuration = properties.getConfiguration();
        if (configuration == null && !StringUtils.hasText(properties.getConfigLocation())) {
            configuration = new org.apache.ibatis.session.Configuration();
        }
        factory.setConfiguration(configuration);
        if (properties.getConfigurationProperties() != null) {
            factory.setConfigurationProperties(properties.getConfigurationProperties());
        }
        if (StringUtils.hasLength(properties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        }
        if (properties.getTypeAliasesSuperType() != null) {
            factory.setTypeAliasesSuperType(properties.getTypeAliasesSuperType());
        }
        if (StringUtils.hasLength(properties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(properties.resolveMapperLocations())) {
            factory.setMapperLocations(properties.resolveMapperLocations());
        }
        Set<String> factoryPropertyNames = Stream
                .of(new BeanWrapperImpl(SqlSessionFactoryBean.class).getPropertyDescriptors()).map(PropertyDescriptor::getName)
                .collect(Collectors.toSet());
        Class<? extends LanguageDriver> defaultLanguageDriver = properties.getDefaultScriptingLanguageDriver();
        if (factoryPropertyNames.contains("defaultScriptingLanguageDriver")) {
            // Need to mybatis-spring 2.0.2+
            factory.setDefaultScriptingLanguageDriver(defaultLanguageDriver);
        }

        return factory.getObject();
    }

    @Bean
    public TransactionInterceptor txAdvice(){
        DefaultTransactionAttribute txAttrRequired = new DefaultTransactionAttribute();
        DefaultTransactionAttribute txAttrReadOnly = new DefaultTransactionAttribute();
        DefaultTransactionAttribute txAttrRequiredNew = new DefaultTransactionAttribute();
        txAttrRequired.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttrReadOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        txAttrReadOnly.setReadOnly(true);
        txAttrRequiredNew.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("add*",txAttrRequired);
        source.addTransactionalMethod("update*",txAttrRequired);
        source.addTransactionalMethod("delete*",txAttrRequired);
        source.addTransactionalMethod("insert*",txAttrRequired);
        source.addTransactionalMethod("modify*",txAttrRequired);
        source.addTransactionalMethod("reset*",txAttrRequired);
        source.addTransactionalMethod("register*",txAttrRequired);

        source.addTransactionalMethod("select*",txAttrReadOnly);
        source.addTransactionalMethod("find*",txAttrReadOnly);
        source.addTransactionalMethod("get*",txAttrReadOnly);
        source.addTransactionalMethod("query*",txAttrReadOnly);


        source.addTransactionalMethod("newTx*",txAttrRequiredNew);

        return new TransactionInterceptor(platformTransactionManager, source);

    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut,txAdvice());
    }

}