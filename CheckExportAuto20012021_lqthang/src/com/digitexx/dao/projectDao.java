package com.digitexx.dao;

import java.util.LinkedList;

import org.bson.types.ObjectId;

import com.digitexx.bean.BeanConfig;
import com.digitexx.config.Configuration;
import com.digitexx.connection.ConnectionDB;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class projectDao {
public LinkedList<String> getProjectTask(String projectID){
		
		LinkedList<String> listTask = new LinkedList<String>();
		
		BeanConfig config = new BeanConfig();
		MongoClient mongoClient = ConnectionDB.getmongoClient();
		DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
			 
		
		 DBCollection collection = db.getCollection("layout_definitions");
		 BasicDBObject query = new BasicDBObject();
		 ObjectId id = new ObjectId(projectID);
		 query.append("project_id", id);
		 
		 
		 BasicDBObject select = new BasicDBObject();

		 select.put("name", 1);
		
		 
		
		 
		 
		 DBCursor find = collection.find(query,select);
		 
		 
		
		
		
		 
		
		while(find.hasNext()){
			 DBObject next = find.next();
			 
			 listTask.add(next.get("name").toString());
			
		}
		
	
		
		return listTask;
		
		
		
		
	}

}
