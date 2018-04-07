package com.cndinuo.service;

import com.cndinuo.common.RespData;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    RespData upload(MultipartFile file);

    String uploadByteArr(String byteStr, String fileName);
}
