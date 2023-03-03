package com.digitexx.form;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonFormatter {
	public static String format(final JSONObject object) throws JSONException{
        final JsonVisitor visitor = new JsonVisitor(500, ' ');
        visitor.visit(object, 0);
        return visitor.toString();
    }

    private static class JsonVisitor{

        private final StringBuilder builder = new StringBuilder();
        private final int indentationSize;
        private final char indentationChar;

        public JsonVisitor(final int indentationSize, final char indentationChar){
            this.indentationSize = indentationSize;
            this.indentationChar = indentationChar;
        }

        private void visit(final JSONArray array, final int indent) throws JSONException{
            final int length = array.length();
            if(length == 0){
                write("[]", indent);
            } else{
                write("[", indent);
                for(int i = 0; i < length; i++){
                    visit(array.get(i), indent + 1);
                }
                write("]", indent);
            }

        }

        private void visit(final JSONObject obj, final int indent) throws JSONException{
            final int length = obj.length();
            if(length == 0){
                write("{}", indent);
            } else{
                write("{", indent);
                final Iterator<String> keys = obj.keys();
                while(keys.hasNext()){
                    final String key = keys.next();
                    write(key + " :", indent + 1);
                    visit(obj.get(key), indent + 1);
                    if(keys.hasNext()){
                        write(",", indent + 1);
                    }
                }
                write("}", indent);
            }

        }

        private void visit(final Object object, final int indent) throws JSONException{
            if(object instanceof JSONArray){
                visit((JSONArray) object, indent);
            } else if(object instanceof JSONObject){
                visit((JSONObject) object, indent);
            } else{
                if(object instanceof String){
                    write("\"" + (String) object + "\"", indent);
                } else{
                    write(String.valueOf(object), indent);
                }
            }

        }

        private void write(final String data, final int indent){
            for(int i = 0; i < (indent * indentationSize); i++){
                builder.append(indentationChar);
            }
            builder.append(data).append('\n');
        }

        @Override
        public String toString(){
            return builder.toString();
        }

    }
    public static void main(String[] args) throws JSONException, IOException {
    	 String filename = "/mnt/x-storage/Projects_SIT/165_191128_340_SUNLIFE/Export/13082020/5f34feb89041405bd4369c2a/TH13080005.json";
    	 JSONObject obj = parseJSONFile(filename);
    	    System.out.println(JsonFormatter.format(obj));
	}
    public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }
}
