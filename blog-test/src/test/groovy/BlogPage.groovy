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

}
