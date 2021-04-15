package com.epam.esm.exception.tag;

import org.springframework.dao.DataIntegrityViolationException;

public class TagNameNotValidException extends DataIntegrityViolationException {

    public TagNameNotValidException(String message) {
        super(message);
    }

}