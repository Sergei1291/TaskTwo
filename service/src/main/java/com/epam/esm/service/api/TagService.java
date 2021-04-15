package com.epam.esm.service.api;

import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.service.ServiceException;

import java.util.List;
import java.util.Optional;

public interface TagService extends Service<Tag> {

    /**
     * This method is used to find Tag object by name. This method will return
     * empty optional, if Tag won't be founded.
     *
     * @param name
     * @return
     * @throws ServiceException
     */
    Optional<Tag> findByName(String name) throws ServiceException;

    /**
     * This method is used to find all Tag objects by certificate id.
     *
     * @param certificateId
     * @return
     * @throws ServiceException
     */
    List<Tag> findAllByCertificate(int certificateId) throws ServiceException;

}