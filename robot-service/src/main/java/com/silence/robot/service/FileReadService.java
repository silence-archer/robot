package com.silence.robot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Path;
import java.util.*;

/**
 * 文件读取服务
 *
 * @author silence
 * @date 2021/8/23
 */
@Service
public class FileReadService {

    @Resource
    private LogFileService logFileService;
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

    public void addFileBody(Path path, String username) {
        List<String> lines = FileUtils.readAllLines(path);
        List<LogFileDto> list = new ArrayList<>(lines.size());
        QueryLogFileDto logFileDto = new QueryLogFileDto();
        logFileDto.setBusinessType(username);
        logFileService.deleteLogFileByCondition(logFileDto);
        lines.forEach(line -> {
            LogFileDto logFile = new LogFileDto();
            logFile.setBusinessType(username);
            logFile.setContent(line);
            list.add(logFile);
        });
        logFileService.insertAndBatch(list);
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

    public void updateFileBody(String separator, JSONArray heads, JSONObject jsonObject) {
        LogFileDto logFileDto = logFileService.getLogFileById(jsonObject.getString("id"));
        List<JSONObject> list = getHeads(heads);
        JSONObject result = getJsonObject(separator, list, logFileDto.getContent());
        result.put(jsonObject.getString("field"), jsonObject.get("value"));
        StringBuilder stringBuilder = new StringBuilder();
        result.forEach((s, o) -> stringBuilder.append(o).append(Objects.requireNonNull(FileFormatEnum.getEnumByName(separator)).getValue()));
        logFileDto.setContent(stringBuilder.substring(0, stringBuilder.length() - 1));
        logFileService.updateLogFile(logFileDto);


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
        QueryLogFileDto queryLogFileDto = new QueryLogFileDto();
        queryLogFileDto.setBusinessType(HttpUtils.getLoginUserName());
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
            throw new BusinessException(ExceptionCode.FILE_READ_ERROR);
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


    public RobotPage<JSONObject> getFileBodyByCondition(String separator, JSONObject data, Integer page, Integer limit) {
        List<JSONObject> heads = getHeads(data.getJSONArray("heads"));
        String queryName = data.getString("queryName");
        String queryValue = data.getString("queryValue") == null ? "" : data.getString("queryValue");
        List<LogFileDto> logFileDtos = logFileService.getLogFileByBusinessType(HttpUtils.getLoginUserName());
        List<JSONObject> result = new ArrayList<>(logFileDtos.size());
        JSONObject headObject = heads.stream().filter(head -> head.getString("title").equals(queryName)).findAny().orElse(null);
        String queryId = CommonUtils.isNotEmpty(headObject) ? headObject.getString("field") : null;
        logFileDtos.forEach(logFileDto -> {
            JSONObject jsonObject = getJsonObject(separator, heads, logFileDto.getContent());
            jsonObject.put("id", logFileDto.getId());
            if (CommonUtils.isEmpty(queryName)) {
                result.add(jsonObject);
            }else {
                if (CommonUtils.isNotEmpty(queryId)) {

                    jsonObject.forEach((s, o) -> {
                        if (s.equals(queryId) && o.toString().contains(queryValue)) {
                            result.add(jsonObject);
                        }
                    });
                }

            }

        });
        return CommonUtils.getSubList(result, page, limit);
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
}
