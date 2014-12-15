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
        def user = new Date().time
        def email = "$user@email.com"
        println 'going to sign up user ' + user

        given: 'The visitor wants to sign up'
        to SignupPage
        at SignupPage

        when: 'He fills in the form'
        signup(user, 'pass', 'pass', email)

        then: 'He should be redirected to the welcome page'
        at WelcomePage
        assert welcomed(user)

        and: 'The user entry is added to the database'
        assert mongoDBAccessor.verifyUserExists(user, email)

        cleanup: 'Remove the user entry from database'
        mongoDBAccessor.deleteUser(user)
        assert mongoDBAccessor.verifyUserExists(user, email) == null
    }

}
