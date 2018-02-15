package sanwada.v1.dao;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Iterator;

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
    
    //The document passed to the client.create() should contain a _id
    doAnswer(new org.mockito.stubbing.Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        Object[] args = invocation.getArguments();
        Document res= (Document) args[0];
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
    Document document=mock(Document.class);
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
        Document res= (Document) args[0];
        res.append("_id", "5a0e38af8bce7d09e8436aff");
        return null; // void method, so return null
      }
    }).when(client).insert(any(Document.class));
    answerDataService.client = client;

    DbResponse dbResponse = answerDataService.createAnswer(ans);
    assertEquals(dbResponse.getStatus(), DbOperationStatus.SUCCESS);
    assertNotNull(dbResponse.getEntity());
    assertNotNull(((Answer) dbResponse.getEntity()).getId());
  }
}
