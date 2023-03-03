package com.digitexx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.digitexx.bean.BeanConfig;

public class test {

	public Map<String, Map<String, LinkedList<String>>> getDataCSV(
			List<String> listpath, List<String> listName) {

		Map<String, Map<String, Integer>> mapTemp = new LinkedHashMap<>();
		Map<String, Map<String, LinkedList<String>>> mapTotal = new LinkedHashMap<>();
		// Map<String, LinkedList<String>> mapDataCSV = new
		// LinkedHashMap<String, LinkedList<String>>();

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
					String[] header = row1.split(";");
					for (int j = 0; j < listName.size(); j++) {
						for (int k = 0; k < header.length; k++) {
//							System.out.println(k + 1);
							if (header[k].equals(listName.get(j))) {
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			try {
				int index;
				Map<String, Integer> map = mapTemp.get(listpath.get(i));

				while ((row = csvReader.readLine()) != null) {
					System.out.println(row);
					for (int k = 0; k < listName.size(); k++) {
						String[] data = row.split(";");
						if (data.length >  1) {
							if (map.containsKey(listName.get(k))) {
								index = map.get(listName.get(k));
								System.out.println("index ===" + index);

								if (mapTotal.containsKey(csvFile.toString())) {
									Map<String, LinkedList<String>> map2 = mapTotal
											.get(csvFile.toString());
									if (map2.containsKey(listName.get(k))) {
										LinkedList<String> listData = map2
												.get(listName.get(k));
										listData.add(data[index]);
										map2.put(listName.get(k), listData);
										mapTotal.put(csvFile.toString(), map2);

									} else {
										// Map<String, LinkedList<String>>
										// mapData = new LinkedHashMap<>();
										LinkedList<String> listData = new LinkedList<>();
										listData.add(data[index]);
										map2.put(listName.get(k), listData);
										mapTotal.put(csvFile.toString(), map2);

									}
								} else {
									Map<String, LinkedList<String>> mapData = new LinkedHashMap<>();
									LinkedList<String> listData = new LinkedList<>();
									listData.add(data[index]);
									mapData.put(listName.get(k), listData);
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

	public static void main(String[] args) {
//		test t = new test();
//		List<String> list = new LinkedList<>();
//		list.add("/home/thanglq/Desktop/test Export/505377_20200603 (copy).csv");
//		list.add("/home/thanglq/Desktop/test Export/test.csv");
//		List<String> listName = new LinkedList<>();
//		listName.add("test");
//		listName.add("Belegnummer");
//		listName.add("lqthagn");
//		Map<String, Map<String, LinkedList<String>>> dataCSV = t.getDataCSV(
//				list, listName);
//
//		Set<String> keySet = dataCSV.keySet();
//
//		for (String key : keySet) {
//			// System.out.println("file path == "+ key);
//			Map<String, LinkedList<String>> map = dataCSV.get(key);
//			Set<String> keySet2 = map.keySet();
//			for (String keye2 : keySet2) {
//				// System.out.println("header = "+ keye2);
//				LinkedList<String> linkedList = map.get(keye2);
//				for (int i = 0; i < linkedList.size(); i++) {
//					System.out.println("file path == " + key);
//					System.out.println("header = " + keye2);
//					System.out.println("du lieu la " + linkedList.get(i));
//				}
//			}
//		}
		
		String a = "function(input, parent, params) {\n  let layout_filename = params.layout_name;\n  if(layout_filename ==='Bad'){\n      return '';\n  }    \n  let Nachname = parent.Nachname;\n  var tableRegex = ['Dipl\\\\.\\\\-Bw\\\\. ', \n'Dipl\\\\.\\\\-Kfm\\\\. ', \n'Dipl\\\\.\\\\-Math\\\\. ', \n'Dipl\\\\.\\\\-med\\\\. ', \n'Dipl\\\\.\\\\-Päd\\\\. ', \n'Dipl\\\\.\\\\-Phys\\\\. ', \n'Dipl\\\\.\\\\-Psych\\\\. ', \n'Dipl\\\\.\\\\-Sozarb\\\\. ', \n'Dipl\\\\.\\\\-Vw\\\\. ', \n'Dipl\\\\.\\\\-Ww\\\\. ', \n'Dipl\\\\.oec\\\\. ', \n'Dipl\\\\.\\\\-Chem\\\\. ', \n'Dipl\\\\.rer\\\\.pol\\\\. ', \n'Dipl\\\\.Wirt\\\\.Inf\\\\. ', \n'Dipl\\\\.Wirt\\\\.Ing\\\\. ', \n'Mag\\\\. ', \n'Pr\\\\.Doz\\\\. ', \n'Prof\\\\. ', \n'Prof\\\\.Dr\\\\. ', \n'Prof\\\\.Dr\\\\.Ing\\\\. ', \n'Prof\\\\.Dr\\\\.med\\\\. ', \n'Prof\\\\.Dr\\\\.med\\\\.habil\\\\. ', \n'Dipl\\\\.\\\\-Designer ', \n'Dr\\\\. ', \n'Dr\\\\.agr ', \n'Dr\\\\.Dr\\\\.med\\\\. ', \n'Dr\\\\.E\\\\.h\\\\. ', \n'Dr\\\\.h\\\\.c\\\\. ', \n'Dr\\\\.Ing\\\\. ', \n'Dr\\\\.jur\\\\. ', \n'Dr\\\\.jur\\\\.utr\\\\. ', \n'Dr\\\\.med\\\\. ', \n'Dr\\\\.med\\\\.dent\\\\. ', \n'Dipl\\\\.\\\\-Dolm\\\\. ', \n'Dr\\\\.med\\\\.habil\\\\. ', \n'Dr\\\\.med\\\\.vet\\\\. ', \n'Dr\\\\.oec\\\\. ', \n'Dr\\\\.oec\\\\.publ\\\\. ', \n'Dr\\\\.paed\\\\. ', \n'Dr\\\\.pharm\\\\. ', \n'Dr\\\\.phil\\\\. ', \n'Dr\\\\.phil\\\\.nat\\\\. ', \n'Dr\\\\.rer\\\\.mont\\\\. ', \n'Dr\\\\.rer\\\\.nat\\\\. ', \n'Dipl\\\\.\\\\-Geogr\\\\. ', \n'Dr\\\\.rer\\\\.oec\\\\. ', \n'Dr\\\\.rer\\\\.pol\\\\. ', \n'Dr\\\\.rer\\\\.techn\\\\. ', \n'Dr\\\\.sc\\\\.hum\\\\. ', \n'Dr\\\\.sc\\\\.math\\\\. ', \n'Dr\\\\.sc\\\\.med\\\\. ', \n'Dr\\\\.sc\\\\.nat\\\\. ', \n'Dr\\\\.sc\\\\.pol ', \n'Dr\\\\.theol\\\\. ', \n'Baron ', \n'Dipl\\\\.\\\\-Graf\\\\. ', \n'Baroness ', \n'Baronesse ', \n'Baronin ', \n'Freiherr ', \n'Freifrau ', \n'Fürst ', \n'Fürstin ', \n'Graf ', \n'Gräfin ', \n'Herzog ', \n'Dipl\\\\.\\\\-Gwl\\\\. ', \n'Herzogin ', \n'Markgraf ', \n'Markgräfin ', \n'Prinz ', \n'Prinzessin ', \n'Jur\\\\. ', \n'Notar ', \n'Volkswirt ', \n'Dipl\\\\.\\\\-Biol\\\\. ', \n'Dr ', \n'Dipl\\\\.\\\\-Inf\\\\. ', \n'Sr\\\\. ', \n'Pf\\\\. ', \n'Pfr\\\\. ', \n'Pfr\\\\.\\si\\\\.\\sR\\\\. ', \n'Dipl\\\\.\\\\-Ing\\\\. ']\n  \n  var titel = params.splitString(Nachname, tableRegex).split('`')[0];\n  var name_export = params.splitString(Nachname, tableRegex).split('`')[1];\n  var ti_export = titel.replace(/[\\s\\t]+/g,' ');;\nreturn ti_export.trim();\n}";
			String name = "Nachname";
			if(a.contains("parent."+name)){
				
			}
			
	}
}
