package com.epam.esm.service.api;

import java.util.List;

/**
 * This interface define methods of business logic save, update, delete, find all
 * and find by id objects type T.
 *
 * @param <T> This is type of object which can used by this interface.
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface Service<T> {

    /**
     * This method is used to save object and then return saved object.
     *
     * @param t This object will be saved.
     * @return This object will be returned with id.
     */
    T save(T t);

    /**
     * This method is used to update object and then return updated object.
     * The object is updated by id and updated only not null fields of object.
     *
     * @param t This object will be updated.
     * @return This object will be returned updated.
     */
    T update(T t);

    /**
     * This method is used to remove object and then return true - if removing was
     * successfully.
     *
     * @param id This is id for deleting object.
     */
    void remove(int id);

    /**
     * This method is used to find all objects.
     *
     * @return List of all founded objects.
     */
    List<T> findAll();

    /**
     * This method is used to find object by id.
     *
     * @param id This is id for finding object.
     * @return This is founded by id object.
     */
    T findById(int id);

}