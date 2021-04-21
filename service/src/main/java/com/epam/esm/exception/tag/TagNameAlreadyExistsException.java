package com.epam.esm.exception.tag;

import org.springframework.dao.DuplicateKeyException;

public class TagNameAlreadyExistsException extends DuplicateKeyException {

    public TagNameAlreadyExistsException(String message) {
        super(message);
    }

}