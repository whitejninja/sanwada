package sanwada.v1.dao;

import sanwada.v1.entity.DbResponse;
import sanwada.v1.entity.Post;

public interface PostDAO {

    /**
     * Add a post to a database
     */
    DbResponse addPost(Post post);

    /**
     * Get a post details based on id
     */
    DbResponse getPost(String id);

    /**
     * Remove a post from database
     */
    DbResponse removePost(String id);

    /**
     * Update an existing post in the database
     */
    DbResponse updatePost(String id, Post newPost);

}
