package sanwada.v1.entity;

public class Answer {
  private String id;
  private String questionId;
  private String content;
  private long timestamp;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getQuestionId() {
    return questionId;
  }
  public void setQuestionId(String questionId) {
    this.questionId = questionId;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public long getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }
}
