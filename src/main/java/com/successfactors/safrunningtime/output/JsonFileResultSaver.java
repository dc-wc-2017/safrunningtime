package com.successfactors.safrunningtime.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.alibaba.fastjson.JSON;

public class JsonFileResultSaver {
	private String prefix;
	private String path;
	
	public JsonFileResultSaver(String prefix, String path) {
		this.prefix = prefix;
		this.path = path;
	}
	
	private File prepareFile() throws IOException{
		File dir = new File(path);
		if(dir.exists()){
			 dir.delete();
		}
		dir.mkdir();
		String fileName = prefix + "Result-" + String.valueOf(System.currentTimeMillis()) + ".json";
		File file = new File(path+"/"+fileName);
		file.createNewFile();
		return file;
	}
	
	public void save(String content) {
		try {
			File output = prepareFile();
			BufferedWriter writer  = new BufferedWriter(new FileWriter(output));
			writer.write(content);  
			writer.flush();   
		    writer.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getJsonString(Results results){
		return JSON.toJSONString(results);
	}

}
