package sanwada.v1.rest;

import static org.testng.Assert.*;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import sanwada.v1.dao.AnswerDataService;
import sanwada.v1.dao.DbOperationStatus;
import sanwada.v1.entity.Answer;
import sanwada.v1.entity.DbResponse;

public class AnswerResourceTest {

  AnswerResource answerResource;

  @BeforeMethod
  void beforeMethod() {
    answerResource = new AnswerResource();
  }

  /*
   * Start of createAnswer() test cases
   */

  @Test
  public void createAnswer_questionIdNotExist_400() {
    Answer answer = mock(Answer.class);
    AnswerDataService dataService = mock(AnswerDataService.class);

    when(answer.getId()).thenReturn("id");
    when(dataService.createAnswer(answer))
            .thenReturn(new DbResponse(DbOperationStatus.NO_SUCH_RECORD, answer));

    answerResource.answerDataService = dataService;

    Response res = answerResource.createAnswer(answer);

    assertEquals(res.getStatus(), 400);
  }

  @Test
  public void createAnswer_successfullyCreated_201() {
    Answer answer = mock(Answer.class);
    AnswerDataService dataService = mock(AnswerDataService.class);

    when(answer.getId()).thenReturn("id");
    when(dataService.createAnswer(answer))
            .thenReturn(new DbResponse(DbOperationStatus.SUCCESS, answer));

    answerResource.answerDataService = dataService;

    Response res = answerResource.createAnswer(answer);

    assertEquals(res.getStatus(), 201);
  }

  @Test
  public void createAnswer_creationFailed_500() {
    Answer answer = mock(Answer.class);
    AnswerDataService dataService = mock(AnswerDataService.class);

    when(answer.getId()).thenReturn("id");
    when(dataService.createAnswer(answer))
            .thenReturn(new DbResponse(DbOperationStatus.FALIURE, answer));

    answerResource.answerDataService = dataService;

    Response res = answerResource.createAnswer(answer);

    assertEquals(res.getStatus(), 500);
  }

  /*
   * End of createAnswer() test cases
   */

  /*
   * Start of getAnswer() test cases
   */

  @Test
  public void getAnswer_answerIdExist_200() {
    Answer answer = mock(Answer.class);
    AnswerDataService dataService = mock(AnswerDataService.class);

    when(dataService.getAnswer("id")).thenReturn(new DbResponse(DbOperationStatus.SUCCESS, answer));

    answerResource.answerDataService = dataService;

    Response res = answerResource.getAnswer("id");

    assertEquals(res.getStatus(), 200);
  }

  @Test
  public void getAnswer_answerIdNotExist_404() {
    Answer answer = mock(Answer.class);
    AnswerDataService dataService = mock(AnswerDataService.class);

    when(dataService.getAnswer("id"))
            .thenReturn(new DbResponse(DbOperationStatus.NO_SUCH_RECORD, answer));

    answerResource.answerDataService = dataService;

    Response res = answerResource.getAnswer("id");

    assertEquals(res.getStatus(), 404);
  }

  @Test
  public void getAnswer_failure_500() {
    Answer answer = mock(Answer.class);
    AnswerDataService dataService = mock(AnswerDataService.class);

    when(dataService.getAnswer("id")).thenReturn(new DbResponse(DbOperationStatus.FALIURE, answer));

    answerResource.answerDataService = dataService;

    Response res = answerResource.getAnswer("id");

    assertEquals(res.getStatus(), 500);
  }

  /*
   * End of getAnswer() test cases
   */

  /*
   * Start of updateAnswer() test cases
   */

  @Test
  public void updateAnswer_answerIdExist_200() {
    Answer answer = mock(Answer.class);
    AnswerDataService dataService = mock(AnswerDataService.class);

    when(dataService.updateAnswer("id", answer))
            .thenReturn(new DbResponse(DbOperationStatus.SUCCESS, answer));

    answerResource.answerDataService = dataService;

    Response res = answerResource.updateAnswer("id", answer);

    assertEquals(res.getStatus(), 200);
  }

  @Test
  public void updateAnswer_answerIdDoesNotExist_400() {
    Answer answer = mock(Answer.class);
    AnswerDataService dataService = mock(AnswerDataService.class);

    when(dataService.updateAnswer("id", answer))
            .thenReturn(new DbResponse(DbOperationStatus.NO_SUCH_RECORD, null));

    answerResource.answerDataService = dataService;

    Response res = answerResource.updateAnswer("id", answer);

    assertEquals(res.getStatus(), 400);
  }

  @Test
  public void updateAnswer_failure_500() {
    Answer answer = mock(Answer.class);
    AnswerDataService dataService = mock(AnswerDataService.class);

    when(dataService.updateAnswer("id", answer))
            .thenReturn(new DbResponse(DbOperationStatus.FALIURE, null));

    answerResource.answerDataService = dataService;

    Response res = answerResource.updateAnswer("id", answer);

    assertEquals(res.getStatus(), 500);
  }

  /*
   * End of updateAnswer() test cases
   */

  /*
   * Start of deleteAnswer() test cases
   */

  @Test
  public void deleteAnswer_answerIdExist_200() {
    AnswerDataService dataService = mock(AnswerDataService.class);

    when(dataService.deleteAnswer("id"))
            .thenReturn(new DbResponse(DbOperationStatus.SUCCESS, null));

    answerResource.answerDataService = dataService;

    Response res = answerResource.deleteAnswer("id");

    assertEquals(res.getStatus(), 200);
  }

  @Test
  public void deleteAnswer_answerIdDoesNotExist_404() {
    AnswerDataService dataService = mock(AnswerDataService.class);

    when(dataService.deleteAnswer("id"))
            .thenReturn(new DbResponse(DbOperationStatus.NO_SUCH_RECORD, null));

    answerResource.answerDataService = dataService;

    Response res = answerResource.deleteAnswer("id");

    assertEquals(res.getStatus(), 404);
  }

  @Test
  public void deleteAnswer_failure_500() {
    AnswerDataService dataService = mock(AnswerDataService.class);

    when(dataService.deleteAnswer("id"))
            .thenReturn(new DbResponse(DbOperationStatus.FALIURE, null));

    answerResource.answerDataService = dataService;

    Response res = answerResource.deleteAnswer("id");

    assertEquals(res.getStatus(), 500);
  }
  /*
   * End of deleteAnswer() test cases
   */
}
