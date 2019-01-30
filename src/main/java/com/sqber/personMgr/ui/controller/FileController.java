package com.sqber.personMgr.ui.controller;

import com.sqber.personMgr.base.StringUtil;
import com.sqber.personMgr.entity.FileUploadResult;
import com.sqber.personMgr.ui.config.FileUploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Map;

@Controller
public class FileController {

    private final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileUploadConfig fileUploadConfig;

    /* 文件上传 */
    @PostMapping("file/upload")
    @ResponseBody
    public FileUploadResult upload(HttpServletRequest request) {

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");

//        MultiValueMap<String, MultipartFile> map = multipartHttpServletRequest.getMultiFileMap();
//        for (Map.Entry<String, List<MultipartFile>> item : map.entrySet()){
//               String key = item.getKey();
//               List<MultipartFile> files = item.getValue();
//        }

        try {
            byte[] content = file.getBytes();

            String fileName = file.getOriginalFilename();
            String extName = fileName.substring(fileName.lastIndexOf("."));

            if (!extName.equals(".jpg") && !extName.equals(".png")) {
                return new FileUploadResult(0, "文件类型不对,请上传jpg,png格式的文件", "");
            }

            String filePath = getFilePath(file);
            FileOutputStream outputStream = new FileOutputStream(filePath);
            outputStream.write(content);

            outputStream.close();

            return new FileUploadResult(1, "", getImgPath(filePath, request));

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage() + e.getStackTrace());
            return new FileUploadResult(0, e.getMessage(), "");
        }

    }

    private String getImgPath(String path, HttpServletRequest request) {
        String ext = path.replaceAll(fileUploadConfig.getSavePath(), "");
        String[] arr = ext.split("/");

        String contextRoot = request.getContextPath();
        return String.format(contextRoot + "/file/get?fileName=%s", ext);
    }

    private String getFilePath(MultipartFile file) throws Exception {

        //String path = DatamonitorApplication.class.getResource("").getFile();
        //System.out.println("path:" + path);

        String guid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = file.getOriginalFilename();
        String extName = fileName.substring(fileName.lastIndexOf("."));
        String fileNameWithoutExt = fileName.replaceAll(extName, "");
        String fileNewName = fileNameWithoutExt + "-" + guid + extName;
        String filePath = fileUploadConfig.getSavePath() + fileNewName;

        File dir = new File(fileUploadConfig.getSavePath());
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success)
                throw new Exception("目录创建失败:" + dir);
        }

        return filePath;
    }

    @GetMapping("file/get")
    public void get(String type, String fileName, Integer w, Integer h, Integer o, HttpServletResponse response) throws IOException {
        String filePath = fileUploadConfig.getSavePath() + "/" + fileName;
        File image = new File(filePath);
        FileInputStream inputStream = new FileInputStream(image);

        //如果不是显示原图
        if (o == null || o != 1) {

            BufferedImage prevImage = ImageIO.read(inputStream);
            int width = prevImage.getWidth();
            int height = prevImage.getHeight();

            int newWidth = width;
            int newHeight = height;

            /*缩放*/
            if (w != null) {
                double percent = w / width;
                newWidth = (int) (width * percent);
                newHeight = (int) (height * percent);

                if (h != null)
                    newHeight = h;
            }

            Graphics2D oldG = prevImage.createGraphics();

            BufferedImage newimage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
            Graphics2D graphics = newimage.createGraphics();

            graphics.setBackground(oldG.getBackground());
            graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);

            //添加水印
            addMark(graphics);

            response.setHeader("Content-Type", "image/jpeg");
            OutputStream toClient = response.getOutputStream();
            ImageIO.write(newimage, "jpeg", toClient);
            toClient.flush();

        } else {

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

    private void addMark(Graphics2D g) {
        int imgHeight = 30;
        g.setColor(Color.gray);
        // 画笔字体
        g.setFont(new Font("宋体", Font.PLAIN, imgHeight - 10));
        //输入汉字，必须设置字体，且 系统中必须有对应字体，否则会乱码。
        String content = "www.sqber.com";
        g.drawString(content, 5, imgHeight - 10);
    }
}
