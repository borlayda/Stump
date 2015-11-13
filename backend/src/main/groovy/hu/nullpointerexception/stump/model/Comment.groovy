package hu.nullpointerexception.stump.model

import org.springframework.data.mongodb.core.mapping.DBRef

class Comment extends Entity {

    String text
    @DBRef User author
    List<Comment> comments

    Comment(String text, User author) {
        this.text = text
        this.author = author
    }

}