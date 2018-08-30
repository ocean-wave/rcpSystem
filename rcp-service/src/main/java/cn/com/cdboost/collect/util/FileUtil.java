package cn.com.cdboost.collect.util;

import java.io.File;

public class FileUtil {
	
	//文件清理
	public static void deleteFile(File parentFile, long minute) {
		long nowTimeStamp = System.currentTimeMillis();
		File[] files = parentFile.listFiles();
		for(File file : files) {
			if(nowTimeStamp - file.lastModified() > minute*60*1000) {
				file.delete();
			}
		}
	}
	
}
