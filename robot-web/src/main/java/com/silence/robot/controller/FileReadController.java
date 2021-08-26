package com.silence.robot.controller;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.dto.DataRequest;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.DataDictService;
import com.silence.robot.service.FileReadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * 文件解析处理
 *
 * @author silence
 * @date 2021/8/21
 */
@RestController
public class FileReadController {

    private final Logger logger = LoggerFactory.getLogger(FileReadController.class);

    @Resource
    private DataDictService dataDictService;
    @Resource
    private FileReadService fileReadService;

    @PostMapping("/upload/uploadFileHead")
    public DataResponse<List<JSONObject>> uploadFileHead(@RequestParam("file") MultipartFile file) {
        Path path = dataDictService.uploadFile(file);
        return new DataResponse<>(fileReadService.getFileHead(path));
    }

    @PostMapping("/getFileBody")
    public DataResponse<List<JSONObject>> getFileBody(@RequestParam("separator") String separator, @RequestBody DataRequest<List<JSONObject>> request) {

        RobotPage<JSONObject> fileBody = fileReadService.getFileBody(separator, request.getData(), request.getPage(), request.getLimit());
        DataResponse<List<JSONObject>> dataResponse = new DataResponse<>(fileBody.getList());
        dataResponse.setCount(fileBody.getTotal());
        return dataResponse;
    }

    @PostMapping("/addFileBody")
    public DataResponse<?> addFileBody(@RequestParam("separator") String separator, @RequestBody List<JSONObject> data) {
        fileReadService.addFileBody(separator, data);
        return new DataResponse<>();
    }

    @PostMapping("/updateFileBody")
    public DataResponse<?> updateFileBody(@RequestParam String separator, @RequestBody JSONObject data) {
        fileReadService.updateFileBody(separator, data.getJSONArray("heads"), data);
        return new DataResponse<>();
    }

    @PostMapping("/deleteFileBody")
    public DataResponse<?> deleteFileBody(@RequestBody List<JSONObject> data) {
        fileReadService.deleteFileBody(data);
        return new DataResponse<>();
    }

    @PostMapping("/upload/uploadFileBody")
    public DataResponse<?> uploadFileBody(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {

        Path path = dataDictService.uploadFile(file);
        fileReadService.addFileBody(path, username);
        return new DataResponse<>();
    }

    @PostMapping("/getFileBodyByCondition")
    public DataResponse<List<JSONObject>> getFileBodyByCondition(@RequestParam String separator, @RequestBody DataRequest<JSONObject> request) {
        RobotPage<JSONObject> fileBody = fileReadService.getFileBodyByCondition(separator, request.getData(), request.getPage(), request.getLimit());
        DataResponse<List<JSONObject>> dataResponse = new DataResponse<>(fileBody.getList());
        dataResponse.setCount(fileBody.getTotal());
        return dataResponse;
    }

    @GetMapping("/fileDownload")
    public void fileDownload(HttpServletResponse response) {
        File file = fileReadService.writeFile();
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition","attachment;filename="+file.getName());
        response.addHeader("Content-Length",""+file.length());
        byte[] buffer = new byte[1024];
        try(FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
            ServletOutputStream outputStream = response.getOutputStream();
            int i = bufferedInputStream.read(buffer);
            while (i != -1) {
                outputStream.write(buffer, 0, i);
                i = bufferedInputStream.read(buffer);
            }
            outputStream.flush();
        }catch (IOException e) {
            logger.error("文件流读取失败", e);
        }

    }
}
