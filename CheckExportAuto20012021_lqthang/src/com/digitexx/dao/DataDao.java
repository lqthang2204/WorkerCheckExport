package com.digitexx.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bouncycastle.LICENSE;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.digitexx.bean.BeanConfig;
import com.digitexx.bean.FieldCheck;
import com.digitexx.bean.FieldsBean;
import com.digitexx.bean.FunctionBean;
import com.digitexx.bll.Collector;
import com.digitexx.config.Configuration;
import com.digitexx.connection.ConnectionDB;
import com.digitexx.scheduler.ChildSheduler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.client.model.Sorts;
import com.sun.org.apache.bcel.internal.generic.CPInstruction;

public class DataDao {

	Gson gson = new Gson();

	public BeanConfig getConfig(String projectID,String section) {

		MongoClient mongoClient = null;
		BeanConfig config = new BeanConfig();
		try {

			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);

			DBCollection collection = db
					.getCollection(Configuration.CONFIG_EXPORT);
			BasicDBObject query = new BasicDBObject();
			query.append("project_id", projectID);
			query.append("section", section);
			BasicDBObject select = new BasicDBObject();
			select.put("_id", 1);
			select.put("project_id", 2);
			select.put("project_name", 3);
			select.put("cron_trigger", 4);
			select.put("status", 5);
			select.put("id_history", 6);
			select.put("document_id", 7);
			select.put("file_filter", 8);

			select.put("path_write_txt", 9);
			select.put("limit", 10);
			select.put("status_check", 11);
			select.put("seperate", 12);
			select.put("template", 13);
			
			select.put("row_start_check", 14);
			select.put("index", 15);
			select.put("section", 16);
			

			DBCursor find = collection.find(query, select);

			

				DBObject next = find.next();
				JSONObject item = new JSONObject(next.toString());
				config.setProject_id(next.get("project_id").toString());
				config.setProject_name(next.get("project_name").toString());
				config.setCron_trigger(next.get("cron_trigger").toString());
				config.setSection(next.get("section").toString());
				config.setStatus(Boolean.parseBoolean(next.get("status")
						.toString()));
				
//				config.setId_history(next.get("id_history").toString());
				// config.setType_desk_key((next.get("type_desk_key").toString()));
				// config.setDocument_id(next.get("document_id").toString());

				JSONArray template = item.getJSONArray("template");
				config.setTemplate(template);
				config.setFile_filter(next.get("file_filter").toString());
				config.setPath_write_txt(next.get("path_write_txt").toString());
				config.setLimit(Integer.parseInt(next.get("limit").toString()));
				config.setStatus(Boolean.parseBoolean(next.get("status_check")
						.toString()));
				config.setSeperate(next.get("seperate").toString());
				config.setIndex(Boolean.parseBoolean(next.get("index").toString()));
				// config.setTemplate(next.get("template").toString());
				config.setRow_start_check(Integer.parseInt(next.get(
						"row_start_check").toString()));

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}

