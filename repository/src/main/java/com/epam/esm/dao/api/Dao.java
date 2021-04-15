package com.epam.esm.dao.api;

import com.epam.esm.entity.identifiable.Identifiable;

import java.util.List;
import java.util.Optional;

/**
 * The DAO interface defines methods for creating, updating, deleting, and searching
 * for objects in a data source
 *
 * @param <T> This is type of object which can used by this interface.
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface Dao<T extends Identifiable> {

    /**
     * This method is used to save object to data source and then get id saved object.
     *
     * @param identifiable This is object will be saved in data source.
     * @return This is id of saved object identifiable.
     */
    Integer save(T identifiable);

    /**
     * This method is used to update object in the data source. Update is carried
     * out by object id.
     *
     * @param identifiable This is object will be updated in data source.
     */
    void update(T identifiable);

    /**
     * This method is used to remove object by id from data source.
     *
     * @param identifiable This is object will be deleted from data source.
     */
    void remove(T identifiable);

    /**
     * This method is used to find all objects from data source. This method
     * will return empty list, if data source does not contain objects T type.
     *
     * @return These are founded object's list type T from data source.
     */
    List<T> findAll();

    /**
     * This method is used to find object by id from data source. This method
     * will return empty optional, if data source does not contain object with
     * equal id.
     *
     * @param id This is id for searching object from data source.
     * @return This is founded optional object type T from data source.
     */
    Optional<T> findById(int id);

}