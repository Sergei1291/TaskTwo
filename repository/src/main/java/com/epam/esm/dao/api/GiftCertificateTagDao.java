package com.epam.esm.dao.api;

import com.epam.esm.dao.DaoException;

public interface GiftCertificateTagDao {

    /**
     * This method is used to save map of id for Certificate and Tag object
     * to warehouse.
     *
     * @param certificateId
     * @param tagId
     * @throws DaoException
     */
    void save(int certificateId, int tagId) throws DaoException;

    /**
     * This method is used to delete map of id for Certificate object.
     *
     * @param certificateId
     * @throws DaoException
     */
    void deleteCertificate(int certificateId) throws DaoException;

    /**
     * This method is used to delete map of id for Tag object.
     *
     * @param tagId
     * @throws DaoException
     */
    void deleteTag(int tagId) throws DaoException;

}