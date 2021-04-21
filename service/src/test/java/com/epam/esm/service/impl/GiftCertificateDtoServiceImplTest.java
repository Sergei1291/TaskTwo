package com.epam.esm.service.impl;

import com.epam.esm.dao.api.GiftCertificateDao;
import com.epam.esm.dao.api.GiftCertificateTagDao;
import com.epam.esm.dao.api.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.GiftCertificateTagDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.exception.certificate.*;
import com.epam.esm.exception.tag.TagNameNotFoundException;
import com.epam.esm.mapper.GiftCertificateDto;
import com.epam.esm.mapper.GiftCertificateDtoMapper;
import com.epam.esm.service.api.GiftCertificateDtoService;
import com.epam.esm.service.api.TagService;
import com.epam.esm.validator.GiftCertificateDtoValidator;
import com.epam.esm.validator.GiftCertificateDtoValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GiftCertificateDtoServiceImplTest {

    private final static Tag TAG_ONE = new Tag(1, "aa");
    private final static Tag TAG_TWO = new Tag(2, "bb");
    private final static Tag TAG_THREE = new Tag(3, "ab");
    private final static List<Tag> TAG_LIST = Arrays.asList(TAG_ONE, TAG_TWO, TAG_THREE);

    private final static GiftCertificate GIFT_CERTIFICATE_ONE =
            new GiftCertificate(1, "name1", null, null, null, "a", null);
    private final static GiftCertificate GIFT_CERTIFICATE_TWO =
            new GiftCertificate(2, "name2", null, null, null, "b", null);
    private final static GiftCertificate GIFT_CERTIFICATE_THREE =
            new GiftCertificate(3, "name3", null, null, null, "c", null);
    private final static List<GiftCertificate> CERTIFICATE_LIST = Arrays.asList(
            GIFT_CERTIFICATE_ONE, GIFT_CERTIFICATE_TWO, GIFT_CERTIFICATE_THREE);

    private final static GiftCertificateDto GIFT_CERTIFICATE_DTO_ONE =
            new GiftCertificateDto(1, "name1", null, null, null, "a", null, TAG_LIST);
    private final static GiftCertificateDto GIFT_CERTIFICATE_DTO_TWO =
            new GiftCertificateDto(2, "name2", null, null, null, "b", null, TAG_LIST);
    private final static GiftCertificateDto GIFT_CERTIFICATE_DTO_THREE =
            new GiftCertificateDto(3, "name3", null, null, null, "c", null, TAG_LIST);
    private final static List<GiftCertificateDto> GIFT_CERTIFICATE_DTO_LIST = Arrays.asList(
            GIFT_CERTIFICATE_DTO_ONE, GIFT_CERTIFICATE_DTO_TWO, GIFT_CERTIFICATE_DTO_THREE);

    private final GiftCertificateDao giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
    private final TagDao tagDao = Mockito.mock(TagDaoImpl.class);
    private final GiftCertificateTagDao giftCertificateTagDao = Mockito.mock(GiftCertificateTagDaoImpl.class);
    private final TagService tagService = Mockito.mock(TagServiceImpl.class);
    private final GiftCertificateDtoValidator validator = Mockito.mock(GiftCertificateDtoValidatorImpl.class);
    private final GiftCertificateDtoMapper mapper = Mockito.mock(GiftCertificateDtoMapper.class);

    private final GiftCertificateDtoService giftCertificateDtoService =
            new GiftCertificateDtoServiceImpl(
                    giftCertificateDao,
                    tagDao,
                    giftCertificateTagDao,
                    tagService,
                    validator,
                    mapper);

    @Test
    public void testFindAllByTagNameShouldThrowTagNameNotFoundExceptionWhenTagNameNotFound() {
        when(tagDao.findByName(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(TagNameNotFoundException.class,
                () -> giftCertificateDtoService.findAllByTagName(anyString()));
        verify(tagDao, times(1)).findByName(anyString());
        verify(giftCertificateDao, times(0)).findAllByTag(anyInt());
        verify(mapper, times(0)).entityToDto(anyObject());
        verify(tagDao, times(0)).findAllByCertificate(anyInt());
    }

    @Test
    public void testFindAllByTagNameShouldReturnListWhenDatabaseContainTagName() {
        when(tagDao.findByName(anyString())).thenReturn(Optional.of(TAG_ONE));
        when(giftCertificateDao.findAllByTag(anyInt())).thenReturn(CERTIFICATE_LIST);
        when(mapper.entityToDto(GIFT_CERTIFICATE_ONE)).thenReturn(GIFT_CERTIFICATE_DTO_ONE);
        when(mapper.entityToDto(GIFT_CERTIFICATE_TWO)).thenReturn(GIFT_CERTIFICATE_DTO_TWO);
        when(mapper.entityToDto(GIFT_CERTIFICATE_THREE)).thenReturn(GIFT_CERTIFICATE_DTO_THREE);
        when(tagDao.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        List<GiftCertificateDto> actual = giftCertificateDtoService.findAllByTagName("");
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_LIST, actual);
        verify(tagDao, times(1)).findByName(anyString());
        verify(giftCertificateDao, times(1)).findAllByTag(anyInt());
        verify(mapper, times(3)).entityToDto(anyObject());
        verify(tagDao, times(3)).findAllByCertificate(anyInt());
    }

    @Test
    public void testFindAllBySearchStringSearchParamNameShouldThrowExceptionWhenSearchParamNameNotNameOrNotDescription() {
        Assertions.assertThrows(UnsupportedSearchParamNameCertificateException.class,
                () -> giftCertificateDtoService.findAllBySearchStringSearchParamName(null, "names"));
    }

    @Test
    public void testFindAllBySearchStringSearchParamNameShouldReturnListWhenCertificatesFounded() {
        when(giftCertificateDao.findAllBySearchStringSearchParamName("", "name"))
                .thenReturn(CERTIFICATE_LIST);
        when(mapper.entityToDto(GIFT_CERTIFICATE_ONE)).thenReturn(GIFT_CERTIFICATE_DTO_ONE);
        when(mapper.entityToDto(GIFT_CERTIFICATE_TWO)).thenReturn(GIFT_CERTIFICATE_DTO_TWO);
        when(mapper.entityToDto(GIFT_CERTIFICATE_THREE)).thenReturn(GIFT_CERTIFICATE_DTO_THREE);
        when(tagDao.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        List<GiftCertificateDto> actual = giftCertificateDtoService
                .findAllBySearchStringSearchParamName("", "name");
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_LIST, actual);
        verify(giftCertificateDao, times(1))
                .findAllBySearchStringSearchParamName(anyString(), anyString());
        verify(mapper, times(3)).entityToDto(anyObject());
        verify(tagDao, times(3)).findAllByCertificate(anyInt());
    }

    @Test
    public void testFindAllSortedByParamShouldThrowExceptionWhenParamNotNameOrNotCreateDate() {
        Assertions.assertThrows(UnsupportedSortedParamNameCertificateException.class,
                () -> giftCertificateDtoService.findAllSortedByParam("create_date_", true));
    }

    @Test
    public void testFindAllSortedByParamShouldReturnSortedList() {
        when(giftCertificateDao.findAllSortedByParam("name", false))
                .thenReturn(CERTIFICATE_LIST);
        when(mapper.entityToDto(GIFT_CERTIFICATE_ONE)).thenReturn(GIFT_CERTIFICATE_DTO_ONE);
        when(mapper.entityToDto(GIFT_CERTIFICATE_TWO)).thenReturn(GIFT_CERTIFICATE_DTO_TWO);
        when(mapper.entityToDto(GIFT_CERTIFICATE_THREE)).thenReturn(GIFT_CERTIFICATE_DTO_THREE);
        when(tagDao.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        List<GiftCertificateDto> actual = giftCertificateDtoService
                .findAllSortedByParam("name", false);
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_LIST, actual);
        verify(giftCertificateDao, times(1))
                .findAllSortedByParam(anyString(), anyBoolean());
        verify(mapper, times(3)).entityToDto(anyObject());
        verify(tagDao, times(3)).findAllByCertificate(anyInt());
    }

    @Test
    public void testSaveShouldThrowExceptionWhenCertificateNotValid() {
        when(validator.isValidForSave(anyObject())).thenReturn(false);
        Assertions.assertThrows(CertificateDtoNotValidException.class,
                () -> giftCertificateDtoService.save(anyObject()));
    }

    @Test
    public void testSaveShouldThrowExceptionWhenCertificateNameAlreadyExists() {
        when(validator.isValidForSave(anyObject())).thenReturn(true);
        when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.of(new GiftCertificate()));
        Assertions.assertThrows(CertificateNameAlreadyExistsException.class,
                () -> giftCertificateDtoService.save(GIFT_CERTIFICATE_DTO_ONE));
    }

    @Test
    public void testSaveShouldReturnSavedCertificate() {
        when(validator.isValidForSave(anyObject())).thenReturn(true);
        when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.empty());
        when(mapper.dtoToEntity(anyObject())).thenReturn(GIFT_CERTIFICATE_ONE);
        when(giftCertificateDao.save(GIFT_CERTIFICATE_ONE)).thenReturn(1);
        when(tagDao.findByName(anyString())).thenReturn(Optional.of(TAG_ONE));
        when(giftCertificateDao.findById(anyInt())).thenReturn(Optional.of(GIFT_CERTIFICATE_ONE));
        when(mapper.entityToDto(anyObject())).thenReturn(GIFT_CERTIFICATE_DTO_ONE);
        when(tagDao.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        GiftCertificateDto actual = giftCertificateDtoService.save(GIFT_CERTIFICATE_DTO_ONE);
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_ONE, actual);
        verify(tagDao, times(3)).findByName(anyString());
        verify(tagDao, times(0)).save(anyObject());
        verify(giftCertificateTagDao, times(3)).save(anyInt(), anyInt());
    }

    @Test
    public void testUpdateShouldThrowExceptionWhenCertificateNotValid() {
        when(validator.isValidForUpdate(anyObject())).thenReturn(false);
        Assertions.assertThrows(CertificateDtoNotValidException.class,
                () -> giftCertificateDtoService.update(anyObject()));
    }

    @Test
    public void testUpdateShouldThrowExceptionWhenCertificateNameAlreadyExists() {
        when(validator.isValidForUpdate(anyObject())).thenReturn(true);
        when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.of(new GiftCertificate()));
        Assertions.assertThrows(CertificateNameAlreadyExistsException.class,
                () -> giftCertificateDtoService.update(GIFT_CERTIFICATE_DTO_ONE));
    }

    @Test
    public void testUpdateShouldReturnUpdatedCertificate() {
        when(validator.isValidForUpdate(anyObject())).thenReturn(true);
        when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.empty());
        when(mapper.dtoToEntity(anyObject())).thenReturn(GIFT_CERTIFICATE_ONE);
        when(tagDao.findByName(anyString())).thenReturn(Optional.of(TAG_ONE));
        when(giftCertificateDao.findById(anyInt())).thenReturn(Optional.of(GIFT_CERTIFICATE_ONE));
        when(mapper.entityToDto(anyObject())).thenReturn(GIFT_CERTIFICATE_DTO_ONE);
        when(tagDao.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        GiftCertificateDto actual = giftCertificateDtoService.update(GIFT_CERTIFICATE_DTO_ONE);
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_ONE, actual);
        verify(giftCertificateDao, times(1)).update(anyObject());
        verify(giftCertificateTagDao, times(1)).deleteCertificate(anyInt());
        verify(tagDao, times(3)).findByName(anyString());
        verify(tagDao, times(0)).save(anyObject());
        verify(giftCertificateTagDao, times(3)).save(anyInt(), anyInt());
    }

    @Test
    public void testRemoveShouldThrowExceptionWhenCertificateIdNotFound() {
        when(giftCertificateDao.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class,
                () -> giftCertificateDtoService.remove(anyInt()));
    }

    @Test
    public void testRemoveShouldRemove() {
        when(giftCertificateDao.findById(anyInt())).thenReturn(Optional.of(GIFT_CERTIFICATE_ONE));
        giftCertificateDtoService.remove(0);
        verify(giftCertificateDao, times(1)).remove(anyObject());
        verify(giftCertificateTagDao, times(1)).deleteCertificate(anyInt());
    }

    @Test
    public void testFindAllShouldReturnList() {
        when(giftCertificateDao.findAll()).thenReturn(CERTIFICATE_LIST);
        when(mapper.entityToDto(GIFT_CERTIFICATE_ONE)).thenReturn(GIFT_CERTIFICATE_DTO_ONE);
        when(mapper.entityToDto(GIFT_CERTIFICATE_TWO)).thenReturn(GIFT_CERTIFICATE_DTO_TWO);
        when(mapper.entityToDto(GIFT_CERTIFICATE_THREE)).thenReturn(GIFT_CERTIFICATE_DTO_THREE);
        when(tagDao.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        List<GiftCertificateDto> actual = giftCertificateDtoService.findAll();
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_LIST, actual);
        verify(tagDao, times(3)).findAllByCertificate(anyInt());
        verify(mapper, times(3)).entityToDto(anyObject());
    }

    @Test
    public void testFindByIdShouldThrowExceptionWhenCertificateNotFound() {
        when(giftCertificateDao.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class,
                () -> giftCertificateDtoService.findById(anyInt()));
    }

    @Test
    public void testFindByIdShouldReturnOptionalWhenCertificateFound() {
        when(giftCertificateDao.findById(anyInt())).thenReturn(Optional.of(GIFT_CERTIFICATE_ONE));
        when(mapper.entityToDto(GIFT_CERTIFICATE_ONE)).thenReturn(GIFT_CERTIFICATE_DTO_ONE);
        when(tagDao.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        GiftCertificateDto actual = giftCertificateDtoService.findById(0);
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO_ONE, actual);
    }

}