package hu.nullpointerexception.stump.transport

import com.fasterxml.jackson.annotation.JsonInclude
import hu.nullpointerexception.stump.model.Comment
import hu.nullpointerexception.stump.model.Task
import hu.nullpointerexception.stump.model.TaskStatus

import java.text.SimpleDateFormat

/**
 * Created by Márton Tóth
 */
class CommentJSONEntity extends JSONEntity<Comment> {

    String id
    String text
    Long timestamp
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String authorId
    @JsonInclude(JsonInclude.Include.NON_NULL)
    UserJSONEntity author
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String taskId
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String commentId
    List<CommentJSONEntity> comments

    CommentJSONEntity(Comment source) {
        super(source)
        id = source.id
        text = source.text
        timestamp = source.timestamp
        author = new UserJSONEntity(source.author)
        comments = source.comments.collect {c -> new CommentJSONEntity(c)}
    }

    CommentJSONEntity() {
    }

    @Override
    Comment createNewEntity() {
        def comment = new Comment()
        comment.text = text
        comment.timestamp = System.currentTimeMillis()
        return comment
    }
}
