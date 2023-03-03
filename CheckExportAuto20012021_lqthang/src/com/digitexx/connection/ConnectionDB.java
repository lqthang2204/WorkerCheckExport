package com.digitexx.connection;

import java.util.Arrays;

import com.digitexx.config.Configuration;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class ConnectionDB {
	
	public static MongoClient getmongoClient(){
		Configuration.configDB();
		
		String host = Configuration.HOST;
		int port = Configuration.PORT;
		String user = Configuration.USERNAME;
		String pass = Configuration.PASSWORD;
//		String db_name = Configuration.DB_NAME;
		MongoClient mongoClient;
		if(user.equals("") && pass.equals("")){
			
			 mongoClient = new MongoClient(host, port);
			 return mongoClient;
		}
		else
		{
			MongoCredential credential = MongoCredential.createScramSha1Credential(
	              user, Configuration.DB_NAME, pass.toCharArray());
	 
	       mongoClient = new MongoClient(
	              new ServerAddress(host, port), Arrays.asList(credential));
	       System.out.println("connected mongodb");
	      return mongoClient;
		}
		
	}
	
	

}
