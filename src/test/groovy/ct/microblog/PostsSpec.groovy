package ct.microblog

import ct.microblog.support.AppDriver
import ct.microblog.support.MongoDriver
import spock.lang.Shared
import spock.lang.Specification

import java.util.stream.Collectors

class PostsSpec extends Specification {

    @Shared
    AppDriver app = new AppDriver()

    @Shared
    MongoDriver mongoDriver = new MongoDriver()

    def userId = "userId"

    def setupSpec() {
        app.start()
    }

    def cleanupSpec() {
        app.stop()
    }

    def "create new post"() {
        given:
        def newPost = [userId: userId,
                       body  : "Hello microblog"]

        when:
        def response = app.createNewPost(newPost)

        then:
        assert response.status == 201
    }



}
