package sanwada.v1.dao;

import java.util.LinkedHashMap;

import org.bson.Document;

import sanwada.v1.entity.DbResponse;
import sanwada.v1.entity.Post;

public class PostDataService implements PostDAO {

    private DataSourceClient<Document> client;
    private DbResponse dbResponse;
    private LinkedHashMap<String, Object> filters;

    public PostDataService() {
        try {
            this.client = new MongoDataSourceClient();
            filters = new LinkedHashMap<String, Object>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DbResponse addPost(Post post) {
        return null;
    }

    @Override
    public DbResponse getPost(String id) {
        return null;
    }

    @Override
    public DbResponse removePost(String id) {
        return null;
    }

    @Override
    public DbResponse updatePost(String id, Post newPost) {
        return null;
    }
	
}
