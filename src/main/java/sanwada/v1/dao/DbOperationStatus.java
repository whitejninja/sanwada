package sanwada.v1.dao;

public enum DbOperationStatus {

    /*
     * Database operaion was a success
     */
    SUCCESS,

    /*
     * Database operation intended to perform on the database record was not
     * found in the database.
     */
    NO_SUCH_RECORD,

    /*
     * Database operation was a failure. This could be a failure in the database
     * server or network.
     */
    FALIURE,

    /*
     * Database operation was a failure. Duplicate entries for unique fields
     * found in the database.
     */
    DUPLICATE_ENTRY;
}
