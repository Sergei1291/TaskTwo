package com.epam.esm.service.api;

import com.epam.esm.service.ServiceException;

import java.util.List;
import java.util.Optional;

public interface Service<T> {

    /**
     * This method is used to save object and then return saved object.
     *
     * @param t
     * @return
     * @throws ServiceException
     */
    T save(T t) throws ServiceException;

    /**
     * This method is used to update object and then return updated object.
     * The object is updated by id and updated only not null fields of object.
     *
     * @param t
     * @return
     * @throws ServiceException
     */
    T update(T t) throws ServiceException;

    /**
     * This method is used to remove object and then return true - if removing was
     * successfully.
     *
     * @param t
     * @return
     * @throws ServiceException
     */
    boolean remove(T t) throws ServiceException;

    /**
     * This method is used to find all objects.
     *
     * @return
     * @throws ServiceException
     */
    List<T> findAll() throws ServiceException;

    /**
     * This method is used to find object by id.
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    Optional<T> findById(int id) throws ServiceException;

}