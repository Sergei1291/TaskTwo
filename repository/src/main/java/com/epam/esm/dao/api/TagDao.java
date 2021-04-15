package com.epam.esm.dao.api;

import com.epam.esm.dao.DaoException;
import com.epam.esm.entity.identifiable.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends Dao<Tag> {

    /**
     * This method is used to find Tag object by name from data warehouse.
     * This method will return empty optional, if data warehouse does not contain
     * object with equal name.
     *
     * @param name
     * @return
     * @throws DaoException
     */
    Optional<Tag> findByName(String name) throws DaoException;

    /**
     * This method is used to find all Tag objects by certificate id from
     * data warehouse. This method will return empty list, if data warehouse
     * does not contain Tag objects with equal certificate id.
     *
     * @param certificateId
     * @return
     * @throws DaoException
     */
    List<Tag> findAllByCertificate(int certificateId) throws DaoException;

}