package hu.nullpointerexception.stump.transport

import com.fasterxml.jackson.annotation.JsonInclude
import hu.nullpointerexception.stump.model.Comment
import hu.nullpointerexception.stump.model.Task
import hu.nullpointerexception.stump.model.TaskStatus

/**
 * Created by Márton Tóth
 */
class CommentJSONEntity extends JSONEntity<Comment> {

    String id
    String text
    String date
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String authorId
    @JsonInclude(JsonInclude.Include.NON_NULL)
    UserJSONEntity author
    String tasktId
    TaskJSONEntity task

    CommentJSONEntity(Comment source) {
        super(source)
        id = source.id
        text = source.text
        date = source.date
        author = new UserJSONEntity(source.author)
        task = new TaskJSONEntity(source.task)
    }

    CommentJSONEntity() {
    }

    @Override
    Comment createNewEntity() {
        def comment = new Comment(text)
        return comment
    }
}
