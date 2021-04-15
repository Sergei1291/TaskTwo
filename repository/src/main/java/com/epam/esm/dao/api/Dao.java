package com.epam.esm.dao.api;

import com.epam.esm.dao.DaoException;
import com.epam.esm.entity.identifiable.Identifiable;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Identifiable> {

    /**
     * This method is used to save object to warehouse and then get id saved object.
     *
     * @param identifiable
     * @return
     * @throws DaoException
     */
    Integer save(T identifiable) throws DaoException;

    /**
     * This method is used to update object in the warehouse. Update is carried
     * out by object id.
     *
     * @param identifiable
     * @throws DaoException
     */
    void update(T identifiable) throws DaoException;

    /**
     * This method is used to remove object by id from data warehouse.
     *
     * @param identifiable
     * @throws DaoException
     */
    void remove(T identifiable) throws DaoException;

    /**
     * This method is used to find all objects from data warehouse. This method
     * will return empty list, if data warehouse does not contain objects T type.
     *
     * @return
     * @throws DaoException
     */
    List<T> findAll() throws DaoException;

    /**
     * This method is used to find object by id from data warehouse. This method
     * will return empty optional, if data warehouse does not contain object with
     * equal id.
     *
     * @param id
     * @return
     * @throws DaoException
     */
    Optional<T> findById(int id) throws DaoException;

}