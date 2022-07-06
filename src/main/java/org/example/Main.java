package org.example;

import org.example.bobobeans.Test;
import org.example.bobobeans.TestInterface;
import org.example.bobobeans.ThirdTest;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContextImpl("org.example.bobobeans");

        var result = context.getAllBeans(TestInterface.class);

        System.out.println("size: " + result.size());
        System.out.println(result);

        var secondResult = context.getBean("secondTest", TestInterface.class);

        System.out.println(secondResult);

        var thirdTest = context.getBean(ThirdTest.class);

        System.out.println(thirdTest);
    }
}