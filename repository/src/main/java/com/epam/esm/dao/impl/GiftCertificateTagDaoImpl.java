package com.epam.esm.dao.impl;

import com.epam.esm.dao.api.GiftCertificateTagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GiftCertificateTagDaoImpl implements GiftCertificateTagDao {

    private final static String SAVE_QUERY =
            "insert into gift_certificate_tag (certificate, tag) values (?,?);";
    private final static String DELETE_CERTIFICATE =
            "delete from gift_certificate_tag where certificate=?;";
    private final static String DELETE_TAG = "delete from gift_certificate_tag where tag=?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateTagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(int certificateId, int tagId) {
        jdbcTemplate.update(SAVE_QUERY, certificateId, tagId);
    }

    @Override
    public void deleteCertificate(int certificateId) {
        jdbcTemplate.update(DELETE_CERTIFICATE, certificateId);
    }

    @Override
    public void deleteTag(int tagId) {
        jdbcTemplate.update(DELETE_TAG, tagId);
    }

}