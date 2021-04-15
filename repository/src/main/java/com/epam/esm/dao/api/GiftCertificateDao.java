package com.epam.esm.dao.api;

import com.epam.esm.entity.identifiable.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * The interface extends interface Dao. Interface defines additional methods
 * for finding and sorting objects type GiftCertificate.
 *
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface GiftCertificateDao extends Dao<GiftCertificate> {

    /**
     * This method is used to find all GiftCertificate objects by tag id from
     * data warehouse. This method will return empty list, if data warehouse
     * does not contain GiftCertificate objects with equal tag id.
     *
     * @param tagId This is id of the tag by which the certificate is searched.
     * @return List of searched certificates which contains tag with id.
     */
    List<GiftCertificate> findAllByTag(int tagId);

    /**
     * This method is used to find all GiftCertificate objects by part of searching
     * param from data source. This method will return empty list, if data source
     * does not contain GiftCertificate objects with equal part of searching param.
     *
     * @param searchString    This is search string of search param.
     * @param searchParamName This is name of search param by which certificates
     *                        are searched.
     * @return List of founded certificates.
     */
    List<GiftCertificate> findAllBySearchStringSearchParamName(String searchString,
                                                               String searchParamName);

    /**
     * This method is used to find all objects sorted by param from data warehouse.
     * Order of sorting define by orderDesc param.
     *
     * @param param     This is name of sorting param.
     * @param orderDesc This is object's sorting order in the result list of objects.
     * @return List of founded all GiftCertificates and sorted by param name.
     */
    List<GiftCertificate> findAllSortedByParam(String param, boolean orderDesc);

    /**
     * This method is used to find Gift Certificate from data source by name.
     *
     * @param name This is name of searched certificate.
     * @return This is founded gift certificate from data source.
     */
    Optional<GiftCertificate> findByName(String name);

}