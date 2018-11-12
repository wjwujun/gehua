package com.gehua.service;


import com.gehua.common.enums.ExceptionEnum;
import com.gehua.common.exception.GehuaException;
import com.gehua.config.uploadConfig;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Service
@EnableConfigurationProperties(uploadConfig.class)
public class UploadService {




    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private uploadConfig prop;

    //private  static final List<String> allowTypes=Arrays.asList("image/png", "image/jpeg","image/bmp");
    private static final Logger log = LoggerFactory.getLogger(UploadService.class);

    public String uploadImage(MultipartFile file){
        try {

            //校验文件类型
            String contentType = file.getContentType();
            if (!prop.getAllowTypes().contains(contentType)){
                log.info("文件类型不存在:",file.getOriginalFilename());
                throw  new GehuaException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            //校验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image==null){
                log.info("文件内容不合法:",file.getOriginalFilename());
                throw  new GehuaException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            /*
            *保存到本地
            *this.getClass().getClassLoader().getResource();
            *File dir= new File("E:/javaproject/gehua/uploadImage/",file.getOriginalFilename());
            * file.transferTo(dir);
            * */

            /*
            * 保存到fastdfs
            * */
            String name=StringUtils.substringAfterLast(file.getOriginalFilename(),".");  //获取后缀名
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), name, null);

            //返回路径
            return prop.getBaseUrl()+storePath.getFullPath();

        } catch (IOException e) {
            //上传失败,抛出失败
            log.info("文件上传失败111！",e);
            throw new GehuaException(ExceptionEnum.UPLOAD_ERROR);
        }
    }
}
