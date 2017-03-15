package com.successfactors.safrunningtime;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

import com.successfactors.safrunningtime.config2.RuleConfiguration;

public class RunningTimeAgent {
    //代理程序入口函数
    public static void premain(String args, Instrumentation inst) throws IOException {
    	// 读取外部配置文件决定哪些类哪些方法需要被计算运行时间
    	RuleConfiguration configuration = new RuleConfiguration("config2.json");
    	
        //添加字节码转换器
        inst.addTransformer(new PrintTimeTransformer(configuration));
    }
}
