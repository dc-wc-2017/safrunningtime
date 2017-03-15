package com.successfactors.safrunningtime.config;

import java.util.ArrayList;
import java.util.List;

public class ClassDetectRule {
	
	private String className;
	private List<String> methodName = new ArrayList<String>();
		
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<String> getMethodName() {
		return methodName;
	}
	public void setMethodName(List<String> methodName) {
		this.methodName = methodName;
	}
	
	public void addMethodName(String methodName) {
		this.methodName.add(methodName);
	}
}
