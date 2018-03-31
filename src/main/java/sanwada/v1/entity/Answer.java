package sanwada.v1.entity;

/**
 * Representation of a answer that is stored in the database
 */
public class Answer{

  /**
   * Unique identity for each answer
   */
  private String id;

  /**
   * Identity of the question that is assoicated with this answer
   */
  private String questionId;

  /**
   * Textual content of this answer.
   * Can contain unicode chracters.
   */
  private String content;

  /**
   * Timestamp the answer is created.
   * Must be in UTC (Coordinated Universal Time) format.
   */
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
