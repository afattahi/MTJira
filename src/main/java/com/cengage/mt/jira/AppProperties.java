package com.cengage.mt.jira;
import java.io.File;
import java.util.Properties;


public class AppProperties {
	
	private static Properties appProperties ;//= new Properties();
	
	public  static void setProperties(Properties p) {
		appProperties = p;
	}
	
	public static String getOutDir() {
		
		return appProperties.getProperty("dir.out");
	}
	
	
	public static String getSnapshotDir() {
		
		return appProperties.getProperty("dir.snapshot");
	}

}
