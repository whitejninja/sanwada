package sanwada.v1.entity;

import sanwada.v1.dao.DbOperationStatus;

public class DbResponse{

  private DbOperationStatus status;
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
