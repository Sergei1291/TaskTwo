package com.epam.esm.validator;

import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.mapper.GiftCertificateDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GiftCertificateDtoValidatorImpl implements GiftCertificateDtoValidator {

    @Override
    public boolean isValidForSave(GiftCertificateDto giftCertificateDto) {
        return isValidNameForSave(giftCertificateDto) && isValidTagList(giftCertificateDto) &&
                isValidPrice(giftCertificateDto) && isValidDuration(giftCertificateDto);
    }

    @Override
    public boolean isValidForUpdate(GiftCertificateDto giftCertificateDto) {
        return isValidNameForUpdate(giftCertificateDto) && isValidTagList(giftCertificateDto) &&
                isValidPrice(giftCertificateDto) && isValidDuration(giftCertificateDto);
    }

    private boolean isValidNameForSave(GiftCertificateDto giftCertificateDto) {
        String name = giftCertificateDto.getName();
        return (name != null) && (!name.isEmpty());
    }

    private boolean isValidNameForUpdate(GiftCertificateDto giftCertificateDto) {
        String name = giftCertificateDto.getName();
        return (name == null) || (!name.isEmpty());
    }

    private boolean isValidTagList(GiftCertificateDto giftCertificateDto) {
        List<Tag> tagList = giftCertificateDto.getTagList();
        if (tagList == null) {
            return true;
        }
        for (int i = 0; i < tagList.size(); i++) {
            if (tagList.get(i) == null) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidPrice(GiftCertificateDto giftCertificateDto) {
        Integer price = giftCertificateDto.getPrice();
        return (price == null) || (isPositiveNumber(price));
    }

    private boolean isValidDuration(GiftCertificateDto giftCertificateDto) {
        Integer duration = giftCertificateDto.getDuration();
        return (duration == null) || (isPositiveNumber(duration));
    }

    private boolean isPositiveNumber(int number) {
        return number >= 0;
    }

}