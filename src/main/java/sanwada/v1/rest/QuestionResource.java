package sanwada.v1.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sanwada.v1.dao.DbOperationStatus;
import sanwada.v1.entity.DbResponse;
import sanwada.v1.entity.NewQuestion;
import sanwada.v1.dao.QuestionDataService;
import sanwada.v1.entity.Question;

@Path(value = "question")
public class QuestionResource {

	@POST
	@Path(value = "create")
	@Consumes(value = MediaType.APPLICATION_JSON)
	public Response createQuestion(NewQuestion question) {
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
}
