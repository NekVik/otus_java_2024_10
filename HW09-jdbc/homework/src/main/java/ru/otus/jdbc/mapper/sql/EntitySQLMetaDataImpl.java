package ru.otus.jdbc.mapper.sql;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaData;

    private final String idFieldName;
    private final String selectAllFields;
    private final String fieldsWithoutId;
    private final String paramsWithoutId;
    private final String updateFields;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {

        this.entityClassMetaData = entityClassMetaData;

        this.idFieldName = entityClassMetaData.getIdField().getName();

        this.selectAllFields =
                entityClassMetaData.getAllFields().stream().map(Field::getName).collect(Collectors.joining(","));

        this.fieldsWithoutId = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));

        this.paramsWithoutId = Stream.generate(() -> "?")
                .limit(entityClassMetaData.getFieldsWithoutId().size())
                .collect(Collectors.joining(","));

        this.updateFields = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> String.format("%s = ?", field.getName()))
                .collect(Collectors.joining(","));
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select %s from %s", selectAllFields, entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(
                "select %s from %s where %s = ?", selectAllFields, entityClassMetaData.getName(), idFieldName);
    }

    @Override
    public String getInsertSql() {
        return String.format(
                "insert into %s (%s) values (%s)", entityClassMetaData.getName(), fieldsWithoutId, paramsWithoutId);
    }

    @Override
    public String getUpdateSql() {
        return String.format("update %s set %s where %s = ?", entityClassMetaData.getName(), updateFields, idFieldName);
    }
}
