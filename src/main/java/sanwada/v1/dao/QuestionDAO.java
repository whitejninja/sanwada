package sanwada.v1.dao;

import sanwada.v1.entity.DbResponse;
import sanwada.v1.entity.Question;

public interface QuestionDAO{

  /**
   * Add a question to a database
   */
  DbResponse addQuestion(Question question);

  /**
   * Get a question details based on id
   */
  DbResponse getQuestion(String id);

  /**
   * Get a question details based on title
   */
  DbResponse getQuestionByTitle(String title);

  /**
   * Remove a question from database
   */
  DbResponse removeQuestion(String id);

  /**
   * Update an existing question in the database
   */
  DbResponse updateQuestion(String id, Question newQuestion);

}