package hu.nullpointerexception.stump.model

import org.springframework.data.mongodb.core.mapping.DBRef

import java.text.SimpleDateFormat

class Comment extends Entity {

    String text
    String date
    @DBRef(lazy = true)
    User author
    @DBRef(lazy = true)
    List<Comment> comments
    @DBRef(lazy = true)
    Task task

    Comment(String text) {
        this.text = text
        this.date = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
    }

}