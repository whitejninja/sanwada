package sanwada.v1.rest;

import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sanwada.v1.dao.AnswerDataService;
import sanwada.v1.dao.DbOperationStatus;
import sanwada.v1.entity.Answer;
import sanwada.v1.entity.DbResponse;

@Path(value = "answer")
public class AnswerResource {

  @Inject
  AnswerDataService answerDataService;

  @POST
  @Path(value = "")
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response createAnswer(Answer answer) {
    System.out.println(answer.getId());
    System.out.println(answerDataService.isQuestionIdValid(answer.getQuestionId()));
    if (answerDataService.isQuestionIdValid(answer.getQuestionId())) {
      DbResponse res = answerDataService.createQuestion(answer);
      if (res.getStatus().equals(DbOperationStatus.SUCCESS)) {
        return Response.status(201).entity(res.getEntity()).build();
      } else {
        return Response.status(500).entity("Error in saving the answer").build();
      }
    } else {
      return Response.status(400).header("location", "answer/create").build();
    }
  }

}
