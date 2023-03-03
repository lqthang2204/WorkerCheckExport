package com.digitexx.form;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonTreeModelExample1 {
	private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws JSONException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	String filename = "/mnt/x-storage/Projects_SIT/165_191128_340_SUNLIFE/Export/13082020/5f34feb89041405bd4369c2a/TH13080005.json";
		JSONObject obj = parseJSONFile(filename);
        String json = obj.toString();

        try {

            // convert JSON string to Map
            Map<String, Object> map = mapper.readValue(json, Map.class);

			// it works
            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
				Object object = map.get(key);
				if(object instanceof JSONArray){
					
				}else  if(object instanceof String){
					
				}
			}
//            System.out.println(map);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }

}
