package ru.otus.annotations.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.TestResult;
import ru.otus.annotations.framework.annotations.After;
import ru.otus.annotations.framework.annotations.Before;
import ru.otus.annotations.framework.annotations.Test;
import ru.otus.annotations.framework.helper.ReflectionHelper;

public class TestExecutor {

    private static final Logger logger = LoggerFactory.getLogger(TestExecutor.class);

    private TestExecutor() {
    }

    public static TestResult runTests(Class<?> clazz) {

        var testMethods = getAnnotationPresentMethods(clazz, Test.class);
        var beforeMethods = getAnnotationPresentMethods(clazz,Before.class);
        var afterMethods = getAnnotationPresentMethods(clazz, After.class);

        logger.info("-------------------------------------------------------");
        logger.info("T E S T S");
        logger.info("-------------------------------------------------------");

        int allTests = testMethods.size();
        int errors = 0;

        for (Method method : testMethods) {

            // для каждого теста создаем экземпляр
            Object instance = ReflectionHelper.instantiate(clazz);

            // выполняем test
            try {
                runMethods(instance, beforeMethods);
                ReflectionHelper.callMethod(instance, method.getName());
                logger.info("Тест - {}. Успешно.", method.getName());
            } catch (Exception e) {
                logger.error("Тест - {}. Ошибка.", method.getName(), e.getCause().getCause());
                errors++;
            } finally {
                // выполняем методы After
                try {
                    runMethods(instance, afterMethods);
                } catch (Exception e) {
                    logger.error("Тест - {}. Ошибка в методе After", method.getName(), e.getCause().getCause());
                }
            }
        }

        return new TestResult(allTests, errors);

    }

    private static void runMethods(Object instance, List<Method> methods) {
        methods.forEach(method -> ReflectionHelper.callMethod(instance, method.getName()));
    }

    private static List<Method> getAnnotationPresentMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        var methods = clazz.getDeclaredMethods();
        var testMethods = new ArrayList<Method>();

        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                testMethods.add(method);
            }
        }
        return testMethods;
    }

    public static void printStatistics(TestResult testResult) {
        logger.info("Tests run: {}, Errors: {}", testResult.allTests(), testResult.errors());
    }
}
