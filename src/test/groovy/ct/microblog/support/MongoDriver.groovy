package ct.microblog.support

import com.mongodb.client.MongoClients
import org.bson.Document

class MongoDriver {
    def dbserver = "mongodb://localhost"
    def mongo = MongoClients.create(dbserver).getDatabase("posts_test")

    def dropDB() {
        mongo.getCollection("posts").deleteMany(new Document())
    }
}
