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
    answerResource=new AnswerResource();
  }
  
  
  @Test
  public void createAnswer_questionIdExist_Not400() {
    Answer answer=mock(Answer.class);
    AnswerDataService dataService = mock(AnswerDataService.class);
    
    when(answer.getId()).thenReturn("id");
    when(dataService.isQuestionIdValid(answer.getQuestionId())).thenReturn(true);
    when(dataService.createQuestion(answer)).thenReturn(new DbResponse(DbOperationStatus.SUCCESS, answer));
    
    answerResource.answerDataService = dataService;
    
    Response res=answerResource.createAnswer(answer);
    
    assertNotEquals(res.getStatus(), 400);
  }
  
  @Test
  public void createAnswer_questionIdNotExist_400() {
    Answer answer=mock(Answer.class);
    AnswerDataService dataService = mock(AnswerDataService.class);
    
    when(answer.getId()).thenReturn("id");
    when(dataService.isQuestionIdValid(answer.getQuestionId())).thenReturn(false);
    
    answerResource.answerDataService = dataService;
    
    Response res=answerResource.createAnswer(answer);
    
    assertEquals(res.getStatus(), 400);
  }
  
  @Test
  public void createAnswer_successfullyCreated_201() {
    Answer answer=mock(Answer.class);    
    AnswerDataService dataService=mock(AnswerDataService.class);
    
    when(answer.getId()).thenReturn("id");
    when(dataService.isQuestionIdValid(answer.getQuestionId())).thenReturn(true);
    when(dataService.createQuestion(answer)).thenReturn(new DbResponse(DbOperationStatus.SUCCESS, answer));
    
    answerResource.answerDataService=dataService;
    
    Response res=answerResource.createAnswer(answer);
    
    assertEquals(res.getStatus(), 201);
  }
  
  @Test
  public void createAnswer_creationFailed_500() {
    Answer answer=mock(Answer.class);
    AnswerDataService dataService=mock(AnswerDataService.class);

    when(answer.getId()).thenReturn("id");
    when(dataService.isQuestionIdValid(answer.getQuestionId())).thenReturn(true);
    when(dataService.createQuestion(answer)).thenReturn(new DbResponse(DbOperationStatus.FALIURE, answer));
    
    answerResource.answerDataService=dataService;
    
    Response res=answerResource.createAnswer(answer);
    
    assertEquals(res.getStatus(), 500);
  }
}
