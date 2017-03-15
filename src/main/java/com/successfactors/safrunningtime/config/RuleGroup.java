package com.successfactors.safrunningtime.config;

import java.util.ArrayList;
import java.util.List;

public class RuleGroup {
	
	private String name;
	private List<ClassDetectRule> ruleList = new ArrayList<ClassDetectRule>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ClassDetectRule> getRuleList() {
		return ruleList;
	}
	public void setRuleList(List<ClassDetectRule> ruleList) {
		this.ruleList = ruleList;
	}
	
	public void addRule(ClassDetectRule rule){
		this.ruleList.add(rule);
	}

}
