package com.silence.robot.service;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.constants.RobotConstants;
import com.silence.robot.domain.DataDiffDetailDto;
import com.silence.robot.domain.DataDiffDto;
import com.silence.robot.enumeration.BusinessTypeEnum;
import com.silence.robot.enumeration.DataSourceTypeEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TDataDictMapper;
import com.silence.robot.mapper.TDatabaseInfoMapper;
import com.silence.robot.model.TDataDict;
import com.silence.robot.model.TDatabaseInfo;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.DateUtils;
import com.silence.robot.utils.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 数据库操作服务
 *
 * @author silence
 * @date 2021/6/24
 */
@Service
public class DatabaseOperationService {

    private final Logger logger = LoggerFactory.getLogger(DatabaseOperationService.class);


    @Resource
    private TDatabaseInfoMapper databaseInfoMapper;

    @Resource
    private TDataDictMapper dataDictMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Value("${silence.loan.dayCutPath}")
    private String dayCutPath;
    public List<DataDiffDto> getDiffParams(String origBusinessType, String destBusinessType) {

        TDatabaseInfo origDatabaseInfo = databaseInfoMapper.selectByBusinessType(origBusinessType);
        TDatabaseInfo destDatabaseInfo = databaseInfoMapper.selectByBusinessType(destBusinessType);
        if(CommonUtils.isEmpty(origDatabaseInfo) || CommonUtils.isEmpty(destDatabaseInfo)) {
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }

        List<TDataDict> dataDicts = dataDictMapper.selectByName(RobotConstants.PARAMETER_TABLE);
        List<DataDiffDto> list = new ArrayList<>();
        dataDicts.parallelStream().forEach(dataDict -> {

            String sql = "select * from "+dataDict.getEnumName();

            List<JSONObject> origList = CommonUtils.getResultSetByDataBase(origDatabaseInfo.getType(), sql, origDatabaseInfo.getUrl(), origDatabaseInfo.getUser(), origDatabaseInfo.getPassword());
            List<JSONObject> destList = CommonUtils.getResultSetByDataBase(destDatabaseInfo.getType(), sql, destDatabaseInfo.getUrl(), destDatabaseInfo.getUser(), destDatabaseInfo.getPassword());

            Iterator<JSONObject> origIterator = origList.iterator();
            while (origIterator.hasNext()) {
                JSONObject origObject = origIterator.next();
                Iterator<JSONObject> destIterator = destList.iterator();
                while (destIterator.hasNext()) {
                    JSONObject destObject = destIterator.next();
                    if (origObject.equals(destObject)) {
                        logger.debug("开始对比，原值{}, 目标值{}", origObject, destObject);
                        logger.debug("比对成功>>>>>>>>>>>>");
                        destIterator.remove();
                        origIterator.remove();
                    }
                }
            }
            if (CommonUtils.isNotEmpty(origList) || CommonUtils.isNotEmpty(destList)) {
                logger.debug("原数据大小[{}], 目标数据大小[{}]", origList.size(), destList.size());
                DataDiffDto dataDiffDto = new DataDiffDto();
                dataDiffDto.setRunScript(sql);
                dataDiffDto.setEnumDesc(dataDict.getEnumDesc());
                dataDiffDto.setOrigValue(origList.toString());
                dataDiffDto.setDestValue(destList.toString());
                dataDiffDto.setEnumName(dataDict.getEnumName());
                dataDiffDto.setDataName(dataDict.getName());
                dataDiffDto.setBusinessType(origBusinessType);
                list.add(dataDiffDto);
            }

        });
        return list;
    }


    public List<DataDiffDto> getDiffData(String destBusinessType) {
        TDatabaseInfo destDatabaseInfo = databaseInfoMapper.selectByBusinessType(destBusinessType);
        if(CommonUtils.isEmpty(destDatabaseInfo)) {
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }

        List<TDataDict> dataDicts = dataDictMapper.selectByName(RobotConstants.SCRIPT_TABLE);
        List<DataDiffDto> list = new ArrayList<>();
        dataDicts.forEach(dataDict -> {

            String sql = dataDict.getEnumName();

            List<JSONObject> destList = CommonUtils.getResultSetByDataBase(destDatabaseInfo.getType(), sql, destDatabaseInfo.getUrl(), destDatabaseInfo.getUser(), destDatabaseInfo.getPassword());
            destList.get(0).forEach((s, o) -> {
                if (!o.equals(dataDict.getRemark())) {
                    DataDiffDto dataDiffDto = new DataDiffDto();
                    dataDiffDto.setRunScript(sql);
                    dataDiffDto.setEnumDesc(dataDict.getEnumDesc());
                    dataDiffDto.setOrigValue(dataDict.getRemark());
                    dataDiffDto.setDestValue(o.toString());
                    dataDiffDto.setEnumName(dataDict.getEnumName());
                    dataDiffDto.setDataName(dataDict.getName());
                    dataDiffDto.setBusinessType(destBusinessType);
                    list.add(dataDiffDto);
                }

            });

        });
        return list;
    }

