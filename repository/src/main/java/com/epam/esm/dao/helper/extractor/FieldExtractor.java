package com.epam.esm.dao.helper.extractor;

import com.epam.esm.entity.identifiable.Identifiable;

import java.util.Map;

/**
 * This is interface define method which extract object type T to map.
 *
 * @param <T> This is type of object which can used by this interface.
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface FieldExtractor<T extends Identifiable> {

    /**
     * This method is used to create map from object type T. Keys of map are
     * names of fields of object type T. Values of map are corresponding field
     * values of object type T.
     *
     * @param identifiable This is object for extracting.
     * @return This is map of fields and values from object identifiable.
     */
    Map<String, Object> extract(T identifiable);

}