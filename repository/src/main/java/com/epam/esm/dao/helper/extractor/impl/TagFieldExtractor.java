package com.epam.esm.dao.helper.extractor.impl;

import com.epam.esm.dao.helper.extractor.FieldExtractor;
import com.epam.esm.entity.identifiable.Tag;

import java.util.LinkedHashMap;
import java.util.Map;

public class TagFieldExtractor implements FieldExtractor<Tag> {

    private final static String ID_COLUMN = "id";
    private final static String NAME_COLUMN = "name";

    @Override
    public Map<String, Object> extract(Tag tag) {
        Map<String, Object> mapNameFiledValue = new LinkedHashMap<>();

        int id = tag.getId();
        mapNameFiledValue.put(ID_COLUMN, id);

        String name = tag.getName();
        mapNameFiledValue.put(NAME_COLUMN, name);

        return mapNameFiledValue;
    }

}