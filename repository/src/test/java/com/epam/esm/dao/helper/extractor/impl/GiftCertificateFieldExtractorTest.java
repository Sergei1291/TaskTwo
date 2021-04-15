package com.epam.esm.dao.helper.extractor.impl;

import com.epam.esm.entity.identifiable.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class GiftCertificateFieldExtractorTest {

    private final GiftCertificateFieldExtractor fieldExtractor = new GiftCertificateFieldExtractor();

    @Test
    public void testExtractShouldCreateMapWhenGiftCertificate() {
        GiftCertificate giftCertificate =
                new GiftCertificate(1, "nameGift", "descriptionValue", 2, 3, "date", "update Date");
        Map<String, Object> actualMap = fieldExtractor.extract(giftCertificate);
        Map<String, Object> expectedMap = new LinkedHashMap<String, Object>() {
            {
                put("id", 1);
                put("name", "nameGift");
                put("description", "descriptionValue");
                put("price", 2);
                put("duration", 3);
                put("create_date", "date");
                put("last_update_date", "update Date");
            }
        };
        Assertions.assertEquals(expectedMap, actualMap);
    }

}