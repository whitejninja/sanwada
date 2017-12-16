package sanwada.v1.rest;

import static org.testng.Assert.*;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import sanwada.v1.entity.Question;

public class QuestionResourceTest {

  QuestionResource questionResource;

  @BeforeMethod
  void beforeMethod() {
    questionResource = new QuestionResource();
  }

  @Test
  public void createQuestion_titleIsAvailable_201StatusCode() {
    Question question = mock(Question.class);
    when(question.getTitle()).thenReturn("test_title");
    when(question.getContent()).thenReturn("test_content");
    when(question.getUserAlias()).thenReturn("test_alias");

    Response res = questionResource.getQuestion(null, "test_title");
    if (res.getStatus() == 200) {
      String id = ((Question) res.getEntity()).getId();
      questionResource.deleteQuestion(id);
    }
    res = questionResource.createQuestion(question);
    assertNotNull(res);
    assertEquals(res.getStatus(), 201);
  }

  @Test
  public void createQuestion_titleIsNotAvailable_409StatusCode() {
    Question question = mock(Question.class);
    when(question.getTitle()).thenReturn("test_title");
    when(question.getContent()).thenReturn("test_content");
    when(question.getUserAlias()).thenReturn("test_alias");

    Response res = questionResource.getQuestion(null, "test_title");
    if (res.getStatus() == 200) {
      questionResource.createQuestion(question);
    }
    res = questionResource.createQuestion(question);
    assertNotNull(res);
    assertEquals(res.getStatus(), 409);
  }
}
