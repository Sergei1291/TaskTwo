package com.epam.esm.dao.impl;

import com.epam.esm.dao.api.AbstractDao;
import com.epam.esm.dao.api.GiftCertificateDao;
import com.epam.esm.dao.helper.DaoQueryHelperImpl;
import com.epam.esm.dao.helper.GiftCertificateDaoQueryHelper;
import com.epam.esm.dao.helper.extractor.impl.GiftCertificateFieldExtractor;
import com.epam.esm.entity.identifiable.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate>
        implements GiftCertificateDao {

    private final static String TABLE_NAME = "gift_certificate";
    private final static String FIND_ALL_BY_TAG = "select * from gift_certificate_tag " +
            "inner join gift_certificate on gift_certificate_tag.certificate = gift_certificate.id " +
            "where gift_certificate_tag.tag=?;";
    private final static String FIND_BY_NAME = "select * from gift_certificate where name = ?;";

    private final GiftCertificateDaoQueryHelper giftCertificateDaoQueryHelper;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate,
                                  GiftCertificateDaoQueryHelper giftCertificateDaoQueryHelper) {
        super(TABLE_NAME, jdbcTemplate, BeanPropertyRowMapper.newInstance(GiftCertificate.class),
                new DaoQueryHelperImpl<GiftCertificate>(new GiftCertificateFieldExtractor()));
        this.giftCertificateDaoQueryHelper = giftCertificateDaoQueryHelper;
    }

    @Override
    public List<GiftCertificate> findAllByTag(int tagId) {
        return jdbcTemplate.query(FIND_ALL_BY_TAG, rowMapper, tagId);
    }

    @Override
    public List<GiftCertificate> findAllBySearchStringSearchParamName(String searchString,
                                                                      String searchParamName) {
        String query = "select * from gift_certificate where "
                + searchParamName + " like concat('%',?,'%');";
        return jdbcTemplate.query(query, rowMapper, searchString);
    }

    @Override
    public List<GiftCertificate> findAllSortedByParam(String param, boolean orderDesc) {
        String query = giftCertificateDaoQueryHelper.createQueryFindAllSortedByParam(param, orderDesc);
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        List<GiftCertificate> foundedObjectList = jdbcTemplate.query(FIND_BY_NAME, rowMapper, name);
        if (foundedObjectList.isEmpty()) {
            return Optional.empty();
        }
        GiftCertificate foundedObject = foundedObjectList.get(0);
        return Optional.of(foundedObject);
    }

}