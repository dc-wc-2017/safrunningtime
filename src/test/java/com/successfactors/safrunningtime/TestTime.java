package com.successfactors.safrunningtime;


import java.io.IOException;


public class TestTime {

    public int getTest() throws InterruptedException {
        Thread.sleep(500);
        return 0;
    }

    public static void testTime() throws InterruptedException, IOException {
        Thread.sleep(1000);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Thread.sleep(2000);
        TestTime.testTime();
        new TestTime().getTest();
        new SubTime().getTest();
    }
    

    public static class SubTime  {
    	public int getTest() throws InterruptedException {
    		Thread.sleep(600);
    		return 0;
    	}
    }

}
