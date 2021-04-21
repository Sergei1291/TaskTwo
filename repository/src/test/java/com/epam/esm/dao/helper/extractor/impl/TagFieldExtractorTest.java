package com.epam.esm.dao.helper.extractor.impl;

import com.epam.esm.entity.identifiable.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class TagFieldExtractorTest {

    private final TagFieldExtractor fieldExtractor = new TagFieldExtractor();

    @Test
    public void testExtractShouldCreateMapWhenTag() {
        Tag tag = new Tag(1, "nameTag");
        Map<String, Object> actualMap = fieldExtractor.extract(tag);
        Map<String, Object> expectedMap = new LinkedHashMap<String, Object>() {
            {
                put("id", 1);
                put("name", "nameTag");
            }
        };
        Assertions.assertEquals(expectedMap, actualMap);
    }

}