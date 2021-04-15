package com.epam.esm.service.impl;

import com.epam.esm.dao.DaoException;
import com.epam.esm.dao.api.GiftCertificateDao;
import com.epam.esm.dao.api.GiftCertificateTagDao;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.service.GiftCertificateDtoValidator;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.api.GiftCertificateDtoService;
import com.epam.esm.service.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateDtoServiceImpl implements GiftCertificateDtoService {

    private GiftCertificateDao giftCertificateDao;
    private GiftCertificateTagDao giftCertificateTagDao;
    private TagService tagService;
    private GiftCertificateDtoValidator validator;

    @Autowired
    public GiftCertificateDtoServiceImpl(GiftCertificateDao giftCertificateDao,
                                         GiftCertificateTagDao giftCertificateTagDao,
                                         TagService tagService,
                                         GiftCertificateDtoValidator validator) {
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.tagService = tagService;
        this.validator = validator;
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findAllByTagName(String tagName) throws ServiceException {
        try {
            Optional<Tag> optionalTag = tagService.findByName(tagName);
            if (!optionalTag.isPresent()) {
                return new ArrayList<>();
            }
            Tag tag = optionalTag.get();
            int tagId = tag.getId();
            List<GiftCertificate> giftCertificates = giftCertificateDao.findAllByTag(tagId);
            return transform(giftCertificates);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findAllByPartSearchParam(String part, String nameSearchParam)
            throws ServiceException {
        try {
            List<GiftCertificate> giftCertificates =
                    giftCertificateDao.findAllByPartSearchParam(part, nameSearchParam);
            if (giftCertificates.isEmpty()) {
                return new ArrayList<>();
            }
            return transform(giftCertificates);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findAllSortedByParam(String param, boolean orderDesc)
            throws ServiceException {
        try {
            List<GiftCertificate> giftCertificates =
                    giftCertificateDao.findAllSortedByParam(param, orderDesc);
            return transform(giftCertificates);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public GiftCertificateDto save(GiftCertificateDto giftCertificateDto) throws ServiceException {
        if (!validator.isValid(giftCertificateDto)) {
            return null;
        }
        try {
            GiftCertificate giftCertificate = giftCertificateDto.getGiftCertificate();
            String timeAtThisMoment = getTimeAtThisMoment();
            giftCertificate.setCreateDate(timeAtThisMoment);
            giftCertificate.setLastUpdateDate(null);
            int idGiftCertificate = giftCertificateDao.save(giftCertificate);
            List<Tag> tagList = giftCertificateDto.getTagList();
            saveTagList(tagList, idGiftCertificate);
            Optional<GiftCertificate> savedOptionalGiftCertificate
                    = giftCertificateDao.findById(idGiftCertificate);
            GiftCertificate savedGiftCertificate = savedOptionalGiftCertificate.orElseThrow(
                    () -> new ServiceException("giftCertificate not found " + idGiftCertificate));
            List<Tag> tags = tagService.findAllByCertificate(idGiftCertificate);
            return new GiftCertificateDto(savedGiftCertificate, tags);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void saveTagList(List<Tag> tagList, int idGiftCertificate)
            throws ServiceException, DaoException {
        if (tagList == null) {
            return;
        }
        for (Tag tag : tagList) {
            String tagName = tag.getName();
            Optional<Tag> optionalTag = tagService.findByName(tagName);
            int idTag;
            if (!optionalTag.isPresent()) {
                Tag tagSaved = tagService.save(tag);
                idTag = tagSaved.getId();
            } else {
                idTag = optionalTag.get().getId();
            }
            giftCertificateTagDao.save(idGiftCertificate, idTag);
        }
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) throws ServiceException {
        if (!validator.isValid(giftCertificateDto)) {
            return null;
        }
        try {
            GiftCertificate giftCertificate = giftCertificateDto.getGiftCertificate();
            String timeAtThisMoment = getTimeAtThisMoment();
            giftCertificate.setLastUpdateDate(timeAtThisMoment);
            giftCertificateDao.update(giftCertificate);
            int idGiftCertificate = giftCertificate.getId();
            giftCertificateTagDao.deleteCertificate(idGiftCertificate);
            List<Tag> tagList = giftCertificateDto.getTagList();
            saveTagList(tagList, idGiftCertificate);
            Optional<GiftCertificate> updatedOptionalGiftCertificate
                    = giftCertificateDao.findById(idGiftCertificate);
            GiftCertificate updatedGiftCertificate = updatedOptionalGiftCertificate.orElseThrow(
                    () -> new ServiceException("giftCertificate not found " + idGiftCertificate));
            List<Tag> tags = tagService.findAllByCertificate(idGiftCertificate);
            return new GiftCertificateDto(updatedGiftCertificate, tags);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public boolean remove(GiftCertificateDto giftCertificateDto) throws ServiceException {
        try {
            GiftCertificate giftCertificate = giftCertificateDto.getGiftCertificate();
            int idGiftCertificate = giftCertificate.getId();
            Optional<GiftCertificate> optionalGiftCertificate =
                    giftCertificateDao.findById(idGiftCertificate);
            if (!optionalGiftCertificate.isPresent()) {
                return false;
            }
            giftCertificateDao.remove(giftCertificate);
            int certificateId = giftCertificate.getId();
            giftCertificateTagDao.deleteCertificate(certificateId);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findAll() throws ServiceException {
        try {
            List<GiftCertificate> giftCertificateList = giftCertificateDao.findAll();
            return transform(giftCertificateList);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Optional<GiftCertificateDto> findById(int id) throws ServiceException {
        try {
            Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
            if (!optionalGiftCertificate.isPresent()) {
                return Optional.empty();
            }
            GiftCertificate giftCertificate = optionalGiftCertificate.get();
            List<Tag> tagList = tagService.findAllByCertificate(id);
            return Optional.of(new GiftCertificateDto(giftCertificate, tagList));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private List<GiftCertificateDto> transform(List<GiftCertificate> giftCertificates)
            throws ServiceException {
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();
        for (GiftCertificate giftCertificate : giftCertificates) {
            int idCertificate = giftCertificate.getId();
            List<Tag> tags = tagService.findAllByCertificate(idCertificate);
            giftCertificateDtoList.add(new GiftCertificateDto(giftCertificate, tags));
        }
        return giftCertificateDtoList;
    }

    private String getTimeAtThisMoment() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.ms");
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        DateTimeFormatter dateTimeFormatterZone = dateTimeFormatter.withZone(zoneOffset);
        return dateTimeFormatterZone.format(Instant.now());
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> search(@Nullable String tagName,
                                           @Nullable String searchParam,
                                           @Nullable String nameSearchParam,
                                           @Nullable String nameSortParam,
                                           @Nullable boolean orderDesc) throws ServiceException {
        if ((nameSearchParam == null) && (tagName == null)) {
            return new ArrayList<>();
        }
        if (nameSortParam == null) {
            return getUnsortedCertificates(tagName, searchParam, nameSearchParam);
        }
        return getSortedCertificates(tagName, searchParam, nameSearchParam, nameSortParam, orderDesc);
    }

    private List<GiftCertificateDto> getUnsortedCertificates(String tagName,
                                                             String searchParam,
                                                             String nameSearchParam) throws ServiceException {
        List<GiftCertificateDto> resultList = new ArrayList<>();
        if ((tagName != null) && (nameSearchParam != null)) {
            List<GiftCertificateDto> giftCertificateDtoList =
                    findAllByPartSearchParam(searchParam, nameSearchParam);
            giftCertificateDtoList.stream().filter(giftCertificateDto -> {
                return hasGiftCertificateTagByName(giftCertificateDto, tagName);
            }).forEach(giftCertificateDto -> resultList.add(giftCertificateDto));
            return resultList;
        }
        if (tagName != null) {
            return findAllByTagName(tagName);
        }
        return findAllByPartSearchParam(searchParam, nameSearchParam);
    }

    private boolean hasGiftCertificateTagByName(GiftCertificateDto giftCertificateDto,
                                                String tagName) {
        List<Tag> tagList = giftCertificateDto.getTagList();
        for (Tag tag : tagList) {
            if (tag.getName().equals(tagName)) {
                return true;
            }
        }
        return false;
    }

    private List<GiftCertificateDto> getSortedCertificates(String tagName,
                                                           String searchParam,
                                                           String nameSearchParam,
                                                           String nameSortParam,
                                                           boolean orderDesc) throws ServiceException {
        List<GiftCertificateDto> giftCertificateDtoListUnsorted =
                getUnsortedCertificates(tagName, searchParam, nameSearchParam);
        List<GiftCertificateDto> giftCertificateDtoListSorted =
                findAllSortedByParam(nameSortParam, orderDesc);
        giftCertificateDtoListSorted.retainAll(giftCertificateDtoListUnsorted);
        return giftCertificateDtoListSorted;
    }

}