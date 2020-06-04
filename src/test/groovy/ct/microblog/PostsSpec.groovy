package ct.microblog

import ct.microblog.support.AppDriver
import ct.microblog.support.MongoDriver
import spock.lang.Shared
import spock.lang.Specification

import java.util.stream.Collectors

class PostsSpec extends Specification {

    @Shared
    AppDriver app = new AppDriver()

    def setupSpec() {
        app.start()
    }

    def cleanupSpec() {
        app.stop()
    }



}
