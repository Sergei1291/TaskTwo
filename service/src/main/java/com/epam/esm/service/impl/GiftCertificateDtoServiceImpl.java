package com.epam.esm.service.impl;

import com.epam.esm.dao.api.GiftCertificateDao;
import com.epam.esm.dao.api.GiftCertificateTagDao;
import com.epam.esm.dao.api.TagDao;
import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.exception.certificate.*;
import com.epam.esm.exception.tag.TagNameNotFoundException;
import com.epam.esm.mapper.GiftCertificateDto;
import com.epam.esm.mapper.GiftCertificateDtoMapper;
import com.epam.esm.service.api.GiftCertificateDtoService;
import com.epam.esm.service.api.TagService;
import com.epam.esm.validator.GiftCertificateDtoValidator;
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

    private final static String NAME = "name";
    private final static String DESCRIPTION = "description";
    private final static String CREATE_DATE = "create_date";

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateTagDao giftCertificateTagDao;
    private final TagService tagService;
    private final GiftCertificateDtoValidator validator;
    private final GiftCertificateDtoMapper giftCertificateDtoMapper;

    @Autowired
    public GiftCertificateDtoServiceImpl(GiftCertificateDao giftCertificateDao,
                                         TagDao tagDao,
                                         GiftCertificateTagDao giftCertificateTagDao,
                                         TagService tagService,
                                         GiftCertificateDtoValidator validator,
                                         GiftCertificateDtoMapper giftCertificateDtoMapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.tagService = tagService;
        this.validator = validator;
        this.giftCertificateDtoMapper = giftCertificateDtoMapper;
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findAllByTagName(String tagName) {
        Optional<Tag> optionalTag = tagDao.findByName(tagName);
        Tag tag = optionalTag.orElseThrow(() -> new TagNameNotFoundException(tagName));
        int tagId = tag.getId();
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAllByTag(tagId);
        return transform(giftCertificates);
    }

    private List<GiftCertificateDto> transform(List<GiftCertificate> giftCertificates) {
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();
        for (GiftCertificate giftCertificate : giftCertificates) {
            GiftCertificateDto giftCertificateDto = transform(giftCertificate);
            giftCertificateDtoList.add(giftCertificateDto);
        }
        return giftCertificateDtoList;
    }

    private GiftCertificateDto transform(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto =
                giftCertificateDtoMapper.entityToDto(giftCertificate);
        int idCertificate = giftCertificate.getId();
        List<Tag> tags = tagDao.findAllByCertificate(idCertificate);
        giftCertificateDto.setTagList(tags);
        return giftCertificateDto;
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findAllBySearchStringSearchParamName(
            String searchString,
            String searchParamName) {
        if (!NAME.equals(searchParamName) &&
                !DESCRIPTION.equals(searchParamName)) {
            throw new UnsupportedSearchParamNameCertificateException(searchParamName);
        }
        List<GiftCertificate> giftCertificates =
                giftCertificateDao.findAllBySearchStringSearchParamName(
                        searchString, searchParamName);
        return transform(giftCertificates);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findAllSortedByParam(String param, boolean orderDesc) {
        if (!NAME.equals(param) && !CREATE_DATE.equals(param)) {
            throw new UnsupportedSortedParamNameCertificateException(param);
        }
        List<GiftCertificate> giftCertificates =
                giftCertificateDao.findAllSortedByParam(param, orderDesc);
        return transform(giftCertificates);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> search(@Nullable String tagName,
                                           @Nullable String searchString,
                                           @Nullable String searchParamName,
                                           @Nullable String sortParamName,
                                           @Nullable boolean orderDesc) {
        if ((searchParamName == null) && (tagName == null)) {
            return new ArrayList<>();
        }
        if (sortParamName == null) {
            return getUnsortedCertificates(tagName, searchString, searchParamName);
        }
        return getSortedCertificates(tagName, searchString, searchParamName, sortParamName, orderDesc);
    }

    private List<GiftCertificateDto> getUnsortedCertificates(String tagName,
                                                             String searchString,
                                                             String searchParamName) {
        List<GiftCertificateDto> resultList = new ArrayList<>();
        if ((tagName != null) && (searchParamName != null)) {
            List<GiftCertificateDto> giftCertificateDtoList =
                    findAllBySearchStringSearchParamName(searchString, searchParamName);
            giftCertificateDtoList.stream().
                    filter(giftCertificateDto -> hasGiftCertificateTagByName(giftCertificateDto, tagName)).
                    forEach(resultList::add);
            return resultList;
        }
        if (tagName != null) {
            return findAllByTagName(tagName);
        }
        return findAllBySearchStringSearchParamName(searchString, searchParamName);
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
                                                           boolean orderDesc) {
        List<GiftCertificateDto> giftCertificateDtoListUnsorted =
                getUnsortedCertificates(tagName, searchParam, nameSearchParam);
        List<GiftCertificateDto> giftCertificateDtoListSorted =
                findAllSortedByParam(nameSortParam, orderDesc);
        giftCertificateDtoListSorted.retainAll(giftCertificateDtoListUnsorted);
        return giftCertificateDtoListSorted;
    }

    @Override
    @Transactional
    public GiftCertificateDto save(GiftCertificateDto giftCertificateDto) {
        if (!validator.isValidForSave(giftCertificateDto)) {
            throw new CertificateDtoNotValidException();
        }
        String certificateName = giftCertificateDto.getName();
        Optional<GiftCertificate> optionalGiftCertificate =
                giftCertificateDao.findByName(certificateName);
        if (optionalGiftCertificate.isPresent()) {
            throw new CertificateNameAlreadyExistsException(certificateName);
        }
        String timeAtThisMoment = getTimeAtThisMoment();
        giftCertificateDto.setCreateDate(timeAtThisMoment);
        giftCertificateDto.setLastUpdateDate(null);
        GiftCertificate giftCertificate =
                giftCertificateDtoMapper.dtoToEntity(giftCertificateDto);
        Integer idSavedCertificate = giftCertificateDao.save(giftCertificate);
        List<Tag> tagList = giftCertificateDto.getTagList();
        saveTagList(tagList, idSavedCertificate);
        return findById(idSavedCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        if (!validator.isValidForUpdate(giftCertificateDto)) {
            throw new CertificateDtoNotValidException();
        }
        String certificateName = giftCertificateDto.getName();
        Optional<GiftCertificate> optionalGiftCertificate =
                giftCertificateDao.findByName(certificateName);
        if (optionalGiftCertificate.isPresent()) {
            throw new CertificateNameAlreadyExistsException(certificateName);
        }
        String timeAtThisMoment = getTimeAtThisMoment();
        giftCertificateDto.setLastUpdateDate(timeAtThisMoment);
        giftCertificateDto.setCreateDate(null);
        GiftCertificate giftCertificate =
                giftCertificateDtoMapper.dtoToEntity(giftCertificateDto);
        giftCertificateDao.update(giftCertificate);
        int idGiftCertificate = giftCertificate.getId();
        giftCertificateTagDao.deleteCertificate(idGiftCertificate);
        List<Tag> tagList = giftCertificateDto.getTagList();
        saveTagList(tagList, idGiftCertificate);
        return findById(idGiftCertificate);
    }

    private String getTimeAtThisMoment() {
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.ms");
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        DateTimeFormatter dateTimeFormatterZone = dateTimeFormatter.withZone(zoneOffset);
        return dateTimeFormatterZone.format(Instant.now());
    }

    private void saveTagList(List<Tag> tagList, int idGiftCertificate) {
        if (tagList == null) {
            return;
        }
        for (Tag tag : tagList) {
            String tagName = tag.getName();
            Optional<Tag> optionalTag = tagDao.findByName(tagName);
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
    public void remove(int id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
        GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(
                () -> new CertificateNotFoundException("" + id));
        giftCertificateDao.remove(giftCertificate);
        giftCertificateTagDao.deleteCertificate(id);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findAll() {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findAll();
        return transform(giftCertificateList);
    }

    @Override
    @Transactional
    public GiftCertificateDto findById(int id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
        GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(
                () -> new CertificateNotFoundException("" + id));
        return transform(giftCertificate);
    }

}