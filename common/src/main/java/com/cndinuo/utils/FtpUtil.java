package com.cndinuo.utils;


import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 * 文件服务器上传工具
 * @author dgb
 *
 */
public class FtpUtil {
	
	private static String basePath;
	
	public static void main(String args[]) throws IOException{
		InputStream io = new FileInputStream("F://aaa.jpg");
		String base= "/opt/tomcat7070/webapps/upload";
		FtpUtil ftp = new FtpUtil();
		ftp.connect("60.205.229.163", "dgp","dgp",base);
		boolean result = ftp.uploadInputStream("20150202","aaa.jpg",io);
		System.out.println(result);
		ftp.close();
	}
	
	private static FTPClient ftpClient = new FTPClient();
	
	public static void connect(String ftpUrl,String ftpUser,String ftpPassword,String base) throws SocketException, IOException{
		ftpClient.connect(ftpUrl); 
        ftpClient.login(ftpUser, ftpPassword);
        basePath = base;
	}
	
	/** 
     * FTP上传单个文件
	 * @throws IOException 
     */ 
    public static synchronized boolean uploadInputStream(String dirPath,String fileName,InputStream in) throws IOException {

    	ftpClient.changeWorkingDirectory(basePath);
        /**
         * 创建目录
         */
    	if(!StringUtil.isEmpty(dirPath)){
    		ftpClient.makeDirectory(dirPath);
    		ftpClient.changeWorkingDirectory(dirPath);
    	}
        ftpClient.setBufferSize(1024); 
        ftpClient.setControlEncoding("UTF-8"); 
        //设置文件类型（二进制） 
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
        boolean result = ftpClient.storeFile(fileName, in);
        IOUtils.closeQuietly(in);

        return result;
    }
    
	public static void close() throws IOException{
		ftpClient.disconnect(); 
	}
}
