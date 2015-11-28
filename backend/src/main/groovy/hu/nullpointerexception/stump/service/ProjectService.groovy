package hu.nullpointerexception.stump.service

import hu.nullpointerexception.stump.exception.DatabaseException
import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.model.Comment
import hu.nullpointerexception.stump.model.Project
import hu.nullpointerexception.stump.model.ProjectStatus
import hu.nullpointerexception.stump.model.Role
import hu.nullpointerexception.stump.model.Task
import hu.nullpointerexception.stump.model.TaskStatus
import hu.nullpointerexception.stump.repository.CommentRepository
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
class ProjectService {

    private ProjectRepository projectRepository
    private UserRepository userRepository
    private TaskRepository taskRepository
    private CommentRepository commentRepository
    private TaskService taskService

    @Autowired
    ProjectService(ProjectRepository projectRepository,
                   UserRepository userRepository,
                   TaskRepository taskRepository,
                   CommentRepository commentRepository) {
        this.projectRepository = projectRepository
        this.userRepository = userRepository
        this.taskService = new TaskService(taskRepository, userRepository, projectRepository)
    }

    def addProject(Project project, String userId) {
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException("User with id '" + userId + "' not found.")
        }
        project.owner = user
        project.users = new ArrayList<>()
        project.users.add(user)
        try {
            return projectRepository.save(project)
        } catch (DuplicateKeyException e) {
            throw new EntityAlreadyExistsException(e)
        }

    }

    def changeProject(String projectId, String title, String descrition, String ownerId, String status) {
        def project = projectRepository.findOne(projectId)
        if (project == null) {
            throw new EntityNotFoundException("Task not found")
        }
        def owner = userRepository.findOne(ownerId)
        if (owner == null) {
            throw new EntityNotFoundException("User with id '" + ownerId + "' not found.")
        }
        project.status = ProjectStatus.valueOf(status)
        project.title = title
        project.description = descrition
        project.owner = owner
        projectRepository.save(project)

    }

    def attachUser(String projectId, String userId){
        def project = projectRepository.findOne(projectId)
        if (project == null) {
            throw new EntityNotFoundException("Project not found")
        }
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException("User not found")
        }
        project.users.add(user)
        projectRepository.save(project)
    }

    def detachUser(String projectkId, String userId){
        def project = projectRepository.findOne(projectId)
        if (project == null) {
            throw new EntityNotFoundException("Project not found")
        }
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException("User not found")
        }
        project.users.remove(user)
        projectRepository.save(project)
    }

    def connectProjectAndUser(String projectId, String userId) {
        def user = userRepository.findOne(userId)
        def project = projectRepository.findOne(projectId)
        if (user == null) {
            throw new EntityNotFoundException("User not found.")
        }
        if (project == null) {
            throw new EntityNotFoundException("Project not found")
        }
        user.projects.add(project);
        project.users.add(user);
        // Manual "transactions" mongodb sucks. This doesn't really work btw.
        user = userRepository.save(user)
        try {
            projectRepository.save(project)
        } catch (Exception e) {
            user.projects.remove(project)
            userRepository.save(user)
            throw new DatabaseException("Could not save project.")
        }

    }

    def changeStatus(String projectId, String status) {
        def project = projectRepository.findOne(projectId)
        if (project == null) {
            throw new EntityNotFoundException("Project not found")
        }
        project.status = ProjectStatus.valueOf(status);
        projectRepository.save(project)

    }

    def delete(String projectId) {
        Project project = projectRepository.findOne(projectId)
        if (project == null){
            throw new EntityNotFoundException("Project not found.")
        }
        for (Task task : project.getTasks()){
            taskService.delete(task.id)
        }
        projectRepository.delete(project)
    }

    def getProjectsForUser(String userId) {
        def user = userRepository.findOne(userId)
        if (user.role == Role.ADMIN) {
            return projectRepository.findAll()
        }
        return user.projects.toList()
    }

    def getProject(String projectId) {
        projectRepository.findOne(projectId)
    }
}
