package com.epam.esm.validator;

import com.epam.esm.entity.identifiable.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TagValidatorImplTest {

    private final TagValidator tagValidator = new TagValidatorImpl();

    @Test
    public void testIsValidNameShouldTrueWhenNameNotNullNotEmpty() {
        Tag tag = new Tag(0, "notNullNotEmpty");
        boolean actual = tagValidator.isValidName(tag);
        Assertions.assertTrue(actual);
    }

    @Test
    public void testIsValidNameShouldFalseWhenNameNull() {
        Tag tag = new Tag(0, null);
        boolean actual = tagValidator.isValidName(tag);
        Assertions.assertFalse(actual);
    }

    @Test
    public void testIsValidNameShouldFalseWhenNameEmpty() {
        Tag tag = new Tag(0, "");
        boolean actual = tagValidator.isValidName(tag);
        Assertions.assertFalse(actual);
    }

}