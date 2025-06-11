package com.vegastore.jitarger.util;

import java.util.Map;
import java.util.StringJoiner;

public class DynamicSqlBuilder {

    //  UPDATE 
    public static String buildUpdateSql(String tableName, Map<String, Object> fields, String whereClause) {
        if (fields == null || fields.isEmpty()) {
            throw new IllegalArgumentException("No hay campos para actualizar.");
        }

        StringJoiner setClause = new StringJoiner(", ");
        fields.keySet().forEach(key -> setClause.add(key + " = ?"));

        return "UPDATE " + tableName + " SET " + setClause + " WHERE " + whereClause;
    }

    //  DELETE 
    public static String buildDeleteSql(String tableName, String whereClause) {
        if (whereClause == null || whereClause.isBlank()) {
            throw new IllegalArgumentException("La cláusula WHERE no puede estar vacía para DELETE.");
        }

        return "DELETE FROM " + tableName + " WHERE " + whereClause;
    }

    //  INSERT 
    public static String buildInsertSql(String tableName, Map<String, Object> fields) {
        if (fields == null || fields.isEmpty()) {
            throw new IllegalArgumentException("No hay campos para insertar.");
        }

        StringJoiner columns = new StringJoiner(", ");
        StringJoiner values = new StringJoiner(", ");

        fields.keySet().forEach(key -> {columns.add(key); values.add("?"); });

        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
    }

    //  COUNT 
    public static String buildCountSql(String tableName, String whereClause) {
        String base = "SELECT COUNT(*) FROM " + tableName;
        if (whereClause != null && !whereClause.isBlank()) {
            base += " WHERE " + whereClause;
        }
        return base;
    }

    public static String buildCountSql(String tableName) {
        return buildCountSql(tableName, null);
    }


}
