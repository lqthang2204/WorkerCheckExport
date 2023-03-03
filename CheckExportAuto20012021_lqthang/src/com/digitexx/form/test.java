package com.digitexx.form;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test {
//	private static Logger _log = LoggerFactory.getLogger(CollectorApp.class);
	
	public static void main(String[] args) throws Exception {
//		PropertyConfigurator.configure("config/log4j.properties");
//		_log.debug("run CollectorApp" );
//		Logger log = LoggerFactory.getLogger(CollectorApp.class);
//		QueueManager queueManager = new QueueManager();
//		try {
//			queueManager.setEnv(Configuration.getInstance().getProperty(Configuration.APP_QUEUE_HOST)
//					, Configuration.getInstance().getProperty(Configuration.APP_QUEUE_VIRTUALHOST), 
//					Configuration.getInstance().getProperty(Configuration.APP_QUEUE_USERNAME),
//					Configuration.getInstance().getProperty(Configuration.APP_QUEUE_PASS));
//			queueManager.consume(Configuration.getInstance().getProperty(Configuration.APP_QUEUE_NAME),
//					new CollectorFinalTask(queueManager));
//		} catch (Exception e) {
//			log.error("APP RUN ERROR:", e);
//			System.exit(0);
//		}
		
		String regex = ".*function.*\\(.*";
		String a = "var ArrayList = Java.type('java.util.ArrayList');\n" + "var arr = new ArrayList();\n"
				+ "function testCompare(arr1,arr2){ \n" + "    for( var i=0; i<arr2.length;i++){\n"
				+ "     if(arr1[i]==arr2[i])\n" + "     {\n" + "        arr.add(true);\n" + "     }\n" + "     else{\n"
				+ "         arr.add(false);\n" + "     }\n" + "     \n" + "}\n" + "        return arr;\n" + "}\n" + "\n"
				+ "function test2(arr1,arr2,map3){ for( var i=0;\n" + " i<arr2.length;\n" + " i++){\n"
				+ "     if(arr1[i]+\"/\"+arr2[i]==map3[i])\n" + "     {\n" + "        arr.add(true);\n" + "     }\n"
				+ "     else{\n" + "         arr.add(false);\n" + "     }\n" + "    \n" + "} return arr;\n" + "}\n"
				+ "\n" + "function testDatum(arr1,arr2){ for( var i=0;\n" + " i<arr2.length;\n"
				+ " i++){print(arr1[i].substring(0,1)+\".\"+arr1[i].substring(2,3)+\".\"+arr1[i].substring(4,7));\n"
				+ "     if(arr1[i].substring(0,2)+\".\"+arr1[i].substring(2,4)+\".\"+arr1[i].substring(4,8)==arr2[i])\n"
				+ "     {\n" + "        arr.add(true);\n" + "     }\n" + "     else{\n" + "         arr.add(false);\n"
				+ "     }\n" + "    \n" + "} return arr;\n" + "}\n" + "\n"
				+ "function testDefault(arr1){ for( var i=0;\n" + " i<arr1.length;\n" + " i++){print(arr1[i]);\n"
				+ "     if(arr1[i]==\"KR\")\n" + "     {\n" + "        arr.add(true);\n" + "     }\n" + "     else{\n"
				+ "         arr.add(false);\n" + "     }\n" + "    \n" + "} return arr;\n" + "}\n"
				+ "function testSP79(arr1,arr2)\n" + "  \n" + "{ for( var i=0;\n" + "    i<arr2.length;\n"
				+ "    i++){\n" + "    \n"
				+ "        print(\"=======================\"+arr1[i].replace(/-/g,'&nbsp;'));\n"
				+ "        print(\"=======================22222222222============\"+arr2[i]);\n"
				+ "     if(arr1[i].replace(/-/g,' ')==arr2[i])\n" + "     {\n" + "        arr.add(true);\n" + "     }\n"
				+ "     else{\n" + "         arr.add(false);\n" + "     }\n" + "     \n" + "}\n"
				+ "        print(arr);\n" + "        return arr;\n" + "}\n" + "function testSP68(arr1){ for( var i=0;\n"
				+ "    i<arr1.length;\n" + "    i++){print(arr1[i]);\n" + "        if(arr1[i]==\"8\")\n" + "        {\n"
				+ "           arr.add(true);\n" + "        }\n" + "        else{\n" + "            arr.add(false);\n"
				+ "        }\n" + "       \n" + "   } return arr;\n" + "   }\n" + "   function testSP10_14(arr1,arr2)\n"
				+ "  \n" + "   { for( var i=0;\n" + "       i<arr2.length;\n" + "       i++){\n" + "       \n"
				+ "           print(\"=======================\"+arr1[i].replace(/-/g,'&nbsp;'));\n"
				+ "           print(\"=======================22222222222============\"+arr2[i]);\n"
				+ "        if(arr1[i].replace(/-/g,'0')==arr2[i])\n" + "        {\n" + "           arr.add(true);\n"
				+ "        }\n" + "        else{\n" + "            arr.add(false);\n" + "        }\n" + "        \n"
				+ "   }\n" + "           print(arr);\n" + "           return arr;\n" + "   }\n"
				+ "   function testBlank(arr1)\n" + "  \n" + "   { for( var i=0;\n" + "       i<arr1.length;\n"
				+ "       i++){\n" + "       \n" + "        \n" + "        if(arr1[i]=='')\n" + "        {\n"
				+ "           arr.add(true);\n" + "        }\n" + "        else{\n" + "            arr.add(false);\n"
				+ "        }\n" + "        \n" + "   }\n" + "           print(arr);\n" + "           return arr;\n"
				+ "   }\n" + "";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(a);

		while (matcher.find()) {
			String group = matcher.group();
			System.out.println(group.replace("function", "").split("\\(")[0].trim());
			System.out.println("------------------------------");
		}
	}

}
