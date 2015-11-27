package hu.nullpointerexception.stump.service

import hu.nullpointerexception.stump.exception.DatabaseException
import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.model.Role
import hu.nullpointerexception.stump.model.Comment
import hu.nullpointerexception.stump.repository.TaskRepository
import hu.nullpointerexception.stump.repository.CommentRepository
import hu.nullpointerexception.stump.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

/**
 * Created by Márton Tóth
 */
@Service
class CommentService {

    private TaskRepository taskRepository
    private UserRepository userRepository
    private CommentRepository commentRepository

    @Autowired
    CommentService(CommentRepository commentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository
        this.userRepository = userRepository
        this.commentRepository = commentRepository
    }

    def addCommentToTask(Comment comment, String authorId, String taskId) {
        def user = userRepository.findOne(authorId)
        if (user == null) {
            throw new EntityNotFoundException("User with id '" + authorId + "' not found.")
        }
        comment.author = user
        def task = taskRepository.findOne(taskId)
        if (task == null) {
            throw new EntityNotFoundException("Task with id '" + taskId + "' not found.")
        }
        try {
            comment = commentRepository.save(comment)
        } catch (DuplicateKeyException e) {
            throw new EntityAlreadyExistsException(e)
        }
        task.comments << comment
        taskRepository.save(task)
    }

    def addCommentToComment(Comment comment, String authorId, String commentId) {
        def user = userRepository.findOne(authorId)
        if (user == null) {
            throw new EntityNotFoundException("User with id '" + authorId + "' not found.")
        }
        comment.author = user
        def parentComment = commentRepository.findOne(commentId)
        if (parentComment == null) {
            throw new EntityNotFoundException("Comment with id '" + commentId + "' not found.")
        }
        try {
            comment = commentRepository.save(comment)
        } catch (DuplicateKeyException e) {
            throw new EntityAlreadyExistsException(e)
        }
        parentComment.comments << comment
        commentRepository.save(parentComment)
    }

    def connectCommentAndUser(String commentId, String userId) {
        def user = userRepository.findOne(userId)
        def comment = commentRepository.findOne(commentId)
        if (user == null) {
            throw new EntityNotFoundException("User not found.")
        }
        if (comment == null) {
            throw new EntityNotFoundException("Comment not found")
        }
        comment.author = user
        // Manual "transactions" mongodb sucks. This doesn't really work btw.
        try {
            commentRepository.save(comment)
        } catch (Exception e) {
            throw new DatabaseException("Could not save comment.")
        }

    }

    def getCommentsForTask(String taskId) {
        def task = userRepository.findOne(taskId)
        return task.comments.toList()
    }

    def connectCommentAndTask(String commentId, String taskId) {
        def task = taskRepository.findOne(taskId)
        def comment = commentRepository.findOne(commentId)
        if (task == null) {
            throw new EntityNotFoundException("Task not found.")
        }
        if (comment == null) {
            throw new EntityNotFoundException("Comment not found")
        }
        task.comments.add(comment);
        comment.task = task;
        // Manual "transactions" mongodb sucks. This doesn't really work btw.
        task = taskRepository.save(task)
        try {
            commentRepository.save(comment)
        } catch (Exception e) {
            task.comments.remove(comment)
            taskRepository.save(task)
            throw new DatabaseException("Could not save comment.")
        }

    }

    def delete(String commentId) {
        def comment = commentRepository.findOne(commentId)
        if (comment == null) {
            throw new EntityNotFoundException("Comment not found.")
        }
        for (Comment com : comment.comments){
            delete(com.id)
        }
        commentRepository.delete(comment)
    }
}
