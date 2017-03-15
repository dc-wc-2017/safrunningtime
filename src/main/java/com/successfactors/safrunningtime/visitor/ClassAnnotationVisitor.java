package com.successfactors.safrunningtime.visitor;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import com.successfactors.safrunningtime.GetRunningTime;

//定义扫描待修改class的visitor，visitor就是访问者模式
public class ClassAnnotationVisitor extends ClassVisitor {
    private String className;
    private String annotationDesc;

    public ClassAnnotationVisitor(ClassVisitor cv, String className) {
        super(Opcodes.ASM5, cv);
        this.className = className;
        //System.out.println("hh:" + this.className);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        this.annotationDesc = desc;
        return super.visitAnnotation(desc, visible);
    }
    
    
    //扫描到每个方法都会进入，参数详情下一篇博文详细分析
    @Override 
    public MethodVisitor visitMethod(int access, final String name, final String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (annotationDesc != null && annotationDesc.equals(Type.getDescriptor(GetRunningTime.class))) {
            final String key = className + name + desc;
            //过来待修改类的构造函数
            if (!name.equals("<init>") && mv != null) {
                mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
                    //方法进入时获取开始时间
                    @Override 
                    public void onMethodEnter() {
                        //相当于com.successfactors.safrunningtime.TimeUtil.setStartTime("key");
                        this.visitLdcInsn(key);
                        this.visitMethodInsn(
                        		Opcodes.INVOKESTATIC, 
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
