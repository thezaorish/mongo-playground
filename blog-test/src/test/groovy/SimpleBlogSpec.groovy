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
        println "going to sign up user $user"

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

    def 'Registered users should be allowed to create new posts'() {
        given: 'The user is signed in'
        def user = 'poster'
        def email = 'poster@blog.com'
        signInUser(user, email)

        when: 'He chooses to create a new blog post'
        to NewPostPage
        at NewPostPage
        def subject = 'page objects are awesome'
        def body = 'page objects really improve the readability of the tests'
        def tags = 'pop, awesome, test'
        createPost(subject, body, tags)

        then: 'The blog post should be saved to the database'
        at BlogPostPage
        assert mongoDBAccessor.verifyPostExists(user, subject, body, tags)

        cleanup: 'Remove the post and the user entry from database'
        mongoDBAccessor.deletePost(subject)
        assert mongoDBAccessor.verifyPostExists(user, subject, body, tags) == null
        mongoDBAccessor.deleteUser(user)
        assert mongoDBAccessor.verifyUserExists(user, email) == null
    }

    def 'Registered users should be allowed to comment on posts'() {
        given: 'The user is signed in'
        def user = 'commenter'
        def email = 'commenter@blog.com'
        signInUser(user, email)

        when: 'He chooses to comment on a blog post'
        to BlogPage
        def permalink = navigateToBlogPost(1)
        at BlogPostPage
        def body = 'this is the post I am commenting on'
        createComment(user, email, body)

        then: 'His comment should be saved to the database'
        assert mongoDBAccessor.verifyCommentExists(user, permalink, body)

        cleanup: 'Remove the comment and the user entry from database'
        mongoDBAccessor.deleteComment(user, email, body, permalink)
        mongoDBAccessor.deleteUser(user)
        assert mongoDBAccessor.verifyUserExists(user, email) == null
    }

    def signInUser(user, email) {
        println "going to sign up user $user"

        to SignupPage
        at SignupPage

        signup(user, 'pass', 'pass', email)
        at WelcomePage
        assert mongoDBAccessor.verifyUserExists(user, email)
    }

}
