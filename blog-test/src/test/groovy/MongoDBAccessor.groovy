import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBObject
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI

/**
 * Created by zaorish on 14/12/14.
 */
class MongoDBAccessor {

    private MongoClient mongoClient
    private DBCollection usersCollection
    private DBCollection postsCollection

    MongoDBAccessor() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost"))
        DB blogDB = mongoClient.getDB("blog")
        usersCollection = blogDB.getCollection("users")
        postsCollection = blogDB.getCollection("posts")
    }

    def verifyUserExists(username, email) {
        DBObject user = usersCollection.findOne(new BasicDBObject("_id", username.toString()).append("email", email.toString()))
        user
    }

    def close() {
        mongoClient.close()
    }

}
