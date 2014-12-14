import geb.Browser
import geb.spock.GebReportingSpec

/**
 * Created by zaorish on 14/12/14.
 */
class SimpleBlogSpec extends GebReportingSpec {

    def 'Any user should access the blog homepage'() {
        Browser browser = new Browser()

        when: 'User accesses the blog homepage'
        browser.go('http://localhost:8082/')

        then: 'User should see the blog posts'
        Thread.sleep(1000)
        def title = browser.$('body').$('h1').text()
        assert title == 'My Blog'
        assert browser.$('h2').size() > 1
    }

    def 'Visitors should be allowed to register'() {
        def now = new Date().time
        println 'going to sign up user ' + now

        given: 'The visitor wants to sign up'
        to SignupPage
        at SignupPage

        when: 'He fills in the form'
        signup(now, 'pass', 'pass', "$now@email.com")

        then: 'He should be redirected to the welcome page'
        at WelcomePage
        assert welcomed(now)
    }

}
