package hu.nullpointerexception.stump.service

import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.model.Comment
import hu.nullpointerexception.stump.model.Task
import hu.nullpointerexception.stump.model.TaskStatus
import hu.nullpointerexception.stump.repository.CommentRepository
import hu.nullpointerexception.stump.repository.ProjectRepository
import hu.nullpointerexception.stump.repository.TaskRepository
import hu.nullpointerexception.stump.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct
import java.util.concurrent.ThreadPoolExecutor

/**
 * Created by Márton Tóth
 */
@Service
class TaskService {

    private ProjectRepository projectRepository
    private UserRepository userRepository
    private TaskRepository taskRepository
    private CommentRepository commentRepository
    private CommentService commentService

    @Autowired
    TaskService(TaskRepository taskRepository, UserRepository userRepository, CommentRepository commentRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository
        this.userRepository = userRepository
        this.projectRepository = projectRepository
        this.commentRepository = commentRepository
        this.commentService = new CommentService(commentRepository, taskRepository, userRepository)
    }

    def addTask(Task task, String userId, String projectId) {
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException("User with id '" + userId + "' not found.")
        }
        task.owner = user
        task.users = new ArrayList<>()
        task.users.add(user)
        def project = projectRepository.findOne(projectId)
        if (project == null) {
            throw new EntityNotFoundException("Project with id '" + projectId + "' not found.")
        }
        try {
            task = taskRepository.save(task)
        } catch (DuplicateKeyException e) {
            throw new EntityAlreadyExistsException(e)
        }
        project.tasks.add(task)
        projectRepository.save(project)
    }

    def changeTask(String taskId, String title, String descrition, String ownerId, String status) {
        def task = taskRepository.findOne(taskId)
        if (task == null) {
            throw new EntityNotFoundException("Task not found")
        }
        def owner = userRepository.findOne(ownerId)
        if (owner == null) {
            throw new EntityNotFoundException("User with id '" + ownerId + "' not found.")
        }
        task.status = TaskStatus.valueOf(status)
        task.title = title
        task.description = descrition
        task.owner = owner
        taskRepository.save(task)

    }

    def changeStatus(String taskId, String status) {
        def task = taskRepository.findOne(taskId)
        if (task == null) {
            throw new EntityNotFoundException("Task not found")
        }
        task.status = TaskStatus.valueOf(status)
        taskRepository.save(task)

    }

    def attachUser(String taskId, String userId){
        def task = taskRepository.findOne(taskId)
        if (task == null) {
            throw new EntityNotFoundException("Task not found")
        }
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException("User not found")
        }
        task.users.add(user)
        taskRepository.save(task)
    }

    def detachUser(String taskId, String userId){
        def task = taskRepository.findOne(taskId)
        if (task == null) {
            throw new EntityNotFoundException("Task not found")
        }
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException("User not found")
        }
        task.users.remove(user)
        taskRepository.save(task)
    }

    def addWorkTime(String taskId, Long workTimeAddition) {
        def task = taskRepository.findOne(taskId)
        if (task == null) {
            throw new EntityNotFoundException("Task not found")
        }
        task.workTime = task.workTime + workTimeAddition
        taskRepository.save(task)

    }

    def delete(String taskId) {
        def task = taskRepository.findOne(taskId)
        if (task == null){
            throw new EntityNotFoundException("Task not found.")
        }
        task.comments.each {
            def comment = commentRepository.findOne(it.id)
            if (comment != null) {
                commentService.delete(comment.id)
            }
        }
        taskRepository.delete(task)
    }

    def getAll() {
        taskRepository.findAll()
    }

    def getTask(String taskId) {
        taskRepository.findOne(taskId)
    }
}
