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
  public void createQuestion() {
    Question question = mock(Question.class);
    when(question.getTitle()).thenReturn("test_title");
    when(question.getContent()).thenReturn("test_content");
    when(question.getUserAlias()).thenReturn("test_alias");

    Response res = questionResource.createQuestion(question);
    assertNotNull(res);
    assertEquals(res.getStatus(), 201);
  }

}
