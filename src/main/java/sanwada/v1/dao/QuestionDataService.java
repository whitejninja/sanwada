package sanwada.v1.dao;

import java.util.LinkedHashMap;

import org.bson.Document;

import sanwada.v1.entity.DbResponse;
import sanwada.v1.entity.Question;

public class QuestionDataService implements QuestionDAO {

    private DataSourceClient<Document> client;
    private DbResponse dbResponse;
    private LinkedHashMap<String, Object> filters;

    public QuestionDataService() {
        try {
            this.client = new MongoDataSourceClient();
            filters = new LinkedHashMap<String, Object>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DbResponse addQuestion(Question question) {
        return null;
    }

    @Override
    public DbResponse getQuestion(String id) {
        return null;
    }

    @Override
    public DbResponse removeQuestion(String id) {
        return null;
    }

    @Override
    public DbResponse updateQuestion(String id, Question newQuestion) {
        return null;
    }
	
}
