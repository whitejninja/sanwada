package sanwada.v1.dao;

import java.util.LinkedHashMap;

import sanwada.v1.entity.DatabaseCollection;

/**
 * Represents the operational contract between a DataSource and the client who
 * interacts with it.
 */
public interface DataSourceClient<O> {

  /**
   * Initialize a connection to the data source
   */
  void initializeConnection();

  /**
   * @return object should represent a connection to a data source
   */
  static Object getClient() {
    return new Object();
  }

  /**
   * @return object represent the database in the data source which the
   *         DataSourceClient interacts with
   */
  Object getDatabase();

  /**
   * Set the name of collection client should work on
   * 
   * @param collection
   *          Collection name
   */
  void setCollection(DatabaseCollection collection);

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
   * Inserts object to data source
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

  /**
   * Delete objects that match filter with provided values
   * 
   * @param filters
   * @return
   */
  boolean delete(LinkedHashMap<String, Object> filters);
}
