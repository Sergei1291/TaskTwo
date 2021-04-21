package com.epam.esm.validator;

import com.epam.esm.mapper.GiftCertificateDto;

/**
 * This interface define method for validating fields of GiftCertificate.
 *
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface GiftCertificateDtoValidator {

    /**
     * This is method is used for validating fields for save GiftCertificateDto
     * method of business logic.
     *
     * @param giftCertificateDto This is giftCertificateDto for validating.
     * @return True or false for requirement of validating.
     */
    boolean isValidForSave(GiftCertificateDto giftCertificateDto);

    /**
     * This is method is used for validating fields for update GiftCertificateDto
     * method of business logic.
     *
     * @param giftCertificateDto This is giftCertificateDto for validating.
     * @return True or false for requirement of validating.
     */
    boolean isValidForUpdate(GiftCertificateDto giftCertificateDto);

}