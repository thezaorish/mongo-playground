import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBObject
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.WriteResult

/**
 * Created by zaorish on 14/12/14.
 */
class MongoDBAccessor {

    private MongoClient mongoClient
    private DBCollection usersCollection
    private DBCollection postsCollection

    MongoDBAccessor() {
        mongoClient = new MongoClient(new MongoClientURI('mongodb://localhost'))
        DB blogDB = mongoClient.getDB('blog')
        usersCollection = blogDB.getCollection('users')
        postsCollection = blogDB.getCollection('posts')
    }

    def verifyUserExists(username, email) {
        DBObject user = usersCollection.findOne(new BasicDBObject('_id', username.toString()).append('email', email.toString()))
        user
    }

    def verifyPostExists(user, subject, body, tags) {
        DBObject post = postsCollection.findOne(new BasicDBObject('title', subject.toString()))
        if (post == null) {
            return null
        }
        if (user != post.get('author')) {
            return null
        }
        if (body != post.get('body')) {
            return null
        }
        if (tags != post.get('tags').collect().join(', ')) {
            return null
        }
        post
    }

    def verifyCommentExists(user, permalink, body) {
        DBObject post = postsCollection.findOne(new BasicDBObject('permalink', permalink.toString()).append('comments.author', user.toString()).append('comments.body', body.toString()))
        post
    }

    def deleteUser(username) {
        WriteResult result = usersCollection.remove(new BasicDBObject('_id', username.toString()))
    }

    def deletePost(subject) {
        WriteResult result = postsCollection.remove(new BasicDBObject('title', subject.toString()))
    }

    def deleteComment(user, email, body, permalink) {
        BasicDBObject comment = new BasicDBObject('author', user.toString()).append('body', body.toString());
        if (email) {
            comment.append('email', email.toString());
        }

        WriteResult result = postsCollection.update(new BasicDBObject('permalink', permalink),
                new BasicDBObject('$pop', new BasicDBObject('comments', comment)), false, false);
    }

    def close() {
        mongoClient.close()
    }

}
