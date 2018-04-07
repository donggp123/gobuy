package com.cndinuo.service.impl;

import com.cndinuo.common.RespData;
import com.cndinuo.service.SysSettingService;
import com.cndinuo.service.UploadService;
import com.cndinuo.utils.Base64;
import com.cndinuo.utils.FileUtil;
import com.cndinuo.utils.StringUtil;
import com.cndinuo.utils.UploadToOss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadServiceImpl implements UploadService {

    private static final Logger log = LoggerFactory.getLogger("service");

    @Autowired
    private SysSettingService settingService;


    @Override
    public RespData upload(MultipartFile file) {
        String dirPath = null;
        String newFileName = null;
        boolean result = false;
        String imgServer = settingService.getByKey("img_server");
        try {
            dirPath = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String fileName = file.getOriginalFilename();
            log.info("*********************************开始上传文件");
            newFileName = FileUtil.getMd5Filename(fileName,StringUtil.toString(System.currentTimeMillis()));
            InputStream in = file.getInputStream();
            UploadToOss oss = new UploadToOss();
            result = oss.uploadPic(in,dirPath+"/"+newFileName);
            log.info("*********************************结束上传文件");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("上传失败，系统异常!"+e);
            return RespData.errorMsg("上传失败，系统异常!");
        }
        if (result) {
            Map<String, Object> retData = new HashMap<String, Object>();
            retData.put("imgServer", imgServer);
            retData.put("imgPath", String.format("%s/%s", dirPath, newFileName));
            return RespData.successMsg("上传成功", retData);
        }
        return RespData.errorMsg("上传失败！");
    }

    @Override
    public String uploadByteArr(String byteStr, String fileName) {
        String dirPath = null;
        boolean result = false;
        String newFileName = null;
        String imgServer = settingService.getByKey("img_server");
        try {
            byte[] b = Base64.decode(byteStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            ByteArrayInputStream in = new ByteArrayInputStream(b);
            log.info("*********************************开始上传文件");
            newFileName = FileUtil.getMd5Filename(fileName, StringUtil.toString(System.currentTimeMillis()));
            dirPath = new SimpleDateFormat("yyyyMMdd").format(new Date());
            UploadToOss oss = new UploadToOss();
            result = oss.uploadPic(in,dirPath+"/"+newFileName);
            log.info("*********************************结束上传文件");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传失败，系统异常!"+e);
            return null;
        }

        if (result) {
            return String.format("%s@%s/%s", imgServer, dirPath, newFileName);
        }
        return null;
    }
}
