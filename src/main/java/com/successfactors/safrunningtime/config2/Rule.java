package com.successfactors.safrunningtime.config2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rule {
	// path of output as json file
	private String outputPath;
	
	// <className, [method1, method2]>
	private Map<String, List<String>> rules = new HashMap<String, List<String>>();
	
	
	
	public String getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	public Map<String, List<String>> getRules() {
		return rules;
	}
	public void setRules(Map<String, List<String>> rules) {
		this.rules = rules;
	}
	public void addRule(String className, List<String> methods){
		this.rules.put(className, methods);
	}
	
	
	
	

}
