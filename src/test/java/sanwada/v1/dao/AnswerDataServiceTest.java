package sanwada.v1.dao;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mockito.invocation.InvocationOnMock;

import static org.mockito.Mockito.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import sanwada.v1.entity.Answer;
import sanwada.v1.entity.DbResponse;

public class AnswerDataServiceTest {

  AnswerDataService answerDataService;

  @BeforeMethod
  public void beforeMethod() {
    answerDataService = new AnswerDataService();
  }

  /*
   * Start of test cases for createAnswer method
   */
  @Test
  public void createAnswer_questionIdAvailable_SUCCESS() {
    Answer ans = mock(Answer.class);
    when(ans.getQuestionId()).thenReturn("5a0e38af8bce7d09e8436aff");

    DataSourceClient<Document> client = mock(MongoDataSourceClient.class);
    Iterable<Document> iterable = mock(Iterable.class);
    Iterator<Document> iterator = mock(Iterator.class);

    when(client.find(answerDataService.filters)).thenReturn(iterable);
    when(iterable.iterator()).thenReturn(iterator);
    when(iterator.hasNext()).thenReturn(true);

    // The document passed to the client.create() should contain a _id
    doAnswer(new org.mockito.stubbing.Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        Object[] args = invocation.getArguments();
        Document res = (Document) args[0];
        res.append("_id", "5a0e38af8bce7d09e8436aff");
        return null; // void method, so return null
      }
    }).when(client).insert(any(Document.class));
    answerDataService.client = client;

    DbResponse res = answerDataService.createAnswer(ans);
    assertEquals(res.getStatus(), DbOperationStatus.SUCCESS);
  }

  @Test
  public void createAnswer_questionIdNotAvailable_NO_SUCH_RECORD() {
    Answer ans = mock(Answer.class);
    when(ans.getId()).thenReturn("5a0e38af8bce7d09e8436aff");
    when(ans.getQuestionId()).thenReturn("5a0e38af8bce7d09e8436aff");

    DataSourceClient<Document> client = mock(MongoDataSourceClient.class);
    Iterable<Document> iterable = mock(Iterable.class);
    Iterator<Document> iterator = mock(Iterator.class);

    when(client.find(answerDataService.filters)).thenReturn(iterable);
    when(iterable.iterator()).thenReturn(iterator);
    when(iterator.hasNext()).thenReturn(false);
    answerDataService.client = client;

    DbResponse res = answerDataService.createAnswer(ans);
    assertEquals(res.getStatus(), DbOperationStatus.NO_SUCH_RECORD);
    assertEquals(res.getEntity(), ans);
  }

  @Test
  public void createAnswer_NA_questionIdShouldBeTranslatedToObjectId() {
    Answer ans = mock(Answer.class);
    when(ans.getQuestionId()).thenReturn("5a0e38af8bce7d09e8436aff");

    DataSourceClient<Document> client = mock(MongoDataSourceClient.class);
    Iterable<Document> iterable = mock(Iterable.class);
    Iterator<Document> iterator = mock(Iterator.class);

    when(client.find(answerDataService.filters)).thenReturn(iterable);
    when(iterable.iterator()).thenReturn(iterator);
    when(iterator.hasNext()).thenReturn(false);
    answerDataService.client = client;

    answerDataService.createAnswer(ans);

    assertNotNull(answerDataService.filters.get("_id"));
    assertEquals(answerDataService.filters.get("_id").getClass(), ObjectId.class);
  }

  @Test
  public void createAnswer_questionIdAvailable_answerIdShouldNotBeNull() {
    Answer ans = mock(Answer.class);
    Document document = mock(Document.class);
    when(ans.getQuestionId()).thenReturn("5a0e38af8bce7d09e8436aff");

    DataSourceClient<Document> client = mock(MongoDataSourceClient.class);
    Iterable<Document> iterable = mock(Iterable.class);
    Iterator<Document> iterator = mock(Iterator.class);

    when(client.find(answerDataService.filters)).thenReturn(iterable);
    when(iterable.iterator()).thenReturn(iterator);
    when(iterator.hasNext()).thenReturn(true);

    doAnswer(new org.mockito.stubbing.Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        Object[] args = invocation.getArguments();
        Document res = (Document) args[0];
        res.append("_id", "5a0e38af8bce7d09e8436aff");
        return null; // void method, so return null
      }
    }).when(client).insert(any(Document.class));
    answerDataService.client = client;

    DbResponse dbResponse = answerDataService.createAnswer(ans);
    assertEquals(dbResponse.getStatus(), DbOperationStatus.SUCCESS);
    assertNotNull(((Answer) dbResponse.getEntity()).getId());
  }
  /*
   * End of test cases for createAnswer
   */

  /*
   * Start of test cases for getAnswer method
   */
  @Test
  public void getAnswer_answerIdAvailable_successWithAnswerObject() {
    Answer answer = mock(Answer.class);
    when(answer.getId()).thenReturn("5a0e38af8bce7d09e8436aff");

    DataSourceClient<Document> client = mock(MongoDataSourceClient.class);
    Iterable<Document> iterable = mock(Iterable.class);
    Iterator<Document> iterator = mock(Iterator.class);

    Document doc = new Document().append("_id", answer.getId()).append("content", "content")
            .append("questionId", "5a0e38af8bce7d09e8436aff").append("time", 1L);
    when(iterator.next()).thenReturn(doc);
    when(iterator.hasNext()).thenReturn(true);
    when(iterable.iterator()).thenReturn(iterator);
    when(client.find(any(LinkedHashMap.class))).thenReturn(iterable);

    answerDataService.client = client;

    DbResponse dbResponse = answerDataService.getAnswer("5a0e38af8bce7d09e8436aff");
    assertEquals(dbResponse.getStatus(), DbOperationStatus.SUCCESS);
    assertEquals(dbResponse.getEntity().getClass(), Answer.class);
    assertNotNull(((Answer) dbResponse.getEntity()).getId());
    assertNotNull(((Answer) dbResponse.getEntity()).getContent());
    assertNotNull(((Answer) dbResponse.getEntity()).getQuestionId());
    assertNotNull(((Answer) dbResponse.getEntity()).getTimestamp());
  }

  @Test
  public void getAnswer_AnswerIdNotAvailable_NO_SUCH_RECORD_FOUND() {
    DataSourceClient<Document> client = mock(MongoDataSourceClient.class);
    Iterable<Document> iterable = mock(Iterable.class);
    Iterator<Document> iterator = mock(Iterator.class);

    when(iterator.next()).thenThrow(NoSuchElementException.class);
    when(iterator.hasNext()).thenReturn(false);
    when(iterable.iterator()).thenReturn(iterator);
    when(client.find(any(LinkedHashMap.class))).thenReturn(iterable);

    answerDataService.client = client;

    DbResponse dbResponse = answerDataService.getAnswer("5a0e38af8bce7d09e8436aff");
    assertEquals(dbResponse.getStatus(), DbOperationStatus.NO_SUCH_RECORD);
  }
}
