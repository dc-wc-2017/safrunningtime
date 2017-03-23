package com.successfactors.safrunningtime;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.successfactors.safrunningtime.output.Result;
import com.successfactors.safrunningtime.output.Results;

public class TimeUtil {
    private static Map<String, Long> startTimes = new HashMap<String, Long>();
    private static Map<String, Long> endTimes   = new HashMap<String, Long>();
    public static Results RESULTS = new Results();
    
    private TimeUtil() {
    }

    public static long getStartTime(String key) {
        return startTimes.remove(key);
    }

    public static void setStartTime(String key) {
        startTimes.put(key, System.currentTimeMillis());
    }

    public static long getEndTime(String key) {
        return endTimes.remove(key);
    }

    public static void setEndTime(String key) {
        endTimes.put(key, System.currentTimeMillis());
    }

    public static void getRunningTime(String className, String methodName, String methodDesc) {
        String key = className + methodName + methodDesc;
        long exclusive = getEndTime(key) - getStartTime(key);
        System.out.println(className.replace("/", ".") + "." + methodName + " elapsed:" + exclusive);
        Result ret = new Result();
        ret.setClassName(className);
        ret.setMethodName(methodName);
        ret.setElapsedTime(String.valueOf(exclusive));
        // only record the code behavior which is over 1 second
        if (exclusive > 1000) {
        	RESULTS.addResult(ret);
        }
    }

    public static Map<String, Long> getStartTimes() {
        return Collections.unmodifiableMap(startTimes);
    }

    public static Map<String, Long> getEndTimes() {
        return Collections.unmodifiableMap(endTimes);
    }
}
