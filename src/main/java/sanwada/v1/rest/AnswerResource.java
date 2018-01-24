package sanwada.v1.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sanwada.v1.dao.AnswerDataService;
import sanwada.v1.entity.Answer;

@Path(value = "answer")
public class AnswerResource {

  @Inject AnswerDataService dataService;
  
  @POST
  @Path(value = "")
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response createAnswer(Answer question) {
    return Response.ok("Answer").build();
  }

}
