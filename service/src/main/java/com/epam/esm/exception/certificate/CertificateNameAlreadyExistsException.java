package com.epam.esm.exception.certificate;

import org.springframework.dao.DuplicateKeyException;

public class CertificateNameAlreadyExistsException extends DuplicateKeyException {

    public CertificateNameAlreadyExistsException(String message) {
        super(message);
    }

}