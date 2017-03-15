package com.successfactors.safrunningtime.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class DetectRuleConfiguration {
	
	private RuleGroup ruleGroup;
	private String cofigFileName;
	
	public DetectRuleConfiguration(String jsonName) throws IOException{
		cofigFileName = jsonName;
		ruleGroup = parseConfigFromJson();
//		printRuleGroupClassName(ruleGroup);
	}
	
	private void printRuleGroupClassName(RuleGroup group){
		List<ClassDetectRule> rules = group.getRuleList();
		for(ClassDetectRule rule : rules){
			System.out.println("DetectClass:" + rule.getClassName());
		}
	}
	
	public RuleGroup readConfig(String jsonString){
		return JSON.parseObject(jsonString, RuleGroup.class);
	}
	
	public String writeConfig(RuleGroup group){
		return JSON.toJSONString(group);
	}
	
	public String getJsonFile() throws IOException{
		StringBuffer sb = new StringBuffer();
		InputStream is = getClass().getResourceAsStream("/" + cofigFileName);
		InputStreamReader inReader = new InputStreamReader(is,"UTF-8"); 
		BufferedReader bufReader = new BufferedReader(inReader); 
		String data = bufReader.readLine();
		while( data!=null){  
			sb.append(data);
		    data = bufReader.readLine(); 
		}  
		return sb.toString();
	}
	
	public RuleGroup parseConfigFromJson() throws IOException{
		return readConfig(getJsonFile());
	}
	
	public boolean isClassBeDetected(String className) {
		String classNameToDetect = className.replace("/", ".");
		List<ClassDetectRule> rules = ruleGroup.getRuleList();
		for(ClassDetectRule rule : rules) {
			if (rule.getClassName().equals(classNameToDetect)){
				return true;
			}
		}
		return false;
	}

}
