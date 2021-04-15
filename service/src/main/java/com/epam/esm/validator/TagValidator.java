package com.epam.esm.validator;

import com.epam.esm.entity.identifiable.Tag;

/**
 * This interface define method for validating fields of Tag.
 *
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface TagValidator {

    /**
     * This is method is used for validating name of Tag.
     *
     * @param tag This is Tag for validating.
     * @return True or false for requirement of validating.
     */
    boolean isValidName(Tag tag);

}