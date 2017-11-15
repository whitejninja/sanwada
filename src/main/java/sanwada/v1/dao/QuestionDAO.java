package sanwada.v1.dao;

import sanwada.v1.entity.DbResponse;
import sanwada.v1.entity.NewQuestion;
import sanwada.v1.entity.Question;

public interface QuestionDAO {

    /**
     * Add a question to a database
     */
    DbResponse addQuestion(NewQuestion question);

    /**
     * Get a question details based on id
     */
    DbResponse getQuestion(String id);

    /**
     * Remove a question from database
     */
    DbResponse removeQuestion(String id);

    /**
     * Update an existing question in the database
     */
    DbResponse updateQuestion(String id, Question newQuestion);

}
