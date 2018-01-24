package sanwada.v1.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadConcern;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MongoDataSourceClient implements DataSourceClient<Document> {

  private String host;
  private int port;
  private static MongoClient mClient;

  public MongoDataSourceClient() {
    this("localhost", 27017);
  }

  public MongoDataSourceClient(String host, int port) {
    this.host = host;
    this.port = port;
    initializeConnection();
  }

  public void initializeConnection(){
    // Sets options for mongo client object
    MongoClientOptions.Builder clientOptions = new MongoClientOptions.Builder();

    // Deafult read concern of the server is used
    clientOptions.readConcern(ReadConcern.DEFAULT);

    // All the write operation at the server end are acknowledged by
    // mongo client.
    // Hence the operation's status can be checked.
    clientOptions.writeConcern(WriteConcern.ACKNOWLEDGED);

    // All the write operation at the server end are acknowledged by
    // mongo client.
    // Hence the operation's status can be checked.
    clientOptions.writeConcern(WriteConcern.ACKNOWLEDGED);

    mClient = new MongoClient(new ServerAddress(host, port), clientOptions.build());
  }

  public synchronized static MongoClient getClient() {
    return mClient;
  }

  public MongoCollection<Document> getCollection() {

    return MongoDataSourceClient.getClient().getDatabase("DiscussDatabase")
            .getCollection("sanwadaCollection");
  }

  @Override
  public Object getDatabase() {
    return MongoDataSourceClient.getClient().getDatabase("DiscussDatabase");
  }

  protected void finalize() throws Throwable {
  }

  /*
   * @param filter must be a sub type of org.bson.BSON
   */
  @Override
  public Iterable<Document> find(LinkedHashMap<String, Object> filters) {
    BasicDBObject query = new BasicDBObject();

    for (Map.Entry<String, Object> f : filters.entrySet()) {
      String key = f.getKey();
      Object val = f.getValue();
      query.append(key, val);
    }

    Bson bsonFilter = (Bson) query;
    FindIterable<Document> docs = getCollection().find(bsonFilter);
    // return (Iterator<Object>) docs;
    return docs;
  }

  /*
   * @param object must be a type Document
   */
  @Override
  public void insert(Object object) {
    getCollection().insertOne((Document) object);
  }

  @Override
  public boolean update(LinkedHashMap<String, Object> filters, Object newObject) {
    BasicDBObject query = new BasicDBObject();
    Document updateObject = new Document("$set", newObject);

    for (Map.Entry<String, Object> f : filters.entrySet()) {
      String key = f.getKey();
      Object val = f.getValue();
      query.append(key, val);
    }

    Bson bsonFilter = (Bson) query;
    Bson bsonUpdate = (Bson) updateObject;
    return getCollection().updateOne(bsonFilter, bsonUpdate).wasAcknowledged();
  }

  @Override
  public boolean delete(LinkedHashMap<String, Object> filters) {
    BasicDBObject query = new BasicDBObject();

    for (Map.Entry<String, Object> f : filters.entrySet()) {
      String key = f.getKey();
      Object val = f.getValue();
      query.append(key, val);
    }

    return getCollection().deleteOne(query).wasAcknowledged();
  }

}
