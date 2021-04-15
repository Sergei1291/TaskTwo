package com.epam.esm.dao.helper;

/**
 * This interface defines helper methods for making request for finding data
 * in data source.
 *
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface GiftCertificateDaoQueryHelper {

    /**
     * This is method is used to create query for finding all GiftCertificates
     * from data source sorted by param.
     *
     * @param param     This is name of param sorting.
     * @param orderDesc This is direction of sorting.
     * @return This is result query for finding all sorted certificates.
     */
    String createQueryFindAllSortedByParam(String param, boolean orderDesc);

}