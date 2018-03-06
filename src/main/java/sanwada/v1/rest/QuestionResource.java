package sanwada.v1.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sanwada.v1.dao.DbOperationStatus;
import sanwada.v1.entity.DbResponse;
import sanwada.v1.dao.QuestionDataService;
import sanwada.v1.entity.Question;

@Path(value = "question")
public class QuestionResource {
  @Inject
  QuestionDataService questionDataService;

  @POST
  @Path(value = "create")
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response createQuestion(Question question) {
    DbResponse dbResponse = questionDataService.addQuestion(question);

    if (dbResponse.getStatus().equals(DbOperationStatus.SUCCESS)) {

      return Response.status(201).header("location", "question/create")
              .entity(dbResponse.getEntity()).build();

    } else if (dbResponse.getStatus().equals(DbOperationStatus.DUPLICATE_ENTRY)) {

      return Response.status(409).header("location", "question/create").build();

    } else {

      return Response.status(500).header("location", "question/create").build();

    }
  }

  @PUT
  @Path(value = "update/{id}")
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response updateQuestion(@PathParam("id") String id, Question question) {
    DbResponse dbResponse = questionDataService.updateQuestion(id, question);

    if (dbResponse.getStatus().equals(DbOperationStatus.SUCCESS)) {

      return Response.ok().header("location", "question/update/" + id)
              .entity(dbResponse.getEntity())
              .build();

    } else if (dbResponse.getStatus().equals(DbOperationStatus.NO_SUCH_RECORD)) {

      return Response.status(400).header("location", "question/update/" + id).build();

    } else if (dbResponse.getStatus().equals(DbOperationStatus.DUPLICATE_ENTRY)) {

      return Response.status(409).header("location", "question/update/" + id).build();

    } else {

      return Response.status(500).header("location", "question/update/" + id).build();

    }
  }

  @GET
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response getQuestion(@QueryParam("id") String id, @QueryParam("title") String title) {
    DbResponse dbResponse = null;
    if (id != null) {
      dbResponse = questionDataService.getQuestion(id);
    } else if (title != null) {
      dbResponse = questionDataService.getQuestionByTitle(title);
    } else {
      return Response.status(400).header("location", "question/" + id).build();
    }
    if (dbResponse.getStatus().equals(DbOperationStatus.SUCCESS)) {

      return Response.ok(dbResponse.getEntity()).header("location", "question/" + id).build();

    } else if (dbResponse.getStatus().equals(DbOperationStatus.NO_SUCH_RECORD)) {

      return Response.status(404).header("location", "question/" + id).build();

    } else {

      return Response.status(500).header("location", "question/" + id).build();

    }
  }

  @DELETE
  @Path(value = "/{id}")
  public Response deleteQuestion(@PathParam("id") String id) {
    DbResponse dbResponse = questionDataService.removeQuestion(id);

    if (dbResponse.getStatus().equals(DbOperationStatus.SUCCESS)) {

      return Response.ok(dbResponse.getEntity()).header("location", "account/deleted")
              .entity(dbResponse.getEntity()).build();

    } else if (dbResponse.getStatus().equals(DbOperationStatus.NO_SUCH_RECORD)) {

      return Response.status(400).header("location", "question/" + id).build();

    } else {

      return Response.status(500).header("location", "question/" + id).build();

    }
  }
}
