package com.epam.esm.dao.impl;

import com.epam.esm.config.RepositoryConfig;
import com.epam.esm.dao.helper.GiftCertificateDaoQueryHelperImpl;
import com.epam.esm.entity.identifiable.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GiftCertificateDaoImplTest {

    private final static GiftCertificate GIFT_CERTIFICATE_ONE =
            new GiftCertificate(1, "partName1", "asdasa sada d asdasd",
                    10, 2, "2021-03-21T20:52:13.5213", null);
    private final static GiftCertificate GIFT_CERTIFICATE_TWO =
            new GiftCertificate(2, "a2", "asdasa sada description first",
                    10, 2, "2021-03-24T00:00:13.5213", null);
    private final static GiftCertificate GIFT_CERTIFICATE_FOUR =
            new GiftCertificate(4, "a4", "descriptions",
                    10, 2, "2021-03-29T06:52:13.5213", null);

    private GiftCertificateDaoImpl giftCertificateDaoImpl;
    private TestInnerDatabaseConfig testInnerDatabaseConfig;

    @BeforeEach
    void initialize() {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(RepositoryConfig.class);
        JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
        GiftCertificateDaoQueryHelperImpl giftCertificateDaoQueryHelper =
                applicationContext.getBean(GiftCertificateDaoQueryHelperImpl.class);
        giftCertificateDaoImpl = new GiftCertificateDaoImpl(jdbcTemplate, giftCertificateDaoQueryHelper);
        testInnerDatabaseConfig = new TestInnerDatabaseConfig(jdbcTemplate);
        testInnerDatabaseConfig.initializeDatabase();
    }

    @AfterEach
    void destroy() {
        testInnerDatabaseConfig.destroyDatabase();
    }

    @Test
    public void testSaveShouldSaveGiftCertificateInDatabaseAndReturnCreatedId() {
        GiftCertificate giftCertificate = new GiftCertificate(0, "nameCertificate",
                "descriptionGift", 1, 1, "createDate", null);
        Integer actualId = giftCertificateDaoImpl.save(giftCertificate);
        Assertions.assertEquals(Integer.valueOf(5), actualId);
        Optional<GiftCertificate> optionalTag = giftCertificateDaoImpl.findById(5);
        Assertions.assertEquals(Optional.of(new GiftCertificate(5, "nameCertificate",
                        "descriptionGift", 1, 1, "createDate", null)),
                optionalTag);
    }

    @Test
    public void testUpdateShouldUpdateNotNullableFields() {
        GiftCertificate giftCertificate = new GiftCertificate(1, "updateName",
                "updateDescription", null, null, "h", null);
        giftCertificateDaoImpl.update(giftCertificate);
        Optional<GiftCertificate> actual = giftCertificateDaoImpl.findById(1);
        Assertions.assertEquals(Optional.of(new GiftCertificate(1, "updateName",
                "updateDescription", 10, 2, "h",
                null)), actual);
    }

    @Test
    public void testRemoveShouldRemoveGiftCertificateFromDatabase() {
        GiftCertificate giftCertificate = new GiftCertificate(1, null,
                null, 0, 0, null, null);
        giftCertificateDaoImpl.remove(giftCertificate);
        List<GiftCertificate> giftCertificateList = giftCertificateDaoImpl.findAll();
        int sizeTagList = giftCertificateList.size();
        Assertions.assertEquals(3, sizeTagList);
    }

    @Test
    public void testFindAllShouldReturnDatabaseListGiftCertificate() {
        List<GiftCertificate> giftCertificateList = giftCertificateDaoImpl.findAll();
        Assertions.assertEquals(4, giftCertificateList.size());
    }

    @Test
    public void testFindByIdShouldReturnOptionalGiftCertificateWhenDatabaseContainCertificateId() {
        Optional<GiftCertificate> actual = giftCertificateDaoImpl.findById(2);
        Assertions.assertEquals(Optional.of(GIFT_CERTIFICATE_TWO), actual);
    }

    @Test
    public void testFindByIdShouldReturnOptionalEmptyWhenDatabaseNotContainCertificateId() {
        Optional<GiftCertificate> actual = giftCertificateDaoImpl.findById(5);
        Assertions.assertEquals(Optional.empty(), actual);
    }

    @Test
    public void testFindAllByTagShouldReturnListGiftCertificateWhichHaveTagId() {
        List<GiftCertificate> actual = giftCertificateDaoImpl.findAllByTag(2);
        List<GiftCertificate> expected = Arrays.asList(
                GIFT_CERTIFICATE_TWO,
                GIFT_CERTIFICATE_FOUR);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFindAllBySearchStringSearchParamNameShouldReturnListGiftCertificateHavingPartName() {
        List<GiftCertificate> actual =
                giftCertificateDaoImpl.findAllBySearchStringSearchParamName("rt", "name");
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_ONE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFindAllBySearchStringSearchParamNameShouldReturnListGiftCertificateHavingPartDescription() {
        List<GiftCertificate> actual =
                giftCertificateDaoImpl.findAllBySearchStringSearchParamName("asdasa sada", "description");
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_ONE, GIFT_CERTIFICATE_TWO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFindAllSortedByParamShouldReturnListGiftCertificateSortedByNameByOrderAsc() {
        List<GiftCertificate> actual =
                giftCertificateDaoImpl.findAllSortedByParam("name", false);
        List<GiftCertificate> unsortedExpected =
                giftCertificateDaoImpl.findAll();
        Collections.sort(unsortedExpected, (certificateOne, certificateTwo) -> {
            String nameOne = certificateOne.getName();
            String nameTwo = certificateTwo.getName();
            return nameOne.compareTo(nameTwo);
        });
        Assertions.assertEquals(unsortedExpected, actual);
    }

    @Test
    public void testFindAllSortedByParamShouldReturnListGiftCertificateSortedByCreateDateByOrderDesc() {
        List<GiftCertificate> actual =
                giftCertificateDaoImpl.findAllSortedByParam("name", true);
        List<GiftCertificate> unsortedExpected =
                giftCertificateDaoImpl.findAll();
        Collections.sort(unsortedExpected, (certificateOne, certificateTwo) -> {
            String createDateOne = certificateOne.getName();
            String createDateTwo = certificateTwo.getName();
            return createDateTwo.compareTo(createDateOne);
        });
        Assertions.assertEquals(unsortedExpected, actual);
    }

    @Test
    public void testFindByNameShouldReturnOptionalGiftCertificateWhenDatabaseContainGiftCertificateName() {
        Optional<GiftCertificate> actual = giftCertificateDaoImpl.findByName("partName1");
        Assertions.assertEquals(Optional.of(GIFT_CERTIFICATE_ONE), actual);
    }

    @Test
    public void testFindByNameShouldReturnOptionalEmptyWhenDatabaseNotContainGiftCertificateName() {
        Optional<GiftCertificate> actual = giftCertificateDaoImpl.findByName("not Contain");
        Assertions.assertEquals(Optional.empty(), actual);
    }

}