package com.epam.esm.mapper;

import com.epam.esm.entity.identifiable.GiftCertificate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GiftCertificateDtoMapper {

    GiftCertificate dtoToEntity(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto entityToDto(GiftCertificate giftCertificate);

}