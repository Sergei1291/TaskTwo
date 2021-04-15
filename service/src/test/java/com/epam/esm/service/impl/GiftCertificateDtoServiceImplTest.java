package com.epam.esm.service.impl;

import com.epam.esm.dao.DaoException;
import com.epam.esm.dao.api.GiftCertificateDao;
import com.epam.esm.dao.api.GiftCertificateTagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.GiftCertificateTagDaoImpl;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.service.GiftCertificateDtoValidator;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.api.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class GiftCertificateDtoServiceImplTest {

    private final static Tag TAG = new Tag(0, "");
    private final static Tag TAG_ONE = new Tag(1, "aa");
    private final static Tag TAG_TWO = new Tag(2, "bb");
    private final static Tag TAG_THREE = new Tag(3, "ab");
    private final static List<Tag> TAG_LIST = Arrays.asList(TAG_ONE, TAG_TWO, TAG_THREE);

    private final static GiftCertificate GIFT_CERTIFICATE_ONE =
            new GiftCertificate(1, "name", null, 0, 0, "a", null);
    private final static GiftCertificate GIFT_CERTIFICATE_TWO =
            new GiftCertificate(2, "name1", null, 0, 0, "b", null);
    private final static GiftCertificate GIFT_CERTIFICATE_THREE =
            new GiftCertificate(3, "name2", null, 0, 0, "c", null);
    private final static List<GiftCertificate> CERTIFICATE_LIST = Arrays.asList(
            GIFT_CERTIFICATE_ONE, GIFT_CERTIFICATE_TWO, GIFT_CERTIFICATE_THREE);

    private final static GiftCertificateDto GIFT_CERTIFICATE_DTO_ONE =
            new GiftCertificateDto(GIFT_CERTIFICATE_ONE, TAG_LIST);
    private final static GiftCertificateDto GIFT_CERTIFICATE_DTO_TWO =
            new GiftCertificateDto(GIFT_CERTIFICATE_TWO, TAG_LIST);
    private final static GiftCertificateDto GIFT_CERTIFICATE_DTO_THREE =
            new GiftCertificateDto(GIFT_CERTIFICATE_THREE, TAG_LIST);
    private final static List<GiftCertificateDto> GIFT_CERTIFICATE_DTO_LIST = Arrays.asList(
            GIFT_CERTIFICATE_DTO_ONE, GIFT_CERTIFICATE_DTO_TWO, GIFT_CERTIFICATE_DTO_THREE);

    private GiftCertificateDao giftCertificateDao =
            Mockito.mock(GiftCertificateDaoImpl.class);
    private GiftCertificateTagDao giftCertificateTagDao =
            Mockito.mock(GiftCertificateTagDaoImpl.class);
    private TagService tagService = Mockito.mock(TagServiceImpl.class);
    private GiftCertificateDtoValidator validator = Mockito.mock(GiftCertificateDtoValidator.class);
    private GiftCertificateDtoServiceImpl giftCertificateDtoServiceImpl =
            new GiftCertificateDtoServiceImpl(
                    giftCertificateDao,
                    giftCertificateTagDao,
                    tagService,
                    validator);

    @Test
    public void testFindAllByTagNameShouldReturnEmptyListWhenTagNameNotFound()
            throws ServiceException {
        when(tagService.findByName(anyString())).thenReturn(Optional.empty());
        List<GiftCertificateDto> actual = giftCertificateDtoServiceImpl.findAllByTagName("");
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    public void testFindAllByTagNameShouldReturnListWhenDatabaseContainTagName()
            throws ServiceException, DaoException {
        when(tagService.findByName(anyString())).thenReturn(Optional.of(TAG));
        when(giftCertificateDao.findAllByTag(anyInt())).thenReturn(CERTIFICATE_LIST);
        when(tagService.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        List<GiftCertificateDto> actual = giftCertificateDtoServiceImpl.findAllByTagName("");
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_LIST, actual);
    }

    @Test
    public void testFindAllByPartSearchParamShouldReturnEmptyListWhenCertificatesNotFounded()
            throws DaoException, ServiceException {
        when(giftCertificateDao.findAllByPartSearchParam(anyString(), anyString()))
                .thenReturn(new ArrayList<>());
        List<GiftCertificateDto> actual = giftCertificateDtoServiceImpl
                .findAllByPartSearchParam("", "");
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    public void testFindAllByPartSearchParamShouldReturnListWhenCertificatesFounded()
            throws DaoException, ServiceException {
        when(giftCertificateDao.findAllByPartSearchParam(anyString(), anyString()))
                .thenReturn(CERTIFICATE_LIST);
        when(tagService.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        List<GiftCertificateDto> actual = giftCertificateDtoServiceImpl
                .findAllByPartSearchParam("", "");
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_LIST, actual);
    }

    @Test
    public void testFindAllSortedByParamShouldReturnSortedList()
            throws DaoException, ServiceException {
        when(giftCertificateDao.findAllSortedByParam(anyString(), anyBoolean()))
                .thenReturn(CERTIFICATE_LIST);
        when(tagService.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        List<GiftCertificateDto> actual = giftCertificateDtoServiceImpl
                .findAllSortedByParam("", false);
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_LIST, actual);
    }

    @Test
    public void testSaveShouldReturnSavedCertificate() throws DaoException, ServiceException {
        when(validator.isValid(anyObject())).thenReturn(true);
        when(giftCertificateDao.save(anyObject())).thenReturn(1);
        when(tagService.findByName(anyString())).thenReturn(Optional.empty());
        when(tagService.save(anyObject())).thenReturn(TAG_ONE);
        when(giftCertificateDao.findById(1)).thenReturn(Optional.of(GIFT_CERTIFICATE_ONE));
        when(tagService.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        GiftCertificateDto actual = giftCertificateDtoServiceImpl.save(GIFT_CERTIFICATE_DTO_TWO);
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_ONE, actual);
    }

    @Test
    public void testUpdateShouldReturnUpdatedCertificate() throws DaoException, ServiceException {
        when(validator.isValid(anyObject())).thenReturn(true);
        when(tagService.findByName(anyString())).thenReturn(Optional.of(TAG_ONE));
        when(giftCertificateDao.findById(anyInt())).thenReturn(Optional.of(GIFT_CERTIFICATE_ONE));
        when(tagService.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        GiftCertificateDto actual = giftCertificateDtoServiceImpl.update(GIFT_CERTIFICATE_DTO_TWO);
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_ONE, actual);
        verify(giftCertificateDao, times(1)).update(anyObject());
        verify(giftCertificateTagDao, times(1)).deleteCertificate(anyInt());
        verify(tagService, times(3)).findByName(anyString());
        verify(giftCertificateTagDao, times(3)).save(anyInt(), anyInt());
    }

    @Test
    public void testRemoveShouldReturnTrueIfRemove() throws DaoException, ServiceException {
        when(giftCertificateDao.findById(anyInt())).thenReturn(Optional.of(GIFT_CERTIFICATE_ONE));
        boolean actual = giftCertificateDtoServiceImpl.remove(GIFT_CERTIFICATE_DTO_TWO);
        Assertions.assertTrue(actual);
        verify(giftCertificateDao, times(1)).remove(anyObject());
        verify(giftCertificateTagDao, times(1)).deleteCertificate(anyInt());
    }

    @Test
    public void testRemoveShouldReturnFalseIfNotFound() throws DaoException, ServiceException {
        when(giftCertificateDao.findById(anyInt())).thenReturn(Optional.empty());
        boolean actual = giftCertificateDtoServiceImpl.remove(GIFT_CERTIFICATE_DTO_TWO);
        Assertions.assertFalse(actual);
        verify(giftCertificateDao, times(0)).remove(anyObject());
        verify(giftCertificateTagDao, times(0)).deleteCertificate(anyInt());
    }

    @Test
    public void testFindAllShouldReturnList() throws DaoException, ServiceException {
        when(giftCertificateDao.findAll()).thenReturn(CERTIFICATE_LIST);
        when(tagService.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        List<GiftCertificateDto> actual = giftCertificateDtoServiceImpl.findAll();
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_LIST, actual);
        verify(tagService, times(3)).findAllByCertificate(anyInt());
    }

    @Test
    public void testFindByIdShouldReturnEmptyOptionalWhenCertificateNotFound()
            throws DaoException, ServiceException {
        when(giftCertificateDao.findById(anyInt())).thenReturn(Optional.empty());
        Optional<GiftCertificateDto> actual = giftCertificateDtoServiceImpl.findById(anyInt());
        Assertions.assertFalse(actual.isPresent());
        verify(tagService, times(0)).findAllByCertificate(anyInt());
    }

    @Test
    public void testFindByIdShouldReturnOptionalWhenCertificateFound()
            throws DaoException, ServiceException {
        when(giftCertificateDao.findById(0)).thenReturn(Optional.of(GIFT_CERTIFICATE_ONE));
        when((tagService.findAllByCertificate(0))).thenReturn(TAG_LIST);
        Optional<GiftCertificateDto> actual = giftCertificateDtoServiceImpl.findById(0);
        Assertions.assertEquals(Optional.of(GIFT_CERTIFICATE_DTO_ONE), actual);
    }

    @Test
    public void testSearchShouldFindAllByTagNameUnsortedListWhenNameSortParamNullAndTagNameNotNull()
            throws ServiceException, DaoException {
        when(tagService.findByName(anyString())).thenReturn(Optional.of(TAG));
        when(giftCertificateDao.findAllByTag(anyInt())).thenReturn(CERTIFICATE_LIST);
        when(tagService.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        List<GiftCertificateDto> actual = giftCertificateDtoServiceImpl.search(
                "", null, null, null, anyBoolean());
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_LIST, actual);
    }

    @Test
    public void testSearchShouldFindAllByPartSearchParamUnsortedListWhenNameSortParamNullAndNameSearchParamNotNull()
            throws ServiceException, DaoException {
        when(giftCertificateDao.findAllByPartSearchParam(anyString(), anyString()))
                .thenReturn(CERTIFICATE_LIST);
        when(tagService.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        List<GiftCertificateDto> actual = giftCertificateDtoServiceImpl.search(
                null, anyString(), "", null, anyBoolean());
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_LIST, actual);
    }

}