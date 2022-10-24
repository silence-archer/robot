package com.silence.robot.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.silence.robot.clock.MailSendService;
import com.silence.robot.domain.freemarker.CommemoratingFreeMarkerArrayDto;
import com.silence.robot.domain.freemarker.CommemoratingFreeMarkerDto;
import com.silence.robot.enumeration.AutoInterfaceEnum;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.enumeration.FileFormatEnum;
import com.silence.robot.mapper.TCommemoratingInfoMapper;
import com.silence.robot.model.TCommemoratingInfo;
import com.silence.robot.utils.DateUtils;
import com.silence.robot.utils.FreeMarkerUtil;

@Service
public class CommemoratingInfoService {

    @Resource
    private TCommemoratingInfoMapper commemoratingInfoMapper;
    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;
    @Resource
    private FreeMarkerUtil freeMarkerUtil;
    @Resource
    private MailSendService mailSendService;

    public CommemoratingFreeMarkerDto generateDto() {
        List<TCommemoratingInfo> commemoratingInfos = commemoratingInfoMapper.selectAll();
        TCommemoratingInfo firstCommemoratingInfo = commemoratingInfos.stream()
            .filter(commemoratingInfo -> DateUtils.convert(commemoratingInfo.getCommemoratingDate())
                .getMonthValue() == LocalDate.now()
                .getMonthValue() && DateUtils.convert(commemoratingInfo.getCommemoratingDate())
                .getDayOfMonth() == LocalDate.now()
                .getDayOfMonth())
            .findAny()
            .orElse(null);
        if (firstCommemoratingInfo == null) {
            return null;
        }
        CommemoratingFreeMarkerDto commemoratingFreeMarkerDto = new CommemoratingFreeMarkerDto();
        commemoratingFreeMarkerDto.setDesc(firstCommemoratingInfo.getCommemoratingDesc());
        commemoratingFreeMarkerDto.setYear(LocalDate.now()
            .getYear() - DateUtils.convert(firstCommemoratingInfo.getCommemoratingDate())
            .getYear());
        List<CommemoratingFreeMarkerArrayDto> commemoratingFreeMarkerArrayDtos = commemoratingInfos.stream()
            .filter(commemoratingInfo -> commemoratingInfo.getCommemoratingDate()
                .compareTo(firstCommemoratingInfo.getCommemoratingDate()) <= 0)
            .sorted(Comparator.comparing(TCommemoratingInfo::getCommemoratingDate)
                .reversed())
            .map(commemoratingInfo -> {
                CommemoratingFreeMarkerArrayDto commemoratingFreeMarkerArrayDto = new CommemoratingFreeMarkerArrayDto();
                commemoratingFreeMarkerArrayDto.setNow(commemoratingInfo.getCommemoratingName().equals(firstCommemoratingInfo.getCommemoratingName()));
                commemoratingFreeMarkerArrayDto.setDate(DateUtils.convert(commemoratingInfo.getCommemoratingDate())
                    .format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")));
                commemoratingFreeMarkerArrayDto.setImageName(commemoratingInfo.getImageName());
                commemoratingFreeMarkerArrayDto.setList(Arrays.asList(commemoratingInfo.getCommemoratingStory()
                    .split(FileFormatEnum.VERTICAL_SEPARATOR.getValue())));
                return commemoratingFreeMarkerArrayDto;
            })
            .collect(Collectors.toList());
        commemoratingFreeMarkerDto.setBodys(commemoratingFreeMarkerArrayDtos);
        return commemoratingFreeMarkerDto;
    }

    public void generate(CommemoratingFreeMarkerDto commemoratingFreeMarkerDto) {
        String filename = subscribeConfigInfoService.getConfigValue(ConfigEnum.COMMEMORATING_PATH_ENUM) + AutoInterfaceEnum.COMMEMORATING.getCode();
        freeMarkerUtil.getFreeMarker(AutoInterfaceEnum.COMMEMORATING.getValue(), filename, commemoratingFreeMarkerDto);
    }

    public void send(CommemoratingFreeMarkerDto commemoratingFreeMarkerDto) {
        String subject = commemoratingFreeMarkerDto.getDesc()+"纪念日快乐";

        String text = String.format("<a href='%s'>%s年前的今天我们%s啦!</a>", subscribeConfigInfoService.getConfigValue(ConfigEnum.COMMEMORATING_URL), commemoratingFreeMarkerDto.getYear(), commemoratingFreeMarkerDto.getDesc());
        mailSendService.sendHtml(subject, text, subscribeConfigInfoService.getConfigValue(ConfigEnum.COMMEMORATING_MAIL_ADDRESS));
    }
}
