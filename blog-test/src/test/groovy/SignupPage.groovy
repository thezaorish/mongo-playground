import geb.Page

/**
 * Created by zaorish on 14/12/14.
 */
class SignupPage extends Page {

    static url = 'http://localhost:8082/signup'

    static at = { title == 'Sign Up' }

    static content = {
        usernameField { $("input", name: "username") }
        passwordField { $("input", name: "password") }
        verifyField { $("input", name: "verify") }
        emailField { $("input", name: "email") }
        submit { $("input", type: "submit") }
    }

    def signup(username, password, verify, email) {
        usernameField.value(username)
        passwordField.value(password)
        verifyField.value(verify)
        emailField.value(email)
        submit.click()
    }

}
