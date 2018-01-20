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
  public void getQuestionByTitle_titleAvailable_200StatusCode() {
    Question question = mock(Question.class);
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.getQuestionByTitle("title"))
            .thenReturn(new DbResponse(DbOperationStatus.SUCCESS, question));

    questionResource.questionDataService = dataService;

    Response res = questionResource.getQuestion(null, "title");

    assertEquals(res.getStatus(), 200);
  }
  
  @Test
  public void getQuestionByTitle_titleNotAvailable_404StatusCode() {
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.getQuestionByTitle("title"))
            .thenReturn(new DbResponse(DbOperationStatus.NO_SUCH_RECORD, "title"));

    questionResource.questionDataService = dataService;

    Response res = questionResource.getQuestion(null, "title");

    assertEquals(res.getStatus(), 404);
  }

  @Test
  public void getQuestionById_idAvailable_200StatusCode() {
    Question question = mock(Question.class);
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.getQuestion("id"))
            .thenReturn(new DbResponse(DbOperationStatus.SUCCESS, question));

    questionResource.questionDataService = dataService;

    Response res = questionResource.getQuestion("id", null);

    assertEquals(res.getStatus(), 200);
  }
  
  @Test
  public void getQuestionById_idNotAvailable_404StatusCode() {
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.getQuestion("id"))
            .thenReturn(new DbResponse(DbOperationStatus.NO_SUCH_RECORD, "id"));

    questionResource.questionDataService = dataService;

    Response res = questionResource.getQuestion("id", null);

    assertEquals(res.getStatus(), 404);
  }

  @Test
  public void createQuestion_titleAvailable_201StatusCode() {
    Question question = mock(Question.class);
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.addQuestion(question))
            .thenReturn(new DbResponse(DbOperationStatus.SUCCESS, question));

    questionResource.questionDataService = dataService;

    Response res = questionResource.createQuestion(question);

    assertEquals(res.getStatus(), 201);
  }

  @Test
  public void createQuestion_titleNotAvailable_409StatusCode() {
    Question question = mock(Question.class);
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.addQuestion(question))
            .thenReturn(new DbResponse(DbOperationStatus.DUPLICATE_ENTRY, question));

    questionResource.questionDataService = dataService;

    Response res = questionResource.createQuestion(question);

    assertEquals(res.getStatus(), 409);
  }
  
  @Test
  public void updateQuestion_Updated_200StatusCode() {
    Question question = mock(Question.class);
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.updateQuestion("id", question))
            .thenReturn(new DbResponse(DbOperationStatus.SUCCESS, question));

    questionResource.questionDataService = dataService;

    Response res = questionResource.updateQuestion("id", question);

    assertEquals(res.getStatus(), 200);
  }
  
  @Test
  public void updateQuestion_wrongId_400StatusCode() {
    Question question = mock(Question.class);
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.updateQuestion("id", question))
            .thenReturn(new DbResponse(DbOperationStatus.NO_SUCH_RECORD, question));

    questionResource.questionDataService = dataService;

    Response res = questionResource.updateQuestion("id", question);

    assertEquals(res.getStatus(), 400);
  }
  
  @Test
  public void updateQuestion_titleAlreadyExist_409StatusCode() {
    Question question = mock(Question.class);
    QuestionDataService dataService = mock(QuestionDataService.class);
    when(dataService.updateQuestion("id", question))
            .thenReturn(new DbResponse(DbOperationStatus.DUPLICATE_ENTRY, question));

    questionResource.questionDataService = dataService;

    Response res = questionResource.updateQuestion("id", question);

    assertEquals(res.getStatus(), 409);
  }
  
  @Test
  public void deleteQuestion_deleted_200StatusCode() {
    QuestionDataService dataService=mock(QuestionDataService.class);
    when(dataService.removeQuestion("id")).thenReturn(new DbResponse(DbOperationStatus.SUCCESS, "id"));
    
    questionResource.questionDataService=dataService;
    
    Response res = questionResource.deleteQuestion("id");
    
    assertEquals(res.getStatus(), 200);
  }
  
  @Test
  public void deleteQuestion_wrongId_404StatusCode() {
    QuestionDataService dataService=mock(QuestionDataService.class);
    when(dataService.removeQuestion("id")).thenReturn(new DbResponse(DbOperationStatus.NO_SUCH_RECORD, "id"));
    
    questionResource.questionDataService=dataService;
    
    Response res = questionResource.deleteQuestion("id");
    
    assertEquals(res.getStatus(), 400);
  }
}
