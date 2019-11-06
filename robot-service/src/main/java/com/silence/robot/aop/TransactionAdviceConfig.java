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

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 〈一句话功能简述〉<br> 
 * 〈全局事务控制〉
 *
 * @author silence
 * @create 2019/10/10
 * @since 1.0.0
 */
@Aspect
@Configuration
public class TransactionAdviceConfig {

    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.silence.robot.service.*.*(..))";

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public TransactionInterceptor txAdvice(){
        DefaultTransactionAttribute txAttrRequired = new DefaultTransactionAttribute();
        txAttrRequired.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("add*",txAttrRequired);
        source.addTransactionalMethod("update*",txAttrRequired);
        source.addTransactionalMethod("delete*",txAttrRequired);
        source.addTransactionalMethod("insert*",txAttrRequired);
        source.addTransactionalMethod("modify*",txAttrRequired);

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