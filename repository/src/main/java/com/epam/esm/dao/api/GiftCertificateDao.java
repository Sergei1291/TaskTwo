package com.epam.esm.dao.api;

import com.epam.esm.dao.DaoException;
import com.epam.esm.entity.identifiable.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao extends Dao<GiftCertificate> {

    /**
     * This method is used to find all GiftCertificate objects by tag id from
     * data warehouse. This method will return empty list, if data warehouse
     * does not contain GiftCertificate objects with equal tag id.
     *
     * @param tagId
     * @return
     * @throws DaoException
     */
    List<GiftCertificate> findAllByTag(int tagId) throws DaoException;

    /**
     * This method is used to find all GiftCertificate objects by part of searching
     * param from data warehouse. This method will return empty list, if data warehouse
     * does not contain GiftCertificate objects with equal part of searching param.
     *
     * @param part
     * @param searchParam
     * @return
     * @throws DaoException
     */
    List<GiftCertificate> findAllByPartSearchParam(String part, String searchParam)
            throws DaoException;

    /**
     * This method is used to find all objects sorted by param from data warehouse.
     * Order of sorting define by orderDesc param.
     *
     * @param param
     * @param orderDesc
     * @return
     * @throws DaoException
     */
    List<GiftCertificate> findAllSortedByParam(String param, boolean orderDesc)
            throws DaoException;

}