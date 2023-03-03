package com.digitexx.bll;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompareXML {
	
	public Map<String, List<Boolean>> getMapResult(Map<String, LinkedList<String>> mapDataToMongo,Map<String, Map<String, LinkedList<String>>> mapdataXML, String rule, String pathFunction){
		
		Map<String, List<Boolean>> mapResult = new LinkedHashMap<>();
		List<Boolean> listResult = new LinkedList<>();
		Object[] arrDataMongo = mapDataToMongo.values().toArray();
		Set<String> keySet = mapdataXML.keySet();
		for (String key : keySet) {
			Map<String, LinkedList<String>> mapValue = mapdataXML.get(key);
			Object[] arrData = mapValue.values().toArray();
			
			for (int i = 0; i < arrData.length; i++) {
				
				if(arrDataMongo.length==0){
					
					listResult =  (List<Boolean>) ExecuteJSUtil.executeJs(pathFunction, rule, arrData);
				}else if (arrDataMongo.length == 1) {
					listResult = (List<Boolean>) ExecuteJSUtil.executeJs(pathFunction, rule,
							arrDataMongo[0], arrData[0]);
				}
				else if (arrDataMongo.length == 2) {
					listResult = (List<Boolean>) ExecuteJSUtil.executeJs(pathFunction, rule,
							arrDataMongo[0], arrDataMongo[1], arrData[0]);
				} else if (arrDataMongo.length == 3) {
					listResult = (List<Boolean>) ExecuteJSUtil.executeJs(pathFunction, rule,
							arrDataMongo[0], arrDataMongo[1], arrDataMongo[2], arrData[0]);
				} else if (arrDataMongo.length == 4) {
					listResult = (List<Boolean>) ExecuteJSUtil.executeJs(pathFunction, rule,
							arrDataMongo[0], arrDataMongo[1], arrDataMongo[2], arrDataMongo[3],
							arrData[0]);
				} else if (arrDataMongo.length == 5) {
					listResult = (List<Boolean>) ExecuteJSUtil.executeJs(pathFunction, rule,
							arrDataMongo[0], arrDataMongo[1], arrDataMongo[2], arrDataMongo[3],
							arrDataMongo[4], arrData[0]);
				} else if (arrDataMongo.length == 6) {
					listResult = (List<Boolean>) ExecuteJSUtil.executeJs(pathFunction, rule,
							arrDataMongo[0], arrDataMongo[1], arrDataMongo[2], arrDataMongo[3],
							arrDataMongo[4], arrDataMongo[5], arrData[0]);
				} else if (arrDataMongo.length == 7) {
					listResult = (List<Boolean>) ExecuteJSUtil.executeJs(pathFunction, rule,
							arrDataMongo[0], arrDataMongo[1], arrDataMongo[2], arrDataMongo[3],
							arrDataMongo[4], arrDataMongo[5], arrDataMongo[6], arrData[0]);
				} else if (arrDataMongo.length == 8) {
					listResult = (List<Boolean>) ExecuteJSUtil.executeJs(pathFunction, rule,
							arrDataMongo[0], arrDataMongo[1], arrDataMongo[2], arrDataMongo[3],
							arrDataMongo[4], arrDataMongo[5], arrDataMongo[6], arrDataMongo[7],
							arrData[0]);
				} else if (arrDataMongo.length == 9) {
					listResult = (List<Boolean>) ExecuteJSUtil.executeJs(pathFunction, rule,
							arrDataMongo[0], arrDataMongo[1], arrDataMongo[2], arrDataMongo[3],
							arrDataMongo[4], arrDataMongo[5], arrDataMongo[6], arrDataMongo[7],
							arrDataMongo[8], arrData[0]);
				} else if (arrDataMongo.length == 10) {
					listResult = (List<Boolean>) ExecuteJSUtil.executeJs(pathFunction, rule,
							arrDataMongo[0], arrDataMongo[1], arrDataMongo[2], arrDataMongo[3],
							arrDataMongo[4], arrDataMongo[5], arrDataMongo[6], arrDataMongo[7],
							arrDataMongo[8], arrDataMongo[9], arrData[0]);
				} else if (arrDataMongo.length == 11) {
					listResult = (List<Boolean>) ExecuteJSUtil.executeJs(pathFunction, rule,
							arrDataMongo[0], arrDataMongo[1], arrDataMongo[2], arrDataMongo[3],
							arrDataMongo[4], arrDataMongo[5], arrDataMongo[6], arrDataMongo[7],
							arrDataMongo[8], arrDataMongo[9], arrDataMongo[10], arrData[0]);
				}
			
				
			}
			mapResult.put(key, listResult);
			System.out.println("thang  ="+mapResult.get(key));
			
			
		}
		
		return mapResult;
	}

}
