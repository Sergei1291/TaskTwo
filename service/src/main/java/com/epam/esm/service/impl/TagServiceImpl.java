package com.epam.esm.service.impl;

import com.epam.esm.dao.api.GiftCertificateTagDao;
import com.epam.esm.dao.api.TagDao;
import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.exception.tag.TagNameAlreadyExistsException;
import com.epam.esm.exception.tag.TagNameNotValidException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.service.api.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final GiftCertificateTagDao giftCertificateTagDao;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao,
                          GiftCertificateTagDao giftCertificateTagDao,
                          TagValidator tagValidator) {
        this.tagDao = tagDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.tagValidator = tagValidator;
    }

    @Override
    @Transactional
    public Tag save(Tag tag) {
        String tagName = tag.getName();
        if (!tagValidator.isValidName(tag)) {
            throw new TagNameNotValidException(tagName);
        }
        Optional<Tag> optionalTag = tagDao.findByName(tagName);
        if (optionalTag.isPresent()) {
            throw new TagNameAlreadyExistsException(tagName);
        }
        Integer tagId = tagDao.save(tag);
        return tagDao.findById(tagId).orElseThrow(() -> new TagNotFoundException("" + tagId));
    }

    @Override
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException("update operation is not supported");
    }

    @Override
    @Transactional
    public void remove(int id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        Tag tag = optionalTag.orElseThrow(() -> new TagNotFoundException("" + id));
        tagDao.remove(tag);
        giftCertificateTagDao.deleteTag(id);
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Tag findById(int id) {
        return tagDao.findById(id).orElseThrow(() -> new TagNotFoundException("" + id));
    }

}