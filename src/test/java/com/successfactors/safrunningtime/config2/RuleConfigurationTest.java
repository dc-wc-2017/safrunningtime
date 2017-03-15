package com.successfactors.safrunningtime.config2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class RuleConfigurationTest {
	
	private Rule rule;
	private RuleConfiguration config;
	
	@Before
	public void beforeTest() throws IOException{
		config = new RuleConfiguration("config2_test.json");
		
		rule = new Rule();
		List<String> ruleMethods = new ArrayList<String>();
		ruleMethods.add("method1");
		ruleMethods.add("method2");
		rule.addRule("aa.bb.cc.dd.testclass", ruleMethods);

		List<String> rule2Methods = new ArrayList<String>();
		rule2Methods.add("method3");
		rule2Methods.add("method4");
		rule.addRule("xx.yy.zz.testclass2", rule2Methods);
		
	}
	
	@Test
	public void testWriteRule() throws IOException{
		System.out.println(config.writeRule());
	}
	
	@Test
	public void testReadRule(){
		String jsonString = "{\"rules\":{\"xx.yy.zz.testclass2\":[\"method3\",\"method4\"],\"aa.bb.cc.dd.testclass\":[\"method1\",\"method2\"]}}";
		Rule rule = config.readRule(jsonString);
		System.out.println(rule.getRules().get("aa.bb.cc.dd.testclass").get(0));
		System.out.println(rule.getRules().get("aa.bb.cc.dd.testclass").get(1));
		System.out.println(rule.getRules().get("xx.yy.zz.testclass2").get(0));
		System.out.println(rule.getRules().get("xx.yy.zz.testclass2").get(1));
	}
	
	@Test
	public void testReadConfigFromFile() throws IOException{
		Rule resultRule = config.parseConfigFromJson();
		Assert.assertEquals(rule.getRules().get("aa.bb.cc.dd.testclass").get(0), resultRule.getRules().get("aa.bb.cc.dd.testclass").get(0));
	}

}
