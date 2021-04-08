package com.codelab.roomwordssample.base;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface for all database(SqLite) access.
 */
public interface CommonDatabase {

    <T> List<T> queryForAll(Class<T> entity) throws SQLException;

    <T> List<T> queryForEq(Class<T> entity, String fieldName, Object value) throws SQLException;

    <T> List<T> queryForFieldValues(Class<T> entity, Map<String, Object> fieldValues) throws SQLException;

    <T> void createOrUpdate(Class<T> clazz, T entity) throws SQLException;

    <T> void createOrUpdate(Class<T> clazz, Collection<T> entities) throws SQLException;

    <T> void deleteAll(Class<T>... clazz) throws SQLException;

    <T> void deleteAll(Collection<Class<T>> clazz) throws SQLException;

    <T> void executeSQL(Class<T> clazz, String sql) throws SQLException;

    <T> List<T> queryForAll(Class<T> entity, LinkedHashMap<String, Boolean> order) throws SQLException;

}

