package com.epam.esm.dao.helper;

import com.epam.esm.dao.helper.extractor.FieldExtractor;
import com.epam.esm.dao.helper.extractor.impl.GiftCertificateFieldExtractor;
import com.epam.esm.dao.helper.extractor.impl.TagFieldExtractor;
import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.entity.identifiable.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

public class DaoQueryHelperImplTest {

    private final static String CUSTOM_TABLE_NAME = "customTableName";
    private final static Map<String, Object> DATA_MAP = new LinkedHashMap<String, Object>() {
        {
            put("name", "nameGift");
            put("duration", 3);
            put("create_date", null);
        }
    };

    @Test
    public void testCreateMapNameFieldValueShouldCreateMapWithoutIdKey() {
        FieldExtractor<GiftCertificate> fieldExtractor =
                Mockito.mock(GiftCertificateFieldExtractor.class);
        Map<String, Object> mockedMap = new LinkedHashMap<String, Object>() {
            {
                put("id", 1);
                put("name", "nameGift");
                put("duration", 3);
                put("create_date", "date");
                put("last_update_date", "update Date");
            }
        };
        when(fieldExtractor.extract(anyObject())).thenReturn(mockedMap);
        DaoQueryHelper<GiftCertificate> daoQueryHelper = new DaoQueryHelperImpl(fieldExtractor);
        Map<String, Object> actual = daoQueryHelper.createMapNameFieldValue(null);
        Map<String, Object> expected = new LinkedHashMap<String, Object>() {
            {
                put("name", "nameGift");
                put("duration", 3);
                put("create_date", "date");
                put("last_update_date", "update Date");
            }
        };
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCreateQuerySaveShouldCreateQueryWhenDataMap() {
        FieldExtractor<GiftCertificate> fieldExtractor =
                Mockito.mock(GiftCertificateFieldExtractor.class);
        DaoQueryHelper<GiftCertificate> daoQueryHelper = new DaoQueryHelperImpl(fieldExtractor);
        String actual = daoQueryHelper.createQuerySave(CUSTOM_TABLE_NAME, DATA_MAP);
        String expected = "insert into customTableName (name,duration,create_date) " +
                "values (:name,:duration,:createDate);";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCreateQueryUpdateShouldCreateQueryWhenDataMap() {
        FieldExtractor<GiftCertificate> fieldExtractor =
                Mockito.mock(GiftCertificateFieldExtractor.class);
        DaoQueryHelper<GiftCertificate> daoQueryHelper = new DaoQueryHelperImpl(fieldExtractor);
        String actual = daoQueryHelper.createQueryUpdate(CUSTOM_TABLE_NAME, DATA_MAP);
        String expected = "update customTableName set name = ?,duration = ? where id = ?;";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCreateParamsQueryUpdateShouldCreateParamsWhenDataMapAndIdentifiableId() {
        FieldExtractor<Tag> fieldExtractor =
                Mockito.mock(TagFieldExtractor.class);
        DaoQueryHelper<Tag> daoQueryHelper = new DaoQueryHelperImpl(fieldExtractor);
        Tag tag = new Tag(123, "tagName");
        Object[] actual = daoQueryHelper.createParamsQueryUpdate(DATA_MAP, tag);
        Object[] expected = new Object[]{"nameGift", 3, 123};
        Assertions.assertArrayEquals(expected, actual);
    }

}