package sanwada.v1.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import sanwada.v1.entity.Question;

@Path(value = "question")
public class QuestionResource {

    @GET
    @Path(value = "create")
    public String createQuestion(Question question) {
        return "Test message";
    }  
}
