package page

import geb.Page

/**
 * Created by zaorish on 15/12/14.
 */
class BlogPostPage extends Page {

    // static url = 'http://localhost:8082/post'

    static at = { title == 'Blog Post' }

    static content = {
        commentNameField { $("input", name: "commentName") }
        commentEmailField { $("input", name: "commentEmail") }
        commentBodyField { $("textarea", name: "commentBody") }
        submit { $("input", type: "submit", value: "Submit") }
    }

    def createComment(name, email, body) {
        commentNameField.value(name)
        commentEmailField.value(email)
        commentBodyField.value(body)
        submit.click()
    }

}
