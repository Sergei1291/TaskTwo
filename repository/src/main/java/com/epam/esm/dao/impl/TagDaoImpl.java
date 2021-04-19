package com.epam.esm.dao.impl;

import com.epam.esm.dao.api.AbstractDao;
import com.epam.esm.dao.api.TagDao;
import com.epam.esm.dao.helper.DaoQueryHelperImpl;
import com.epam.esm.dao.helper.extractor.impl.TagFieldExtractor;
import com.epam.esm.entity.identifiable.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {

    private final static String TABLE_NAME = "tag";
    private final static String FIND_BY_NAME = "select * from tag where name=?;";
    private final static String FIND_ALL_BY_CERTIFICATE = "select tag.id, tag.name " +
            "from gift_certificate_tag inner join tag on gift_certificate_tag.tag=tag.id " +
            "where gift_certificate_tag.certificate=?;";

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        super(TABLE_NAME, jdbcTemplate, BeanPropertyRowMapper.newInstance(Tag.class),
                new DaoQueryHelperImpl<Tag>(new TagFieldExtractor()));
    }

    @Override
    public void update(Tag identifiable) {
        throw new UnsupportedOperationException("this operation not supported for tags");
    }

    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> tagList = jdbcTemplate.query(FIND_BY_NAME, rowMapper, name);
        if (tagList.isEmpty()) {
            return Optional.empty();
        }
        Tag tag = tagList.get(0);
        return Optional.of(tag);
    }

    @Override
    public List<Tag> findAllByCertificate(int certificateId) {
        return jdbcTemplate.query(FIND_ALL_BY_CERTIFICATE, rowMapper, certificateId);
    }

}