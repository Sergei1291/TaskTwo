package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.identifiable.GiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateDtoValidator {

    public boolean isValid(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto == null) {
            return false;
        }
        GiftCertificate giftCertificate = giftCertificateDto.getGiftCertificate();
        if (giftCertificate == null) {
            return false;
        }
        return isValidPrice(giftCertificate) && isValidDuration(giftCertificate);
    }

    private boolean isValidPrice(GiftCertificate giftCertificate) {
        Integer price = giftCertificate.getPrice();
        return (price == null) || (isPositiveNumber(price));
    }

    private boolean isValidDuration(GiftCertificate giftCertificate) {
        Integer duration = giftCertificate.getDuration();
        return (duration == null) || (isPositiveNumber(duration));
    }

    private boolean isPositiveNumber(int number) {
        return number >= 0;
    }

}