package sanwada.v1.dao;

import static com.mongodb.client.model.Filters.eq;
import java.util.LinkedHashMap;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

import sanwada.v1.entity.DbResponse;
import sanwada.v1.entity.Question;

public class QuestionDataService implements QuestionDAO {

	private MongoCollection<Document> collection;
	private DbResponse dbResponse;

	public QuestionDataService() {
		try {
			// This should be removed because of high coupling
			DataSourceClient<Document> client = new MongoDataSourceClient();
			this.collection= (MongoCollection<Document>) client.getCollection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public DbResponse addQuestion(Question question) {
		try {
			Document document = new Document("alias", question.getUserAlias()).append("title", question.getTitle())
					.append("content", question.getContent());

			// check duplicate title
			Boolean titleAvailable = this.collection.find(eq("title", question.getTitle())).first() == null;

			if (titleAvailable) {
				// Generate dbObject to be persist to the database
				Long postedTime = System.currentTimeMillis();
				document.append("time", postedTime);

				// Persist to database
				collection.insertOne(document);

				// Convert _id object to hexadecimal string
				Object idObj = document.get("_id");
				String idStr = (String) idObj.toString();

				// Generate response object
				Question createdQuestion=new Question();
				createdQuestion.setId(idStr);
				createdQuestion.setUserAlias(document.getString("alias"));
				createdQuestion.setTitle(document.getString("title"));
				createdQuestion.setContent(document.getString("content"));
				createdQuestion.setTimeStamp(document.getLong("time"));
				
				// Build response upon success
				this.dbResponse = new DbResponse(DbOperationStatus.SUCCESS, createdQuestion);
				return this.dbResponse;
			} else {
				// Build response upon failure
				this.dbResponse = new DbResponse(DbOperationStatus.DUPLICATE_ENTRY, question);
				// Send response upon failure
				return this.dbResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.dbResponse = new DbResponse(DbOperationStatus.FALIURE, null);
			return dbResponse;
		}
	}

	@Override
	public DbResponse getQuestion(String id) {
		return null;
	}

	@Override
	public DbResponse removeQuestion(String id) {
		return null;
	}

	@Override
	public DbResponse updateQuestion(String id, Question question) {
		System.out.println("id to be update: " + id);
		System.out.println("alias: " + question.getUserAlias());
		System.out.println("title: " + question.getTitle());
		System.out.println("content: " + question.getContent());
		return null;
	}

}
