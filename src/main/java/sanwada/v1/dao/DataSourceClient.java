package sanwada.v1.dao;

import java.util.LinkedHashMap;

/**
 * Represents the operational contract between a DataSource and the client who
 * interacts with it.
 */
public interface DataSourceClient<O> {

  /**
   * @return object should represent a connection to a datasource
   */
  static Object getClient() {
    return new Object();
  }

  /**
   * @return object represent the database in the datasource which the
   *         DataSourceClient interacts with
   */
  Object getDatabase();

  /**
   * @return object represent the table/collection DataSourceClient interacts with
   */
  Object getCollection();

  /**
   *
   * @param key
   * @param value
   * @return list return list of object that satisfy filter query
   */
  Iterable<O> find(LinkedHashMap<String, Object> filters);

  /**
   * Inserts object to datasource
   * 
   * @param object
   */
  void insert(Object object);

  /**
   * Update objects that match filter with provided values
   * 
   * @param filter
   * @param values
   * @return
   */
  boolean update(LinkedHashMap<String, Object> filters, Object newObject);

  boolean delete(LinkedHashMap<String, Object> filters);
}
