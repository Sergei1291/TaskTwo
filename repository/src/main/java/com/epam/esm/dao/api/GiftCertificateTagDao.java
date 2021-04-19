package com.epam.esm.dao.api;

/**
 * The interface extends interface Dao. Interface defines additional methods
 * for saving and deleting map between GiftCertificate and Tag objects.
 *
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface GiftCertificateTagDao {

    /**
     * This method is used to save map of id for Certificate and Tag object
     * to data source.
     *
     * @param certificateId This is certificate id.
     * @param tagId         This is tag's id.
     */
    void save(int certificateId, int tagId);

    /**
     * This method is used to delete map of id for Certificate object.
     *
     * @param certificateId This is id certificate.
     */
    void deleteCertificate(int certificateId);

    /**
     * This method is used to delete map of id for Tag object.
     *
     * @param tagId This is id tag.
     */
    void deleteTag(int tagId);

}