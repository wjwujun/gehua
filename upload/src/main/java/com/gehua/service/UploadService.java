package com.gehua.service;


import com.gehua.common.enums.ExceptionEnum;
import com.gehua.common.exception.GehuaException;
import junit.framework.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Service
public class UploadService {


    private  static final List<String> allowTypes=Arrays.asList("image/png", "image/jpeg","image/bmp");
    private static final Logger log = LoggerFactory.getLogger(UploadService.class);

    public String uploadImage(MultipartFile file){
        try {

            //校验文件类型
            String contentType = file.getContentType();
            if (!allowTypes.contains(contentType)){
                log.info("文件类型不存在:",file.getOriginalFilename());
                throw  new GehuaException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            //校验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image==null){
                log.info("文件内容不合法:",file.getOriginalFilename());
                throw  new GehuaException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            //准备目标路径
            //this.getClass().getClassLoader().getResource();
            File dir= new File("E:/javaproject/gehua/uploadImage/",file.getOriginalFilename());

            //保存文件到本地
            file.transferTo(dir);

            //返回路径
            return "http://localhost/"+file.getOriginalFilename();

        } catch (IOException e) {
            //上传失败,抛出失败
            log.info("文件上传失败111！",e);
            throw new GehuaException(ExceptionEnum.UPLOAD_ERROR);
        }
    }
}
