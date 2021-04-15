package com.epam.esm.dao.impl;

import com.epam.esm.config.RepositoryConfig;
import com.epam.esm.dao.DaoException;
import com.epam.esm.entity.identifiable.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GiftCertificateDaoImplTest {

    private GiftCertificateDaoImpl giftCertificateDaoImpl;
    private TestInnerDatabaseConfig testInnerDatabaseConfig;

    @BeforeEach
    void initialize() throws SQLException {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(RepositoryConfig.class);
        JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
        giftCertificateDaoImpl = new GiftCertificateDaoImpl(jdbcTemplate);
        testInnerDatabaseConfig = new TestInnerDatabaseConfig(jdbcTemplate);
        testInnerDatabaseConfig.initializeDatabase();
    }

    @AfterEach
    void destroy() {
        testInnerDatabaseConfig.destroyDatabase();
    }

    @Test
    public void testSaveShouldSaveGiftCertificateInDatabaseANdReturnCreatedId() throws DaoException {
        GiftCertificate giftCertificate = new GiftCertificate("nameCertificate",
                "descriptionGift", 1, 1, "createDate", null);
        Integer actualId = giftCertificateDaoImpl.save(giftCertificate);
        Assertions.assertEquals(Integer.valueOf(5), actualId);
        Optional<GiftCertificate> optionalTag = giftCertificateDaoImpl.findById(5);
        Assertions.assertEquals(Optional.of(new GiftCertificate(5, "nameCertificate",
                        "descriptionGift", 1, 1, "createDate", null)),
                optionalTag);
    }

    @Test
    public void testUpdateShouldUpdate() throws DaoException {
        GiftCertificate giftCertificate = new GiftCertificate(1, "updateName",
                "updateDescription", null, null, "h", null);
        giftCertificateDaoImpl.update(giftCertificate);
        Optional<GiftCertificate> actual = giftCertificateDaoImpl.findById(1);
        Assertions.assertEquals(Optional.of(new GiftCertificate(1, "updateName",
                "updateDescription", 10, 2, "h",
                null)), actual);
    }

    @Test
    public void testRemoveShouldRemoveGiftCertificateFromDatabase() throws DaoException {
        GiftCertificate giftCertificate = new GiftCertificate(1, null,
                null, 0, 0, null, null);
        giftCertificateDaoImpl.remove(giftCertificate);
        List<GiftCertificate> giftCertificateList = giftCertificateDaoImpl.findAll();
        int sizeTagList = giftCertificateList.size();
        Assertions.assertEquals(3, sizeTagList);
    }

    @Test
    public void testFindAllShouldReturnDatabaseListGiftCertificate() throws DaoException {
        List<GiftCertificate> giftCertificateList = giftCertificateDaoImpl.findAll();
        Assertions.assertEquals(4, giftCertificateList.size());
    }

    @Test
    public void testFindByIdShouldReturnOptionalGiftCertificateWhenDatabaseContainCertificateId()
            throws DaoException {
        Optional<GiftCertificate> actual = giftCertificateDaoImpl.findById(2);
        Assertions.assertEquals(Optional.of(new GiftCertificate(2, "a2",
                "asdasa sada description first", 10, 2,
                "2021-03-24T00:00:13.5213", null)), actual);
    }

    @Test
    public void testFindByIdShouldReturnOptionalEmptyWhenDatabaseNotContainCertificateId()
            throws DaoException {
        Optional<GiftCertificate> actual = giftCertificateDaoImpl.findById(5);
        Assertions.assertEquals(Optional.empty(), actual);
    }

    @Test
    public void testFindAllByTagShouldReturnListGiftCertificateWhichHaveTag() throws DaoException {
        List<GiftCertificate> actual = giftCertificateDaoImpl.findAllByTag(2);
        List<GiftCertificate> expected = Arrays.asList(
                new GiftCertificate(2, "a2", "asdasa sada description first",
                        10, 2, "2021-03-24T00:00:13.5213", null),
                new GiftCertificate(4, "a4", "descriptions",
                        10, 2, "2021-03-29T06:52:13.5213", null));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFindAllByPartSearchParamShouldReturnListGiftCertificateHavingPartName()
            throws DaoException {
        List<GiftCertificate> actual =
                giftCertificateDaoImpl.findAllByPartSearchParam("rt", "name");
        List<GiftCertificate> expected = Arrays.asList(new GiftCertificate(1, "partName1",
                "asdasa sada d asdasd", 10, 2,
                "2021-03-21T20:52:13.5213", null));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFindAllByPartSearchParamShouldReturnListGiftCertificateHavingPartDescription()
            throws DaoException {
        List<GiftCertificate> actual =
                giftCertificateDaoImpl.findAllByPartSearchParam("asdasa sada", "description");
        List<GiftCertificate> expected = Arrays.asList(new GiftCertificate(1, "partName1",
                        "asdasa sada d asdasd", 10, 2, "2021-03-21T20:52:13.5213", null),
                new GiftCertificate(2, "a2", "asdasa sada description first",
                        10, 2, "2021-03-24T00:00:13.5213", null));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFindAllByPartSearchParamShouldThrowDaoExceptionWhenSearchParamNotEqualNameOrDescription()
            throws DaoException {
        Assertions.assertThrows(DaoException.class, () -> {
            giftCertificateDaoImpl.findAllByPartSearchParam("a", "descriptions");
        });
    }

    @Test
    public void testFindAllSortedByParamShouldThrowDaoExceptionWhenParamNotEqualNameOrCreateDate() {
        Assertions.assertThrows(DaoException.class, () -> {
            giftCertificateDaoImpl.findAllSortedByParam("names", true);
        });
    }

    @Test
    public void testFindAllSortedByParamShouldReturnListGiftCertificateSortedByName() throws DaoException {
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
    public void testFindAllSortedByParamShouldReturnListGiftCertificateSortedByCreateDateToSmallestOrder() throws DaoException {
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

}