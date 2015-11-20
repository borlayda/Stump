package hu.nullpointerexception.stump.controller

import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.exception.StumpException
import hu.nullpointerexception.stump.model.Project
import hu.nullpointerexception.stump.security.StumpPrincipal
import hu.nullpointerexception.stump.service.ProjectService
import hu.nullpointerexception.stump.transport.ChangeRoleJSONEntity
import hu.nullpointerexception.stump.transport.ChangeStatusJSONEntity
import hu.nullpointerexception.stump.transport.GenericResponse
import hu.nullpointerexception.stump.transport.ProjectJSONEntity
import hu.nullpointerexception.stump.transport.TaskJSONEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Márton Tóth
 */
@RestController
@RequestMapping("/api/projects")
class ProjectController {

    private ProjectService projectService

    @Autowired
    ProjectController(ProjectService projectService) {
        this.projectService = projectService
    }

    @RequestMapping(method = RequestMethod.POST)
    def addProject(@RequestBody ProjectJSONEntity projectJSONEntity) {
        projectService.addProject(projectJSONEntity.createNewEntity(), projectJSONEntity.ownerId)
        return GenericResponse.okResponse()
    }

    @RequestMapping
    List<ProjectJSONEntity> getAll(@AuthenticationPrincipal StumpPrincipal stumpPrincipal) {
        projectService.getProjectsForUser(stumpPrincipal.user.id).collect { u -> new ProjectJSONEntity(u) }
    }

    @RequestMapping("{projectId}")
    ProjectJSONEntity getOne(@AuthenticationPrincipal StumpPrincipal stumpPrincipal,
                                   @PathVariable("projectId") String projectId) {
        def project = projectService.getProject(projectId)
        if (project == null) {
            throw new EntityNotFoundException("Project with id '" + projectId + "' not found.")
        }
        def projectJSONEntity = new ProjectJSONEntity(project)
        projectJSONEntity.tasks = project.getTasks().collect {t -> new TaskJSONEntity(t)}
        return projectJSONEntity
    }

    @RequestMapping(value = "/change-status", method = RequestMethod.POST)
    GenericResponse changeStatus(@RequestBody ChangeStatusJSONEntity csje) {
        projectService.changeStatus(csje.id, csje.status)
        return GenericResponse.okResponse()
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
