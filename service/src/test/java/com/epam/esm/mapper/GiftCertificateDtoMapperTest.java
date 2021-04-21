package com.epam.esm.mapper;

import com.epam.esm.entity.identifiable.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class GiftCertificateDtoMapperTest {

    private final static GiftCertificateDto GIFT_CERTIFICATE_DTO =
            new GiftCertificateDto(1, "name", "description", 2, 3, "createDate", "updateDate", null);
    private final static GiftCertificate GIFT_CERTIFICATE =
            new GiftCertificate(1, "name", "description", 2, 3, "createDate", "updateDate");

    private final GiftCertificateDtoMapper mapper =
            Mappers.getMapper(GiftCertificateDtoMapper.class);

    @Test
    public void testDtoToEntity() {
        GiftCertificate actual = mapper.dtoToEntity(GIFT_CERTIFICATE_DTO);
        Assertions.assertEquals(GIFT_CERTIFICATE, actual);
    }

    @Test
    public void testEntityToDto() {
        GiftCertificateDto actual = mapper.entityToDto(GIFT_CERTIFICATE);
        Assertions.assertEquals(GIFT_CERTIFICATE_DTO, actual);
    }

}