package sanwada.v1.entity;

import org.jvnet.hk2.annotations.Optional;

/**
 * Representation of a question that is stored in the database
 */
public class Question{

  /**
   * Unique identity value for the question
   */
  @Optional
  private String id;

  /**
   * Unique username of the user who created this question
   */
  @Optional
  private String userAlias;

  /**
   * Title of the question.
   * Can contain unicode characters.
   */
  private String title;

  /**
   * Textual content of this question.
   * Can contain unicode chracters.
   */
  private String content;

  /**
   * Timestamp the question is created.
   * Must be in UTC (Coordinated Universal Time) format.
   */
  private long timeStamp;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserAlias() {
    return userAlias;
  }

  public void setUserAlias(String userAlias) {
    this.userAlias = userAlias;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }

}
