package com.digitexx.bll;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;

import com.sun.istack.internal.Nullable;



public class ExecuteJSUtil {

	public static Object executeJs(String js, @Nullable String funcName,
			Object... args) {

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		try {
			File functionscript = new File(js);

			Reader reader = new FileReader(functionscript);
			Object res = engine.eval(reader);
			
			
			 
			if (StringUtils.isNotBlank(funcName.replace("\"", ""))) {

				if (engine instanceof Invocable) {
					Invocable invoke = (Invocable) engine;
					res = invoke.invokeFunction(funcName.replace("\"", ""), args);
					return res;

				}
			}
			return res.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
