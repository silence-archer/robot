package com.silence.robot.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.silence.robot.utils.Excelutils;
import com.silence.robot.utils.FileUtils;
import com.silence.robot.utils.HttpUtils;

/**
 * 文件上传下载controller
 *
 * @author silence
 * @since 2020/12/6
 */
@RestController
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${robot.outside.url}")
    private String outsideUri;

    @PostMapping("/terr/interface/exportAll/excel")
    public void exportExcelByUri(HttpServletRequest request, HttpServletResponse response) {
        HttpPost httpPost = new HttpPost(outsideUri);
        StringBuilder data = new StringBuilder();

        try {
            BufferedReader reader = request.getReader();
            String line;
            while((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            logger.error("获取post请求入参失败", e);
        }
        HttpEntity httpEntity = new StringEntity(data.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(httpEntity);
        CloseableHttpResponse httpResponse = HttpUtils.httpClientExecuteByStream(httpPost);

        InputStream inputStream;
        try {
            inputStream = httpResponse.getEntity().getContent();
            Header[] allHeaders = httpResponse.getAllHeaders();
            for (Header header : allHeaders) {
                response.setHeader(header.getName(), header.getValue());
            }
            ServletOutputStream outputStream = response.getOutputStream();
            int ch;
            while ((ch = inputStream.read()) != -1) {
                outputStream.write(ch);
            }
            outputStream.flush();
        } catch (IOException e) {
            logger.error("使用流失败",e);
        }

    }
    @PostMapping("/getExcel")
    public void getExcel(HttpServletRequest request, HttpServletResponse response) {

        List<List<String>> list = new ArrayList<>();
        list.add(new ArrayList<>());
        Workbook workbook = Excelutils.createExcel("test", list);

        downloadExcel(request, response, workbook);

    }

    @PostMapping("/convertCsvToExcel")
    public void convertCsvToExcel(HttpServletRequest request, HttpServletResponse response) {
        String filepath = request.getParameter("filepath");
        String filename = request.getParameter("filename");
        List<List<String>> list = FileUtils.readCsvFile(filepath, filename);
        Workbook workbook = Excelutils.createExcel("test", list);
        downloadExcel(request, response, workbook);

    }

    private void downloadExcel(HttpServletRequest request, HttpServletResponse response, Workbook workbook) {
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
                logger.error("下载文件编码解析失败", e);
            }

            response.setCharacterEncoding("UTF-8");
            // 设置contentType为excel格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "Attachment;Filename="+ fileName+".xls");
            workbook.write(fos);
            fos.close();
        } catch (IOException e) {
            logger.error("下载文件失败", e);
        }
    }
}
