package com.epam.esm.validator;

import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.mapper.GiftCertificateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class GiftCertificateDtoValidatorImplTest {

    private final static GiftCertificateDto VALID_FOR_SAVE_GIFT_CERTIFICATE_DTO =
            new GiftCertificateDto(1,
                    "name",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
    private final static GiftCertificateDto VALID_FOR_UPDATE_GIFT_CERTIFICATE_DTO =
            new GiftCertificateDto(1,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);

    private final GiftCertificateDtoValidator validator = new GiftCertificateDtoValidatorImpl();

    @Test
    public void testIsValidForSaveShouldTrueWhenGiftCertificateValid() {
        boolean actual = validator.isValidForSave(VALID_FOR_SAVE_GIFT_CERTIFICATE_DTO);
        Assertions.assertTrue(actual);
    }

    @Test
    public void testIsValidForSaveShouldFalseWhenNameNull() {
        GiftCertificateDto GIFT_CERTIFICATE_DTO =
                new GiftCertificateDto(1,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        boolean actual = validator.isValidForSave(GIFT_CERTIFICATE_DTO);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testIsValidForSaveShouldFalseWhenNameEmpty() {
        GiftCertificateDto GIFT_CERTIFICATE_DTO =
                new GiftCertificateDto(1,
                        "",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        boolean actual = validator.isValidForSave(GIFT_CERTIFICATE_DTO);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testIsValidForSaveShouldFalseWhenTagListContainsNull() {
        GiftCertificateDto GIFT_CERTIFICATE_DTO =
                new GiftCertificateDto(1,
                        "name",
                        null,
                        null,
                        null,
                        null,
                        null,
                        Arrays.asList(new Tag(), new Tag(), null));
        boolean actual = validator.isValidForSave(GIFT_CERTIFICATE_DTO);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testIsValidForSaveShouldFalseWhenPriceNegative() {
        GiftCertificateDto GIFT_CERTIFICATE_DTO =
                new GiftCertificateDto(1,
                        "name",
                        null,
                        -1,
                        null,
                        null,
                        null,
                        null);
        boolean actual = validator.isValidForSave(GIFT_CERTIFICATE_DTO);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testIsValidForSaveShouldFalseWhenDurationNegative() {
        GiftCertificateDto GIFT_CERTIFICATE_DTO =
                new GiftCertificateDto(1,
                        "name",
                        null,
                        null,
                        -1,
                        null,
                        null,
                        null);
        boolean actual = validator.isValidForSave(GIFT_CERTIFICATE_DTO);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testIsValidForUpdateShouldTrueWhenGiftCertificateValid() {
        boolean actual = validator.isValidForUpdate(VALID_FOR_UPDATE_GIFT_CERTIFICATE_DTO);
        Assertions.assertTrue(actual);
    }

    @Test
    public void testIsValidForUpdateShouldFalseWhenNameEmpty() {
        GiftCertificateDto GIFT_CERTIFICATE_DTO =
                new GiftCertificateDto(1,
                        "",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        boolean actual = validator.isValidForUpdate(GIFT_CERTIFICATE_DTO);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testIsValidForUpdateShouldFalseWhenTagListContainsNull() {
        GiftCertificateDto GIFT_CERTIFICATE_DTO =
                new GiftCertificateDto(1,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Arrays.asList(new Tag(), new Tag(), null));
        boolean actual = validator.isValidForUpdate(GIFT_CERTIFICATE_DTO);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testIsValidForUpdateShouldFalseWhenPriceNegative() {
        GiftCertificateDto GIFT_CERTIFICATE_DTO =
                new GiftCertificateDto(1,
                        null,
                        null,
                        -1,
                        null,
                        null,
                        null,
                        null);
        boolean actual = validator.isValidForUpdate(GIFT_CERTIFICATE_DTO);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testIsValidForUpdateShouldFalseWhenDurationNegative() {
        GiftCertificateDto GIFT_CERTIFICATE_DTO =
                new GiftCertificateDto(1,
                        "name",
                        null,
                        null,
                        -1,
                        null,
                        null,
                        null);
        boolean actual = validator.isValidForUpdate(GIFT_CERTIFICATE_DTO);
        Assertions.assertFalse(actual);
    }

}