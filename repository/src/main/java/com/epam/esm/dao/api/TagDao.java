package com.epam.esm.dao.api;

import com.epam.esm.entity.identifiable.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface extends interface Dao. Interface defines additional methods
 * for finding objects type Tag.
 *
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface TagDao extends Dao<Tag> {

    /**
     * This method is used to find Tag object by name from data warehouse.
     * This method will return empty optional, if data warehouse does not contain
     * object with equal name.
     *
     * @param name This is name of searched tag.
     * @return This is founded object type Tag.
     */
    Optional<Tag> findByName(String name);

    /**
     * This method is used to find all Tag objects by certificate id from
     * data warehouse. This method will return empty list, if data warehouse
     * does not contain Tag objects with equal certificate id.
     *
     * @param certificateId this is id of certificate by which find all certificates.
     * @return List of founded objects type Tag by id certificate.
     */
    List<Tag> findAllByCertificate(int certificateId);

}