package hu.nullpointerexception.stump.service

import hu.nullpointerexception.stump.exception.DatabaseException
import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.model.Task
import hu.nullpointerexception.stump.model.Role
import hu.nullpointerexception.stump.repository.ProjectRepository
import hu.nullpointerexception.stump.repository.TaskRepository
import hu.nullpointerexception.stump.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

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
        task.project = project
        try {
            return taskRepository.save(task)
        } catch (DuplicateKeyException e) {
            throw new EntityAlreadyExistsException(e)
        }

    }

    def connectTaskAndUser(String taskId, String userId) {
        def user = userRepository.findOne(userId)
        def task = taskRepository.findOne(taskId)
        if (user == null) {
            throw new EntityNotFoundException("User not found.")
        }
        if (task == null) {
            throw new EntityNotFoundException("Task not found")
        }
        user.tasks.add(task);
        task.users.add(user);
        // Manual "transactions" mongodb sucks. This doesn't really work btw.
        user = userRepository.save(user)
        try {
            taskRepository.save(task)
        } catch (Exception e) {
            user.tasks.remove(task)
            userRepository.save(user)
            throw new DatabaseException("Could not save task.")
        }

    }

    def getTasksForUser(String userId) {
        def user = userRepository.findOne(userId)
        if (user.role == Role.ADMIN) {
            return taskRepository.findAll()
        }
        return user.tasks.toList()
    }

    def connectTaskAndProject(String taskId, String projectId) {
        def project = projectRepository.findOne(projectId)
        def task = taskRepository.findOne(taskId)
        if (project == null) {
            throw new EntityNotFoundException("Project not found.")
        }
        if (task == null) {
            throw new EntityNotFoundException("Task not found")
        }
        project.tasks.add(task);
        task.project = project;
        // Manual "transactions" mongodb sucks. This doesn't really work btw.
        project = projectRepository.save(project)
        try {
            taskRepository.save(task)
        } catch (Exception e) {
            project.tasks.remove(task)
            projectRepository.save(project)
            throw new DatabaseException("Could not save task.")
        }

    }

}
