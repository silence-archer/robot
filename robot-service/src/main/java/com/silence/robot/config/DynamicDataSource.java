/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: DynamicDataSource
 * Author:   silence
 * Date:     2020/4/21 22:58
 * Description: 动态数据源配置
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.config;

import com.silence.robot.enumeration.DataSourceTypeEnum;
import com.silence.robot.utils.DataSourceContextHelper;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈一句话功能简述〉<br> 
 * 〈动态数据源配置〉
 *
 * @author silence
 * @create 2020/4/21
 * @since 1.0.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private AtomicInteger count = new AtomicInteger(0);

    public DynamicDataSource(DataSource writeDataSource, List<DataSource> readDataSources) {
        super.setDefaultTargetDataSource(writeDataSource);
        Map<Object, Object> targetDataSources = new Hashtable<>(readDataSources.size()+1);
        targetDataSources.put("write",writeDataSource);
        AtomicInteger atomicInteger = new AtomicInteger();
        readDataSources.forEach(readDataSource -> {
            targetDataSources.put("read-"+atomicInteger.getAndIncrement(), readDataSource);
        });
        super.setTargetDataSources(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceKey;
        if(DataSourceTypeEnum.WRITE == DataSourceContextHelper.get()) {
            dataSourceKey = "write";
        }else {
            int i = count.getAndIncrement() % 2;
            dataSourceKey = "read-"+i;
        }
        return dataSourceKey;
    }
}