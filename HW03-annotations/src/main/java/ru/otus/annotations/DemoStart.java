package ru.otus.annotations;

import ru.otus.annotations.framework.TestExecutor;

public class DemoStart {

    public static void main(String[] args) {
        TestResult result = TestExecutor.runTests(ClassForTesting.class);
        TestExecutor.printStatistics(result);
    }
}
