package com.digitexx.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	public static String HOST;
	public static int PORT;
	public static String USERNAME;
	public static String PASSWORD;
	public static String DB_NAME;
	public static String db_uat_eclaim;
	public static String DB_COLLECTION;
	public static String DB_NAME_TOOL;
	public static String DB_COLLECTION_TOOL;
	public static String CONFIG_EXPORT;
	public static String CRON_TRIGGER;

	public static void configDB(){
		String classpath = System.getProperty("java.class.path");
		
		if(System.getProperty("os.name").contains("linux")){
			classpath = classpath.replace(":", ";");
			
		}
		String[] arrclassPath = classpath.split(";");
		String file="";
		if(arrclassPath.length>0){
			classpath = arrclassPath[0];
		}
		file = getJarPath()+"/config/config.properties";
		
		File f = new File(file);
		
		if(f.exists()){
			Properties propertyConfig = new Properties();
			try {
				propertyConfig.load(new FileInputStream(f));
				Configuration.HOST = propertyConfig.getProperty("host");
				Configuration.PORT = Integer.parseInt(propertyConfig.getProperty("port"));
				Configuration.USERNAME = propertyConfig.getProperty("username")==null ? "" : propertyConfig.getProperty("username");
				Configuration.PASSWORD = propertyConfig.getProperty("password")== null ? "" : propertyConfig.getProperty("password");
				Configuration.DB_NAME = propertyConfig.getProperty("db_name")== null ? "" : propertyConfig.getProperty("db_name");
				Configuration.DB_COLLECTION = propertyConfig.getProperty("db_collection")== null ? "" : propertyConfig.getProperty("db_collection");
				Configuration.DB_COLLECTION_TOOL = propertyConfig.getProperty("db_collection_tool");
				Configuration.db_uat_eclaim = propertyConfig.getProperty("db_uat_eclaim");
				Configuration.CONFIG_EXPORT = propertyConfig.getProperty("config_export");
				Configuration.DB_NAME_TOOL = propertyConfig.getProperty("db_name_tool");
				Configuration.CRON_TRIGGER = propertyConfig.getProperty("cron_trigger");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	private static String getJarPath(){
		String path = System.getProperty("java.class.path");
		if(System.getProperty("os.name").contains("Linux")){
			path = path.replace(":", ";");
		}
		if(path.contains(";")){
			path = path.split(";")[0].replace("\\", "/");
		}
		if(path.lastIndexOf("/") > -1) {
			path = path.substring(0, path.lastIndexOf("/"));
		}else {
			path = ".";
		}
		return path;
	}


}
