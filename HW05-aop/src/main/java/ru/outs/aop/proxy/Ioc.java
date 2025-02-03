package ru.outs.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.outs.aop.annotation.Log;

public class Ioc {

    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {
    }

    public static TestLoggingInterface getInstance() {
        InvocationHandler handler = new IocHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                                                             new Class[] {TestLoggingInterface.class}, handler);
    }

    private static class IocHandler implements InvocationHandler {

        private final TestLoggingInterface myClass;
        private final Set<MethodSignature> methodSignatures;

        private IocHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
            this.methodSignatures = getAnnotatedMethodSignatures(myClass);
        }

        @Override
        public Object invoke(Object proxy, Method invokeMethod, Object[] args) throws Throwable {

            var invokeSignature = new MethodSignature(invokeMethod);
            if (methodSignatures.contains(invokeSignature)) {
                logger.info("executed method: {}, params: {}", invokeMethod.getName(), Arrays.toString(args));
            }

            return invokeMethod.invoke(myClass, args);
        }

        private Set<MethodSignature> getAnnotatedMethodSignatures(TestLoggingInterface myClass) {

            return Arrays.stream(myClass.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Log.class))
                .map(MethodSignature::new)
                .collect(Collectors.toSet());
        }
    }

    private static class MethodSignature {

        private final String name;
        private final String[] params;

        public MethodSignature(Method method) {
            this.name = method.getName();
            this.params = Arrays.stream(method.getParameterTypes()).map(Class::getName).toArray(String[]::new);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MethodSignature that = (MethodSignature) o;
            return Objects.equals(name, that.name) && Arrays.equals(params, that.params);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(name);
            result = 31 * result + Arrays.hashCode(params);
            return result;
        }

        @Override
        public String toString() {
            return "MethodSignature{" +
                "name='" + name + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
        }
    }
}
