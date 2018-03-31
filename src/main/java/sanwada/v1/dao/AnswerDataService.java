
package sanwada.v1.dao;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import sanwada.v1.entity.Answer;
import sanwada.v1.entity.DatabaseCollection;
import sanwada.v1.entity.DbResponse;

public class AnswerDataService{

  /**
   * Represents a client who interacts with a database
   */
  @Inject
  DataSourceClient<Document> client;

  /**
   * Filters to perform with the database query
   */
  LinkedHashMap<String, Object> filters = new LinkedHashMap<>();

  public DbResponse createAnswer(Answer ans) {
    try {
      client.setCollection(DatabaseCollection.QUESTION_COLLECTION);
      ObjectId id = new ObjectId(ans.getQuestionId());
      filters.put("_id", id);

      // check whether questionId exist
      Boolean questionIdAvailable = this.client.find(filters).iterator().hasNext();

      if (!questionIdAvailable) {
        return new DbResponse(DbOperationStatus.NO_SUCH_RECORD, ans);
      }

      Document document = new Document("questionId", ans.getQuestionId())
          .append("content", ans.getContent());

      client.setCollection(DatabaseCollection.ANSWER_COLLECTION);

      Long postedTime = System.currentTimeMillis();
      document.append("time", postedTime);
      client.insert(document);

      // Convert _id object to hexadecimal string
      Object idObj = document.get("_id");
      String idStr = (String) idObj.toString();

      Answer createdAnswer = new Answer();
      createdAnswer.setId(idStr);
      createdAnswer.setQuestionId(document.getString("content"));
      createdAnswer.setContent(document.getString("content"));
      createdAnswer.setTimestamp(postedTime);

      return new DbResponse(DbOperationStatus.SUCCESS, createdAnswer);
    } catch (Exception e) {
      e.printStackTrace();
      return new DbResponse(DbOperationStatus.FALIURE, null);
    }
  }

  public DbResponse getAnswer(String id) {
    client.setCollection(DatabaseCollection.ANSWER_COLLECTION);

    ObjectId objId = new ObjectId(id);
    filters.put("_id", objId);

    if (this.client.find(filters).iterator().hasNext()) {
      Document ansDoc = this.client.find(filters)
          .iterator()
          .next();

      Answer ans = new Answer();

      Object idObj = ansDoc.get("_id");
      String idStr = (String) idObj.toString();
      ans.setId(idStr);

      ans.setContent(ansDoc.getString("content"));

      Object qidObj = ansDoc.get("questionId");
      String qidStr = (String) qidObj.toString();
      ans.setQuestionId(qidStr);

      ans.setTimestamp(ansDoc.getLong("time"));

      return new DbResponse(DbOperationStatus.SUCCESS, ans);
    } else {
      return new DbResponse(DbOperationStatus.NO_SUCH_RECORD, null);
    }
  }

  public DbResponse updateAnswer(String id, Answer answer) {
    client.setCollection(DatabaseCollection.ANSWER_COLLECTION);

    ObjectId objId = new ObjectId(id);
    filters.put("_id", objId);

    if (this.client.find(filters).iterator().hasNext()) {
      return new DbResponse(DbOperationStatus.SUCCESS, answer);
    }
    return new DbResponse(DbOperationStatus.NO_SUCH_RECORD, answer);
  }

  public DbResponse deleteAnswer(String id) {
    return null;
  }

  public boolean isQuestionIdAvailable(String id) {
    client.setCollection(DatabaseCollection.QUESTION_COLLECTION);
    ObjectId objId = new ObjectId(id);
    filters.put("_id", objId);

    return this.client.find(filters).iterator().hasNext();
  }
}