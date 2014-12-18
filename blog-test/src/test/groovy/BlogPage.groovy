import geb.Page

/**
 * Created by zaorish on 14/12/14.
 */
class BlogPage extends Page {

    static url = 'http://localhost:8082/'

    static at = { $('body').$('h1').text() == 'My Blog' }

    def hasPosts() {
        return $('h2').size() > 1
    }

    def navigateToBlogPost(pos) {
        String fullUrl = $("h2", pos - 1).find('a').@href
        def permalink = fullUrl.substring(fullUrl.lastIndexOf('/') + 1, fullUrl.length())
        browser.go(fullUrl)
        permalink
    }

}
