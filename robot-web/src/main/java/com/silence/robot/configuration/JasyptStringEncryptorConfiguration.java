/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: JasyptStringEncryptorConfiguration
 * Author:   silence
 * Date:     2019/12/5 15:36
 * Description: 数据库信息加密配置
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉<br> 
 * 〈数据库信息加密配置〉
 *
 * @author silence
 * @since 2019/12/5
 * 
 */
@Configuration
public class JasyptStringEncryptorConfiguration {


    /**
     * 必须使用默认的bean名称
     * @return
     */
    @Bean("jasyptStringEncryptor")
    public StringEncryptor getStringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword("robot");
        return encryptor;
    }

}