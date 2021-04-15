package com.epam.esm.service.api;

import com.epam.esm.mapper.GiftCertificateDto;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * This interface define additional methods for business logic on GiftCertificateDto.
 *
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface GiftCertificateDtoService extends Service<GiftCertificateDto> {

    /**
     * This method is used to find all GiftCertificateDto objects by tag name.
     *
     * @param tagName This is tag's name for searching.
     * @return List of founded objects by tag name.
     */
    List<GiftCertificateDto> findAllByTagName(String tagName);

    /**
     * This method is used to find list of all GiftCertificateDto objects by
     * part of nameSearchParam.
     *
     * @param searchString    This is search string.
     * @param searchParamName This is name search param.
     * @return List of founded objects by search string.
     */
    List<GiftCertificateDto> findAllBySearchStringSearchParamName(String searchString,
                                                                  String searchParamName);

    /**
     * This method is used to find sorted by param list of all GiftCertificateDto
     * objects. Order by sorting define param orderDesc.
     *
     * @param param     This is name param for sorting.
     * @param orderDesc This is direction for sorting.
     * @return List sorted objects.
     */
    List<GiftCertificateDto> findAllSortedByParam(String param, boolean orderDesc);

    /**
     * This method is used to find list of all GiftCertificateDto objects
     * by several params: tag's name, part of searched param. Also list of
     * objects can be sorted by nameSortParam by order equal param orderDesc.
     * Some of params of method can be absent.
     *
     * @param tagName         This is tag's name for searching.
     * @param searchString    This is search string.
     * @param searchParamName This is name search param.
     * @param sortParamName   This is name param for sorting.
     * @param orderDesc       This is direction for sorting.
     * @return List of founded and sorted objects.
     */
    List<GiftCertificateDto> search(@Nullable String tagName,
                                    @Nullable String searchString,
                                    @Nullable String searchParamName,
                                    @Nullable String sortParamName,
                                    @Nullable boolean orderDesc);

}