import geb.spock.GebReportingSpec
import spock.lang.Shared

/**
 * Created by zaorish on 14/12/14.
 * read more https://code.google.com/p/spock/wiki/SpockBasics
 */
class SimpleBlogSpec extends GebReportingSpec {

    @Shared MongoDBAccessor mongoDBAccessor

    def setupSpec() {
        mongoDBAccessor = new MongoDBAccessor()
    }

    def cleanupSpec() {
        mongoDBAccessor.close()
    }

    def 'Any user should access the blog homepage'() {
        when: 'User accesses the blog homepage'
        to BlogPage

        then: 'User should see the blog posts'
        at BlogPage
        assert hasPosts()
    }

    def 'Visitors should be allowed to register'() {
        def now = new Date().time
        println 'going to sign up user ' + now

        given: 'The visitor wants to sign up'
        to SignupPage
        at SignupPage

        when: 'He fills in the form'
        def email = "$now@email.com"
        signup(now, 'pass', 'pass', email)

        then: 'He should be redirected to the welcome page'
        at WelcomePage
        assert welcomed(now)

        and: 'The user entry is added to the database'
        assert mongoDBAccessor.verifyUserExists(now, email)
    }

}
