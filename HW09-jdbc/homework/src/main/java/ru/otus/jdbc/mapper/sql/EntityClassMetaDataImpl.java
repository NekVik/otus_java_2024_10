package ru.otus.jdbc.mapper.sql;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import ru.otus.jdbc.mapper.annotation.Id;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final String entityClassName;
    private Constructor<T> constructor;
    private List<Field> nonIdFields;
    private Field idAnnotatedField;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        this.entityClassName = entityClass.getSimpleName();
        findEntityFields(entityClass);
        findEntityConstructor(entityClass);
    }

    @Override
    public String getName() {
        return entityClassName;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idAnnotatedField;
    }

    @Override
    public List<Field> getAllFields() {
        var allFields = new ArrayList<>(nonIdFields);
        allFields.add(idAnnotatedField);
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return nonIdFields;
    }

    private void findEntityConstructor(Class<T> entityClass) {
        Constructor<T>[] constructors = (Constructor<T>[]) entityClass.getDeclaredConstructors();
        this.constructor = Arrays.stream(constructors).filter(c -> c.getParameterCount() == nonIdFields.size() + 1)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Entity constructor not found"));
    }

    private void findEntityFields(Class<T> entityClass) {

        this.nonIdFields = Stream.of(entityClass.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Id.class))
            .toList();

        this.idAnnotatedField = Stream.of(entityClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Id.class))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Id field not found"));
    }

}
