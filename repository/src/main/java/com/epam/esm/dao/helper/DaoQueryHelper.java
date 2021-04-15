package com.epam.esm.dao.helper;

import com.epam.esm.entity.identifiable.Identifiable;

import java.util.Map;

/**
 * This interface defines helper methods for making requests for save and
 * update data in data source.
 *
 * @param <T> This is type of object which can used by this interface.
 * @author Siarhei Katuzhenets
 * @since 19-04-2021
 */
public interface DaoQueryHelper<T extends Identifiable> {

    /**
     * This method is used to create map from object type T. Keys of map are
     * names of fields of object type T without field which has name 'id'.
     * Values of map are corresponding field values of object type T.
     *
     * @param identifiable This is object which will be map to result.
     * @return This is result map, which contain key - name field of identifiable
     * object, and value - value filed.
     */
    Map<String, Object> createMapNameFieldValue(T identifiable);

    /**
     * This method is used to create query PrepareStatement for save object.
     * Fields and values of fields of object saved in Map fields. TableName
     * param defines in which table of data warehouse will be saved object.
     *
     * @param tableName This is name of table.
     * @param fields    This is map names field and values of fields.
     * @return Result query for saving object in data source.
     */
    String createQuerySave(String tableName, Map<String, Object> fields);

    /**
     * This method is used to create query PrepareStatement for update data about
     * object. Fields and values of fields of object saved in Map fields. TableName
     * param defines in which table of data warehouse contains updating object.
     *
     * @param tableName This is name of table.
     * @param fields    This is map names field and values of fields.
     * @return Result query for updating object in data source.
     */
    String createQueryUpdate(String tableName, Map<String, Object> fields);

    /**
     * This method is used to create array of object. Each of object keep value
     * which will be set up in PrepareStatement query. Param 'id' of identifiable object
     * will set in the end of the array.
     * This method is used in a couple with methods of this class: createQueryUpdate.
     *
     * @param mapNameFiledValue This is map names field and values of fields.
     * @param identifiable      This is object from which was get map of name field
     *                          and values.
     * @return Array of object according to map field and values.
     */
    Object[] createParamsQueryUpdate(Map<String, Object> mapNameFiledValue, T identifiable);

}