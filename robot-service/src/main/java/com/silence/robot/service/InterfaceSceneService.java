package com.silence.robot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.context.GlobalContext;
import com.silence.robot.domain.InterfaceSceneDropdownDto;
import com.silence.robot.domain.InterfaceSceneDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TInterfaceSceneMapper;
import com.silence.robot.model.TInterfaceScene;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 挡板服务
 *
 * @author silence
 * @since 2020/10/7
 */
@Service
public class InterfaceSceneService {

    private final Logger logger = LoggerFactory.getLogger(InterfaceSceneService.class);

    @Resource
    private TInterfaceSceneMapper interfaceSceneMapper;

    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;

    @Resource
    private SequenceService sequenceService;

    public RobotPage<InterfaceSceneDto> getSceneByTranCode(String tranCode, Integer page, Integer limit) {
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        List<TInterfaceScene> interfaceScenes = interfaceSceneMapper.selectByTranCode(tranCode);
        PageInfo<TInterfaceScene> pageInfo = new PageInfo<>(interfaceScenes);
        return new RobotPage<>(pageInfo.getTotal(), BeanUtils.copyList(InterfaceSceneDto.class, interfaceScenes));
    }

    public JSONObject getSceneBySceneIdVersion3(String sceneId) {
        TInterfaceScene interfaceScene = interfaceSceneMapper.selectBySceneId(sceneId);
        if (interfaceScene == null) {
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        JSONObject jsonObject = JSONObject.parseObject(interfaceScene.getSceneValue());
        JSONObject sysHead = jsonObject.getJSONObject("sysHead");
        sysHead.forEach((s, o) -> {
            sysHead.put(s, o);
            if (CommonUtils.isNotEquals(s, "seqNo") && CommonUtils.isNotEquals(s, "subSeqNo")) {
                sysHead.put(s, getConfigValue(o.toString()));
            }

        });
        JSONObject body = jsonObject.getJSONObject("body");
        JSONObject result = getBody(body);
        jsonObject.put("sysHead", sysHead);
        jsonObject.put("body", result);
        return jsonObject;
    }

    public JSONObject getSceneBySceneIdVersion2(String sceneId) {
        TInterfaceScene interfaceScene = interfaceSceneMapper.selectBySceneId(sceneId);
        if (interfaceScene == null) {
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        JSONObject jsonObject = JSONObject.parseObject(interfaceScene.getSceneValue());
        JSONObject sysHead = jsonObject.getJSONObject("SYS_HEAD");
        sysHead.forEach((s, o) -> {
            sysHead.put(s, o);
            if (CommonUtils.isNotEquals(s, "SEQ_NO")) {
                sysHead.put(s, getConfigValue(o.toString()));
            }

        });
        JSONObject body = jsonObject.getJSONObject("BODY");
        CommonUtils.updateJsonArray(body, "scene");
        JSONObject result = getBody(body);
        jsonObject.put("SYS_HEAD", sysHead);
        jsonObject.put("BODY", result);
        return jsonObject;
    }

    public String getSceneBySceneIdVersion217(String sceneId) {
        TInterfaceScene interfaceScene = interfaceSceneMapper.selectBySceneId(sceneId);
        if (interfaceScene == null) {
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        String sceneValue = interfaceScene.getSceneValue();
        StringBuilder stringBuilder = new StringBuilder();
        int start = 0, end = 0;
        while (sceneValue.contains("{{")) {
            end = sceneValue.indexOf("{{");
            stringBuilder.append(sceneValue, start, end);
            start = end+2;
            end = sceneValue.indexOf("}}");
            stringBuilder.append(GlobalContext.getHttpClientContext(sceneValue.substring(start, end)));
            sceneValue = sceneValue.substring(end+2);
            start = 0;
        }
        stringBuilder.append(sceneValue);
        return stringBuilder.toString();

    }

    private JSONObject getBody(JSONObject body) {
        JSONObject result = new JSONObject();
        body.forEach((s, o) -> {
            if (o instanceof List) {
                JSONArray jsonArray = body.getJSONArray(s);
                JSONArray resultArray = new JSONArray();
                result.put(s, resultArray);
                for (Object arrayObject : jsonArray) {
                    if (arrayObject instanceof List) {
                        throw new BusinessException(ExceptionCode.JSON_PARSE_ERROR);
                    }
                    JSONObject jsonArrayObject = (JSONObject) arrayObject;
                    jsonArrayObject.forEach((s1, o1) -> {
                        if (o1 instanceof List) {
                            throw new BusinessException(ExceptionCode.JSON_PARSE_ERROR);
                        }

                        jsonArrayObject.put(s1, getConfigValue(o1.toString()));


                    });
                    resultArray.add(jsonArrayObject);
                }
            } else {
                result.put(s, getConfigValue(o.toString()));
            }
        });
        return result;
    }

    private String getConfigValue(String configName) {
        String configValue = configName;
        if (configName.contains("{{") && configName.contains("}}")) {
            configName = configName.replace("{{", "");
            configName = configName.replace("}}", "");
            if (CommonUtils.isEquals("$timestamp", configName)) {
                configValue = System.currentTimeMillis() + "";
            } else {
                configValue = subscribeConfigInfoService.getConfigValue(configName, HttpUtils.getLoginUserName());
            }
        }
        return configValue;
    }

    public RobotPage<InterfaceSceneDto> getScene(Integer page, Integer limit) {

        PageHelper.startPage(page, limit);
        List<TInterfaceScene> interfaceScenes = interfaceSceneMapper.selectAll();
        PageInfo<TInterfaceScene> pageInfo = new PageInfo<>(interfaceScenes);
        return new RobotPage<>(pageInfo.getTotal(), BeanUtils.copyList(InterfaceSceneDto.class, interfaceScenes));
    }

    public void addInterfaceScene(InterfaceSceneDto interfaceSceneDto) {
        checkJson(interfaceSceneDto.getSceneValue());
        interfaceSceneDto.setSceneId("Scene" + sequenceService.getSequence("Scene"));
        TInterfaceScene interfaceScene = BeanUtils.copy(TInterfaceScene.class, interfaceSceneDto);
        interfaceSceneMapper.insert(interfaceScene);
    }

    public void updateInterfaceScene(InterfaceSceneDto interfaceSceneDto) {
        checkJson(interfaceSceneDto.getSceneValue());
        TInterfaceScene interfaceScene = BeanUtils.copy(TInterfaceScene.class, interfaceSceneDto);
        interfaceSceneMapper.updateByPrimaryKey(interfaceScene);
    }

    public void deleteInterfaceScene(String id) {
        interfaceSceneMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatchInterfaceScene(List<String> ids) {
        interfaceSceneMapper.deleteBatchByPrimaryKey(ids);
    }

    private void checkJson(String sceneValue) {
        String configValue = subscribeConfigInfoService.getConfigValue(ConfigEnum.FREE_MARKER_VERSION_ENUM);
        if ("2.0".equals(configValue)) {
            JSONObject jsonObject = JSONObject.parseObject(sceneValue);
            if (CommonUtils.existEmpty(jsonObject.getJSONObject("SYS_HEAD"), jsonObject.getJSONObject("BODY"))) {
                throw new BusinessException(ExceptionCode.JSON_TEXT_ERROR);
            }
        } else if ("3.0".equals(configValue)) {
            JSONObject jsonObject = JSONObject.parseObject(sceneValue);
            if (CommonUtils.existEmpty(jsonObject.getJSONObject("sysHead"), jsonObject.getJSONObject("body"))) {
                throw new BusinessException(ExceptionCode.JSON_TEXT_ERROR);
            }
        }

    }

    public List<InterfaceSceneDropdownDto> getSceneTranCode() {
        List<InterfaceSceneDropdownDto> list = new ArrayList<>();
        interfaceSceneMapper.selectAll().stream().collect(Collectors.groupingBy(TInterfaceScene::getTranCode)).forEach((s, tInterfaceScenes) -> {
            InterfaceSceneDropdownDto interfaceSceneDropdownDto = new InterfaceSceneDropdownDto();
            interfaceSceneDropdownDto.setTitle(tInterfaceScenes.get(0).getTranDesc());
            interfaceSceneDropdownDto.setId(s);
            interfaceSceneDropdownDto.setType("group");
            List<InterfaceSceneDropdownDto> child = tInterfaceScenes.stream().map(tInterfaceScene -> {
                InterfaceSceneDropdownDto dropdownDto = new InterfaceSceneDropdownDto();
                dropdownDto.setType("normal");
                dropdownDto.setId(tInterfaceScene.getSceneId());
                dropdownDto.setTitle(tInterfaceScene.getSceneDesc());
                return dropdownDto;
            }).collect(Collectors.toList());
            interfaceSceneDropdownDto.setChild(child);
            list.add(interfaceSceneDropdownDto);
        });
        return list;
    }

    public JSONObject getSceneBySceneId(String sceneId) {
        TInterfaceScene interfaceScene = interfaceSceneMapper.selectBySceneId(sceneId);
        if (interfaceScene == null) {
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        return JSONObject.parseObject(JSONObject.toJSONString(interfaceScene));
    }
}
