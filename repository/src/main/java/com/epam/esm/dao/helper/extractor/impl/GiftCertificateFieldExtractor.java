package com.epam.esm.dao.helper.extractor.impl;

import com.epam.esm.dao.helper.extractor.FieldExtractor;
import com.epam.esm.entity.identifiable.GiftCertificate;

import java.util.LinkedHashMap;
import java.util.Map;

public class GiftCertificateFieldExtractor implements FieldExtractor<GiftCertificate> {

    private final static String ID_COLUMN = "id";
    private final static String NAME_COLUMN = "name";
    private final static String DESCRIPTION_COLUMN = "description";
    private final static String PRICE_COLUMN = "price";
    private final static String DURATION_COLUMN = "duration";
    private final static String CREATE_DATE_COLUMN = "create_date";
    private final static String LAST_UPDATE_DATE_COLUMN = "last_update_date";

    @Override
    public Map<String, Object> extract(GiftCertificate giftCertificate) {
        Map<String, Object> mapNameFiledValue = new LinkedHashMap<>();

        int id = giftCertificate.getId();
        mapNameFiledValue.put(ID_COLUMN, id);

        String name = giftCertificate.getName();
        mapNameFiledValue.put(NAME_COLUMN, name);

        String description = giftCertificate.getDescription();
        mapNameFiledValue.put(DESCRIPTION_COLUMN, description);

        Integer price = giftCertificate.getPrice();
        mapNameFiledValue.put(PRICE_COLUMN, price);

        Integer duration = giftCertificate.getDuration();
        mapNameFiledValue.put(DURATION_COLUMN, duration);

        String createDate = giftCertificate.getCreateDate();
        mapNameFiledValue.put(CREATE_DATE_COLUMN, createDate);

        String lastUpdateDate = giftCertificate.getLastUpdateDate();
        mapNameFiledValue.put(LAST_UPDATE_DATE_COLUMN, lastUpdateDate);

        return mapNameFiledValue;
    }

}