package sanwada.v1.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.persistence.internal.sessions.DirectCollectionChangeRecord.NULL;

import sanwada.v1.dao.DbOperationStatus;
import sanwada.v1.entity.DbResponse;
import sanwada.v1.dao.QuestionDataService;
import sanwada.v1.entity.Question;

@Path(value = "question")
public class QuestionResource {

  @POST
  @Path(value = "create")
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response createQuestion(Question question) {
    QuestionDataService questionDataService = new QuestionDataService();
    DbResponse dbResponse = questionDataService.addQuestion(question);

    if (dbResponse.getStatus().equals(DbOperationStatus.SUCCESS)) {

      return Response.status(201).header("location", "question/create")
              .entity(dbResponse.getQuestion()).build();

    } else if (dbResponse.getStatus().equals(DbOperationStatus.NO_SUCH_RECORD)) {

      return Response.status(400).header("location", "question/create").build();

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
    QuestionDataService questionDataService = new QuestionDataService();
    DbResponse dbResponse = questionDataService.updateQuestion(id, question);

    if (dbResponse.getStatus().equals(DbOperationStatus.SUCCESS)) {

      return Response.status(201).header("location", "question/update/" + id)
              .entity(dbResponse.getQuestion())
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
  @Path(value = "")
  @Consumes(value = MediaType.APPLICATION_JSON)
  @Produces(value = MediaType.APPLICATION_JSON)
  public Response getQuestion(@QueryParam("id") String id, @QueryParam("title") String title) {
    
    QuestionDataService questionDataService = new QuestionDataService();
    DbResponse dbResponse = null;
    if (id != null) {
      dbResponse = questionDataService.getQuestion(id);
    } else if (title != null) {
      dbResponse = questionDataService.getQuestionByTitle(title);
    } else {
      return Response.status(400).header("location", "question/" + id).build();
    }
    if (dbResponse.getStatus().equals(DbOperationStatus.SUCCESS)) {

      return Response.ok(dbResponse.getQuestion()).header("location", "question/" + id).build();

    } else if (dbResponse.getStatus().equals(DbOperationStatus.NO_SUCH_RECORD)) {

      return Response.status(404).header("location", "question/" + id).build();

    } else {

      return Response.status(500).header("location", "question/" + id).build();

    }
  }

  @DELETE
  @Path(value = "/{id}")
  public Response deleteQuestion(@PathParam("id") String id) {
    QuestionDataService questionDataService = new QuestionDataService();
    DbResponse dbResponse = questionDataService.removeQuestion(id);

    if (dbResponse.getStatus().equals(DbOperationStatus.SUCCESS)) {

      return Response.ok(dbResponse.getQuestion()).header("location", "account/deleted")
              .entity(dbResponse.getQuestion()).build();

    } else if (dbResponse.getStatus().equals(DbOperationStatus.NO_SUCH_RECORD)) {

      return Response.status(400).header("location", "question/" + id).build();

    } else {

      return Response.status(500).header("location", "question/" + id).build();

    }
  }
}
