package com.cndinuo.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件类
 * @author dgb
 */
public class FileUtil {

	public static void copyFile(String sourceFilename, String targetFilename) throws IOException {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(sourceFilename));
			out = new BufferedOutputStream(new FileOutputStream(targetFilename));

			byte[] b = new byte[4096];
			int len;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.flush();
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}

	public static String getFileExt(String filename) {
		if (filename != null && filename.length() > 0 && !filename.trim().equals("")) {
			int i = filename.lastIndexOf('.');
			if ((i > 0) && (i < (filename.length() - 1))) {
				return filename.toLowerCase().substring(i + 1);
			}
		}
		return "";
	}

	public static String getFileName(String filename) {
		if (filename != null && filename.length() > 0 && !filename.trim().equals("")) {
			int i = filename.lastIndexOf('.');
			if ((i > 0) && (i < (filename.length() - 1))) {
				return filename.substring(0, i);
			} else {
				return filename;
			}
		}
		return "";
	}

	public static String setFileName(String filename, String context) {
		return getFileName(filename) + context + "." + getFileExt(filename);
	}

	public static String getMd5Filename(String filename, String key) {
		if (filename != null && filename.length() > 0 && !filename.trim().equals("")) {
			int i = filename.lastIndexOf('.');
			if ((i > 0) && (i < (filename.length() - 1))) {
				return MD5.encode(filename.substring(0, i) + key) + "." + filename.toLowerCase().substring(i + 1);
			} else {
				return MD5.encode(filename + key);
			}
		}
		return "";
	}

	public static long getFileSize(String filename) {
		File file = new File(filename);
		if (file.exists() && file.isFile()) {
			return file.length();
		} else {
			return 0;
		}
	}
	public static void createPath(String path) {
		File file = new File(path);
		if (!file.exists() && !file.isFile()) {
			  file.mkdirs();
		} else {
			System.out.println(path+"=is already or not Directory");
		}
	}

	public static boolean existsPath(String path) {
		File file = new File(path);
		return file.exists();
	}

	public static String getFileIcon(String ext) {
		if(StringUtil.isEmpty(ext)){
			return "file.png";
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("jpg", "pic.png");
		map.put("jpeg", "pic.png");
		map.put("png", "pic.png");
		map.put("gif", "pic.png");
		map.put("doc", "word.png");
		map.put("docx", "word.png");
		map.put("xls", "excel.png");
		map.put("xlsx", "excel.png");
		map.put("ppt", "ppt.png");
		map.put("pptx", "ppt.png");
		map.put("pdf", "pdf.png");
		map.put("rar", "rar.png");
		map.put("zip", "rar.png");

		String icon = map.get(ext.toLowerCase());
		if (icon == null) {
			icon = "file.png";
		}

		return icon;
	}
}