package com.digitexx.bll;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import com.digitexx.bean.BeanConfig;
import com.digitexx.bean.FieldCheck;
import com.digitexx.dao.DataDao;
import com.digitexx.util.Util;

public class Collector {

	private BeanConfig info;
	private DataDao dao;
	private Util util;
	private compareCSV compareCSV;
	private CompareXML compareXML;
	private CompareXLSX compareXLSX;
	public static List<String> listDataCSV = new LinkedList<>();

	public Collector(BeanConfig info) {

		dao = new DataDao();
		this.info = info;
		util = new Util();

	}

	public void run() {
		String source_image = null;
		DataDao data = new DataDao();
		List<String> list = new ArrayList<String>();

		int result = 1;
		
		String temId = "";
		if (info.getType_export().toLowerCase().equals("doc") && info.isStatus() && info.getFile_filter().toLowerCase().equals("csv")) {
			try {
				JSONArray document_id = objectToJSONArray(info
						.getBatch_id());
					// Object document_id2 = info.getDocument_id();
					if (document_id.length()>=1) {
//						JSONArray document_id = objectToJSONArray(info
//								.getDocument_id());
						if (document_id.length() != 0) {
							
							for (int k = 0; k < document_id.length(); k++) {
								String _id = document_id.get(k).toString();
								Integer status = dao.getStatus(
										info.getProject_id()+"_document", _id,info.getType_export().toLowerCase().trim());
								if (info.getStatus_check() == status) {
										compareCSV = new compareCSV();
										String id = info.getProject_id();
										List<String> listPathMongoDB = dao.getpathMongo(id+"_document", _id,info.getType_export().toLowerCase().trim());
//										listPathMongoDB = getListPathMongoDB(listPathMongoDB, info);
										String pathFunction = dao.getPath(id);
										List<FieldCheck> listCheck = dao.getListCheck(id,info.getSection());
										for (int i = 0; i < listCheck.size(); i++) {
											String columDb = listCheck.get(i).getColumDb().replace("\"", "");
											String columFile = listCheck.get(i).getColumnFile();
											String rule = listCheck.get(i).getRule();
											Map<String, LinkedList<String>> mapDataToMongo = dao.getDataToMongo(info.getProject_id(),columDb, _id,info.getSeperate());
											
											Map<String, Map<String, LinkedList<String>>> mapdataCSV = util.getDataCSV(listPathMongoDB,columFile, info);
											Map<String, List<Boolean>> mapResult = compareCSV.getMapResult(mapDataToMongo,mapdataCSV, rule,pathFunction);
											util.ExportResultFromCSV(mapdataCSV,mapResult, columFile,this.info, _id,mapDataToMongo);
										}
										boolean removeDocumentID = dao.removeDocumentID(info.getId(), k, _id);
										if (removeDocumentID) {
											JSONArray temp = document_id;
											info.setDocument_id(temp.remove(k));
										}
										
//										if(info.isExport_test_case_status()){
//											
//										}
									}
							}
						}
					}
					else if(!info.getId_history().equals("")){
						
						if(info.isStatus()){
							
							String _id = dao.getDocumentIdFromMongo(info);
							
							if(!_id.equals("")){
								Integer status = dao.getStatus(
										info.getProject_id()+"_document", _id,info.getType_export().toLowerCase().trim());
								if (info.getStatus_check() == status) {
									
										compareCSV = new compareCSV();
										String id = info.getProject_id();
										List<String> listPathMongoDB = dao.getpathMongo(id+"_document", _id,info.getType_export().toLowerCase().trim());
										String pathFunction = dao.getPath(id);
										List<FieldCheck> listCheck = dao.getListCheck(id,info.getSection());
										for (int i = 0; i < listCheck.size(); i++) {
											String columDb = listCheck.get(i).getColumDb().replace("\"", "");
											String columFile = listCheck.get(i).getColumnFile();
											String rule = listCheck.get(i).getRule();
											Map<String, LinkedList<String>> mapDataToMongo = dao.getDataToMongo(info.getProject_id(),columDb, _id,info.getSeperate());
											Map<String, Map<String, LinkedList<String>>> mapdataCSV = util.getDataCSV(listPathMongoDB,columFile, info);
											Map<String, List<Boolean>> mapResult = compareCSV.getMapResult(mapDataToMongo,mapdataCSV, rule,pathFunction);
											util.ExportResultFromCSV(mapdataCSV,mapResult, columFile,this.info, _id,mapDataToMongo);
										}
										
										info.setId_history(_id);
									}
							}
							
						}
						
//						System.out.println("listDataCSV.size()======= "+listDataCSV.size());
					}
					
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(info.getType_export().toLowerCase().equals("doc") && info.isStatus() && info.getFile_filter().toLowerCase().equals("xml")){
			JSONArray document_id = objectToJSONArray(info
					.getDocument_id());
			
			if(document_id.length()>=1){
				for (int k = 0; k < document_id.length(); k++) {
					String _id = document_id.get(k).toString();
					Integer status = dao.getStatus(
							info.getProject_id()+"_document", _id,info.getType_export().toLowerCase().trim());
					if (info.getStatus_check() == status) {
						compareXML = new CompareXML();
						String id = info.getProject_id();
						List<String> listPathMongoDB = dao.getpathMongo(id+"_document", _id,info.getType_export().toLowerCase().trim());
						String pathFunction = dao.getPath(id);
						List<FieldCheck> listCheck = dao.getListCheck(id,info.getSection());
						for (int i = 0; i < listCheck.size(); i++) {
							String columDb = listCheck.get(i).getColumDb().replace("\"", "");
							String columFile = listCheck.get(i).getColumnFile();
							String rule = listCheck.get(i).getRule();
							Map<String, LinkedList<String>> mapDataToMongo = dao.getDataToMongo(info.getProject_id(),columDb, _id,info.getSeperate());
							System.out.println(mapDataToMongo);
							Map<String, Map<String, LinkedList<String>>> mapdataXML = util.getDataXML(listPathMongoDB, columFile, info);
							Map<String, List<Boolean>> mapResult = compareXML.getMapResult(mapDataToMongo,mapdataXML, rule,pathFunction);
//							util.ExportResultFromCSV(mapdataCSV,mapResult, columFile,this.info, _id);
							util.exportCheckXMLDOC(mapdataXML, mapResult, columFile, this.info, _id, mapDataToMongo);
						}
						boolean removeDocumentID = dao.removeDocumentID(info.getId(), k, _id);
						if (removeDocumentID) {
							JSONArray temp = document_id;
							info.setDocument_id(temp.remove(k));
						}
						
						
					}
					
				}
			}
			else if(!info.getId_history().equals("")){
				
				if(info.isStatus()){
					
					String _id = dao.getDocumentIdFromMongo(info);
					
					if(!_id.equals("")){
						Integer status = dao.getStatus(
								info.getProject_id()+"_document", _id,info.getType_export().toLowerCase().trim());
						if (info.getStatus_check() == status) {
							
								compareXML = new CompareXML();
								String id = info.getProject_id();
								List<String> listPathMongoDB = dao.getpathMongo(id+"_document", _id,info.getType_export().toLowerCase().trim());
								String pathFunction = dao.getPath(id);
								List<FieldCheck> listCheck = dao.getListCheck(id,info.getSection());
								for (int i = 0; i < listCheck.size(); i++) {
									String columDb = listCheck.get(i).getColumDb().replace("\"", "");
									String columFile = listCheck.get(i).getColumnFile();
									String rule = listCheck.get(i).getRule();
									Map<String, LinkedList<String>> mapDataToMongo = dao.getDataToMongo(info.getProject_id(),columDb, _id,info.getSeperate());
									System.out.println(mapDataToMongo);
									Map<String, Map<String, LinkedList<String>>> mapdataXML = util.getDataXML(listPathMongoDB, columFile, info);
									Map<String, List<Boolean>> mapResult = compareXML.getMapResult(mapDataToMongo,mapdataXML, rule,pathFunction);
//									util.ExportResultFromCSV(mapdataCSV,mapResult, columFile,this.info, _id);
									util.exportCheckXMLDOC(mapdataXML, mapResult, columFile, this.info, _id, mapDataToMongo);
								}
								
								info.setId_history(_id);
							}
					}
					
				}
				
				
			}
		}
		else if(info.getType_export().toLowerCase().equals("batch") && info.isStatus() && info.getFile_filter().toLowerCase().equals("csv")){
			
			JSONArray batch_id = objectToJSONArray(info
					.getBatch_id());
			
				if(batch_id.length()>=1){
				for (int k = 0; k < batch_id.length(); k++) {
					String _id = batch_id.get(k).toString();
					
					Integer status = dao.getStatus(info.getProject_id()+"_batch", _id,info.getType_export().toLowerCase().trim());
					if(status == info.getStatus_check()){
						compareCSV = new compareCSV();
//						boolean haveStatusFinish = dao.haveStatusFinish(info.getProject_id(), info.getStatus_check(), batch_id.get(k).toString());
							String pathFunction = dao.getPath(info.getProject_id());
							LinkedList<String> listDocumentID = dao.getListDocumentIDWithBatch(info.getProject_id(),_id);
							List<String> listPathMongo = dao.getpathMongo(info.getProject_id()+"_batch", _id,info.getType_export().toLowerCase().trim());
							
								List<FieldCheck> listCheck = dao.getListCheck(info.getProject_id(),info.getSection());
								for (int i = 0; i < listCheck.size(); i++) {
									String columDb = listCheck.get(i).getColumDb().replace("\"", "");
									String columFile = listCheck.get(i).getColumnFile();
									String rule = listCheck.get(i).getRule();
									String index = listCheck.get(i).getIndex();
									try {
										Map<String, LinkedList<String>> listMongoDB = dao.getDataToMongoWithBatch(info.getProject_id(),columDb, listDocumentID,info.getSeperate());
										Map<String, LinkedList<Map<String, LinkedList<String>>>> mapDataCSV = util.getDataCSVBatch(listPathMongo, columFile, info,index);
										Map<String, List<Boolean>> mapResult = compareCSV.getMapResultBatch(listMongoDB,mapDataCSV, rule,pathFunction);
										util.ExportResultFromCSVWithBatch(mapDataCSV, mapResult, columFile, this.info, _id, listMongoDB,index.replace("\"", ""));
//										util.ExportTestCase("/home/lqthang/data/workspacejava8/CheckExportAuto/template/template_export.xlsx", "/home/lqthang/Desktop/test Export/File check Export/0516/test_tmp.xlsx", "/home/lqthang/Desktop/test Export/File check Export/0516/test_case.xlsx", mapDataCSV, mapResult, columFile,this.info, _id, listMongoDB);
										
									} catch (Exception e) {
										e.printStackTrace();
									}

								}
								boolean removeBatchID = dao.removeBatchID(info.getId(), k, _id);
								if (removeBatchID) {
									JSONArray temp = batch_id;
									info.setBatch_id(temp.remove(k).toString());
								}
								
								
						
					}
					
				}
			}
		
		}
		else if(info.getType_export().toLowerCase().equals("batch") && info.isStatus() && info.getFile_filter().toLowerCase().equals("xlsx")){
			JSONArray batch_id = objectToJSONArray(info
					.getBatch_id());
			
				if(batch_id.length()>=1){
					for (int k = 0; k < batch_id.length(); k++) {
						String _id = batch_id.get(k).toString();
			
			
				
				Integer status = dao.getStatus(info.getProject_id()+"_batch", _id,info.getType_export().toLowerCase().trim());
				if(status == info.getStatus_check()){
					 compareXLSX = new CompareXLSX();
					 String pathFunction = dao.getPath(info.getProject_id());
					LinkedList<String> listDocumentID = dao.getListDocumentIDWithBatch(info.getProject_id(),_id);
					List<String> listPathMongo = dao.getpathMongo(info.getProject_id()+"_batch", _id,info.getType_export().toLowerCase().trim());
					List<FieldCheck> listCheck = dao.getListCheck(info.getProject_id(),info.getSection());
					for (int j = 0; j < listCheck.size(); j++) {
						try {
							String columDb = listCheck.get(j).getColumDb().replace("\"", "");
							String columFile = listCheck.get(j).getColumnFile();
							String rule = listCheck.get(j).getRule();
							String index = listCheck.get(j).getIndex();
							Map<String, LinkedList<String>> dataToMongoWithBatch = dao.getDataToMongo(info.getProject_id(),columDb, listDocumentID,info.getSeperate());
						
								
								Map<String, Map<String, LinkedList<String>>> mapDataXlSX = util.getMapDataXlSX(listPathMongo, columFile, info,index);
							
						Map<String, List<Boolean>> mapResult = compareXLSX.getMapResultBatch(dataToMongoWithBatch, mapDataXlSX, rule, pathFunction);
						
						
							util.ExportResultFromXLSXWithBatch(mapDataXlSX, mapResult, columFile, this.info, _id, dataToMongoWithBatch,index);
//							util.ExportTestCase("/home/lqthang/data/workspacejava8/CheckExportAuto/template/template_export.xlsx", "/home/lqthang/Desktop/test Export/File check Export/0516/test_tmp.xlsx", "/home/lqthang/Desktop/test Export/File check Export/0516/test_case.xlsx", mapDataCSV, mapResult, columFile,this.info, _id, listMongoDB);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						
					}
					boolean removeBatchID = dao.removeBatchID(info.getId(), k, _id);
					if (removeBatchID) {
						JSONArray temp = batch_id;
						info.setBatch_id(temp.remove(k).toString());
					}
					
					
					
				}
				
			
			
		}
				}
		}
else if(info.getType_export().toLowerCase().equals("doc_set") && info.isStatus() && info.getFile_filter().toLowerCase().equals("csv")){
			
			JSONArray batch_id = objectToJSONArray(info
					.getBatch_id());
			
				if(batch_id.length()>=1){
				for (int k = 0; k < batch_id.length(); k++) {
					String _id = batch_id.get(k).toString();
					
					Integer status = dao.getStatus(info.getProject_id()+"_doc_set", _id,info.getType_export().toLowerCase().trim());
					if(status == info.getStatus_check()){
						compareCSV = new compareCSV();
//						boolean haveStatusFinish = dao.haveStatusFinish(info.getProject_id(), info.getStatus_check(), batch_id.get(k).toString());
							String pathFunction = dao.getPath(info.getProject_id());
							LinkedList<String> listDocumentID = dao.getListDocumentIDWithBatch(info.getProject_id(),_id);
							List<String> listPathMongo = dao.getpathMongo(info.getProject_id()+"_doc_set", _id,info.getType_export().toLowerCase().trim());
							
								List<FieldCheck> listCheck = dao.getListCheck(info.getProject_id(),info.getSection());
								for (int i = 0; i < listCheck.size(); i++) {
									String columDb = listCheck.get(i).getColumDb().replace("\"", "");
									String columFile = listCheck.get(i).getColumnFile();
									String rule = listCheck.get(i).getRule();
									String index = listCheck.get(i).getIndex();
									try {
										Map<String, LinkedList<String>> listMongoDB = dao.getDataToMongoWithBatch(info.getProject_id(),columDb, listDocumentID,info.getSeperate());
										Map<String, LinkedList<Map<String, LinkedList<String>>>> mapDataCSV = util.getDataCSVBatch(listPathMongo, columFile, info,index);
										Map<String, List<Boolean>> mapResult = compareCSV.getMapResultBatch(listMongoDB,mapDataCSV, rule,pathFunction);
										util.ExportResultFromCSVWithBatch(mapDataCSV, mapResult, columFile, this.info, _id, listMongoDB,index.replace("\"", ""));
//										util.ExportTestCase("/home/lqthang/data/workspacejava8/CheckExportAuto/template/template_export.xlsx", "/home/lqthang/Desktop/test Export/File check Export/0516/test_tmp.xlsx", "/home/lqthang/Desktop/test Export/File check Export/0516/test_case.xlsx", mapDataCSV, mapResult, columFile,this.info, _id, listMongoDB);
										
									} catch (Exception e) {
										e.printStackTrace();
									}

								}
								boolean removeBatchID = dao.removeBatchID(info.getId(), k, _id);
								if (removeBatchID) {
									JSONArray temp = batch_id;
									info.setBatch_id(temp.remove(k).toString());
								}
								
								
						
					}
					
				}
			}
		
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
	
	public List<String> getListPathMongoDB(List<String> listPathMongoDB, BeanConfig info){
		
		for (int i = 0; i < listPathMongoDB.size(); i++) {
		
			if(listPathMongoDB.get(i).contains("."+info.getFile_filter().toLowerCase())){
				
				listPathMongoDB.remove(i);
			}
		}
		return listPathMongoDB;
		
	}

}
