package com.successfactors.safrunningtime;

import com.successfactors.safrunningtime.output.JsonFileResultSaver;

public class OutputUtil {
	
	public static void saveResult(String outputPrefix, String path, String result){
		JsonFileResultSaver saver = new JsonFileResultSaver(outputPrefix, path);
		saver.save(result);
	}

}
