package com.digitexx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utilities {

	public LinkedList<Integer> getIndex(String extension, String path, String header_name,String seperate){
		LinkedList<Integer> listIndex = new LinkedList<>();
		int index=0;
		if(extension.equalsIgnoreCase("xlsx")){
			File f = new File(path);
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);
				Workbook wb = getWorkbook(fis, path);
				Sheet sheet = wb.getSheetAt(0);
				Row row = sheet.getRow(0);
				int lastCellNum = row.getLastCellNum();
				DataFormatter formatter = new DataFormatter();
				for (int i = 0; i < lastCellNum; i++) {
					Cell cell = row.getCell(i);
					String data = formatter.formatCellValue(cell);
					String[] arr_header = header_name.split(",");
					for (int j = 0; j < arr_header.length; j++) {
						
						if(data.equals(arr_header[j])){
							index= i;
							listIndex.add(index);
						}
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
		else if(extension.equalsIgnoreCase("csv")){
			BufferedReader csvReader = null;
		
			
				File csvFile = new File(path);
				
				if (csvFile.isFile()) {
					String row="";
					try {
						csvReader = new BufferedReader(new FileReader(csvFile));
						String readLine = csvReader.readLine();
						String[] rowData = readLine.split(seperate);
						for (int j = 0; j < rowData.length; j++) {
							
							String[] arr_header = header_name.split(",");
							for (int k = 0; k < arr_header.length; k++) {
								
								if(rowData[j].equals(arr_header[k])){
									index= j;
									listIndex.add(index);
								}
							}
							
//							mapFile.put(template.get(i).toString(),rowData[j].toString());
						}

						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
			
			
			try {
				csvReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listIndex;
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
}
