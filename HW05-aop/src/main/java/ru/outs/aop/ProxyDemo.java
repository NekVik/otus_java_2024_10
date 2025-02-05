package ru.outs.aop;

import ru.outs.aop.proxy.Ioc;
import ru.outs.aop.proxy.TestLoggingInterface;

public class ProxyDemo {

    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.getInstance();
        testLogging.calculation(3, 15, new MyObject(2));

        testLogging.calculation(3, 15, "Привет");

        testLogging.calculation(6, 10);
    }
}
