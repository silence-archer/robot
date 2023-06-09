package com.silence.robot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.silence.robot.context.GlobalContext;
import com.silence.robot.domain.LogFileDto;
import com.silence.robot.domain.QueryLogFileDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.enumeration.FileFormatEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.FileUtils;
import com.silence.robot.utils.HttpUtils;
import com.silence.robot.utils.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文件读取服务
 *
 * @author silence
 * @since 2021/8/23
 */
@Service
public class FileReadService {

    private final Logger logger = LoggerFactory.getLogger(FileReadService.class);

    @Resource
    private LogFileService logFileService;
    @Resource
    private LogFileReadService logFileReadService;
    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;

    public List<JSONObject> getFileHead(Path path) {
        JSONObject jsonObject = XmlUtils.xmlToJsonByPath(path);
        JSONObject fileConvert = jsonObject.getJSONObject("fileConvert");
        if (CommonUtils.isEmpty(fileConvert)) {
            throw new BusinessException(ExceptionCode.FILE_READ_ERROR);
        }
        JSONObject body = fileConvert.getJSONObject("body");
        JSONArray fields = body.getJSONArray("field");
        List<JSONObject> results = new ArrayList<>(fields.size());
        for (Object field : fields) {
            Map<String, Object> fieldObject = (LinkedHashMap) field;
            JSONObject result = new JSONObject();
            String name = CommonUtils.toString(fieldObject.get("@name"));
            String desc = CommonUtils.toString(fieldObject.get("@desc"));
            result.put("field", name);
            result.put("title", desc);
            results.add(result);
        }

        return results;
    }

    @Async
    public void addFileBody(Path path, String username) {
        List<String> lines = FileUtils.readAllLines(path);
        List<LogFileDto> list = new ArrayList<>(lines.size());

        lines.forEach(line -> {
            LogFileDto logFile = new LogFileDto();
            logFile.setBusinessType(username);
            logFile.setContent(line);
            list.add(logFile);
        });
        GlobalContext.getFirstUserOperationLock(username);
        logFileReadService.insertAndBatchSplit(list, 100);
        GlobalContext.getUserOperationLock(username);
    }

    public void addFileBody(String separator, List<JSONObject> heads) {
        LogFileDto logFileDto = new LogFileDto();
        logFileDto.setBusinessType(HttpUtils.getLoginUserName());
        FileFormatEnum fileFormatEnum = FileFormatEnum.getEnumByName(separator);
        if (CommonUtils.isEmpty(fileFormatEnum)) {
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 3; i < heads.size(); i++) {
            stringBuilder.append(fileFormatEnum.getValue());

        }
        logFileDto.setContent(stringBuilder.toString());
        logFileService.insert(logFileDto);
    }

    public void updateBatchFileBody(String separator, JSONObject data) {
        String updateName = data.getString("updateName");
        if (CommonUtils.isEmpty(updateName)) {
            throw new BusinessException(ExceptionCode.CHECK_NULL_ERROR);
        }
        String updateId = getQueryId(getHeads(data.getJSONArray("heads")), updateName);
        String updateValue = data.getString("updateValue") == null ? "" : data.getString("updateValue");
        RobotPage<JSONObject> robotPage = getFileBodyByCondition(separator, data, 1, Integer.MAX_VALUE);
        robotPage.getList().forEach(jsonObject -> {
            jsonObject.put(updateId, updateValue);
            LogFileDto logFileDto = new LogFileDto();
            logFileDto.setId(jsonObject.getString("id"));
            logFileDto.setContent(getContent(separator, jsonObject));
            logFileDto.setBusinessType(HttpUtils.getLoginUserName());
            logFileService.updateLogFile(logFileDto);
        });
    }

    public void updateFileBody(String separator, JSONArray heads, JSONObject jsonObject) {
        LogFileDto logFileDto = logFileService.getLogFileById(jsonObject.getString("id"));
        List<JSONObject> list = getHeads(heads);
        JSONObject result = getJsonObject(separator, list, logFileDto.getContent());
        result.put(jsonObject.getString("field"), jsonObject.get("value"));
        logFileDto.setContent(getContent(separator, result));
        logFileService.updateLogFile(logFileDto);


    }

