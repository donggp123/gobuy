package com.cndinuo.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;

import java.io.InputStream;

public class UploadToOss {

	public boolean uploadPic(InputStream inputStream , String key) {
		// endpoint以北京为例，其它region请按实际情况填写
		String endpoint = "oss-cn-shenzhen.aliyuncs.com";

		String accessKeyId = "LTAI1LcEJy3qeuhz";
		String accessKeySecret = "lmkcuKXAdnYyjMw6aUTIM8gFaSR3FS";
		String bucketName = "cndinuo";
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);		
		// 上传文件流
		PutObjectResult putObject = ossClient.putObject(bucketName , key , inputStream);
		String requestId = putObject.getRequestId();
		ossClient.shutdown();
		if (requestId != null) {
			return true;
		}
		return false;
	}

}
