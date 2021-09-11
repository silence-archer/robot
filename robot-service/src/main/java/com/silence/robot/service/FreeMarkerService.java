package com.silence.robot.service;

import com.silence.robot.domain.DataDictDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.freemarker.FreeMarkerArrayDto;
import com.silence.robot.domain.freemarker.FreeMarkerBodyDto;
import com.silence.robot.domain.freemarker.FreeMarkerDto;
import com.silence.robot.enumeration.AutoInterfaceEnum;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.enumeration.FilePathEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * FreeMarker  文件输出
 *
 * @author silence
 * @date 2021/9/5
 */
@Service
public class FreeMarkerService {

    private final Logger logger = LoggerFactory.getLogger(FreeMarkerService.class);

    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;
    @Resource
    private DataDictService dataDictService;
    @Resource
    private MenuService menuService;

    public FreeMarkerDto getInterfaceFreeMarker(String uri, String name, Integer port, String content) {
        FreeMarkerDto freeMarkerDto = new FreeMarkerDto();
        freeMarkerDto.setUri(uri);
        freeMarkerDto.setName(name);
        freeMarkerDto.setPort(port);
        //入参赋值
        String[] lines = content.split("\n");
        List<FreeMarkerBodyDto> freeMarkerBodyDtos = new ArrayList<>();
        List<FreeMarkerArrayDto> freeMarkerArrayDtos = new ArrayList<>();
        freeMarkerDto.setBodys(freeMarkerBodyDtos);
        freeMarkerDto.setArrays(freeMarkerArrayDtos);
        String arrayName = null;
        boolean arrayFlag = false;
        for (String line : lines) {
            String[] split = line.split("\t");
            if (split.length != 5) {
                throw new BusinessException(ExceptionCode.QUERY_ERROR);
            }
            if (CommonUtils.isEquals(split[2], "Array") && CommonUtils.isNotEquals(arrayName, split[0])) {
                if (arrayFlag) {
                    throw new BusinessException(ExceptionCode.JSON_PARSE_ERROR);
                }
                arrayFlag = true;
                FreeMarkerArrayDto freeMarkerArrayDto = new FreeMarkerArrayDto();
                freeMarkerArrayDto.setName(split[0]);
                freeMarkerArrayDto.setDesc(split[1]);
                freeMarkerArrayDto.setList(new ArrayList<>());
                freeMarkerArrayDtos.add(freeMarkerArrayDto);
                arrayName = split[0];
                continue;
            }
            if (CommonUtils.isEquals(split[2], "Array") && CommonUtils.isEquals(arrayName, split[0])) {
                arrayFlag = false;
            }

            FreeMarkerBodyDto freeMarkerBodyDto = new FreeMarkerBodyDto();
            freeMarkerBodyDto.setType("input");
            RobotPage<DataDictDto> robotPage = dataDictService.getDataDictList(split[0], 1, 1);
            if (robotPage.getTotal() > 0) {
                freeMarkerBodyDto.setType("select");
            }

            freeMarkerBodyDto.setRequired(CommonUtils.isEqualsY(split[4]));
            freeMarkerBodyDto.setName(split[0]);
            freeMarkerBodyDto.setDesc(split[1]);
            if (arrayFlag) {
                freeMarkerArrayDtos.get(freeMarkerArrayDtos.size()-1).getList().add(freeMarkerBodyDto);
            }else {
                freeMarkerBodyDtos.add(freeMarkerBodyDto);
            }

        }

        return freeMarkerDto;

    }

    public String getFileName(String name, AutoInterfaceEnum autoInterfaceEnum) {
        String rootPath = subscribeConfigInfoService.getConfigValue(ConfigEnum.AUTO_INTERFACE_PATH_ENUM);
        String filePath = String.format(FilePathEnum.AUTO_INTERFACE_PATH.getCode(), rootPath, name);
        String fileName = filePath + autoInterfaceEnum.getCode();
        FileUtils.createFile(fileName);
        return fileName;
    }



    public void autoUpdateMenu(String name, String desc) {
        String rootPath = subscribeConfigInfoService.getConfigValue(ConfigEnum.AUTO_INTERFACE_PATH_ENUM);
        String filePath = String.format(FilePathEnum.AUTO_INTERFACE_COMMON_PATH.getCode(), rootPath);
        //
        String fileName = "commonservice.js";
        String contents = FileUtils.readAllContents(filePath, fileName);
        if (contents.contains(name)) {
            return;
        }
        String line = "\ndocument.write(\"<script language=javascript src='robot/autoInterface/"+name+"/loan.js'></script>\");\n";
        String resultLine = "document.write(\"<script language=javascript src='robot/autoInterface/"+name+"/result/result.js'></script>\");";
        contents = contents + line + resultLine;
        FileUtils.writeFile(filePath, fileName, contents);
        //
        fileName = "main-route.js";
        List<String> lines = FileUtils.readAllLines(filePath, fileName);
        String routeLine = "\n}).when('/autoInterface/"+name+"',{\n" +
                "        templateUrl: 'robot/autoInterface/"+name+"/loan.html',\n" +
                "        controller: '"+name+"Controller'\n" +
                "    }).when('/autoInterface/"+name+"/result',{\n" +
                "        templateUrl: 'robot/autoInterface/"+name+"/result/result.html',\n" +
                "        controller: '"+name+"ResultController'\n";
        lines.add(lines.size()-4, routeLine);
        FileUtils.writeFile(filePath, fileName, FileUtils.convertListToContent(lines));
        menuService.addAutoInterfaceMenu(name, desc);

    }


}
