package hu.nullpointerexception.stump.service

import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.model.Task
import hu.nullpointerexception.stump.model.TaskStatus
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

    private TaskRepository taskRepository
    private UserRepository userRepository
    private ProjectRepository projectRepository

    @Autowired
    TaskService(TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository
        this.userRepository = userRepository
        this.projectRepository = projectRepository
    }

//    @PostConstruct
//    def postConstruct() {
//        def task = new Task()
//        task.title = "Krumplipucolas"
//        task.description = "Meg kell pucolni a taskokat"
//        task.status = TaskStatus.OPEN;
//        addTask(task, "5647435aabce860542707d73", "56478674abce5d43d49e0014")
//    }

    def addTask(Task task, String userId, String projectId) {
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException("User with id '" + userId + "' not found.")
        }
        task.owner = user
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

    def changeStatus(String taskId, String status) {
        def task = taskRepository.findOne(taskId)
        if (task == null) {
            throw new EntityNotFoundException("Task not found")
        }
        task.status = TaskStatus.valueOf(status);
        taskRepository.save(task)

    }

    def getAll() {
        taskRepository.findAll()
    }

    def getTask(String taskId) {
        taskRepository.findOne(taskId)
    }
}
