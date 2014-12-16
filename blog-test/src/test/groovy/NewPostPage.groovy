import geb.Page

/**
 * Created by zaorish on 15/12/14.
 */
class NewPostPage extends Page {

    static url = 'http://localhost:8082/newpost'

    static at = { title == 'Create a new post' }

    static content = {
        subjectField { $("input", name: "subject") }
        bodyField { $("textarea", name: "body") }
        tagsField { $("input", name: "tags") }
        submit { $("input", type: "submit") }
    }

    def createPost(subject, body, tags) {
        subjectField.value(subject)
        bodyField.value(body)
        tagsField.value(tags)
        submit.click()
    }

}
