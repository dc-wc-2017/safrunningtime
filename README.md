# safrunningtime
The project target is to get running time of each method of each class.

# configuration
The configuration is \src\main\resources\config2.json, like : 

{
    "outputPath" : "D:\\github.com\\safrunningtime\\output",
    "rules" :
    {
        "xx.yy.zz.testclass2" : ["method3", "method4"],
        "aa.bb.cc.dd.testclass" : ["method1", "method2"]
    }
}

outputPath is to define output path. Currently output only supports the case run by TestNG. Other support will be TODO. 
rules is a map. Key is the class name with full page. Value is a list which contains the method in that class. rules is a filter.
If all methods are wanted, it can simply define as : "xx.yy.zz.testclass2" : ["*"]

# output

Output when running TestNG case will be like :

D:\\github.com\\safrunningtime\\output\\TestNGResult-1489564040652.json

The content is like:


{
    "results" : [
        {
            "className" : "com/successfactors/safrunningtimetest/TestNGCase1",
            "elapsedTime" : "0",
            "methodName" : "beforeClass"
        },
        {
            "className" : "com/successfactors/safrunningtimetest/TestNGCase1",
            "elapsedTime" : "501",
            "methodName" : "beforeMethod"
        }
    ]
}


# thought
Originally I feel like the web UI automation scripts running very slow so I want to know which place it is slow. But it is no possible to every time add code like System.currentTimeMillis() in the method or class I want to, so I use ASM which is a JAVA byte code modification library to do this thing like AOP. 

Currently output only occurs when testNG suite or testNG class finishes running and all collected result will be written as a json to avoid disk I/O for too much times. And later on, it may enhance to get into some NOSQL DB for query and show result.

# Usage
Run as maven install , copy target\safrunningtime.jar to X:\aa\bb\dd\safrunningtime.jar.  Setup the VM args "-javaagent:X:\aa\bb\dd\safrunningtime.jar" when running test.



