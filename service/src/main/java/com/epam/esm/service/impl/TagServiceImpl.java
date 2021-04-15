package com.epam.esm.service.impl;

import com.epam.esm.dao.DaoException;
import com.epam.esm.dao.api.GiftCertificateTagDao;
import com.epam.esm.dao.api.TagDao;
import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private TagDao tagDao;
    private GiftCertificateTagDao giftCertificateTagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao, GiftCertificateTagDao giftCertificateTagDao) {
        this.tagDao = tagDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
    }

    @Override
    public Optional<Tag> findByName(String name) throws ServiceException {
        try {
            return tagDao.findByName(name);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tag> findAllByCertificate(int certificateId) throws ServiceException {
        try {
            return tagDao.findAllByCertificate(certificateId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Tag save(Tag tag) throws ServiceException {
        try {
            Integer tagId = tagDao.save(tag);
            return tagDao.findById(tagId).orElseThrow(
                    () -> new ServiceException("tag id not found: " + tagId));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Tag update(Tag tag) throws ServiceException {
        throw new UnsupportedOperationException("update operation is not supported");
    }

    @Override
    @Transactional
    public boolean remove(Tag tag) throws ServiceException {
        try {
            String tagName = tag.getName();
            Optional<Tag> optionalTag = tagDao.findByName(tagName);
            if (!optionalTag.isPresent()) {
                return false;
            }
            Tag tagForDelete = optionalTag.get();
            tagDao.remove(tagForDelete);
            int tagId = tagForDelete.getId();
            giftCertificateTagDao.deleteTag(tagId);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tag> findAll() throws ServiceException {
        try {
            return tagDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Tag> findById(int id) throws ServiceException {
        try {
            return tagDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}