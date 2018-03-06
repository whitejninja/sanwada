package sanwada.v1.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sanwada.v1.dao.AnswerDataService;
import sanwada.v1.entity.Answer;
import sanwada.v1.entity.DbResponse;

@Path(value = "answer")
public class AnswerResource {

  @Inject
  AnswerDataService answerDataService;

  @POST
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response createAnswer(Answer answer) {
    DbResponse res = answerDataService.createAnswer(answer);

    switch (res.getStatus()) {
      case SUCCESS:
        return Response.status(201).entity(res.getEntity()).build();
      case NO_SUCH_RECORD:
        return Response.status(400).entity("Wrong question id").build();
      default:
        return Response.status(500).entity("Error occured").build();
    }
  }

  @GET
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response getAnswer(@PathParam("id") String id) {
    DbResponse res = answerDataService.getAnswer(id);

    switch (res.getStatus()) {
      case SUCCESS:
        return Response.status(200).entity(res.getEntity()).build();
      case NO_SUCH_RECORD:
        return Response.status(404).build();
      default:
        return Response.status(500).entity("Error occured").build();
    }
  }

  @PUT
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response updateAnswer(@PathParam("id") String id, Answer answer) {
    DbResponse res = answerDataService.updateAnswer(id, answer);

    switch (res.getStatus()) {
      case SUCCESS:
        return Response.status(200).entity(res.getEntity()).build();
      case NO_SUCH_RECORD:
        return Response.status(400).entity(res.getEntity()).build();
      default:
        return Response.status(500).entity("Error occured").build();
    }
  }

  @DELETE
  public Response deleteAnswer(String id) {
    DbResponse res = answerDataService.deleteAnswer(id);

    switch (res.getStatus()) {
      case SUCCESS:
        return Response.status(200).entity(res.getEntity()).build();
      case NO_SUCH_RECORD:
        return Response.status(404).entity(res.getEntity()).build();
      default:
        return Response.status(500).entity("Error occured").build();
    }
  }

}
