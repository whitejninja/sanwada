package sanwada.v1.dao;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
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
			this.collection = (MongoCollection<Document>) client.getCollection();
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
				Question createdQuestion = new Question();
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

		try {
			ObjectId objId = new ObjectId(id);
			Document doc = collection.find(eq("_id", objId)).first();

			Question returnedQuestion = new Question();
			returnedQuestion.setId(id);
			returnedQuestion.setTitle(doc.getString("title"));
			returnedQuestion.setContent(doc.getString("content"));
			returnedQuestion.setUserAlias(doc.getString("alias"));
			returnedQuestion.setTimeStamp(doc.getLong("time"));

			this.dbResponse = new DbResponse(DbOperationStatus.SUCCESS, returnedQuestion);
		} catch (NullPointerException ex) {
			this.dbResponse = new DbResponse(DbOperationStatus.NO_SUCH_RECORD, id);
		} catch (java.lang.IllegalArgumentException ex) {
			this.dbResponse = new DbResponse(DbOperationStatus.NO_SUCH_RECORD, id);
		}

		return this.dbResponse;
	}

	@Override
	public DbResponse removeQuestion(String id) {
		return null;
	}

	@Override
	public DbResponse updateQuestion(String id, Question question) {

		ObjectId objectId = new ObjectId(id);
		try {

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("$set",
					new BasicDBObject().append("title", question.getTitle()).append("content", question.getContent()));

			BasicDBObject searchQuery = new BasicDBObject().append("_id", objectId);

			collection.updateOne(searchQuery, newDocument);

			Document updatedDocument = collection.find(eq("_id", objectId)).first();
			Question updatedQuestion = question;
			updatedQuestion.setId(id);
			updatedQuestion.setUserAlias(updatedDocument.getString("alias"));
			updatedQuestion.setTitle(updatedDocument.getString("title"));
			updatedQuestion.setContent(updatedDocument.getString("content"));
			updatedQuestion.setTimeStamp(updatedDocument.getLong("time"));

			this.dbResponse = new DbResponse(DbOperationStatus.SUCCESS, updatedQuestion);

		} catch (Exception e) {

			e.printStackTrace();
			this.dbResponse = new DbResponse(DbOperationStatus.FALIURE, null);
			return dbResponse;
		}

		return dbResponse;
	}

}
