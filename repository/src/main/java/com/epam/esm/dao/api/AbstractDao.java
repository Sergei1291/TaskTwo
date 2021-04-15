package com.epam.esm.dao.api;

import com.epam.esm.dao.DaoException;
import com.epam.esm.dao.helper.DaoQueryHelper;
import com.epam.esm.entity.identifiable.Identifiable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDao<T extends Identifiable> implements Dao<T> {

    protected String tableName;
    protected JdbcTemplate jdbcTemplate;
    protected RowMapper<T> rowMapper;
    private DaoQueryHelper<T> daoQueryHelper;

    public AbstractDao(String tableName, JdbcTemplate jdbcTemplate, RowMapper<T> rowMapper,
                       DaoQueryHelper<T> daoQueryHelper) {
        this.tableName = tableName;
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.daoQueryHelper = daoQueryHelper;
    }

    @Override
    public Integer save(T identifiable) throws DaoException {
        Map<String, Object> mapNameFiledValue = daoQueryHelper.createMapNameFieldValue(identifiable);
        String query = daoQueryHelper.createQuerySave(tableName, mapNameFiledValue);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate =
                new NamedParameterJdbcTemplate(jdbcTemplate);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(query,
                new BeanPropertySqlParameterSource(identifiable), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(T identifiable) throws DaoException {
        Map<String, Object> mapNameFiledValue = daoQueryHelper.createMapNameFieldValue(identifiable);
        String query = daoQueryHelper.createQueryUpdate(tableName, mapNameFiledValue);
        Object[] params = daoQueryHelper.createParamsQueryUpdate(mapNameFiledValue, identifiable);
        jdbcTemplate.update(query, params);
    }

    @Override
    public void remove(T identifiable) throws DaoException {
        String query = String.format("delete from %s where id=?;", tableName);
        int id = identifiable.getId();
        jdbcTemplate.update(query, id);
    }

    @Override
    public List<T> findAll() throws DaoException {
        String query = String.format("select * from %s;", tableName);
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public Optional<T> findById(int id) throws DaoException {
        String query = String.format("select * from %s where id = ?;", tableName);
        List<T> foundedObjectList = jdbcTemplate.query(query, rowMapper, id);
        if (foundedObjectList.isEmpty()) {
            return Optional.empty();
        }
        T foundedObject = foundedObjectList.get(0);
        return Optional.of(foundedObject);
    }

}