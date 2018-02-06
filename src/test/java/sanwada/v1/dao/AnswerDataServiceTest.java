package sanwada.v1.dao;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Iterator;

import org.bson.Document;

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

    DataSourceClient<Document> client = mock(MongoDataSourceClient.class);
    Iterable<Document> iterable=mock(Iterable.class);
    Iterator<Document> iterator = mock(Iterator.class);

    when(client.find(answerDataService.filters)).thenReturn(iterable);
    when(iterable.iterator()).thenReturn(iterator);
    when(iterator.hasNext()).thenReturn(true);
    answerDataService.client = client;

    DbResponse res = answerDataService.createAnswer(ans);
    assertEquals(res.getStatus(), DbOperationStatus.SUCCESS);
  }

  @Test
  public void createAnswer_questionIdNotAvailable_NO_SUCH_RECORD() {
    Answer ans = mock(Answer.class);

    DataSourceClient<Document> client = mock(MongoDataSourceClient.class);
    Iterable<Document> iterable=mock(Iterable.class);
    Iterator<Document> iterator = mock(Iterator.class);

    when(client.find(answerDataService.filters)).thenReturn(iterable);
    when(iterable.iterator()).thenReturn(iterator);
    when(iterator.hasNext()).thenReturn(false);
    answerDataService.client = client;

    DbResponse res = answerDataService.createAnswer(ans);
    assertEquals(res.getStatus(), DbOperationStatus.NO_SUCH_RECORD);
    assertEquals(res.getEntity(), ans);
  }
}
