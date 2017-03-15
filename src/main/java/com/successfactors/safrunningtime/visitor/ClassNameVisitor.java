package com.successfactors.safrunningtime.visitor;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import com.successfactors.safrunningtime.TimeUtil;
import com.successfactors.safrunningtime.config2.RuleConfiguration;
import com.successfactors.safrunningtime.output.JsonFileResultSaver;


//定义扫描待修改class的visitor，visitor就是访问者模式
public class ClassNameVisitor extends ClassVisitor {
    private String className;
    private final RuleConfiguration config;

    public ClassNameVisitor(ClassVisitor cv, String className, RuleConfiguration config) {
        super(Opcodes.ASM5, cv);
        this.className = className;
        this.config = config;
    }

    
    @Override 
    public MethodVisitor visitMethod(int access, final String name, final String desc, String signature, String[] exceptions) {
    	//System.out.println("visit: " + this.className + " " + name);
    	MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
    	
    	if (this.className.equals("org/testng/SuiteResultCounts") && name.equals("calculateResultCounts")){
        	mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
        		@Override 
                public void onMethodExit(int opcode) {
        			// 设置调用输入参数prefix
        			this.visitLdcInsn("TestNG");
        			// 设置调用输入参数path
        			String outputPath = config.getParsedRule().getOutputPath();
                	this.visitLdcInsn(outputPath);
                	// 设置调用输入参数result
                	String json = JsonFileResultSaver.getJsonString(TimeUtil.RESULTS);
                	this.visitLdcInsn(json);
                	// 调用com.successfactors.safrunningtime.TimeUtil.saveResult()输出结果到外部json
                	this.visitMethodInsn(
                    		Opcodes.INVOKESTATIC, "com/successfactors/safrunningtime/OutputUtil", 
                    		"saveResult", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
                            false);
        		}
        	};
    	}
        	
        
        if (config.isClassDetectable(this.className) && config.isMethodDetectable(this.className, name)){
            final String key = className + name + desc;
            //判断待修改类的构造函数
            if (!name.equals("<init>") && mv != null) {
                mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
                    //方法进入时获取开始时间
                    @Override 
                    public void onMethodEnter() {
                        //相当于com.successfactors.safrunningtime.TimeUtil.setStartTime("key");
                        this.visitLdcInsn(key);
                        this.visitMethodInsn(Opcodes.INVOKESTATIC, 
                        		"com/successfactors/safrunningtime/TimeUtil", "setStartTime", 
                        		"(Ljava/lang/String;)V", false);
                    }

                    //方法退出时获取结束时间并计算执行时间
                    @Override 
                    public void onMethodExit(int opcode) {
                        //相当于com.successfactors.safrunningtime.TimeUtil.setEndTime("key");
                        this.visitLdcInsn(key);
                        this.visitMethodInsn(
                        		Opcodes.INVOKESTATIC, "com/successfactors/safrunningtime/TimeUtil", 
                        		"setEndTime", "(Ljava/lang/String;)V", 
                        		false);
                        
                        //向栈中压入类名称
                        this.visitLdcInsn(className);
                        //向栈中压入方法名
                        this.visitLdcInsn(name);
                        //向栈中压入方法描述
                        this.visitLdcInsn(desc);
                        
                        //相当于com.successfactors.safrunningtime.TimeUtil.getRunningTime("com/successfactors/safrunningtime/TestTime","testTime");
                        this.visitMethodInsn(
                        		Opcodes.INVOKESTATIC, "com/successfactors/safrunningtime/TimeUtil", 
                        		"getRunningTime", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
                                false);
                    }
                };
            }
        }
        
        return mv;
    }
    
}
