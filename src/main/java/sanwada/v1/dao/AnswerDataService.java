package sanwada.v1.dao;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import org.bson.Document;

import sanwada.v1.entity.Answer;
import sanwada.v1.entity.DbResponse;

public class AnswerDataService {

  @Inject
  DataSourceClient<Document> client;
  LinkedHashMap<String, Object> filters = new LinkedHashMap<>();

  public DbResponse createAnswer(Answer ans) {
    try {
      Document document = new Document("questionId", ans.getQuestionId())
              .append("content", ans.getContent());

      filters.put("questionId", ans.getQuestionId());

      // check whether questionId exist
      Boolean questionIdAvailable = this.client.find(filters).iterator().hasNext();

      if (questionIdAvailable) {
        return new DbResponse(DbOperationStatus.SUCCESS, ans);
      }
      return new DbResponse(DbOperationStatus.NO_SUCH_RECORD, ans);
    } catch (Exception e) {
      e.printStackTrace();
      return new DbResponse(DbOperationStatus.FALIURE, null);
    }
  }

  public DbResponse getAnswer(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  public DbResponse updateAnswer(String id, Answer answer) {
    // TODO Auto-generated method stub
    return null;
  }

  public DbResponse deleteAnswer(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isQuestionIdAvailable(String id) {
    // TODO Auto-generated method stub
    return true;
  }
}
