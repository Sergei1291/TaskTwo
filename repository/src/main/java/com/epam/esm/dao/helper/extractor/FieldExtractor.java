package com.epam.esm.dao.helper.extractor;

import com.epam.esm.entity.identifiable.Identifiable;

import java.util.Map;

public interface FieldExtractor<T extends Identifiable> {

    /**
     * This method is used to create map from object type T. Keys of map are
     * names of fields of object type T. Values of map are corresponding field
     * values of object type T.
     *
     * @param identifiable
     * @return
     */
    Map<String, Object> extract(T identifiable);

}