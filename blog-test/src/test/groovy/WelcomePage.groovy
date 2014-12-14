import geb.Page

/**
 * Created by zaorish on 14/12/14.
 */
class WelcomePage extends Page {

    static url = 'http://localhost:8082/welcome'

    static at = { title == 'Welcome' }

    def welcomed(user) {
        return $('body').text().startsWith("Welcome $user")
    }

}
