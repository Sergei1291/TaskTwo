package com.epam.esm.dao.impl;

import com.epam.esm.config.RepositoryConfig;
import com.epam.esm.dao.DaoException;
import com.epam.esm.entity.identifiable.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TagDaoImplTest {

    private TagDaoImpl tagDaoImpl;
    private TestInnerDatabaseConfig testInnerDatabaseConfig;

    @BeforeEach
    void initialize() throws SQLException {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(RepositoryConfig.class);
        JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
        tagDaoImpl = new TagDaoImpl(jdbcTemplate);
        testInnerDatabaseConfig = new TestInnerDatabaseConfig(jdbcTemplate);
        testInnerDatabaseConfig.initializeDatabase();
    }

    @AfterEach
    void destroy() {
        testInnerDatabaseConfig.destroyDatabase();
    }

    @Test
    public void testSaveShouldSaveTagInDatabaseAndReturnCreatedId() throws DaoException {
        Tag tag = new Tag("newTag");
        Integer actualId = tagDaoImpl.save(tag);
        Assertions.assertEquals(Integer.valueOf(4), actualId);
        Optional<Tag> optionalTag = tagDaoImpl.findById(4);
        Assertions.assertEquals(Optional.of(new Tag(4, "newTag")), optionalTag);
    }

    @Test
    public void testUpdateShouldTrowUnsupportedOperationException() throws DaoException {
        Tag tag = new Tag(1, "asa");
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            tagDaoImpl.update(tag);
        });
    }

    @Test
    public void testRemoveShouldRemoveTagFromDatabase() throws DaoException {
        Tag tag = new Tag(2, "bsb");
        tagDaoImpl.remove(tag);
        List<Tag> tagList = tagDaoImpl.findAll();
        int sizeTagList = tagList.size();
        Assertions.assertEquals(2, sizeTagList);
    }

    @Test
    public void testFindAllShouldReturnDatabaseListTag() throws DaoException {
        List<Tag> tagList = tagDaoImpl.findAll();
        List<Tag> expected = Arrays.asList(
                new Tag(1, "asa"),
                new Tag(2, "bsb"),
                new Tag(3, "csc"));
        Assertions.assertEquals(expected, tagList);
    }

    @Test
    public void testFindByIdShouldReturnOptionalTagWhenDatabaseContainTagId() throws DaoException {
        Optional<Tag> optionalTag = tagDaoImpl.findById(2);
        Assertions.assertEquals(Optional.of(new Tag(2, "bsb")), optionalTag);
    }

    @Test
    public void testFindByIdShouldReturnOptionalEmptyWhenDatabaseNotContainTagId() throws DaoException {
        Optional<Tag> optionalTag = tagDaoImpl.findById(4);
        Assertions.assertEquals(Optional.empty(), optionalTag);
    }

    @Test
    public void testFindByNameShouldReturnOptionalTagWhenDatabaseContainTagName() throws DaoException {
        Optional<Tag> actual = tagDaoImpl.findByName("asa");
        Assertions.assertEquals(Optional.of(new Tag(1, "asa")), actual);
    }

    @Test
    public void testFindByNameShouldReturnOptionalEmptyWhenDatabaseNotContainTagName() throws DaoException {
        Optional<Tag> actual = tagDaoImpl.findByName("not have name");
        Assertions.assertEquals(Optional.empty(), actual);
    }

    @Test
    public void testFindAllByCertificateShouldReturnListTagForCertificate() throws DaoException {
        List<Tag> actual = tagDaoImpl.findAllByCertificate(4);
        List<Tag> expected = Arrays.asList(
                new Tag(1, "asa"),
                new Tag(2, "bsb"),
                new Tag(3, "csc"));
        Assertions.assertEquals(expected, actual);
    }

}