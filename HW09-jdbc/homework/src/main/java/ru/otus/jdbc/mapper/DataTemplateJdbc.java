package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.jdbc.mapper.sql.EntityClassMetaData;
import ru.otus.jdbc.mapper.sql.EntitySQLMetaData;

/** Сохраняет объект в базу, читает объект из базы */
@SuppressWarnings({"java:S1068", "java:S112", "java:S3011"})
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return mapToObject(rs);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    var clientList = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            clientList.add(mapToObject(rs));
                        }
                        return clientList;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Ошибка при выполнении запроса"));
    }

    @Override
    public long insert(Connection connection, T client) {
        return dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getInsertSql(),
                getFieldValues(client, entityClassMetaData.getFieldsWithoutId()));
    }

    @Override
    public void update(Connection connection, T object) {
        dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getUpdateSql(),
                getFieldValues(object, entityClassMetaData.getFieldsWithoutId(), entityClassMetaData.getIdField()));
    }

    private T mapToObject(ResultSet rs) {
        List<Object> entityFields = new ArrayList<>();

        try {
            /* Получаем ID сущности из RS */
            long idValue = rs.getLong(entityClassMetaData.getIdField().getName());
            entityFields.add(idValue);

            entityClassMetaData.getFieldsWithoutId().forEach(field -> {
                try {
                    entityFields.add(rs.getObject(field.getName()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            return entityClassMetaData.getConstructor().newInstance(entityFields.toArray());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<Object> getFieldValues(T object, List<Field> fields) {
        List<Object> fieldValues = new ArrayList<>();

        fields.forEach(field -> {
            try {
                field.setAccessible(true);
                fieldValues.add(field.get(object));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        return fieldValues;
    }

    private List<Object> getFieldValues(T object, List<Field> fields, Field idField) {
        List<Object> fieldValues = new ArrayList<>();

        fields.forEach(field -> {
            try {
                field.setAccessible(true);
                fieldValues.add(field.get(object));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
        // Добавляем ID в список значений для запроса UPDATE
        try {
            idField.setAccessible(true);
            fieldValues.add(idField.get(object));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return fieldValues;
    }
}
