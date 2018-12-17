package com.sqber.personMgr.ui.controller;

import com.sqber.personMgr.ui.config.FileUploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class FileController {

    private final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileUploadConfig fileUploadConfig;

    /* 文件上传 */
    @PostMapping("file/upload")
    @ResponseBody
    public String upload(HttpServletRequest request) {

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");

        try {
            byte[] content = file.getBytes();

            String fileName = file.getOriginalFilename();
            String extName = fileName.substring(fileName.lastIndexOf("."));

            if (!extName.equals(".jpg") && !extName.equals(".png"))
                return "extError";

            String filePath = getFilePath(file);
            FileOutputStream outputStream = new FileOutputStream(filePath);
            outputStream.write(content);

            outputStream.close();

            return "ok" + getImgPath(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage() + e.getStackTrace());
            return "error";
        }

    }

    private String getImgPath(String path) {
        String ext = path.replaceAll(fileUploadConfig.getSavePath(), "");
        String[] arr = ext.split("/");
        return String.format("/file/get?type=%s&fileName=%s", arr[1], arr[2]);
    }

    private String getFilePath(MultipartFile file) throws Exception {

        //String path = DatamonitorApplication.class.getResource("").getFile();
        //System.out.println("path:" + path);

        String guid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = file.getOriginalFilename();
        String extName = fileName.substring(fileName.lastIndexOf("."));
        String fileNameWithoutExt = fileName.replaceAll(extName, "");
        String fileNewName = fileNameWithoutExt + "-" + guid + extName;
        String filePath = fileUploadConfig.getSavePath() + "/logo/" + fileNewName;

        File dir = new File(fileUploadConfig.getSavePath() + "/logo/");
        if (!dir.exists()) {
            boolean success = dir.mkdir();
            if(!success)
                throw new Exception("目录创建失败:" + dir);
        }

        return filePath;
    }

    @GetMapping("file/get")
    public void get(String type,String fileName,HttpServletResponse response) throws IOException {
        String filePath = fileUploadConfig.getSavePath() + "/" + type +"/"  + fileName;
        File image = new File(filePath);
        FileInputStream inputStream = new FileInputStream(image);
        int length = inputStream.available();
        byte data[] = new byte[length];
        inputStream.read(data);

        response.setContentLength(length);
        response.setHeader("Content-Type", "image/jpeg");
        OutputStream toClient = response.getOutputStream();
        toClient.write(data);
        toClient.flush();
    }
}
