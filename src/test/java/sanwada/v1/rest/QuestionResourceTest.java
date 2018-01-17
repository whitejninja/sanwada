package sanwada.v1.rest;

import static org.testng.Assert.*;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import sanwada.v1.dao.DbOperationStatus;
import sanwada.v1.dao.QuestionDataService;
import sanwada.v1.entity.DbResponse;
import sanwada.v1.entity.Question;

public class QuestionResourceTest {

  QuestionResource questionResource;

  @BeforeMethod
  void beforeMethod() {
    questionResource = new QuestionResource();
  }

  @Test
  public void getQuestionByTitle_titleIsAvailableInTheDB_200StatusCode() {
    Question question = mock(Question.class);
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.getQuestionByTitle("title"))
            .thenReturn(new DbResponse(DbOperationStatus.SUCCESS, question));

    questionResource.questionDataService = dataService;

    Response res = questionResource.getQuestion(null, "title");

    assertEquals(res.getStatus(), 200);
  }

  @Test
  public void getQuestionById_idIsAvailableInTheDB_200StatusCode() {
    Question question = mock(Question.class);
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.getQuestion("id"))
            .thenReturn(new DbResponse(DbOperationStatus.SUCCESS, question));

    questionResource.questionDataService = dataService;

    Response res = questionResource.getQuestion("id", null);

    assertEquals(res.getStatus(), 200);
  }

  @Test
  public void createQuestion_titleIsAvailable_201StatusCode() {
    Question question = mock(Question.class);
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.addQuestion(question))
            .thenReturn(new DbResponse(DbOperationStatus.SUCCESS, question));

    questionResource.questionDataService = dataService;

    Response res = questionResource.createQuestion(question);

    assertEquals(res.getStatus(), 201);
  }

  @Test
  public void createQuestion_titleIsNotAvailable_409StatusCode() {
    Question question = mock(Question.class);
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.addQuestion(question))
            .thenReturn(new DbResponse(DbOperationStatus.DUPLICATE_ENTRY, question));

    questionResource.questionDataService = dataService;

    Response res = questionResource.createQuestion(question);

    assertEquals(res.getStatus(), 409);
  }
}
