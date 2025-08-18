package ru.otus.appcontainer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    public AppComponentsContainerImpl(Class<?>... initialConfigClass) {
        Arrays.stream(initialConfigClass)
            .filter(c -> c.isAnnotationPresent(AppComponentsContainerConfig.class))
            .sorted(Comparator.comparingInt(c -> c.getAnnotation(AppComponentsContainerConfig.class).order()))
            .forEachOrdered(this::processConfig);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...

        List<Method> componentMethods = Arrays.stream(configClass.getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(AppComponent.class))
            .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()))
            .toList();

        Object configInstance;
        try {
            configInstance = configClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create config instance", e);
        }

        // Создаем компоненты
        for (Method method : componentMethods) {
            try {

                String componentName = method.getAnnotation(AppComponent.class).name();
                if (appComponentsByName.containsKey(componentName)) {
                    throw new RuntimeException("Component with name " + componentName + " already exists");
                }

                Object component = createComponent(configInstance, method);
                appComponents.add(component);
                appComponentsByName.put(componentName, component);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create component: " + method.getName(), e);
            }
        }

    }

    private Object createComponent(Object configInstance, Method method) throws Exception {

        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Class<?> parameterType = parameters[i].getType();
            Object dependency = getAppComponent(parameterType);
            if (dependency == null) {
                throw new RuntimeException("No component found for type: " + parameterType);
            }
            args[i] = dependency;
        }

        return method.invoke(configInstance, args);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {

        C result = null;
        for (Object component : appComponents) {
            if (componentClass.isAssignableFrom(component.getClass())) {

                if (result != null) {
                    throw new RuntimeException("Multiple components found for type: " + componentClass.getName());
                }

                result = (C) component;
            }
        }

        if (result == null) {
            throw new RuntimeException("No component found for type: " + componentClass.getName());
        }

        return result;
    }

    @Override
    public <C> C getAppComponent(String componentName) {

        if (!appComponentsByName.containsKey(componentName)) {
            throw new RuntimeException("No component found for name: " + componentName);
        }

        return (C) appComponentsByName.get(componentName);
    }

}
