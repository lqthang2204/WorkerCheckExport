package com.digitexx.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
















import com.digitexx.bean.BeanConfig;



public class Util {
	
	public Map<String, Map<String, LinkedList<String>>> getDataCSV(
			List<String> listpath, String listName, BeanConfig config) {

		Map<String, Map<String, Integer>> mapTemp = new LinkedHashMap<>();
		Map<String, Map<String, LinkedList<String>>> mapTotal = new LinkedHashMap<>();
		// Map<String, LinkedList<String>> mapDataCSV = new
		// LinkedHashMap<String, LinkedList<String>>();
		String[] name = listName.split(",");
		for (int i = 0; i < listpath.size(); i++) {
			// System.out.println("file path thu "+ (i+1));
			File csvFile = new File(listpath.get(i));
			String row;
			String result;
			BufferedReader csvReader = null;
			if (csvFile.isFile()) {
				try {
					csvReader = new BufferedReader(new FileReader(
							listpath.get(i)));
					String row1 = csvReader.readLine();
					String[] header = row1.split(config.getSeperate());
					
					for (int j = 0; j < name.length; j++) {
						for (int k = 0; k < header.length; k++) {
//							System.out.println(k + 1);
							if (header[k].equals(name[j].replace("\"", ""))) {
								if (mapTemp.containsKey(csvFile.toString())) {
									Map<String, Integer> map = mapTemp
											.get(csvFile.toString());
									if (map.containsKey(header[k])) {
										int index = map.get(header[k]);
										index = k;
										map.put(header[k], index);
									} else {
										int index;
										index = k;
										map.put(header[k], index);
									}
									mapTemp.put(listpath.get(i), map);

								} else {
									Map<String, Integer> map = new LinkedHashMap<>();
									int index = k;

									map.put(header[k], index);
									mapTemp.put(listpath.get(i), map);
								}
							}
						}

					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			try {
				int index;
				Map<String, Integer> map = mapTemp.get(listpath.get(i));

				while ((row = csvReader.readLine()) != null) {
//					System.out.println(row);
					for (int k = 0; k < name.length; k++) {
						String[] data = row.split(config.getSeperate());
						if (data.length >  1) {
							String nameTemp = name[k].replace("\"", "");
							if (map.containsKey(nameTemp)) {
								index = map.get(nameTemp);
//								System.out.println("index ===" + index);

								if (mapTotal.containsKey(csvFile.toString())) {
									Map<String, LinkedList<String>> map2 = mapTotal
											.get(csvFile.toString());
									if (map2.containsKey(nameTemp)) {
										LinkedList<String> listData = map2
												.get(nameTemp);
										listData.add(data[index]);
										map2.put(nameTemp, listData);
										mapTotal.put(csvFile.toString(), map2);

									} else {
										LinkedList<String> listData = new LinkedList<>();
										listData.add(data[index]);
										map2.put(nameTemp, listData);
										mapTotal.put(csvFile.toString(), map2);

									}
								} else {
									Map<String, LinkedList<String>> mapData = new LinkedHashMap<>();
									LinkedList<String> listData = new LinkedList<>();
									listData.add(data[index]);
									mapData.put(nameTemp, listData);
									mapTotal.put(csvFile.toString(), mapData);
								}

							}
						}
					}
				}

				csvReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return mapTotal;
	}
	public Map<String, LinkedList<Map<String, LinkedList<String>>>> getDataCSVBatch(
			List<String> listpath, String listName, BeanConfig config, String index_csv) {
		Map<String, LinkedList<Map<String, LinkedList<String>>>> mapTotal = new LinkedHashMap<>();
		LinkedList<Map<String, LinkedList<String>>> list = new LinkedList<>() ;
//		Map<String, Map<String, LinkedList<String>>> mapTotal = new LinkedHashMap<>();
		// Map<String, LinkedList<String>> mapDataCSV = new
		// LinkedHashMap<String, LinkedList<String>>();
		if(!config.isIndex()){
			String[] name = listName.split(",");
			for (int i = 0; i < listpath.size(); i++) {
				Map<String, Map<String, Integer>> mapTemp = new LinkedHashMap<>();
				// System.out.println("file path thu "+ (i+1));
				File csvFile = new File(listpath.get(i));
				String row;
				String result;
				BufferedReader csvReader = null;
				if (csvFile.isFile()) {
					try {
						csvReader = new BufferedReader(new FileReader(
								listpath.get(i)));
						String row1 = csvReader.readLine();
						String[] header = row1.split(config.getSeperate());
						
						for (int j = 0; j < name.length; j++) {
							for (int k = 0; k < header.length; k++) {
//								System.out.println(k + 1);
								if (header[k].equals(name[j].replace("\"", ""))) {
									if (mapTemp.containsKey(csvFile.toString())) {
										Map<String, Integer> map = mapTemp
												.get(csvFile.toString());
										if (map.containsKey(header[k])) {
											int index = map.get(header[k]);
											index = k;
											map.put(header[k], index);
										} else {
											int index;
											index = k;
											map.put(header[k], index);
										}
										mapTemp.put(listpath.get(i), map);

									} else {
										Map<String, Integer> map = new LinkedHashMap<>();
										int index = k;

										map.put(header[k], index);
										mapTemp.put(listpath.get(i), map);
									}
								}
							}

						}

					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				try {
					int index;
					if(!mapTemp.isEmpty()){
						Map<String, Integer> map = mapTemp.get(listpath.get(i));

						while ((row = csvReader.readLine()) != null) {
//							System.out.println(row);
							for (int k = 0; k < name.length; k++) {
								String[] data = row.split(config.getSeperate());
								if (data.length >  1) {
									String nameTemp = name[k].replace("\"", "");
									if (map.containsKey(nameTemp)) {
										
										index = map.get(nameTemp);
//										System.out.println("index ===" + index);

										if (mapTotal.containsKey(csvFile.toString())) {
											 LinkedList<Map<String, LinkedList<String>>> linkedList = mapTotal.get(csvFile.toString());
											 for (int l = 0; l < linkedList.size(); l++) {
												 Map<String, LinkedList<String>> map2 = linkedList.get(l);
													if (map2.containsKey(nameTemp)) {
														LinkedList<String> listData = map2
																.get(nameTemp);
														listData.add(data[index]);
														map2.put(nameTemp, listData);
														
														mapTotal.put(csvFile.toString(), linkedList);
														

													} else {
														LinkedList<String> listData = new LinkedList<>();
											
														listData.add(data[index]);
														map2.put(nameTemp, listData);
														
														mapTotal.put(csvFile.toString(), list);

													}
											}
										
										} else {
											Map<String, LinkedList<String>> mapData = new LinkedHashMap<>();
											LinkedList<String> listData = new LinkedList<>();
											listData.add(data[index]);
											mapData.put(nameTemp, listData);
											if(mapTotal.containsKey(csvFile.toString())){
												LinkedList<Map<String, LinkedList<String>>> linkedList = mapTotal.get(csvFile.toString());
												linkedList.add(mapData);
												mapTotal.put(csvFile.toString(), list);
											}else{
												LinkedList<Map<String, LinkedList<String>>> linkedList = new LinkedList<>();
												linkedList.add(mapData);
												mapTotal.put(csvFile.toString(), linkedList);
											}
										}

									}
								}
							}
						}
					}
					

					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					try {
						csvReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
		else{
			String[] arrIndex = index_csv.split(",");
			for (int i = 0; i < listpath.size(); i++) {
				Map<String, Map<String, Integer>> mapTemp = new LinkedHashMap<>();
				// System.out.println("file path thu "+ (i+1));
				File csvFile = new File(listpath.get(i));
				String row;
				String result;
				BufferedReader csvReader = null;
				if (csvFile.isFile()) {
					try {
						csvReader = new BufferedReader(new FileReader(
								listpath.get(i)));
						if(config.getRow_start_check()==0){
							while((row = csvReader.readLine()) != null){
								String[] data = row.split(config.getSeperate());
								for (int j = 0; j < data.length; j++) {
									
								}
								
							}
						}
						else {
							for (int j = 0; j < config.getRow_start_check(); j++) {
								
									csvReader.readLine();
							}
							while((row = csvReader.readLine()) != null){
								String[] data = row.split(config.getSeperate());
								
									for (int j2 = 0; j2 < arrIndex.length; j2++) {
										
										if (mapTotal.containsKey(csvFile.toString())) {
											LinkedList<Map<String, LinkedList<String>>> linkedList = mapTotal.get(csvFile.toString());
											 for (int l = 0; l < linkedList.size(); l++) {
												 Map<String, LinkedList<String>> map2 = linkedList.get(l);
													if (map2.containsKey(arrIndex[j2].replace("\"", ""))) {
														LinkedList<String> listData = map2
																.get(arrIndex[j2].replace("\"", ""));
														listData.add(data[Integer.parseInt(arrIndex[j2].replace("\"", ""))]);
														map2.put(arrIndex[j2].replace("\"", ""), listData);
														
														mapTotal.put(csvFile.toString(), linkedList);
														

													} else {
														LinkedList<String> listData = new LinkedList<>();
											
														listData.add(data[Integer.parseInt(arrIndex[j2].replace("\"", ""))]);
														map2.put(arrIndex[j2].replace("\"", ""), listData);
														
														mapTotal.put(csvFile.toString(), list);

													}
											}
											
										}
										else{
											Map<String, LinkedList<String>> mapData = new LinkedHashMap<>();
											LinkedList<String> listData = new LinkedList<>();
											listData.add(data[Integer.parseInt(arrIndex[j2].replace("\"", ""))]);
											mapData.put(arrIndex[j2].replace("\"", ""), listData);
											if(mapTotal.containsKey(csvFile.toString())){
												LinkedList<Map<String, LinkedList<String>>> linkedList = mapTotal.get(csvFile.toString());
												linkedList.add(mapData);
												mapTotal.put(csvFile.toString(), linkedList);
											}else{
												LinkedList<Map<String, LinkedList<String>>> linkedList = new LinkedList<>();
												linkedList.add(mapData);
												mapTotal.put(csvFile.toString(), linkedList);
											}
											
										}
									}
									
									
									
								
								
							}
						}
						
					}
					catch(Exception e){
						e.printStackTrace();
					}
					finally{
						try {
							csvReader.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
		}
	

		return mapTotal;
	}
	
	public void ExportResultFromCSV(Map<String, Map<String, LinkedList<String>>> mapdataCSV,Map<String, List<Boolean>> mapResult,String header, BeanConfig config, String document_id,Map<String, LinkedList<String>> mapDataToMongo){
		Set<String> files = mapResult.keySet();
		
		Date date = new Date();
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String folder_name = ft.format(date);
		for(String file: files){
			
			
			JSONArray template = (JSONArray) config.getTemplate();
			String path= template.getString(0);
			int index = path.lastIndexOf("/");
			path = path.substring(0,index+1)+folder_name;
			File f = new File(path);
			if(!f.exists()){
				f.mkdir();
			}
//			int lastIndexOf = file.lastIndexOf("/");
//			file = file.substring(0, lastIndexOf+1);
			String nameFile=path+"/"+config.getProject_name()+"_TestExport"+".xlsx";
			File f_new = new File(nameFile);
			XSSFWorkbook wb = null;
			
			Sheet sheet = null;
			Row row = null;
			String[] name = header.split(",");
			if(f_new.exists()){
				try {
					FileInputStream fis = new FileInputStream(f_new);
					wb = new XSSFWorkbook(fis);
					XSSFCellStyle headerStyleTrue = wb.createCellStyle();
					XSSFCellStyle headerStyleFalse = wb.createCellStyle();
					CreationHelper createHelper = wb.getCreationHelper();
					headerStyleTrue.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					headerStyleTrue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerStyleFalse.setFillForegroundColor(IndexedColors.RED.getIndex());
					headerStyleFalse.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					sheet = wb.getSheet(document_id);
					if(sheet==null){
						 sheet = wb.createSheet(document_id);
					}
					int lastRowNum = sheet.getLastRowNum();
//					Row rowFile = sheet.createRow(lastRowNum+1);
//					Cell cellFile = rowFile.createCell(0);
//					cellFile.setCellValue(file+ "_documentID "+document_id);
					for (int i = 1; i <= name.length; i++) {
						row = sheet.createRow(lastRowNum+i);
						Cell cell = row.createCell(0);
						cell.setCellValue(name[i-1].toString().replace("\"", ""));
						
						List<Boolean> list = mapResult.get(file);
						for (int j = 1; j <= list.size(); j++) {
								
							Map<String, LinkedList<String>> map = mapdataCSV.get(file);
							LinkedList<String> listCSV = map.get(name[i-1].toString().replace("\"", ""));
								Cell cellValue = row.createCell(j);
								cellValue.setCellValue(listCSV.get(j-1));
								
								if(list.get(j-1)==true){
									cellValue.setCellStyle(headerStyleTrue);
								}
								else{
									Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
									link.setAddress("Data MongoDB: "+mapDataToMongo.toString());
									cellValue.setHyperlink(link);
									cellValue.setCellStyle(headerStyleFalse);
								}
								

							}
							
							
						}

					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				try {
					wb = new XSSFWorkbook();
					XSSFCellStyle headerStyleTrue = wb.createCellStyle();
					XSSFCellStyle headerStyleFalse = wb.createCellStyle();
					CreationHelper createHelper = wb.getCreationHelper();
					headerStyleTrue.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					headerStyleTrue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerStyleFalse.setFillForegroundColor(IndexedColors.RED.getIndex());
					headerStyleFalse.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					sheet = wb.createSheet(document_id);
					int lastRowNum = sheet.getLastRowNum();
//					Row rowFile = sheet.createRow(0);
//					Cell cellFile = rowFile.createCell(0);
//					cellFile.setCellValue("documentID - "+document_id);
					Row rowHeader ;
					for (int i = 0; i < name.length; i++) {
						 rowHeader = sheet.createRow(lastRowNum+i);
						Cell cellHeader = rowHeader.createCell(0);
//						cellHeader.setCellValue("test");
						cellHeader.setCellValue(name[i].toString().replace("\"", ""));
						
						List<Boolean> list = mapResult.get(file);
						for (int j = 1; j <= list.size(); j++) {	
							Map<String, LinkedList<String>> map = mapdataCSV.get(file);
							LinkedList<String> listCSV = map.get(name[i].toString().replace("\"", ""));
								Cell cellValue = rowHeader.createCell(j);
								cellValue.setCellValue(listCSV.get(j-1));
								
								if(list.get(j-1)==true){
									cellValue.setCellStyle(headerStyleTrue);
								}
								else{
									cellValue.setCellStyle(headerStyleFalse);
									Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
									link.setAddress("Data MongoDB: "+mapDataToMongo.toString());
									cellValue.setHyperlink(link);
								}
							
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			try {
				FileOutputStream out;
				out = new FileOutputStream(new File(nameFile));
				wb.write(out);
				out.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	}
	public void ExportResultFromCSVWithBatch(Map<String, LinkedList<Map<String, LinkedList<String>>>> mapDataCSV,Map<String, List<Boolean>> mapResult,String header, BeanConfig config, String document_id,Map<String, LinkedList<String>> listMongoDB,String index_csv){
		Set<String> files = mapResult.keySet();
		Date date = new Date();
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String folder_name = ft.format(date);
		for(String file: files){
			JSONArray template = (JSONArray) config.getTemplate();
			String path= template.getString(0);
			int index = path.lastIndexOf("/");
			path = path.substring(0,index+1)+folder_name;
			File f = new File(path);
	        if(!f.exists()){
		        f.mkdir();
	        }
			int lastIndexOf = file.lastIndexOf("/")+1;
			String substring1= file.substring(lastIndexOf);
			int lastIndexOf2 = substring1.lastIndexOf(".");
			System.out.println(substring1.substring(0,lastIndexOf2));
			String file_name = substring1.substring(0,lastIndexOf2);
			
			
//			int lastIndexOf = file.lastIndexOf("/");
//			file = file.substring(0, lastIndexOf+1);
			String nameFile=path+"/"+config.getProject_name()+"_TestExport_"+file_name+".xlsx";
			File f_new = new File(nameFile);
			XSSFWorkbook wb = null;
			
			Sheet sheet = null;
			Row row = null;
			String[] name;
			if(!config.isIndex()){
				
				name= header.split(",");
			}
			else{
				name= index_csv.split(",");
			}
			if(f_new.exists()){
				try {
					FileInputStream fis = new FileInputStream(f_new);
					wb = new XSSFWorkbook(fis);
					XSSFCellStyle headerStyleTrue = wb.createCellStyle();
					XSSFCellStyle headerStyleFalse = wb.createCellStyle();
					CreationHelper createHelper = wb.getCreationHelper();
					headerStyleTrue.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					headerStyleTrue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerStyleFalse.setFillForegroundColor(IndexedColors.RED.getIndex());
					headerStyleFalse.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					sheet = wb.getSheet(document_id);
					if(sheet==null){
						 sheet = wb.createSheet(document_id);
					}
					int lastRowNum = sheet.getLastRowNum();
//					Row rowFile = sheet.createRow(lastRowNum+1);
//					Cell cellFile = rowFile.createCell(0);
//					cellFile.setCellValue(file+ "_documentID "+document_id);
					for (int i = 1; i <= name.length; i++) {
						row = sheet.createRow(lastRowNum+i);
						Cell cell = row.createCell(0);
						cell.setCellValue(name[i-1].toString().replace("\"", ""));
						
						List<Boolean> list = mapResult.get(file);
						for (int j = 1; j <= list.size(); j++) {
							LinkedList<Map<String, LinkedList<String>>> linkedList = mapDataCSV.get(file);
							for (int k = 0; k < linkedList.size(); k++) {
								Map<String, LinkedList<String>> map = linkedList.get(k);
//								Map<String, LinkedList<String>> map = mapDataCSV.get(file);
								LinkedList<String> listCSV = map.get(name[i-1].toString().replace("\"", ""));
									Cell cellValue = row.createCell(j);
									cellValue.setCellValue(listCSV.get(j-1));
									
									if(list.get(j-1)==true){
										cellValue.setCellStyle(headerStyleTrue);
//										Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
//										link.setAddress("Data MongoDB: "+listMongoDB.toString());
//										cellValue.setHyperlink(link);
									}
									else{
										cellValue.setCellStyle(headerStyleFalse);
										Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
										link.setAddress("Data MongoDB: "+listMongoDB.toString());
										cellValue.setHyperlink(link);
									}
							}
							}	
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				try {
					wb = new XSSFWorkbook();
					XSSFCellStyle headerStyleTrue = wb.createCellStyle();
					XSSFCellStyle headerStyleFalse = wb.createCellStyle();
					CreationHelper createHelper = wb.getCreationHelper();
					headerStyleTrue.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					headerStyleTrue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerStyleFalse.setFillForegroundColor(IndexedColors.RED.getIndex());
					headerStyleFalse.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					sheet = wb.createSheet(document_id);
					int lastRowNum = sheet.getLastRowNum();
//					Row rowFile = sheet.createRow(0);
//					Cell cellFile = rowFile.createCell(0);
//					cellFile.setCellValue("documentID - "+document_id);
					Row rowHeader ;
					for (int i = 0; i < name.length; i++) {
						 rowHeader = sheet.createRow(lastRowNum+i);
						Cell cellHeader = rowHeader.createCell(0);
//						cellHeader.setCellValue("test");
						cellHeader.setCellValue(name[i].toString().replace("\"", ""));
						
						List<Boolean> list = mapResult.get(file);
						for (int j = 1; j <= list.size(); j++) {	
							LinkedList<Map<String, LinkedList<String>>> linkedList = mapDataCSV.get(file);
							for (int k = 0; k < linkedList.size(); k++) {
								Map<String, LinkedList<String>> map = linkedList.get(k);
							
								LinkedList<String> listCSV = map.get(name[i].toString().replace("\"", ""));
									Cell cellValue = rowHeader.createCell(j);
									cellValue.setCellValue(listCSV.get(j-1));
									
									if(list.get(j-1)==true){
										cellValue.setCellStyle(headerStyleTrue);
//										Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
//										link.setAddress("Data MongoDB: "+listMongoDB.toString());
//										cellValue.setHyperlink(link);
									}
									else{
										cellValue.setCellStyle(headerStyleFalse);
										Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
										link.setAddress("Data MongoDB: "+listMongoDB.toString());
										cellValue.setHyperlink(link);
									}
							}
							
							
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			try {
				FileOutputStream out;
				out = new FileOutputStream(new File(nameFile));
				wb.write(out);
				out.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	}
	public void ExportResultFromXLSXWithBatch(Map<String, Map<String, LinkedList<String>>> mapDataXLSX,Map<String, List<Boolean>> mapResult,String header, BeanConfig config, String document_id,Map<String, LinkedList<String>> dataToMongoWithBatch,String index_file){
		Set<String> files = mapResult.keySet();
		Date date = new Date();
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String folder_name = ft.format(date);
		for(String file: files){
			JSONArray template = (JSONArray) config.getTemplate();
			String path= template.getString(0);
			int index = path.lastIndexOf("/");
			path = path.substring(0,index+1)+folder_name;
			File f = new File(path);
	        if(!f.exists()){
		        f.mkdir();
	        }
			int lastIndexOf = file.lastIndexOf("/")+1;
			String substring1= file.substring(lastIndexOf);
			int lastIndexOf2 = substring1.lastIndexOf(".");
			System.out.println(substring1.substring(0,lastIndexOf2));
			String file_name = substring1.substring(0,lastIndexOf2);
			
			
//			int lastIndexOf = file.lastIndexOf("/");
//			file = file.substring(0, lastIndexOf+1);
			String nameFile=path+"/"+config.getProject_name()+"_TestExport_"+file_name+".xlsx";
			File f_new = new File(nameFile);
			XSSFWorkbook wb = null;
			
			Sheet sheet = null;
			Row row = null;
			String[] name ;
			if(config.isIndex()){
				 name = index_file.split(",");
			}else{
				
				name = header.split(",");
			}
			if(f_new.exists()){
				try {
					FileInputStream fis = new FileInputStream(f_new);
					wb = new XSSFWorkbook(fis);
					XSSFCellStyle headerStyleTrue = wb.createCellStyle();
					XSSFCellStyle headerStyleFalse = wb.createCellStyle();
					CreationHelper createHelper = wb.getCreationHelper();
					headerStyleTrue.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					headerStyleTrue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerStyleFalse.setFillForegroundColor(IndexedColors.RED.getIndex());
					headerStyleFalse.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					sheet = wb.getSheet(document_id);
					if(sheet==null){
						 sheet = wb.createSheet(document_id);
					}
					int lastRowNum = sheet.getLastRowNum();
//					Row rowFile = sheet.createRow(lastRowNum+1);
//					Cell cellFile = rowFile.createCell(0);
//					cellFile.setCellValue(file+ "_documentID "+document_id);
					for (int i = 1; i <= name.length; i++) {
						row = sheet.createRow(lastRowNum+i);
						Cell cell = row.createCell(0);
						cell.setCellValue(name[i-1].toString().replace("\"", ""));
						
						List<Boolean> list = mapResult.get(file);
						for (int j = 1; j <= list.size(); j++) {
							Map<String, LinkedList<String>> map = mapDataXLSX.get(file);
							
							
//								Map<String, LinkedList<String>> map = mapDataCSV.get(file);
								LinkedList<String> listCSV = map.get(name[i-1].toString().replace("\"", ""));
									Cell cellValue = row.createCell(j);
									cellValue.setCellValue(listCSV.get(j-1));
									
									if(list.get(j-1)==true){
										cellValue.setCellStyle(headerStyleTrue);
//										Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
//										link.setAddress("Data MongoDB: "+listMongoDB.toString());
//										cellValue.setHyperlink(link);
									}
									else{
										cellValue.setCellStyle(headerStyleFalse);
										Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
										link.setAddress("Data MongoDB: "+dataToMongoWithBatch.toString());
										cellValue.setHyperlink(link);
									}
							
							}	
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				try {
					wb = new XSSFWorkbook();
					XSSFCellStyle headerStyleTrue = wb.createCellStyle();
					XSSFCellStyle headerStyleFalse = wb.createCellStyle();
					CreationHelper createHelper = wb.getCreationHelper();
					headerStyleTrue.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					headerStyleTrue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerStyleFalse.setFillForegroundColor(IndexedColors.RED.getIndex());
					headerStyleFalse.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					sheet = wb.createSheet(document_id);
					int lastRowNum = sheet.getLastRowNum();
//					Row rowFile = sheet.createRow(0);
//					Cell cellFile = rowFile.createCell(0);
//					cellFile.setCellValue("documentID - "+document_id);
					Row rowHeader ;
					for (int i = 0; i < name.length; i++) {
						 rowHeader = sheet.createRow(lastRowNum+i);
						Cell cellHeader = rowHeader.createCell(0);
//						cellHeader.setCellValue("test");
						cellHeader.setCellValue(name[i].toString().replace("\"", ""));
						
						List<Boolean> list = mapResult.get(file);
						for (int j = 1; j <= list.size(); j++) {	
							Map<String, LinkedList<String>> map = mapDataXLSX.get(file);
						
					
							
								LinkedList<String> listCSV = map.get(name[i].toString().replace("\"", ""));
									Cell cellValue = rowHeader.createCell(j);
									cellValue.setCellValue(listCSV.get(j-1));
									
									if(list.get(j-1)==true){
										cellValue.setCellStyle(headerStyleTrue);
//										Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
//										link.setAddress("Data MongoDB: "+listMongoDB.toString());
//										cellValue.setHyperlink(link);
									}
									else{
										cellValue.setCellStyle(headerStyleFalse);
										Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
										link.setAddress("Data MongoDB: "+dataToMongoWithBatch.toString());
										cellValue.setHyperlink(link);
									}
							
							
							
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			try {
				FileOutputStream out;
				out = new FileOutputStream(new File(nameFile));
				wb.write(out);
				out.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	}
	
	public Map<String, Map<String, LinkedList<String>>> getDataXML(
			List<String> listpath, String listName, BeanConfig config) {
		Map<String, Map<String, LinkedList<String>>> mapResult = new LinkedHashMap<>();
		Map<String, LinkedList<String>> map = new LinkedHashMap<>();
		LinkedList<String> list = new LinkedList<>();
		
		// Map<String, LinkedList<String>> mapDataCSV = new
		// LinkedHashMap<String, LinkedList<String>>();
		String[] name = listName.split(",");
		for (int i = 0; i < listpath.size(); i++) {
			for (int j = 0; j < name.length; j++) {
				File xmlFile = new File(listpath.get(i));
		        try {
		        	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
					Document doc = documentBuilder.parse(xmlFile);
				     NodeList nl=doc.getDocumentElement().getChildNodes();
				     
				     for(int k=0;k<nl.getLength();k++){
				    	  list = getDataXML((Node)nl.item(k),name[j].replace("\"", ""), list);  
			         }
				     map.put(name[j], list);
					
				} catch (ParserConfigurationException | SAXException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				

			}
			
			mapResult.put(listpath.get(i), map);
			}
			// System.out.println("file path thu "+ (i+1));
		

		return mapResult;
	}
	public  LinkedList<String> getDataXML(Node nodes, String name, LinkedList<String> list){
		
		String[] nodeTemp = name.split(":");
		String parentNodeTemp = nodeTemp[0];
		String childNodeTemp = nodeTemp[1];
		   if(nodes.hasChildNodes()){
	           NodeList nl=nodes.getChildNodes();
	           System.out.println(((Node) nl).getNodeName());
	           if(nl.getLength()>1){
	        	   
	        	   for(int j=0;j<nl.getLength();j++){
	        		   getDataXML(nl.item(j), name, list);
		           }
	           }else{ 
	        	  
	        	   if(((Node) nl).getNodeName().equals(childNodeTemp) && ((Node) nl).getParentNode().toString().contains((parentNodeTemp)) ){
	        		   
//	        		   System.out.println(((Node) nl).getNodeName() +" co data la " + ((Node) nl).getTextContent());
	        		   list.add(((Node) nl).getTextContent());
	        	   }
	        	  
	           }

	       }
		   return list;
	      
	   }

	public void exportCheckXMLDOC(Map<String, Map<String, LinkedList<String>>> mapdataXML,Map<String, List<Boolean>> mapResult,String header, BeanConfig config, String document_id,Map<String, LinkedList<String>> mapDataToMongo){
		Date date = new Date();
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String folder_name = ft.format(date);
		Set<String> files = mapResult.keySet();
		for(String file: files){

			JSONArray template = (JSONArray) config.getTemplate();
			String path= template.getString(0);
			int index = path.lastIndexOf("/");
			path = path.substring(0,index+1)+folder_name;
			 File f = new File(path);
		        if(!f.exists()){
			        f.mkdir();
		        }
//			int lastIndexOf = file.lastIndexOf("/");
//			file = file.substring(0, lastIndexOf+1);
		        String nameFile=path+"/"+config.getProject_name()+"_TestExport"+".xlsx";
				File f_new = new File(nameFile);
			XSSFWorkbook wb = null;
			
			Sheet sheet = null;
			Row row = null;
			String[] name = header.split(",");
			if(f_new.exists()){
				try {
					FileInputStream fis = new FileInputStream(f_new);
					wb = new XSSFWorkbook(fis);
					XSSFCellStyle headerStyleTrue = wb.createCellStyle();
					XSSFCellStyle headerStyleFalse = wb.createCellStyle();
					CreationHelper createHelper = wb.getCreationHelper();
					headerStyleTrue.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					headerStyleTrue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerStyleFalse.setFillForegroundColor(IndexedColors.RED.getIndex());
					headerStyleFalse.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					sheet = wb.getSheet(document_id);
					if(sheet==null){
						 sheet = wb.createSheet(document_id);
					}
					int lastRowNum = sheet.getLastRowNum();
//					Row rowFile = sheet.createRow(lastRowNum+1);
//					Cell cellFile = rowFile.createCell(0);
//					cellFile.setCellValue(file+ "_documentID "+document_id);
					for (int i = 1; i <= name.length; i++) {
						row = sheet.createRow(lastRowNum+i);
						Cell cell = row.createCell(0);
						cell.setCellValue(name[i-1].toString().replace("\"", ""));
						
						List<Boolean> list = mapResult.get(file);
						for (int j = 1; j <= list.size(); j++) {
								
							Map<String, LinkedList<String>> map = mapdataXML.get(file);
							LinkedList<String> listCSV = map.get(name[i-1].toString());
								Cell cellValue = row.createCell(j);
								cellValue.setCellValue(listCSV.get(j-1));
								if(list.get(j-1)==true){
									cellValue.setCellStyle(headerStyleTrue);
								}
								else{
									cellValue.setCellStyle(headerStyleFalse);
									Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
									link.setAddress("Data MongoDB: "+mapDataToMongo.toString());
									cellValue.setHyperlink(link);
								}
							}	
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					wb = new XSSFWorkbook();
					XSSFCellStyle headerStyleTrue = wb.createCellStyle();
					XSSFCellStyle headerStyleFalse = wb.createCellStyle();
					CreationHelper createHelper = wb.getCreationHelper();
					headerStyleTrue.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					headerStyleTrue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					headerStyleFalse.setFillForegroundColor(IndexedColors.RED.getIndex());
					headerStyleFalse.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					sheet = wb.createSheet(document_id);
					int lastRowNum = sheet.getLastRowNum();
//					Row rowFile = sheet.createRow(0);
//					Cell cellFile = rowFile.createCell(0);
//					cellFile.setCellValue("documentID - "+document_id);
					Row rowHeader ;
					for (int i = 0; i < name.length; i++) {
						 rowHeader = sheet.createRow(lastRowNum+i);
						Cell cellHeader = rowHeader.createCell(0);
//						cellHeader.setCellValue("test");
						cellHeader.setCellValue(name[i].toString().replace("\"", ""));
						List<Boolean> list = mapResult.get(file);
						for (int j = 1; j <= list.size(); j++) {	
							Map<String, LinkedList<String>> map = mapdataXML.get(file);
							LinkedList<String> listCSV = map.get(name[i].toString());
								Cell cellValue = rowHeader.createCell(j);
								cellValue.setCellValue(listCSV.get(j-1));
								if(list.get(j-1)==true){
									cellValue.setCellStyle(headerStyleTrue);
								}
								else{
									cellValue.setCellStyle(headerStyleFalse);
									Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
									link.setAddress("Data MongoDB: "+mapDataToMongo.toString());
									cellValue.setHyperlink(link);
								}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			try {
				FileOutputStream out;
				out = new FileOutputStream(new File(nameFile));
				wb.write(out);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	 public  Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
	        Workbook workbook = null;
	        if (excelFilePath.endsWith("xlsx")) {
	            workbook = new XSSFWorkbook(inputStream);
	        } else if (excelFilePath.endsWith("xls")) {
	            workbook = new HSSFWorkbook(inputStream);
	        } else {
	            throw new IllegalArgumentException("The specified file is not Excel file");
	        }
	 
	        return workbook;
	    }
	 

	 public void ExportTestCase(String template_excel,String pathTmp,String pathFile, Map<String, LinkedList<Map<String, LinkedList<String>>>> mapDataCSV,Map<String, List<Boolean>> mapResult,String header, BeanConfig config, String document_id,LinkedList<Map<String, LinkedList<String>>> listMongoDB){
		  	Workbook workbook = null;
	        Workbook workbookTemplate = null;
	        boolean isTemplate = false;
	        try (FileOutputStream fileOut = new FileOutputStream(pathFile);
	                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(4096);) {
	            File tmp = new File(pathTmp);
	            if (template_excel != null) {
	                isTemplate = true;
	                workbookTemplate = new XSSFWorkbook(template_excel);
	                workbookTemplate.write(outputStream);
	                workbook = WorkbookFactory.create(new ByteArrayInputStream(outputStream.toByteArray()));
	                workbookTemplate.close();
	            } else {
	                workbook = new XSSFWorkbook();
	            }
	        }
	        catch (Exception e) {
				e.printStackTrace();
			}
			
	            Set<String> files = mapResult.keySet();
	    		
	    		for(String file: files){
	    			JSONArray template = (JSONArray) config.getTemplate();
	    			String path= template.getString(0);
	    			int index = path.lastIndexOf("/");
	    			path = path.substring(0,index+1);
	    			File f = new File(path);
	    	        if(!f.exists()){
	    		        f.mkdir();
	    	        }
	    		
	    			
	    			
//	    			int lastIndexOf = file.lastIndexOf("/");
//	    			file = file.substring(0, lastIndexOf+1);
	    		
	    			File f_new = new File(pathFile);
	    		
	    			
	    			Sheet sheet = null;
	    			Row row = null;
	    			String[] name = header.split(",");
	    			if(f_new.exists()){
	    				try {
	    					FileInputStream fis = new FileInputStream(f_new);
	    					workbook = new XSSFWorkbook(fis);
	    					CellStyle headerStyleTrue = workbook.createCellStyle();
	    					CellStyle headerStyleFalse = workbook.createCellStyle();
	    					CreationHelper createHelper = workbook.getCreationHelper();
	    					headerStyleTrue.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	    					headerStyleTrue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    					headerStyleFalse.setFillForegroundColor(IndexedColors.RED.getIndex());
	    					headerStyleFalse.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    					sheet = workbook.getSheet(document_id);
	    					if(sheet==null){
	    						 sheet = workbook.createSheet(document_id);
	    					}
	    					int lastRowNum = sheet.getLastRowNum();
//	    					Row rowFile = sheet.createRow(lastRowNum+1);
//	    					Cell cellFile = rowFile.createCell(0);
//	    					cellFile.setCellValue(file+ "_documentID "+document_id);
	    					for (int i = 1; i <= name.length; i++) {
	    						row = sheet.createRow(lastRowNum+i);
	    						Cell cell = row.createCell(1);
	    						cell.setCellValue(name[i-1].toString().replace("\"", ""));
	    						
	    						List<Boolean> list = mapResult.get(file);
	    						for (int j = 1; j <= list.size(); j++) {
	    							LinkedList<Map<String, LinkedList<String>>> linkedList = mapDataCSV.get(file);
	    							for (int k = 0; k < linkedList.size(); k++) {
	    								Map<String, LinkedList<String>> map = linkedList.get(k);
//	    								Map<String, LinkedList<String>> map = mapDataCSV.get(file);
	    								LinkedList<String> listCSV = map.get(name[i-1].toString().replace("\"", ""));
	    									Cell cellValue = row.createCell(j+1);
	    									cellValue.setCellValue(listCSV.get(j-1));
	    									
	    									if(list.get(j-1)==true){
	    										cellValue.setCellStyle(headerStyleTrue);
//	    										Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
//	    										link.setAddress("Data MongoDB: "+listMongoDB.toString());
//	    										cellValue.setHyperlink(link);
	    									}
	    									else{
	    										cellValue.setCellStyle(headerStyleFalse);
	    										Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
	    										link.setAddress("Data MongoDB: "+listMongoDB.toString());
	    										cellValue.setHyperlink(link);
	    									}
	    							}
	    							}	
	    						}
	    				} catch (IOException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}
	    				
	    			}else{
	    				try {
	    					workbook = new XSSFWorkbook();
	    					CellStyle headerStyleTrue = workbook.createCellStyle();
	    					CellStyle headerStyleFalse = workbook.createCellStyle();
	    					CreationHelper createHelper = workbook.getCreationHelper();
	    					headerStyleTrue.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	    					headerStyleTrue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    					headerStyleFalse.setFillForegroundColor(IndexedColors.RED.getIndex());
	    					headerStyleFalse.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    					sheet = workbook.getSheetAt(0);
	    					int lastRowNum = sheet.getLastRowNum();
//	    					Row rowFile = sheet.createRow(0);
//	    					Cell cellFile = rowFile.createCell(0);
//	    					cellFile.setCellValue("documentID - "+document_id);
	    					Row rowHeader ;
	    					for (int i = 0; i < name.length; i++) {
	    						 rowHeader = sheet.createRow(lastRowNum+i);
	    						Cell cellHeader = rowHeader.createCell(0);
//	    						cellHeader.setCellValue("test");
	    						cellHeader.setCellValue(name[i].toString().replace("\"", ""));
	    						
	    						List<Boolean> list = mapResult.get(file);
	    						for (int j = 1; j <= list.size(); j++) {	
	    							LinkedList<Map<String, LinkedList<String>>> linkedList = mapDataCSV.get(file);
	    							for (int k = 0; k < linkedList.size(); k++) {
	    								Map<String, LinkedList<String>> map = linkedList.get(k);
	    							
	    								LinkedList<String> listCSV = map.get(name[i].toString().replace("\"", ""));
	    									Cell cellValue = rowHeader.createCell(j);
	    									cellValue.setCellValue(listCSV.get(j-1));
	    									
	    									if(list.get(j-1)==true){
	    										cellValue.setCellStyle(headerStyleTrue);
//	    										Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
//	    										link.setAddress("Data MongoDB: "+listMongoDB.toString());
//	    										cellValue.setHyperlink(link);
	    									}
	    									else{
	    										cellValue.setCellStyle(headerStyleFalse);
	    										Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
	    										link.setAddress("Data MongoDB: "+listMongoDB.toString());
	    										cellValue.setHyperlink(link);
	    									}
	    							}
	    							
	    							
	    						}
	    					}
	    				} catch (Exception e) {
	    					e.printStackTrace();
	    				}	
	    			}
	    			try {
	    				FileOutputStream out;
	    				out = new FileOutputStream(new File(pathFile));
	    				workbook.write(out);
	    				out.close();
	    				
	    			} catch (IOException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    			
	    		}

}
	 public Map<String, Map<String, LinkedList<String>>> getMapDataXlSX(List<String> listPathMongo, String columFile, BeanConfig info,String index){
		 Map<String, Map<String, LinkedList<String>>> map = new LinkedHashMap<>();
		 FileInputStream fis= null;
		 Workbook wb = null;
		if(!info.isIndex()){
			 try {
					
				 String[] arrHeader = columFile.split(",");
				 
				 for (int m = 0; m < arrHeader.length; m++) {
					 for (int i = 0; i < listPathMongo.size(); i++) {
						 String file_name = listPathMongo.get(i);
						 File f = new File(file_name);
							 fis = new FileInputStream(f);
							wb = getWorkbook(fis, file_name);
							Sheet sheet = wb.getSheetAt(0);
							int lastRowNum = sheet.getLastRowNum();
//							Row row_header = null;
//							if(info.getRow_start_check()==0){
//								row_header = sheet.getRow(info.getRow_start_check()-1);
//							}
							Row row_header  = sheet.getRow(info.getRow_start_check()-1);
							int cell_header = row_header.getLastCellNum();
							for (int j = 0; j < cell_header; j++) {
								DataFormatter formatter = new DataFormatter();
								Cell cell = row_header.getCell(j);
								String header_name = formatter.formatCellValue(cell);
								if(header_name.equals(arrHeader[m].replace("\"", ""))){
									
									for (int k = info.getRow_start_check(); k <= lastRowNum; k++) {
										Row row = sheet.getRow(k);
										Cell cell_data = row.getCell(j);
										DataFormatter formatter_data = new DataFormatter();
										String data = formatter_data.formatCellValue(cell_data);
										if (map.containsKey(file_name)) {
											Map<String, LinkedList<String>> mapTemp = map.get(file_name);
										
										if(mapTemp.containsKey(header_name)){
											LinkedList<String> list_temp = mapTemp.get(header_name);
											list_temp.add(data);
											mapTemp.put(header_name, list_temp);
											
										}else{
											LinkedList<String> list_temp = new LinkedList<>();
											list_temp.add(data);
											mapTemp.put(header_name, list_temp);
										}
										map.put(file_name, mapTemp);
									}
										else{
											Map<String, LinkedList<String>> mapTemp = new LinkedHashMap<>();
											LinkedList<String> list_temp = new LinkedList<>();
											list_temp.add(data);
											mapTemp.put(header_name, list_temp);
											map.put(file_name, mapTemp);
											
										}
									
									}
									
									
								}
							}
							
				}
				}
				 
			 }catch (Exception e) {
				e.printStackTrace();
			}
			 finally{
				 try {
					 fis.close();
					 wb.close();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			 }
		}
		else{
			String[] arrIndex = index.split(",");
			 
			 for (int m = 0; m < arrIndex.length; m++) {
				 for (int i = 0; i < listPathMongo.size(); i++) {
					 String file_name = listPathMongo.get(i);
					 File f = new File(file_name);
						 try {
							fis = new FileInputStream(f);
							wb = getWorkbook(fis, file_name);
							Sheet sheet = wb.getSheetAt(0);
							int lastRowNum = sheet.getLastRowNum();
							DataFormatter formatter_data = new DataFormatter();
							
							for (int j = info.getRow_start_check(); j <= lastRowNum; j++) {
								Row row = sheet.getRow(j);
								Cell cell = row.getCell(Integer.parseInt(arrIndex[m].replace("\"", "")));
								String data = formatter_data.formatCellValue(cell);
								if (map.containsKey(file_name)) {
									Map<String, LinkedList<String>> mapTemp = map.get(file_name);
								
								if(mapTemp.containsKey(arrIndex[m].replace("\"", ""))){
									LinkedList<String> list_temp = mapTemp.get(arrIndex[m].replace("\"", ""));
									list_temp.add(data);
									mapTemp.put(arrIndex[m].replace("\"", ""), list_temp);
									
								}else{
									LinkedList<String> list_temp = new LinkedList<>();
									list_temp.add(data);
									mapTemp.put(arrIndex[m].replace("\"", ""), list_temp);
								}
								map.put(file_name, mapTemp);
							}
								else{
									Map<String, LinkedList<String>> mapTemp = new LinkedHashMap<>();
									LinkedList<String> list_temp = new LinkedList<>();
									list_temp.add(data);
									mapTemp.put(arrIndex[m].replace("\"", ""), list_temp);
									map.put(file_name, mapTemp);
									
								}
								
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						

						
					
						
			}
			}
			
		}
		
				
				
				
			
		 
		 
		 
		 return map;
		 
	 
	 }
	 
}
