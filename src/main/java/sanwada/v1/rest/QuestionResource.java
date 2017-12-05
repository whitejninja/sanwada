package sanwada.v1.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import sanwada.v1.dao.DbOperationStatus;
import sanwada.v1.entity.DbResponse;
import sanwada.v1.dao.QuestionDataService;
import sanwada.v1.entity.Question;

@Path(value = "question")
@Api(value = "question")
public class QuestionResource {

	@POST
	@Path(value = "create")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	@ApiOperation(value="Creates a question")
	@ApiResponses(value = { 
		      @ApiResponse(code = 201, message = "Question created", response=Question.class),
		      @ApiResponse(code = 409, message = "Title already exist"),
		      @ApiResponse(code = 500, message = "Internal server error") 
		      })
	public Response createQuestion(Question question) {
		QuestionDataService questionDataService = new QuestionDataService();
		DbResponse dbResponse = questionDataService.addQuestion(question);

		if (dbResponse.getStatus().equals(DbOperationStatus.SUCCESS)) {

			return Response.status(201).header("location", "question/create").entity(dbResponse.getQuestion()).build();

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
	@ApiOperation(value="Updates a question")
	@ApiResponses(value = { 
		      @ApiResponse(code = 200, message = "Question updated", response=Question.class),
		      @ApiResponse(code = 400, message = "ID doesn't exist"),
		      @ApiResponse(code = 500, message = "Internal server error") 
		      })
	public Response updateQuestion(@PathParam("id") String id, Question question) {
		QuestionDataService questionDataService = new QuestionDataService();
		DbResponse dbResponse = questionDataService.updateQuestion(id, question);

		if (dbResponse.getStatus().equals(DbOperationStatus.SUCCESS)) {

			return Response.status(201).header("location", "question/update/" + id).entity(dbResponse.getQuestion())
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
	@Path(value = "/{id}")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	@ApiOperation(value="Retrieve a question")
	@ApiResponses(value = { 
		      @ApiResponse(code = 200, message = "Retrieving question successful", response=Question.class),
		      @ApiResponse(code = 400, message = "ID doesn't exist"),
		      @ApiResponse(code = 500, message = "Internal server error") 
		      })
	public Response getQuestion(@PathParam("id") String id) {
		QuestionDataService questionDataService = new QuestionDataService();
		DbResponse dbResponse = questionDataService.getQuestion(id);

		if (dbResponse.getStatus().equals(DbOperationStatus.SUCCESS)) {

			return Response.ok(dbResponse.getQuestion()).header("location", "question/" + id).build();

		} else if (dbResponse.getStatus().equals(DbOperationStatus.NO_SUCH_RECORD)) {

			return Response.status(400).header("location", "question/" + id).build();

		} else {

			return Response.status(500).header("location", "question/" + id).build();

		}
	}
	
	@DELETE
	@Path(value = "/{id}")
	@ApiOperation(value="Delete a question")
	@ApiResponses(value = { 
		      @ApiResponse(code = 200, message = "Deleting question successful", response=String.class),
		      @ApiResponse(code = 400, message = "ID doesn't exist"),
		      @ApiResponse(code = 500, message = "Internal server error") 
		      })
	public Response deleteQuestion(@PathParam("id") String id) {
		QuestionDataService questionDataService = new QuestionDataService();
		DbResponse dbResponse = questionDataService.removeQuestion(id);
		
		if (dbResponse.getStatus().equals(DbOperationStatus.SUCCESS)) {

			return Response.ok(dbResponse.getQuestion()).header("location", "account/deleted").entity(dbResponse.getQuestion()).build();

		} else if (dbResponse.getStatus().equals(DbOperationStatus.NO_SUCH_RECORD)) {

			return Response.status(400).header("location", "question/" + id).build();

		} else {

			return Response.status(500).header("location", "question/" + id).build();

		}
	}
}
