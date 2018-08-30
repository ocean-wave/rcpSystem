package cn.com.cdboost.collect.filter;

import java.io.File;
import java.io.FilenameFilter;

public class MyFilenameFilter implements FilenameFilter {
	
	private String type;  
    public MyFilenameFilter(String type){  
        this.type = type;  
    } 
	@Override
	public boolean accept(File dir, String name) {
		return name.contains(type);
	}

}
