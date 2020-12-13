package com.silence.robot.controller;

import com.silence.robot.dto.DataResponse;
import com.silence.robot.utils.Excelutils;
import org.apache.http.HttpHeaders;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * 文件上传下载controller
 *
 * @author silence
 * @date 2020/12/6
 */
@RestController
public class FileController {

    @PostMapping("/terr/interface/exportAll/excel")
    public void getExcel(HttpServletRequest request, HttpServletResponse response) {

        Workbook workbook = Excelutils.createExcel("测试1", Arrays.asList("测试2", "测试3", "测试4"));


        OutputStream fos = null;
        try {
            fos = response.getOutputStream();
            String userAgent = request.getHeader("USER-AGENT");
            String fileName = "test";
            try {
                if(userAgent.contains("Mozilla")){
                    fileName = new String(fileName.getBytes(), "ISO8859-1");
                }else {
                    fileName = URLEncoder.encode(fileName, "utf8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");// 设置contentType为excel格式
            response.setHeader("Content-Disposition", "Attachment;Filename="+ fileName+".xls");
            workbook.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
