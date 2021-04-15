package com.epam.esm.dao.helper;

import com.epam.esm.entity.identifiable.Identifiable;

import java.util.Map;

public interface DaoQueryHelper<T extends Identifiable> {

    /**
     * This method is used to create map from object type T. Keys of map are
     * names of fields of object type T without field which has name 'id'.
     * Values of map are corresponding field values of object type T.
     *
     * @param identifiable
     * @return
     */
    Map<String, Object> createMapNameFieldValue(T identifiable);

    /**
     * This method is used to create query PrepareStatement for save object.
     * Fields and values of fields of object saved in Map fields. TableName
     * param defines in which table of data warehouse will be saved object.
     *
     * @param tableName
     * @param fields
     * @return
     */
    String createQuerySave(String tableName, Map<String, Object> fields);

    /**
     * This method is used to create query PrepareStatement for update data about
     * object. Fields and values of fields of object saved in Map fields. TableName
     * param defines in which table of data warehouse contains updating object.
     *
     * @param tableName
     * @param fields
     * @return
     */
    String createQueryUpdate(String tableName, Map<String, Object> fields);

    /**
     * This method is used to create array of object. Each of object keep value
     * which will be set up in PrepareStatement query. Param 'id' of identifiable object
     * will set in the end of the array.
     * This method is used in a couple with methods of this class: createQueryUpdate.
     *
     * @param mapNameFiledValue
     * @param identifiable
     * @return
     */
    Object[] createParamsQueryUpdate(Map<String, Object> mapNameFiledValue, T identifiable);

}