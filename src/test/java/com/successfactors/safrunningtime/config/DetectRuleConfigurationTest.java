package com.successfactors.safrunningtime.config;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class DetectRuleConfigurationTest {
	
	
	private DetectRuleConfiguration config ;
	private RuleGroup group;
	
	@Before
	public void beforeTest() throws IOException{
		config = new DetectRuleConfiguration("config_test.json");
				
		ClassDetectRule rule1 = new ClassDetectRule();
		rule1.setClassName("a.ba.c.testclass");
		rule1.addMethodName("mytestmethod1");
		rule1.addMethodName("mytestmethod2");
		
		ClassDetectRule rule2 = new ClassDetectRule();
		rule2.setClassName("xx.yy.zz.testclass2");
		rule2.addMethodName("mytestmethod3");
		rule2.addMethodName("mytestmethod4");
		
		group = new RuleGroup();
		group.addRule(rule1);
		group.addRule(rule2);
		group.setName("test_group");
	}
	
	@Test
	public void testReadConfig(){
		String jsonString = config.writeConfig(group);
		System.out.println(jsonString);
		
	}
	
	@Test
	public void testWriteConfig(){
		String testJsonString = "{\"name\":\"test_group\",\"ruleList\":[{\"className\":\"a.ba.c.testclass\",\"methodName\":[\"mytestmethod1\",\"mytestmethod2\"]},{\"className\":\"xx.yy.zz.testclass2\",\"methodName\":[\"mytestmethod3\",\"mytestmethod4\"]}]}";
		RuleGroup resultGroup = config.readConfig(testJsonString);
		Assert.assertEquals(group.getRuleList().get(0).getClassName(), resultGroup.getRuleList().get(0).getClassName());
	}
	
	@Test
	public void testReadConfigFromFile() throws IOException{
		RuleGroup resultGroup = config.parseConfigFromJson();
		Assert.assertEquals(group.getRuleList().get(0).getClassName(), resultGroup.getRuleList().get(0).getClassName());
	}
}
