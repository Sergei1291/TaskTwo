package com.epam.esm.service.api;

import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.service.ServiceException;
import org.springframework.lang.Nullable;

import java.util.List;

public interface GiftCertificateDtoService extends Service<GiftCertificateDto> {

    /**
     * This method is used to find all GiftCertificateDto objects by tag name.
     *
     * @param tagName
     * @return
     * @throws ServiceException
     */
    List<GiftCertificateDto> findAllByTagName(String tagName) throws ServiceException;

    /**
     * This method is used to find list of all GiftCertificateDto objects by
     * part of nameSearchParam.
     *
     * @param part
     * @param nameSearchParam
     * @return
     * @throws ServiceException
     */
    List<GiftCertificateDto> findAllByPartSearchParam(String part, String nameSearchParam)
            throws ServiceException;

    /**
     * This method is used to find sorted by param list of all GiftCertificateDto
     * objects. Order by sorting define param orderDesc.
     *
     * @param param
     * @param orderDesc
     * @return
     * @throws ServiceException
     */
    List<GiftCertificateDto> findAllSortedByParam(String param, boolean orderDesc)
            throws ServiceException;

    /**
     * This method is used to find list of all GiftCertificateDto objects
     * by several params: tag's name, part of searched param. Also list of
     * objects can be sorted by nameSortParam by order equal param orderDesc.
     * Some of params of method can be absent.
     *
     * @param tagName
     * @param searchParam
     * @param nameSearchParam
     * @param nameSortParam
     * @param orderDesc
     * @return
     * @throws ServiceException
     */
    List<GiftCertificateDto> search(@Nullable String tagName,
                                    @Nullable String searchParam,
                                    @Nullable String nameSearchParam,
                                    @Nullable String nameSortParam,
                                    @Nullable boolean orderDesc) throws ServiceException;

}