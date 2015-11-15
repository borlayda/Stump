package hu.nullpointerexception.stump.model

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

import java.text.SimpleDateFormat

@Document(collection = "comments")
class Comment extends Entity {

    String text
    Long timestamp
    @DBRef(lazy = true)
    User author
    @DBRef
    List<Comment> comments


    Comment() {
        this.text = text
        comments = []
    }

}