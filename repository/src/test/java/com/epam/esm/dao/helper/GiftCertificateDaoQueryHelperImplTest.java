package com.epam.esm.dao.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GiftCertificateDaoQueryHelperImplTest {

    private final static String PARAM = "paramValue";

    @Test
    public void testCreateQueryFindAllSortedByParamShouldCreateQueryWhenOrderDescTrue() {
        GiftCertificateDaoQueryHelper helper =
                new GiftCertificateDaoQueryHelperImpl();
        String actual = helper.createQueryFindAllSortedByParam(PARAM, true);
        Assertions.assertEquals("select * from gift_certificate order by paramValue desc;", actual);
    }

    @Test
    public void testCreateQueryFindAllSortedByParamShouldCreateQueryWhenOrderDescFalse() {
        GiftCertificateDaoQueryHelper helper =
                new GiftCertificateDaoQueryHelperImpl();
        String actual = helper.createQueryFindAllSortedByParam(PARAM, false);
        Assertions.assertEquals("select * from gift_certificate order by paramValue asc;", actual);
    }

}