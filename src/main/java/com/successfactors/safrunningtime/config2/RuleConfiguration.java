package com.successfactors.safrunningtime.config2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import com.alibaba.fastjson.JSON;


public class RuleConfiguration {
	
	private Rule rule;
	private String cofigFileName;
	
	public RuleConfiguration(String jsonName) throws IOException{
		cofigFileName = jsonName;
		rule = parseConfigFromJson();
//		printRule(rule);
	}
	
	private void printRule(Rule rule){
		Set<String> classNames = rule.getRules().keySet();
		for(String name : classNames){
			System.out.println("DetectClass:" + name);
		}
	}

	
	public Rule readRule(String jsonString){
		return JSON.parseObject(jsonString, Rule.class);
	}
	
	public String writeRule(){
		return JSON.toJSONString(rule);
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
	
	public Rule parseConfigFromJson() throws IOException{
		System.out.println("com.successfactors.safrunningtime.config2.RuleConfiguration.parseConfigFromJson() should only be once");
		return readRule(getJsonFile());
	}
	
	public boolean isClassDetectable(String className) {
		String classNameToDetect = className.replace("/", ".");
		return rule.getRules().keySet().contains(classNameToDetect);
	}
	
	public boolean isMethodDetectable(String className, String methodName) {
		String classNameToDetect = className.replace("/", ".");
		List<String> methods = rule.getRules().get(classNameToDetect);
		
		if (methods.size() == 0){
			System.out.println(className + "." + methodName +"() is out of rule");
			return false;
		}
		
		// filter out the method name like "<clinit>"
		if (methodName.equals("<clinit>")) {
			return false;
		}
		
		if (methods.size() == 1 && methods.get(0).equals("*")){
			return true;
		}
		
		for (String m : methods) {
			if (m.equals(methodName)){
				return true;
			}
		}
		
		System.out.println(className + "." + methodName +"() is out of rule");
		return false;
	}
	
	public Rule getParsedRule(){
		return this.rule;
	}
}
