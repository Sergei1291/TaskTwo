package com.epam.esm.dao.helper;

import com.epam.esm.dao.helper.extractor.FieldExtractor;
import com.epam.esm.entity.identifiable.Identifiable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DaoQueryHelperImpl<T extends Identifiable> implements DaoQueryHelper<T> {

    private final static String ID = "id";

    private final FieldExtractor<T> fieldExtractor;

    public DaoQueryHelperImpl(FieldExtractor<T> fieldExtractor) {
        this.fieldExtractor = fieldExtractor;
    }

    @Override
    public Map<String, Object> createMapNameFieldValue(T identifiable) {
        Map<String, Object> mapNameFiledValue = fieldExtractor.extract(identifiable);
        mapNameFiledValue.remove(ID);
        return mapNameFiledValue;
    }

    @Override
    public String createQuerySave(String tableName, Map<String, Object> mapNameFiledValue) {
        StringBuilder builder = new StringBuilder();
        builder.append("insert into ");
        builder.append(tableName);
        builder.append(" (");
        for (String columnName : mapNameFiledValue.keySet()) {
            builder.append(columnName);
            builder.append(",");
        }
        removeLastSymbol(builder);
        builder.append(") values (");
        for (String fieldName : mapNameFiledValue.keySet()) {
            builder.append(":");
            String camelCaseFieldName = transformSnakeCaseToCamelCase(fieldName);
            builder.append(camelCaseFieldName);
            builder.append(",");
        }
        removeLastSymbol(builder);
        builder.append(");");
        return new String(builder);
    }

    @Override
    public String createQueryUpdate(String tableName, Map<String, Object> mapNameFiledValue) {
        StringBuilder builder = new StringBuilder();
        builder.append("update ");
        builder.append(tableName);
        builder.append(" set ");
        Map<String, Object> mapDeletedNullable = deleteNullableValues(mapNameFiledValue);
        for (String columnName : mapDeletedNullable.keySet()) {
            builder.append(columnName);
            builder.append(" = ?,");
        }
        removeLastSymbol(builder);
        builder.append(" where id = ?;");
        return new String(builder);
    }

    @Override
    public Object[] createParamsQueryUpdate(Map<String, Object> mapNameFiledValue, T identifiable) {
        Map<String, Object> mapDeletedNullable = deleteNullableValues(mapNameFiledValue);
        int numberParams = mapDeletedNullable.size();
        numberParams++;
        Object[] params = new Object[numberParams];
        Collection<Object> values = mapDeletedNullable.values();
        values.toArray(params);
        int identifiableId = identifiable.getId();
        params[numberParams - 1] = identifiableId;
        return params;
    }

    private String transformSnakeCaseToCamelCase(String data) {
        while (data.contains("_")) {
            data = data.replaceFirst("_[a-z]",
                    String.valueOf(Character.toUpperCase(data.charAt(data.indexOf("_") + 1))));
        }
        return data;
    }

    private Map<String, Object> deleteNullableValues(Map<String, Object> mapNameFiledValue) {
        Map<String, Object> mapWithoutNullable = new LinkedHashMap<>();
        Set<String> nameFields = mapNameFiledValue.keySet();
        for (String key : nameFields) {
            Object value = mapNameFiledValue.get(key);
            if (value != null) {
                mapWithoutNullable.put(key, value);
            }
        }
        return mapWithoutNullable;
    }

    private void removeLastSymbol(StringBuilder builder) {
        int lengthBuilder = builder.length();
        builder.deleteCharAt(lengthBuilder - 1);
    }

}