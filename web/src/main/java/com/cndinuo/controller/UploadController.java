package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by sunwei on 2017/8/28.
 */

@Controller
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @RequestMapping("/upload")
    public @ResponseBody RespData upload(@RequestParam(value="uploadFile") MultipartFile file) {

        return uploadService.upload(file);
    }


}
