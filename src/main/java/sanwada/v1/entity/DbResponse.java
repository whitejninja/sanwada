package sanwada.v1.entity;

import sanwada.v1.dao.DbOperationStatus;

/**
 * Data transfer object between REST and data layers
 */
public class DbResponse{

  /**
   * Status of the database operation executed.
   */
  private DbOperationStatus status;

  /**
   * Result after the database operation.
   * In the case of a put operation on database, result will be the modified object.
   * In the case of a get operation on database result will be object retrieved.
   */
  private Object result;

  public DbResponse(DbOperationStatus status, Object result) {
    this.status = status;
    this.result = result;
  }

  public DbOperationStatus getStatus() {
    return status;
  }

  public void setStatus(DbOperationStatus status) {
    this.status = status;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }

}