package sanwada.v1.dao;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import sanwada.v1.entity.DatabaseCollection;
import sanwada.v1.entity.DbResponse;
import sanwada.v1.entity.Question;

public class QuestionDataService implements QuestionDAO {

  @Inject
  DataSourceClient<Document> client;
  private LinkedHashMap<String, Object> filters;
  private DbResponse dbResponse;

  public QuestionDataService() {
    try {
      filters = new LinkedHashMap<String, Object>();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public DbResponse addQuestion(Question question) {
    try {
      client.setCollection(DatabaseCollection.QUESTION_COLLECTION);

      Document document = new Document("alias", question.getUserAlias())
              .append("title", question.getTitle())
              .append("content", question.getContent());

      this.filters.clear();
      this.filters.put("title", question.getTitle());
      // check duplicate title
      Boolean titleAvailable = !this.client.find(this.filters).iterator().hasNext();

      if (titleAvailable) {
        Long postedTime = System.currentTimeMillis();
        document.append("time", postedTime);
        client.insert(document);

        // Convert _id object to hexadecimal string
        Object idObj = document.get("_id");
        String idStr = (String) idObj.toString();

        // Generate response object
        Question createdQuestion = new Question();
        createdQuestion.setId(idStr);
        createdQuestion.setUserAlias(document.getString("alias"));
        createdQuestion.setTitle(document.getString("title"));
        createdQuestion.setContent(document.getString("content"));
        createdQuestion.setTimeStamp(document.getLong("time"));

        // Build response upon success
        this.dbResponse = new DbResponse(DbOperationStatus.SUCCESS, createdQuestion);
        return this.dbResponse;
      } else {
        // Build response upon failure
        this.dbResponse = new DbResponse(DbOperationStatus.DUPLICATE_ENTRY, question);
        return this.dbResponse;
      }

    } catch (Exception e) {
      e.printStackTrace();
      this.dbResponse = new DbResponse(DbOperationStatus.FALIURE, null);
      return dbResponse;
    }
  }

  @Override
  public DbResponse getQuestion(String id) {
    try {
      client.setCollection(DatabaseCollection.QUESTION_COLLECTION);

      ObjectId objId = new ObjectId(id);

      this.filters.clear();
      this.filters.put("_id", objId);
      Document doc = this.client.find(this.filters).iterator().next();

      Question returnedQuestion = new Question();
      returnedQuestion.setId(id);
      returnedQuestion.setTitle(doc.getString("title"));
      returnedQuestion.setContent(doc.getString("content"));
      returnedQuestion.setUserAlias(doc.getString("alias"));
      returnedQuestion.setTimeStamp(doc.getLong("time"));

      this.dbResponse = new DbResponse(DbOperationStatus.SUCCESS, returnedQuestion);
    } catch (java.util.NoSuchElementException ex) {
      this.dbResponse = new DbResponse(DbOperationStatus.NO_SUCH_RECORD, id);
    } catch (NullPointerException ex) {
      this.dbResponse = new DbResponse(DbOperationStatus.NO_SUCH_RECORD, id);
    } catch (java.lang.IllegalArgumentException ex) {
      this.dbResponse = new DbResponse(DbOperationStatus.NO_SUCH_RECORD, id);
    }

    return this.dbResponse;
  }

  @Override
  public DbResponse getQuestionByTitle(String title) {
    try {
      client.setCollection(DatabaseCollection.QUESTION_COLLECTION);

      this.filters.clear();
      this.filters.put("title", title);
      Document doc = this.client.find(this.filters).iterator().next();

      Question returnedQuestion = new Question();
      Object idObj = doc.get("_id");
      String idStr = (String) idObj.toString();
      returnedQuestion.setId(idStr);
      returnedQuestion.setTitle(doc.getString("title"));
      returnedQuestion.setContent(doc.getString("content"));
      returnedQuestion.setUserAlias(doc.getString("alias"));
      returnedQuestion.setTimeStamp(doc.getLong("time"));

      this.dbResponse = new DbResponse(DbOperationStatus.SUCCESS, returnedQuestion);
    } catch (java.util.NoSuchElementException ex) {
      this.dbResponse = new DbResponse(DbOperationStatus.FALIURE, title);
    } catch (NullPointerException ex) {
      this.dbResponse = new DbResponse(DbOperationStatus.FALIURE, title);
    } catch (java.lang.IllegalArgumentException ex) {
      this.dbResponse = new DbResponse(DbOperationStatus.NO_SUCH_RECORD, title);
    }

    return this.dbResponse;
  }

  @Override
  public DbResponse updateQuestion(String id, Question question) {

    ObjectId objectId = new ObjectId(id);
    try {
      client.setCollection(DatabaseCollection.QUESTION_COLLECTION);

      Document newDocument = new Document("title", question.getTitle()).append("content",
              question.getContent());

      this.filters.clear();
      this.filters.put("_id", objectId);
      this.client.update(this.filters, newDocument);

      Document updatedDocument = this.client.find(this.filters).iterator().next();
      Question updatedQuestion = question;
      updatedQuestion.setId(id);
      updatedQuestion.setUserAlias(updatedDocument.getString("alias"));
      updatedQuestion.setTitle(updatedDocument.getString("title"));
      updatedQuestion.setContent(updatedDocument.getString("content"));
      updatedQuestion.setTimeStamp(updatedDocument.getLong("time"));

      this.dbResponse = new DbResponse(DbOperationStatus.SUCCESS, updatedQuestion);

    } catch (Exception e) {

      e.printStackTrace();
      this.dbResponse = new DbResponse(DbOperationStatus.FALIURE, null);
      return dbResponse;
    }

    return dbResponse;
  }

  @Override
  public DbResponse removeQuestion(String id) {
    try {
      client.setCollection(DatabaseCollection.QUESTION_COLLECTION);

      ObjectId objId = new ObjectId(id);

      filters.clear();
      filters.put("_id", objId);
      boolean isSuccess = this.client.delete(filters);

      if (isSuccess) {

        this.dbResponse = new DbResponse(DbOperationStatus.SUCCESS, id);

      } else {

        this.dbResponse = new DbResponse(DbOperationStatus.NO_SUCH_RECORD, null);

      }

    } catch (IllegalArgumentException ex) {

      // Invalid request error
      this.dbResponse = new DbResponse(DbOperationStatus.FALIURE, null);

    } catch (Exception ex) {

      // Invalid request error
      ex.printStackTrace();
      this.dbResponse = new DbResponse(DbOperationStatus.FALIURE, null);

    }

    return this.dbResponse;
  }
}
