package hu.nullpointerexception.stump.service

import hu.nullpointerexception.stump.exception.DatabaseException
import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.model.Project
import hu.nullpointerexception.stump.model.Role
import hu.nullpointerexception.stump.repository.ProjectRepository
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

    @Autowired
    ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository
        this.userRepository = userRepository
    }

    def addProject(Project project, String userId) {
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException("User with id '" + userId + "' not found.")
        }
        project.owner = user
        try {
            return projectRepository.save(project)
        } catch (DuplicateKeyException e) {
            throw new EntityAlreadyExistsException(e)
        }

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
