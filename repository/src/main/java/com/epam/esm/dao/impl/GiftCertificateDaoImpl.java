package com.epam.esm.dao.impl;

import com.epam.esm.dao.DaoException;
import com.epam.esm.dao.api.AbstractDao;
import com.epam.esm.dao.api.GiftCertificateDao;
import com.epam.esm.dao.helper.DaoQueryHelperImpl;
import com.epam.esm.dao.helper.extractor.impl.GiftCertificateFieldExtractor;
import com.epam.esm.entity.identifiable.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate>
        implements GiftCertificateDao {

    private final static String TABLE_NAME = "gift_certificate";
    private final static String FIND_ALL_BY_TAG = "select * from gift_certificate_tag " +
            "inner join gift_certificate on gift_certificate_tag.certificate = gift_certificate.id " +
            "where gift_certificate_tag.tag=?;";

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        super(TABLE_NAME, jdbcTemplate, BeanPropertyRowMapper.newInstance(GiftCertificate.class),
                new DaoQueryHelperImpl<GiftCertificate>(new GiftCertificateFieldExtractor()));
    }

    @Override
    public List<GiftCertificate> findAllByTag(int tagId) throws DaoException {
        return jdbcTemplate.query(FIND_ALL_BY_TAG, rowMapper, tagId);
    }

    @Override
    public List<GiftCertificate> findAllByPartSearchParam(String part, String nameSearchParam)
            throws DaoException {
        if (!"name".equals(nameSearchParam) && !"description".equals(nameSearchParam)) {
            throw new DaoException("unsupported searching param " + nameSearchParam);
        }
        String query = "select * from gift_certificate where " + nameSearchParam + " like concat('%',?,'%');";
        return jdbcTemplate.query(query, rowMapper, part);
    }

    @Override
    public List<GiftCertificate> findAllSortedByParam(String param, boolean orderDesc) throws DaoException {
        String query = createQueryFindAllSortedByParam(param, orderDesc);
        return jdbcTemplate.query(query, rowMapper);
    }

    private String createQueryFindAllSortedByParam(String param, boolean orderDesc) throws DaoException {
        if (!"name".equals(param) && !"create_date".equals(param)) {
            throw new DaoException("unsupported operation for giftCertificate sorting by " + param);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select * from gift_certificate order by ");
        stringBuilder.append(param);
        if (orderDesc) {
            stringBuilder.append(" desc;");
        } else {
            stringBuilder.append(" asc;");
        }
        return new String(stringBuilder);
    }

}