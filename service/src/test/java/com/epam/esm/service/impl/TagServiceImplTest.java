package com.epam.esm.service.impl;

import com.epam.esm.dao.api.GiftCertificateTagDao;
import com.epam.esm.dao.api.TagDao;
import com.epam.esm.dao.impl.GiftCertificateTagDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.exception.tag.TagNameAlreadyExistsException;
import com.epam.esm.exception.tag.TagNameNotValidException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.service.api.TagService;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.TagValidatorImpl;
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

    private final TagDao tagDao = Mockito.mock(TagDaoImpl.class);
    private final GiftCertificateTagDao giftCertificateTagDao =
            Mockito.mock(GiftCertificateTagDaoImpl.class);
    private final TagValidator tagValidator = Mockito.mock(TagValidatorImpl.class);
    private final TagService tagService =
            new TagServiceImpl(tagDao, giftCertificateTagDao, tagValidator);

    @Test
    public void testSaveShouldSaveTagToDatabaseAndReturnSavedTag() {
        when(tagValidator.isValidName(anyObject())).thenReturn(true);
        when(tagDao.findByName(anyString())).thenReturn(Optional.empty());
        when(tagDao.save(anyObject())).thenReturn(1);
        when(tagDao.findById(anyInt())).thenReturn(Optional.of(TAG_LIST.get(0)));
        Tag actual = tagService.save(new Tag(0, ""));
        Assertions.assertEquals(new Tag(1, "one"), actual);
    }

    @Test
    public void testSaveShouldThrowTagNameNotValidExceptionWhenTagNameNotValid() {
        when(tagValidator.isValidName(anyObject())).thenReturn(false);
        Assertions.assertThrows(TagNameNotValidException.class,
                () -> tagService.save(new Tag(0, "")));
        verify(tagDao, times(0)).findByName(anyString());

    }

    @Test
    public void testSaveShouldThrowTagNameAlreadyExistsExceptionWhenDatabaseContainTagName() {
        when(tagValidator.isValidName(anyObject())).thenReturn(true);
        when(tagDao.findByName(anyString())).thenReturn(Optional.of(new Tag()));
        Assertions.assertThrows(TagNameAlreadyExistsException.class,
                () -> tagService.save(new Tag(0, "")));
        verify(tagDao, times(0)).save(anyObject());
        verify(tagDao, times(0)).findById(anyInt());
    }

    @Test
    public void testRemoveShouldRemoveTagAndMapFromGiftCertificateTagWhenTagFoundedInDataBase() {
        when(tagDao.findById(anyInt())).thenReturn(Optional.of(new Tag()));
        tagService.remove(0);
        verify(tagDao, times(1)).remove(anyObject());
        verify(giftCertificateTagDao, times(1)).deleteTag(anyInt());
    }

    @Test
    public void testRemoveShouldThrowTagNotFoundExceptionWhenDatabaseNotContainTagId() {
        when(tagDao.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(TagNotFoundException.class,
                () -> tagService.remove(0));
        verify(tagDao, times(0)).remove(anyObject());
        verify(giftCertificateTagDao, times(0)).deleteTag(anyInt());
    }

    @Test
    public void testFindAllShouldReturnTagList() {
        when(tagDao.findAll()).thenReturn(TAG_LIST);
        List<Tag> actual = tagService.findAll();
        Assertions.assertEquals(TAG_LIST, actual);
    }

    @Test
    public void testFindByIdShouldReturnTag() {
        when(tagDao.findById(anyInt())).thenReturn(Optional.of(TAG_LIST.get(0)));
        Tag actual = tagService.findById(0);
        Assertions.assertEquals(new Tag(1, "one"), actual);
    }

    @Test
    public void testFindByIdShouldThrowTagNotFoundExceptionWhenDatabaseNotContainTagId() {
        when(tagDao.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(TagNotFoundException.class,
                () -> tagService.findById(anyInt()));
    }

}