package com.epam.esm.service.impl;

import com.epam.esm.dao.DaoException;
import com.epam.esm.dao.api.GiftCertificateTagDao;
import com.epam.esm.dao.api.TagDao;
import com.epam.esm.dao.impl.GiftCertificateTagDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.service.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class TagServiceImplTest {

    private final static List<Tag> TAG_LIST =
            Arrays.asList(new Tag(1, "one"), new Tag(2, "two"));

    private TagDao tagDao = Mockito.mock(TagDaoImpl.class);
    private GiftCertificateTagDao giftCertificateTagDao = Mockito.mock(GiftCertificateTagDaoImpl.class);
    private TagServiceImpl tagServiceImpl = new TagServiceImpl(tagDao, giftCertificateTagDao);

    @Test
    public void testFindByNameShouldReturnOptionalNotEmpty() throws ServiceException, DaoException {
        when(tagDao.findByName(anyString())).thenReturn(Optional.of(TAG_LIST.get(0)));
        Optional<Tag> actual = tagServiceImpl.findByName("one");
        Assertions.assertEquals(Optional.of(new Tag(1, "one")), actual);
    }

    @Test
    public void testFindAllByCertificateShouldReturnTagList() throws DaoException, ServiceException {
        when(tagDao.findAllByCertificate(anyInt())).thenReturn(TAG_LIST);
        List<Tag> actual = tagServiceImpl.findAllByCertificate(1);
        Assertions.assertEquals(
                Arrays.asList(new Tag(1, "one"), new Tag(2, "two")), actual);
    }

    @Test
    public void testSaveShouldSaveTagInDatabaseAndReturnSavedTag() throws DaoException, ServiceException {
        when(tagDao.save(anyObject())).thenReturn(1);
        when(tagDao.findById(1)).thenReturn(Optional.of(TAG_LIST.get(0)));
        Tag actual = tagServiceImpl.save(null);
        Assertions.assertEquals(new Tag(1, "one"), actual);
    }

    @Test
    public void testRemoveShouldRemoveTagAndMapFromGiftCertificateTagIfTagFoundedInDataBase()
            throws ServiceException, DaoException {
        when(tagDao.findByName(anyString())).thenReturn(Optional.of(new Tag()));
        boolean actual = tagServiceImpl.remove(new Tag());
        Assertions.assertTrue(actual);
        verify(tagDao, times(1)).remove(anyObject());
        verify(giftCertificateTagDao, times(1)).deleteTag(anyInt());
    }

    @Test
    public void testRemoveShouldRemoveTagAndMapFromGiftCertificateTagIfTagNotFoundInDataBase()
            throws ServiceException, DaoException {
        when(tagDao.findByName(anyString())).thenReturn(Optional.empty());
        boolean actual = tagServiceImpl.remove(new Tag());
        Assertions.assertFalse(actual);
        verify(tagDao, times(0)).remove(anyObject());
        verify(giftCertificateTagDao, times(0)).deleteTag(anyInt());
    }

    @Test
    public void testFindAllShouldReturnTagList() throws DaoException, ServiceException {
        when(tagDao.findAll()).thenReturn(TAG_LIST);
        List<Tag> actual = tagServiceImpl.findAll();
        Assertions.assertEquals(TAG_LIST, actual);
    }

    @Test
    public void testFindByIdShouldReturnOptionalNotEmpty() throws ServiceException, DaoException {
        when(tagDao.findById(anyInt())).thenReturn(Optional.of(TAG_LIST.get(0)));
        Optional<Tag> actual = tagServiceImpl.findById(0);
        Assertions.assertEquals(Optional.of(new Tag(1, "one")), actual);
    }

}