		return config;

	}

	public Map<String, String> getListProject() {

		Map<String, String> mapProjects = new LinkedHashMap<String, String>();
		MongoClient mongoClient = null;
		try {
			LinkedList<String> list = new LinkedList<String>();

			Map<String, String> mapField = new LinkedHashMap<String, String>();

			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);

			DBCollection collection = db
					.getCollection(Configuration.CONFIG_EXPORT);
			BasicDBObject query = new BasicDBObject();

			BasicDBObject select = new BasicDBObject();
			select.put("project_id", 1);
			select.put("project_name", 2);

			DBCursor find = collection.find(query, select);

			while (find.hasNext()) {

				DBObject next = find.next();

				String id = next.get("project_id").toString();
				String name = next.get("project_name").toString();
				mapProjects.put(name, id);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}

		return mapProjects;

	}

	public Map<String, LinkedList<FieldsBean>> getListField(String idProjects) {
		Map<String, LinkedList<FieldsBean>> mapField = null;
		MongoClient mongoClient = null;
		try {
			mapField = new LinkedHashMap<String, LinkedList<FieldsBean>>();
			LinkedList<FieldsBean> list = new LinkedList<FieldsBean>();

			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
			ObjectId id = new ObjectId(idProjects);
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.append("project_id", id);

			BasicDBObject field = new BasicDBObject();
			field.put("name", 1);
			field.put("field_display", 2);
			field.put("_id", 3);

			DBCollection collection = db
					.getCollection("field_value_definitions");

			DBCursor find = collection.find(searchQuery, field);

			while (find.hasNext()) {
				FieldsBean fieldsBean = new FieldsBean();
				//
				DBObject next = find.next();
				fieldsBean.setName(next.get("name").toString());
				fieldsBean.setDisplay(next.get("field_display").toString());
				fieldsBean.set_id(next.get("_id").toString());

				if (mapField.containsKey(next.get("_id").toString())) {
					LinkedList<FieldsBean> listtemp = mapField.get(next.get(
							"_id").toString());

					listtemp.add(fieldsBean);
					mapField.put(next.get("_id").toString(), listtemp);

				} else {
					LinkedList<FieldsBean> listTemp = new LinkedList<FieldsBean>();
					listTemp.add(fieldsBean);
					mapField.put(next.get("_id").toString(), listTemp);
				}

				// mapField.put(next.get("field_display").toString(), list);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
		System.out.println(mapField.keySet());

		return mapField;

	}

	public Map<String, FunctionBean> getFunction(String idProjects) {
		Map<String, FunctionBean> MapFunction = null;
		List<String> list = null;
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionDB.getmongoClient();
			MapFunction = new LinkedHashMap<>();
			list = new LinkedList<>();
			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);
			DBCollection collection = db.getCollection("function_export");
			BasicDBObject query = new BasicDBObject();
			query.append("project_id", idProjects);
			BasicDBObject select = new BasicDBObject();
			select.put("function", 1);
			select.put("path_function_rule", 2);
			DBCursor find = collection.find(query, select);
			while (find.hasNext()) {
				FunctionBean function = new FunctionBean();
				DBObject next = find.next();
				function.setPath_function_rule(next.get("path_function_rule")
						.toString());

				BasicDBList result = (BasicDBList) next.get("function");
				for (int i = 0; i < result.size(); i++) {
					// String function = result.get(i).toString();
					// JSONObject jsonObject = new JSONObject(function);
					// Set<String> keySet = jsonObject.keySet();
					// for(String key : keySet){

					String function_name = result.get(i).toString();
					list.add(function_name);
					function.setListFunction(list);

					MapFunction.put(idProjects, function);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}

		return MapFunction;

	}

	public void saveRule(String projectID, LinkedList<FieldCheck> list,
			String projectName,String section) {

		MongoClient mongoClient = null;

		try {
			mongoClient = ConnectionDB.getmongoClient();
			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);
			DBCollection dbCollection = db.getCollection("rule_export_check");
			boolean checkProjectExits = checkProjectExits(projectID);
			BasicDBObject document = new BasicDBObject();
			ObjectId id = new ObjectId(projectID);
			document.put("id_Project", id);
			document.put("ProjectName", projectName);
			document.put("section", section);
			if (checkProjectExits) {
				BasicDBObject documentDetail;
				BasicDBObject documentField = new BasicDBObject();
				for (int i = 0; i < list.size(); i++) {
					documentDetail = new BasicDBObject();
					documentDetail.put("function_name", list.get(i).getRule());
					documentDetail.put("column_db", list.get(i).getColumDb());
					documentDetail.put("column_file", list.get(i)
							.getColumnFile());
					documentDetail.put("index", list.get(i).getIndex());

					documentField.put("rule." + "row " + (i + 1),
							documentDetail);
				}

				BasicDBObject setQuery = new BasicDBObject();
				setQuery.append("$set", documentField);
				dbCollection.update(document, setQuery);

			} else {
				BasicDBObject documentDetail;
				BasicDBObject documentField = new BasicDBObject();
				for (int i = 0; i < list.size(); i++) {

					documentDetail = new BasicDBObject();
					documentDetail.put("function_name", list.get(i).getRule());
					documentDetail.put("column_db", list.get(i).getColumDb());
					documentDetail.put("column_file", list.get(i)
							.getColumnFile());
					documentDetail.put("index", list.get(i).getIndex());

					documentField.put("row " + (i + 1), documentDetail);
				}
				document.put("rule", documentField);
				dbCollection.insert(document);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();

		}

	}

	public boolean checkProjectExits(String id) {

		// DBCollection collection = db.getCollection(colllection);
		MongoClient mongoClient = ConnectionDB.getmongoClient();
		DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);
		DBCollection dbCollection = db.getCollection("rule_export_check");
		ObjectId idProject = new ObjectId(id);
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.append("id_Project", idProject);

		BasicDBObject select = new BasicDBObject();

		DBCursor find = dbCollection.find(searchQuery, select);

		try {
			find.next();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public Map<String, FieldCheck> getMapRule(String idProjects,
			Map<String, LinkedList<FieldsBean>> mapFields) {
		Map<String, FieldCheck> MapRule = null;
		MongoClient mongoClient = null;
		Gson gson = new Gson();
		try {
			mongoClient = ConnectionDB.getmongoClient();
			MapRule = new LinkedHashMap<>();
			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);
			DBCollection collection = db.getCollection("rule_export_check");
			BasicDBObject query = new BasicDBObject();
			ObjectId id = new ObjectId(idProjects);
			query.append("id_Project", id);
			BasicDBObject select = new BasicDBObject();
			select.put("rule", 1);
			DBCursor find = collection.find(query, select);
			while (find.hasNext()) {

				DBObject next = find.next();
				Object object = next.get("rule");
				// JsonObject fromJson = gson.fromJson(object.toString(),
				// JsonObject.class);
				// Set<String> keySet = fromJson.keySet();
				JsonObject fromJson = gson.fromJson(object.toString(),
						JsonObject.class);
				Set<String> keySet = fromJson.keySet();
				// Set<String> keySet = mapFields.keySet();
				for (String key : keySet) {
					Object object2 = fromJson.get(key);
					FieldCheck fields = new FieldCheck();
					JsonObject fromJson2 = gson.fromJson(object2.toString(),
							JsonObject.class);

					fields.setColumDb(fromJson2.get("column_db").toString());
					fields.setColumnFile(fromJson2.get("column_file")
							.toString());
					fields.setRule(fromJson2.get("function_name").toString());
					MapRule.put(key, fields);
					// for (int i = 0; i < listFieldBean.size(); i++) {
					// String name = listFieldBean.get(i).getName();
					// JsonObject fromJson2 =
					// gson.fromJson(fromJson.get((name)),
					// JsonObject.class);
					// if(fromJson2!=null){
					// FieldCheck fields = new FieldCheck();
					// fields.setRule(fromJson2.get("function_name").toString());
					// fields.setColumDb(fromJson2.get("column_db").toString());
					// fields.setColumnFile(fromJson2.get("column_file").toString());
					// MapRule.put(fromJson2.get("column_db").toString(),fields);
					// }
					// }

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}

		Set<String> keySet = MapRule.keySet();
		for (String key : keySet) {
			System.out.println("key= " + key);
			System.out.println(MapRule.get(key).getColumDb());
		}

		return MapRule;
	}

	public String getPath(String idProjects) {

		List<String> list = null;
		MongoClient mongoClient = null;
		String function = null;
		try {
			mongoClient = ConnectionDB.getmongoClient();
			list = new LinkedList<>();
			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);
			DBCollection collection = db.getCollection("function_export");
			BasicDBObject query = new BasicDBObject();
			query.append("project_id", idProjects);
			BasicDBObject select = new BasicDBObject();
			select.put("path_function_rule", 1);
			DBCursor find = collection.find(query, select);
			while (find.hasNext()) {

				DBObject next = find.next();
				function = next.get("path_function_rule").toString();

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}

		return function;

	}
	

	public HashMap<String, ChildSheduler> getListConfig() {

		HashMap<String, ChildSheduler> listScheduler = new HashMap<String, ChildSheduler>();
		MongoClient mongoClient = null;

		try {

			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);

			DBCollection collection = db
					.getCollection(Configuration.CONFIG_EXPORT);
			BasicDBObject query = new BasicDBObject();

			BasicDBObject select = new BasicDBObject();
			select.put("_id", 1);
			select.put("project_id", 2);
			select.put("project_name", 3);
			select.put("cron_trigger", 4);
			select.put("status", 5);
//			select.put("id_history", 6);
//			select.put("document_id", 7);
			select.put("file_filter", 6);

			select.put("path_write_txt", 7);
			select.put("limit", 8);
			select.put("status_check", 9);
			select.put("seperate", 10);
			select.put("template", 11);
		
			select.put("row_start_check", 12);
			select.put("type_export", 13);
			select.put("batch_id", 14);
			select.put("export_test_case", 15);
			select.put("index", 16);
			select.put("section", 17);
			

			DBCursor find = collection.find(query, select);

			while (find.hasNext()) {

				DBObject next = find.next();
				JSONObject item = new JSONObject(next.toString());
				BeanConfig config = new BeanConfig();
				config.setId(next.get("_id").toString());
				config.setProject_id(next.get("project_id").toString());
				config.setProject_name(next.get("project_name").toString());
				config.setCron_trigger(next.get("cron_trigger").toString());
				config.setStatus(Boolean.parseBoolean(next.get("status")
						.toString()));
				config.setExport_test_case_status(Boolean.parseBoolean(next.get("export_test_case")
						.toString()));
				config.setSection(next.get("section").toString());
				config.setIndex(Boolean.parseBoolean(next.get("index").toString()));
//				config.setBatch_id(next.get("batch_id").toString());
//				if(next.get("type_export").toString().toLowerCase().equals("doc")){
//					
//					config.setId_history(next.get("id_history").toString());
//					JSONArray document_id = item.getJSONArray("document_id");
//					
//					config.setDocument_id(document_id);
//				}
				// String type_desk_key = next.get("type_desk_key").toString();
				// JSONObject type_desk_key =
				// item.getJSONObject("type_desk_key");
			
				// config.setType_desk_key(jsonArray);
				JSONArray template = item.getJSONArray("template");
				config.setTemplate(template);
				
				
				config.setFile_filter(next.get("file_filter").toString());
				config.setPath_write_txt(next.get("path_write_txt").toString());
				config.setLimit(Integer.parseInt(next.get("limit").toString()));
				config.setStatus_check(Integer.parseInt(next
						.get("status_check").toString()));
				
				config.setSeperate(next.get("seperate").toString());
				// config.setTemplate(next.get("template").toString());
				config.setRow_start_check(Integer.parseInt(next.get(
						"row_start_check").toString()));
				config.setType_export(next.get("type_export").toString());
				
					JSONArray batch_id = item.getJSONArray("batch_id");
//					
				
					config.setBatch_id(batch_id);
					
				
				listScheduler.put(next.get("_id").toString(),
						new ChildSheduler(config));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}

		return listScheduler;

	}

	public List<FieldCheck> getListCheck(String idProjects,String section) {
		List<FieldCheck> listCheck = new LinkedList();
		MongoClient mongoClient = null;

		try {
			mongoClient = ConnectionDB.getmongoClient();
			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);
			DBCollection collection = db.getCollection("rule_export_check");
			BasicDBObject query = new BasicDBObject();
			ObjectId id = new ObjectId(idProjects);
			query.append("id_Project", id);
			query.append("section", section);
			BasicDBObject select = new BasicDBObject();
			select.put("rule", 1);
			DBCursor find = collection.find(query, select);
			while (find.hasNext()) {

				DBObject next = find.next();
				String object2 = next.get("rule").toString();
				JSONObject jsonObject = new JSONObject(object2);

				Set<String> keySet = jsonObject.keySet();
				for (String key : keySet) {
					Object object = jsonObject.get(key);
					// JSONObject data = new JSONObject(object);
					FieldCheck fields = new FieldCheck();
					JsonObject fromJson = gson.fromJson(object.toString(),
							JsonObject.class);
				
					fields.setRule(fromJson.get("function_name").toString());
					fields.setColumDb(fromJson.get("column_db").toString());
					fields.setColumnFile(fromJson.get("column_file").toString());
					fields.setIndex(fromJson.get("index").toString());

					listCheck.add(fields);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return listCheck;
	}

	public Map<String, LinkedList<String>> getDataToMongo(String projectID,
			String fieldNames, String document_id,
			String seperate) {

		Map<String, LinkedList<String>> mapValueDB = new LinkedHashMap<>();
		MongoClient mongoClient = null;
		String function = null;
		try {
			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
			DBCollection collection = db.getCollection(projectID + "_document");

			BasicDBObject query = new BasicDBObject();
			ObjectId id = new ObjectId(document_id);
			query.append("_id", id);
			BasicDBObject select = new BasicDBObject();
			select.put("records", 1);
			DBCursor find = collection.find(query, select);
			while (find.hasNext()) {
				DBObject next = find.next();
				String[] name = fieldNames.split(",");
				if (name.length == 1 && name[0].equals("")) {
					return mapValueDB;
				}
				for (int k = 0; k < name.length; k++) {
				
						
						JSONObject item1 = new JSONObject(next.toString());
						JSONArray record = (JSONArray) item1.get("records");
						for (int i = 0; i < record.length(); i++) {
							JSONObject keyed_data = new JSONObject(record
									.get(i).toString());
							JSONArray array_data = (JSONArray) keyed_data
									.get("final_data");
							for (int j = 0; j < array_data.length(); j++) {
								JSONObject objectData = new JSONObject(
										array_data.get(j).toString());
							
									JSONObject dataSave = new JSONObject(
											objectData.toString());
									JSONArray arrData = (JSONArray) dataSave
											.get("data");
									for (int l = 0; l < arrData.length(); l++) {
										JSONObject dataField = new JSONObject(
												arrData.get(l).toString());
										
										if(dataField.has(name[k]))
										{
											JSONObject value = (JSONObject) dataField
													.get(name[k]);
											String result = value.get("text")
													.toString();
											if (mapValueDB.containsKey(name[k])) {
												LinkedList<String> listDB = mapValueDB
														.get(name[k]);
												listDB.add(result);
												mapValueDB.put(name[k], listDB);
											} else {
												LinkedList<String> listDb = new LinkedList<>();
												listDb.add(result);
												mapValueDB.put(name[k], listDb);
											}
										}
										

									}
								
							}
						}

					

				}

				// }

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}

		return mapValueDB;

	}
	public Map<String, LinkedList<String>> getDataToMongoWithBatch(String projectID,
			String fieldNames, LinkedList<String> listID,
			String seperate) {
//		LinkedList<Map<String, LinkedList<String>>> list = new LinkedList<>();
		Map<String, LinkedList<String>> mapValueDB = null ;
		MongoClient mongoClient = null;

		try {
			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
			DBCollection collection = db.getCollection(projectID + "_document");

			BasicDBObject query = new BasicDBObject();
			 mapValueDB = new LinkedHashMap<>();
			for (int m = 0; m < listID.size(); m++) {
				String document_id = listID.get(m);
				System.out.println("=========document id == "+ document_id);
				ObjectId id = new ObjectId(document_id);
				query.append("_id", id);
				BasicDBObject select = new BasicDBObject();
				select.put("records", 1);
				DBCursor find = collection.find(query, select);
				while (find.hasNext()) {
					DBObject next = find.next();
					String[] name = fieldNames.split(",");
					if (name.length == 1 && name[0].equals("")) {
						return mapValueDB;
					}
					for (int k = 0; k < name.length; k++) {
						
							
							JSONObject item1 = new JSONObject(next.toString());
							JSONArray record = (JSONArray) item1.get("records");
							for (int i = 0; i < record.length(); i++) {
								JSONObject keyed_data = new JSONObject(record
										.get(i).toString());
//								if(keyed_data.has("final_data")){
//									
//								}
								JSONArray array_data = (JSONArray) keyed_data
										.get("final_data");
								for (int j = 0; j < array_data.length(); j++) {
									JSONObject objectData = new JSONObject(
											array_data.get(j).toString());
								
										JSONObject dataSave = new JSONObject(
												objectData.toString());
										JSONArray arrData = (JSONArray) dataSave
												.get("data");
										for (int l = 0; l < arrData.length(); l++) {
											JSONObject dataField = new JSONObject(
													arrData.get(l).toString());
											
											if(dataField.has(name[k]))
											{
												JSONObject value = (JSONObject) dataField
														.get(name[k]);
												String result = value.get("text")
														.toString();
												if (mapValueDB.containsKey(name[k])) {
													LinkedList<String> listDB = mapValueDB
															.get(name[k]);
													listDB.add(result);
													mapValueDB.put(name[k], listDB);
												} else {
													LinkedList<String> listDb = new LinkedList<>();
													listDb.add(result);
													mapValueDB.put(name[k], listDb);
												}
												
											
											}
											

										}
									
								}
							}

						

					}

					// }

				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}

		return mapValueDB;

	}


	public List<String> getpathMongo(String projectID, String document_id,String type) {

		List<String> listPath = new LinkedList<>();
		MongoClient mongoClient = null;
		String function = null;
		try {
			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
			DBCollection collection = db.getCollection(projectID);

			BasicDBObject query = new BasicDBObject();
			ObjectId id = new ObjectId(document_id);
			if(type.equals("batch")){
				query.append("_id", id);
			}
			else if(type.equals("doc_set")){
				query.append("batch_id", id);
			}
			else if(type.equals("doc")){
				query.append("batch_id", id);
			}
//			query.append("_id", id);
			BasicDBObject select = new BasicDBObject();
			select.put("path_export", 1);
			DBCursor find = collection.find(query, select);
			while (find.hasNext()) {
				DBObject next = find.next();
				JSONObject item = new JSONObject(next.toString());
				JSONArray arrPath = item.getJSONArray("path_export");

				for (int i = 0; i < arrPath.length(); i++) {
					listPath.add(arrPath.getString(i));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}

		return listPath;

	}
	

	public Integer getStatus(String projectID, String document_id,String type) {
		List<String> listPath = new LinkedList<>();
		MongoClient mongoClient = null;
		String function = null;
		int status = 0;
		try {
			mongoClient = ConnectionDB.getmongoClient();
			DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
			DBCollection collection = db.getCollection(projectID);
			BasicDBObject query = new BasicDBObject();
			ObjectId id = new ObjectId(document_id);
			if(type.equals("batch")){
				query.append("_id", id);
			}
			else if(type.equals("doc_set")){
				query.append("batch_id", id);
			}
			else if(type.equals("doc")){
				query.append("batch_id", id);
			}
//			query.append("_id", id);
			BasicDBObject select = new BasicDBObject();
			select.put("status", 1);
			DBCursor find = collection.find(query, select);
			while (find.hasNext()) {
				DBObject next = find.next();
				if(next.get("status")!=null){
					status = Integer.parseInt(next.get("status").toString());
				}
				else{
					return 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return status;
	}
	
	public 	Map<String, LinkedList<String>> getDataToMongo(String projectID,
			String fieldNames, LinkedList<String> listID,
			String seperate) {
//		LinkedList<Map<String, LinkedList<String>>> list = new LinkedList<>();
		Map<String, LinkedList<String>> mapValueDB = null ;
		MongoClient mongoClient = null;

		try {
			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
			DBCollection collection = db.getCollection(projectID + "_document");

			BasicDBObject query = new BasicDBObject();
			 mapValueDB = new LinkedHashMap<>();
			for (int m = 0; m < listID.size(); m++) {
				String document_id = listID.get(m);
				ObjectId id = new ObjectId(document_id);
				query.append("_id", id);
				BasicDBObject select = new BasicDBObject();
				select.put("records", 1);
				DBCursor find = collection.find(query, select);
				while (find.hasNext()) {
					DBObject next = find.next();
					String[] name = fieldNames.split(",");
					if (name.length == 1 && name[0].equals("")) {
						return mapValueDB;
					}
					for (int k = 0; k < name.length; k++) {
						
							
							JSONObject item1 = new JSONObject(next.toString());
							JSONArray record = (JSONArray) item1.get("records");
							for (int i = 0; i < record.length(); i++) {
								JSONObject keyed_data = new JSONObject(record
										.get(i).toString());
								JSONArray array_data = (JSONArray) keyed_data
										.get("final_data");
								for (int j = 0; j < array_data.length(); j++) {
									JSONObject objectData = new JSONObject(
											array_data.get(j).toString());
								
										JSONObject dataSave = new JSONObject(
												objectData.toString());
										JSONArray arrData = (JSONArray) dataSave
												.get("data");
										for (int l = 0; l < arrData.length(); l++) {
											JSONObject dataField = new JSONObject(
													arrData.get(l).toString());
											
											if(dataField.has(name[k]))
											{
												JSONObject value = (JSONObject) dataField
														.get(name[k]);
												String result = value.get("text")
														.toString();
												if (mapValueDB.containsKey(name[k])) {
													LinkedList<String> listDB = mapValueDB
															.get(name[k]);
													listDB.add(result);
													mapValueDB.put(name[k], listDB);
												} else {
													LinkedList<String> listDb = new LinkedList<>();
													listDb.add(result);
													mapValueDB.put(name[k], listDb);
												}
												
											}
											

										}
									
								}
							}

						

					}

					// }

				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}

		return mapValueDB;

	}
	
	public boolean haveStatusFinish(String projectID, int status, String batch_id){
		boolean result= true;
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionDB.getmongoClient();
			DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
			DBCollection collection = db.getCollection(projectID + "_document");
			ObjectId batch_id_temp = new ObjectId(batch_id);
			BasicDBObject query = new BasicDBObject();
			query.append("_id", batch_id_temp);
			BasicDBObject select = new BasicDBObject();
			select.put("status", 1);
			DBCursor find = collection.find(query, select);
			while (find.hasNext()) {
				DBObject next = find.next();
				if(next.get("status")!=null){
					int status_temp = Integer.parseInt(next.get("status").toString());
					if(status != status_temp){
						result = false;
					}	
				}
				else{
					result = false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return result;
	}
	public LinkedList<String> getListDocumentIDWithBatch(String projectID, String batch_id){
		LinkedList<String> list = new LinkedList<>();
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionDB.getmongoClient();
			DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
			DBCollection collection = db.getCollection(projectID + "_document");
			ObjectId batch_id_temp = new ObjectId(batch_id);
			BasicDBObject query = new BasicDBObject();
			query.append("batch_id", batch_id_temp);
			BasicDBObject select = new BasicDBObject();
			select.put("_id", 1);
			select.put("status", 2);
			DBCursor find = collection.find(query, select);
			while (find.hasNext()) {
				DBObject next = find.next();
				String id = next.get("_id").toString();
				int status = Integer.parseInt(next.get("status").toString());
				if(status==450){
					list.add(id);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return list;
	}
	 

	public boolean removeDocumentID(String configId, int index, String document_id) {
		MongoClient mongoClient = null;
		boolean result = false;
		try {
			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);
			DBCollection collection = db
					.getCollection(Configuration.CONFIG_EXPORT);

			BasicDBObject query = new BasicDBObject();
			ObjectId id = new ObjectId(configId);
			query.append("_id", id);
			BasicDBObject object = new BasicDBObject();
			object.put("$pullAll", new BasicDBObject("document_id",
					new String[] {document_id}));
			// "document_id", new int[] {index}/
			WriteResult update2 = collection.update(query, object);
			 result = update2.isUpdateOfExisting();
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return result;

	}
	public boolean removeBatchID(String configId, int index, String batch_id) {
		MongoClient mongoClient = null;
		boolean result = false;
		try {
			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);
			DBCollection collection = db
					.getCollection(Configuration.CONFIG_EXPORT);

			BasicDBObject query = new BasicDBObject();
			ObjectId id = new ObjectId(configId);
			query.append("_id", id);
			BasicDBObject object = new BasicDBObject();
			object.put("$pullAll", new BasicDBObject("batch_id",
					new String[] {batch_id}));
			// "document_id", new int[] {index}/
			WriteResult update2 = collection.update(query, object);
			 result = update2.isUpdateOfExisting();
			 
			 
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
		return result;

	}
	public String getDocumentIdFromMongo(BeanConfig config) {			
//		mongoClient = ConnectionDB.getmongoClient();
//		  MongoDatabase database = mongoClient.getDatabase(Configuration.db_uat_eclaim);
//		  MongoCollection<Document> collection = database.getCollection(config.getProject_id()+"_document");
////		DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
////		DBCollection collection = db.getCollection(config.getProject_id()+"_document");
//		  FindIterable<Document> iterDoc = collection.find(
//			        Filters.and(Filters.("_id", config.getId_history()), Filters.eq("status_check", config.getStatus_check())));
//		  Iterator<Document> it = iterDoc.iterator();
		
		MongoClient mongoClient = null;
		String id="";
		try {
			
			BasicDBObject query = new BasicDBObject();
			
			if (config.getId_history() != null && !config.getId_history().equals("")) {
				Document id_history = new Document();
				id_history.put("$gt", new ObjectId(config.getId_history()));
				
				query.put("_id", id_history);
				
			}
			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
			DBCollection collection = db.getCollection(config.getProject_id()+"_document");
			
			DBCursor cursor = collection.find(query);
			
			
			while (cursor.hasNext()) {

				DBObject next = cursor.next();
				
				if(next.get("status")!=null && Integer.parseInt(next.get("status").toString())== config.getStatus_check()){
				 id = next.get("_id").toString();
				
				if(Collector.listDataCSV.size()>0){
						if(!Collector.listDataCSV.contains(id)){
							Collector.listDataCSV.add(id);
							return  id;
					}
				}else{
					Collector.listDataCSV.add(id);
					return id;
				}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mongoClient.close();
		} finally {
			
				mongoClient.close();
			
		}
//		System.out.println("list======================"+list.size()+ "------"+ data.get_id());
		return id;
	}
	
	public Map<String, LinkedList<String>> getFieldsMapToHeader(String projectId, LinkedList<String> listHeader, Map<String, LinkedList<FieldsBean>> mapFields){
		Map<String, LinkedList<String>> map = new LinkedHashMap<>();
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionDB.getmongoClient();
			DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
			DBCollection collection = db.getCollection("transforms");
			ObjectId id = new ObjectId(projectId);
			BasicDBObject query = new BasicDBObject();
			query.append("project_id", id);
			BasicDBObject select = new BasicDBObject();
			select.put("rules", 1);
			
			DBCursor find = collection.find(query, select);
			while (find.hasNext()) {
				DBObject next = find.next();
//				String rules = next.get("rules").toString();
				JSONObject Fields = new JSONObject(next.get("rules").toString());
				JSONObject Items = new JSONObject(Fields.get("content").toString());
				for (int i = 0; i < listHeader.size(); i++) {
					
					String item = listHeader.get(i);
					if(Items.has(item)){
						JSONObject content = new JSONObject(Items.get(item).toString());
						String dataKey = content.getString("dataKey").replace("\"", "");
						String value = content.getString("default").replace("\"", "");
						Set<String> keySet = mapFields.keySet();
						for (String key : keySet) {
							LinkedList<FieldsBean> listField = mapFields.get(key);
							if(!value.equals("") && value.contains("parent."+listField.get(0).getName()) || value.contains("params."+listField.get(0).getName()) || value.contains("."+listField.get(0).getName())){
								if(map.containsKey(dataKey)){
									 LinkedList<String> list = map.get(dataKey);
									 list.add(listField.get(0).getName());
									 map.put(item, list);
								}else{ 
									LinkedList<String> list = new LinkedList<>();
									list.add(listField.get(0).getName());
									map.put(item, list);
								}
							}
						}
					
						
						
					}
				}
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
		
		
		return map;
	}
	public Map<String, LinkedList<String>> getFieldsMapToHeadertoXML(String projectId, LinkedList<String> listHeader, Map<String, LinkedList<FieldsBean>> mapFields){
		Map<String, LinkedList<String>> map = new LinkedHashMap<>();
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionDB.getmongoClient();
			DB db = mongoClient.getDB(Configuration.db_uat_eclaim);
			DBCollection collection = db.getCollection("transforms");
			ObjectId id = new ObjectId(projectId);
			BasicDBObject query = new BasicDBObject();
			query.append("project_id", id);
			BasicDBObject select = new BasicDBObject();
			select.put("rules", 1);
			DBCursor find = collection.find(query, select);
			while (find.hasNext()) {
				DBObject next = find.next();
//				String rules = next.get("rules").toString();
				JSONObject Fields = new JSONObject(next.get("rules").toString());
				JSONObject Items = new JSONObject(Fields.get("content").toString());
				for (int i = 0; i < listHeader.size(); i++) {
					String item = listHeader.get(i);
					int lastIndexOf = item.lastIndexOf(":")+1;
					String itemTemp = item.substring(lastIndexOf);
					if(Items.has(itemTemp)){
						JSONObject content = new JSONObject(Items.get(itemTemp).toString());
						String dataKey = content.getString("dataKey").replace("\"", "");
						String value = content.getString("default").replace("\"", "");
						Set<String> keySet = mapFields.keySet();
						for (String key : keySet) {
							LinkedList<FieldsBean> listField = mapFields.get(key);
							if(!value.equals("") && value.contains("parent."+listField.get(0).getName()) ||  value.contains("params."+listField.get(0).getName()) || value.contains("."+listField.get(0).getName())){
								if(map.containsKey(dataKey)){
									 LinkedList<String> list = map.get(dataKey);
									 list.add(listField.get(0).getName());
									 map.put(item, list);
								}else{
									LinkedList<String> list = new LinkedList<>();
									list.add(listField.get(0).getName());
									map.put(item, list);
								}
							}
						}	
					}
				}
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
		
		
		return map;
	}

	public void InsertFunction(String id, String name, LinkedList<String> listFunction){
		
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);

			DBCollection collection = db.getCollection("function_export");
			BasicDBObject query = new BasicDBObject();
			query.append("project_id", id);
			BasicDBObject select = new BasicDBObject();
			select.put("project_id", 1);
			select.put("project_name", 2);
			select.put("function", 3);
			select.put("status", 4);
			select.put("path_function_rule", 5);
			DBCursor find = collection.find(query, select);
			boolean checkProjectExits = checkProjectExits(id);
			if(checkProjectExits){
				if(find.hasNext()){
					DBObject next = find.next();
					String project_id = next.get("project_id").toString();
					String project_name = next.get("project_name").toString();
					boolean status = Boolean.parseBoolean(next.get("status").toString());
					String path_function_rule = next.get("path_function_rule").toString();
					JSONObject item = new JSONObject(next.toString());
					JSONArray batch_id ;
					for (int i = 0; i < listFunction.size(); i++) {
						
					}
					
					
					

				}
				
			}
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	public static JSONArray objectToJSONArray(Object object) {
		Object json = null;
		JSONArray jsonArray = null;
		try {
			json = new JSONTokener(object.toString()).nextValue();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (json instanceof JSONArray) {
			jsonArray = (JSONArray) json;
		}
		return jsonArray;
	}
	public boolean checkProjectExits(String id, DBCollection collection) {

//		 DBCollection collection = db.getCollection(colllection);
//		ObjectId idProject = new ObjectId(id);
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.append("project_id", id);
		

		BasicDBObject select = new BasicDBObject();

		DBCursor find = collection.find(searchQuery, select);

		try {
			find.next();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	public boolean checkProjectAndSectionExits(String id,String section) {

		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionDB.getmongoClient();

			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);

			DBCollection collection = db.getCollection("function_export");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.append("project_id", id);
		searchQuery.append("section", section);
		

		BasicDBObject select = new BasicDBObject();

		DBCursor find = collection.find(searchQuery, select);

		
			find.next();
			return true;
		} catch (Exception e) {
			return false;
		}
		finally{
			mongoClient.close();
		}

	}
	public boolean updateFunction(String id, String section, LinkedList<String> listSection,String projectName,boolean flag,String path){
		boolean result= true;		
		MongoClient mongoClient = null;
		
		try {
			mongoClient = ConnectionDB.getmongoClient();
			DB db = mongoClient.getDB(Configuration.DB_NAME_TOOL);
			DBCollection collection = db.getCollection("function_export");
			if(flag){
				BasicDBObject searchQuery = new BasicDBObject();
				searchQuery.append("project_id", id);
				searchQuery.append("section", section);
				
				DBObject listFunction = new BasicDBObject("function", new Document("$each",listSection));
				DBObject updateQuery = new BasicDBObject("$push", listFunction);
				collection.update(searchQuery, updateQuery);			
			}else{
				
				BasicDBObject doc_function = new BasicDBObject();
				doc_function.put("project_id", id);
				doc_function.put("project_name", projectName);
				doc_function.put("status", true);
				doc_function.put("section",section);
				doc_function.put("path_function_rule",path);
//				DBObject listFunction = new BasicDBObject("function", new Document("$each",listSection));
//				DBObject updateQuery = new BasicDBObject("$push", listFunction);
//				documentField.put(listFunction, doc_function);
				doc_function.append("function", listSection);
				collection.insert(doc_function);		
			}
		} catch (Exception e) {
			e.printStackTrace();
			result=false;
			return result;
		}
		finally{
			mongoClient.close();
		}		
		return result;
	}
}
