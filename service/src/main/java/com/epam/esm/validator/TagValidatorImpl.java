package com.epam.esm.validator;

import com.epam.esm.entity.identifiable.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagValidatorImpl implements TagValidator {

    @Override
    public boolean isValidName(Tag tag) {
        String name = tag.getName();
        return (name != null) && (!name.isEmpty());
    }

}