    public List<DataDiffDetailDto> getDiffDetail(DataDiffDto dataDiffDto) {
        List<DataDiffDetailDto> list = new ArrayList<>();
        TDatabaseInfo databaseInfo = databaseInfoMapper.selectByBusinessType(dataDiffDto.getBusinessType());
        String[] split = dataDiffDto.getEnumName().split("\\.");
        String tableName = "";
        String owner = "";
        if (split.length > 1) {
            tableName = split[1];
            owner = split[0];
        }else {
            tableName = split[0];
        }
        String pkSql = CommonUtils.getPkQuerySql(databaseInfo.getType(), tableName, owner);
        List<JSONObject> pkList = CommonUtils.getResultSetByDataBase(databaseInfo.getType(), pkSql, databaseInfo.getUrl(), databaseInfo.getUser(), databaseInfo.getPassword());
        List<JSONObject> origArray = JSONObject.parseArray(dataDiffDto.getOrigValue(), JSONObject.class);
        List<JSONObject> destArray = JSONObject.parseArray(dataDiffDto.getDestValue(), JSONObject.class);
        Map<String, JSONObject> origMap = new HashMap<>(origArray.size());
        Map<String, JSONObject> destMap = new HashMap<>(destArray.size());
        origArray.forEach(origObject -> {
            JSONObject origPk = new JSONObject();
            pkList.forEach(pkObject -> {
                String pkName = pkObject.getString("COLUMN_NAME");
                String pkValue = origObject.getString(pkName);
                origPk.put(pkName, pkValue);
            });
            origMap.put(origPk.toJSONString(), origObject);
        });
        destArray.forEach(destObject -> {
            JSONObject destPk = new JSONObject();
            pkList.forEach(pkObject -> {
                String pkName = pkObject.getString("COLUMN_NAME");
                String pkValue = destObject.getString(pkName);
                destPk.put(pkName, pkValue);
            });
            if(origMap.containsKey(destPk.toJSONString())) {
                getJsonDiff(origMap.get(destPk.toJSONString()), destObject);
            }
            destMap.put(destPk.toJSONString(), destObject);

        });
        origMap.forEach((s, jsonObject) -> {
            DataDiffDetailDto dataDiffDetailDto = new DataDiffDetailDto();
            dataDiffDetailDto.setPrimalKey(s);
            dataDiffDetailDto.setOrigDetailValue(jsonObject.toJSONString());
            if(destMap.containsKey(s)) {
                dataDiffDetailDto.setDestDetailValue(destMap.get(s).toJSONString());
                destMap.remove(s);
            }
            list.add(dataDiffDetailDto);
        });
        destMap.forEach((s, jsonObject) -> {
            DataDiffDetailDto dataDiffDetailDto = new DataDiffDetailDto();
            dataDiffDetailDto.setPrimalKey(s);
            dataDiffDetailDto.setDestDetailValue(jsonObject.toJSONString());
            list.add(dataDiffDetailDto);
        });
        return list;
    }

    private void getJsonDiff(JSONObject origObject, JSONObject destObject) {
        Iterator<Map.Entry<String, Object>> origIterator = origObject.entrySet().iterator();

        while (origIterator.hasNext()) {
            Map.Entry<String, Object> origNext = origIterator.next();
            String origKey = origNext.getKey();
            String origValue = origNext.getValue().toString();
            Iterator<Map.Entry<String, Object>> destIterator = destObject.entrySet().iterator();
            while (destIterator.hasNext()) {
                Map.Entry<String, Object> destNext = destIterator.next();
                String destKey = destNext.getKey();
                String destValue = destNext.getValue().toString();
                if (origKey.equals(destKey) && origValue.equals(destValue)) {
                    origIterator.remove();
                    destIterator.remove();
                }
            }
        }

    }

    public void loan306DayCut(String date, String dbType) {
        dayCut(date, "dayCut.sql", dbType);
    }

    public void loan306DayCutInit(String date, String dbType) {
        dayCut(date, "dayCutInit.sql", dbType);
    }

    private void dayCut(String date, String fileName, String dbType) {
        for (BusinessTypeEnum value : BusinessTypeEnum.getByDbType(dbType)) {
            TDatabaseInfo databaseInfo = databaseInfoMapper.selectByBusinessType(value.getCode());
            if(CommonUtils.isEmpty(databaseInfo)) {
                throw new BusinessException(ExceptionCode.NO_EXIST);
            }
            String content = FileUtils.getFileContent(dayCutPath+value.getDbType()+ File.separator+value.getBusinessKind()+File.separator+fileName);
            content = content.replace("${tranDate}", date).replace("${lastTranDate}", Objects.requireNonNull(
                DateUtils.addDay(date, -1))).replace("${nextTranDate}", Objects.requireNonNull(DateUtils.addDay(date, 1))).replace(";","");
            if(CommonUtils.isNotEmpty(content)) {
                List<String> sqls = Arrays.asList(content.split("\r\n"));
                CommonUtils.executeBatchSql(databaseInfo.getType(), sqls, databaseInfo.getUrl(), databaseInfo.getUser(), databaseInfo.getPassword());
            }
        }
        stringRedisTemplate.delete(Objects.requireNonNull(stringRedisTemplate.keys("*")));
    }
}
