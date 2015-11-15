package hu.nullpointerexception.stump.controller

import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.exception.StumpException
import hu.nullpointerexception.stump.security.StumpPrincipal
import hu.nullpointerexception.stump.service.CommentService
import hu.nullpointerexception.stump.transport.GenericResponse
import hu.nullpointerexception.stump.transport.CommentJSONEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * Created by Márton Tóth
 */
@RestController
@RequestMapping("/api/comments")
class CommentController {

    private CommentService commentService

    @Autowired
    CommentController(CommentService commentService) {
        this.commentService = commentService
    }

    @RequestMapping(method = RequestMethod.POST)
    def addComment(@RequestBody CommentJSONEntity commentJSONEntity) {
        if (commentJSONEntity.commentId == null) {
            commentService.addCommentToTask(
                    commentJSONEntity.createNewEntity(),
                    commentJSONEntity.authorId,
                    commentJSONEntity.taskId)
        } else {
            commentService.addCommentToComment(
                    commentJSONEntity.createNewEntity(),
                    commentJSONEntity.authorId,
                    commentJSONEntity.commentId)
        }
        return GenericResponse.okResponse()
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{commentId}")
    def addComment(@PathVariable("commentId") String commentId) {
        commentService.delete(commentId)
        return GenericResponse.okResponse()
    }

    @RequestMapping
    List<CommentJSONEntity> getAll(@AuthenticationPrincipal StumpPrincipal stumpPrincipal) {
        commentService.getCommentsForTask(stumpPrincipal.task.id).collect {u -> new CommentJSONEntity(u)}
    }

    @ExceptionHandler(StumpException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    def handleStumpException(Exception e) {
        return new GenericResponse("ERROR", e.getMessage())
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    def handleEntityNotFound(Exception e) {
        return new GenericResponse("ERROR", "Entity not found: " + e.getMessage())
    }

    @ExceptionHandler([EntityAlreadyExistsException.class])
    @ResponseStatus(HttpStatus.CONFLICT)
    def handleEntityAlreadyExists() {
        return new GenericResponse("ERROR", "Entity already exists.");
    }

}