    private String getContent(String separator, JSONObject result) {
        StringBuilder stringBuilder = new StringBuilder();
        result.forEach((s, o) -> {
            if (!"id".equals(s)) {
                stringBuilder.append(o).append(Objects.requireNonNull(FileFormatEnum.getEnumByName(separator)).getValue());
            }
        });
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    private List<JSONObject> getHeads(JSONArray heads) {
        List<JSONObject> list = new ArrayList<>();
        for (int i = 2; i < heads.size(); i++) {
            JSONObject headObject = new JSONObject((LinkedHashMap)heads.get(i));
            list.add(headObject);
        }
        return list;
    }

    public RobotPage<JSONObject> getFileBody(String separator, List<JSONObject> heads, Integer page, Integer limit) {
        String userName = HttpUtils.getLoginUserName();
        GlobalContext.releaseUserOperationLock(userName);
        QueryLogFileDto queryLogFileDto = new QueryLogFileDto();
        queryLogFileDto.setBusinessType(userName);
        RobotPage<LogFileDto> robotPage = logFileService.getLogFileByCondition(queryLogFileDto, page, limit);
        List<JSONObject> list = new ArrayList<>(robotPage.getList().size());
        robotPage.getList().forEach(logFileDto -> {
            JSONObject jsonObject = getJsonObject(separator, heads.subList(2, heads.size()), logFileDto.getContent());
            jsonObject.put("id", logFileDto.getId());
            list.add(jsonObject);

        });
        return new RobotPage<>(robotPage.getTotal(), list);


    }

    private JSONObject getJsonObject(String separator, List<JSONObject> heads, String content) {
        FileFormatEnum fileFormatEnum = FileFormatEnum.getEnumByName(separator);
        if (CommonUtils.isEmpty(fileFormatEnum)) {
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        String[] split = content.split(fileFormatEnum.getValue(), -1);
        if (split.length != heads.size()) {
            throw new BusinessException(ExceptionCode.FILE_READ_ERROR, content);
        }
        JSONObject jsonObject = new JSONObject(true);
        for (int i = 0; i < split.length; i++) {
            jsonObject.put(heads.get(i).getString("field"), split[i]);
        }
        return jsonObject;
    }

    public void deleteFileBody(List<JSONObject> list) {
        List<String> ids = new ArrayList<>(list.size());
        list.forEach(jsonObject -> ids.add(jsonObject.getString("id")));
        logFileService.deleteLogFiles(ids);
    }

    public void deleteFileBody(Integer fileSize) {
        String userName = HttpUtils.getLoginUserName();
        QueryLogFileDto logFileDto = new QueryLogFileDto();
        logFileDto.setBusinessType(userName);
        logFileService.newTxDeleteLogFile(logFileDto);
        GlobalContext.setUserOperationLock(userName, fileSize);
    }


    public RobotPage<JSONObject> getFileBodyByCondition(String separator, JSONObject data, Integer page, Integer limit) {
        List<JSONObject> heads = getHeads(data.getJSONArray("heads"));
        String queryName = data.getString("queryName");
        String queryValue = data.getString("queryValue") == null ? "" : data.getString("queryValue");
        List<LogFileDto> logFileDtos = logFileService.getLogFileByBusinessType(HttpUtils.getLoginUserName());
        List<JSONObject> result = new ArrayList<>(logFileDtos.size());
        String queryId = getQueryId(heads, queryName);

        logFileDtos.forEach(logFileDto -> {
            JSONObject jsonObject = getJsonObject(separator, heads, logFileDto.getContent());
            jsonObject.put("id", logFileDto.getId());
            if (CommonUtils.isEmpty(queryName)) {
                result.add(jsonObject);
            }else {
                if (CommonUtils.isNotEmpty(queryId)) {
                    jsonObject.forEach((s, o) -> {
                        if (s.equals(queryId)) {
                            if (CommonUtils.isAllEmpty(o, queryValue)) {
                                result.add(jsonObject);
                            } else if(o.toString().contains(queryValue) && CommonUtils.isNotEmpty(queryValue)) {
                                result.add(jsonObject);
                            }

                        }
                    });
                }

            }

        });
        return CommonUtils.getSubList(result, page, limit);
    }

    private String getQueryId(List<JSONObject> heads, String queryName) {
        JSONObject headObject = heads.stream().filter(head -> head.getString("title").equals(queryName)).findAny().orElse(null);
        String queryId = CommonUtils.isNotEmpty(headObject) ? headObject.getString("field") : null;
        if (CommonUtils.isNotEmpty(queryName) && CommonUtils.isEmpty(queryId)) {
            throw new BusinessException(ExceptionCode.QUERY_ERROR);
        }
        return queryId;
    }

    public File writeFile() {
        List<LogFileDto> logFileDtos = logFileService.getLogFileByBusinessType(HttpUtils.getLoginUserName());
        if (CommonUtils.isEmpty(logFileDtos)) {
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        StringBuilder stringBuilder = new StringBuilder();
        logFileDtos.forEach(logFileDto -> {
            stringBuilder.append(logFileDto.getContent()).append("\n");
        });
        String filePath = subscribeConfigInfoService.getConfigValue(ConfigEnum.UPLOAD_PATH_ENUM);
        String fileName = System.currentTimeMillis() + ".txt";
        FileUtils.writeFile(filePath, fileName, stringBuilder.substring(0, stringBuilder.length()-1));
        return new File(filePath + File.separator + fileName);
    }

    public synchronized String queryProgress(Integer fileSize) {
        AtomicInteger atomicInteger = GlobalContext.getRemainSize(HttpUtils.getLoginUserName());
        int remainSize = atomicInteger == null ? 0 : atomicInteger.get();
        logger.info("查询进度>>>>>>>>>>>>>>>>>>{}", remainSize);
        BigDecimal result = new BigDecimal(fileSize - remainSize).divide(new BigDecimal(fileSize), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        logger.info("查询进度>>>>>>>>>>>>>>>>>>{}", result);
        return result.setScale(0, RoundingMode.HALF_UP) + "%";
    }

}
