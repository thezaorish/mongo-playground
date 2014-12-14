import geb.Browser
import geb.spock.GebReportingSpec

/**
 * Created by zaorish on 14/12/14.
 */
class SimpleBlogSpec extends GebReportingSpec {

    def 'going to homepage'() {
        Browser browser = new Browser()

        when:
        browser.go('http://localhost:8082/')

        then:
        Thread.sleep(1000)
        def title = browser.$('body').$('h1').text()
        assert title == 'My Blog'
    }

}
