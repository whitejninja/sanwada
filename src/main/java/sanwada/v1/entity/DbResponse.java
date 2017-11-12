package sanwada.v1.entity;

import sanwada.v1.dao.DbOperationStatus;

public class DbResponse {

    private DbOperationStatus status;
    private Object responseObj;

    public DbResponse(DbOperationStatus status, Object responseObj) {
        this.status = status;
        this.responseObj = responseObj;
    }

    public DbOperationStatus getStatus() {
        return status;
    }

    public void setStatus(DbOperationStatus status) {
        this.status = status;
    }

    public Object getPost() {
        return responseObj;
    }

    public void setNewUser(Post post) {
        this.responseObj = post;
    }

}
