package hu.nullpointerexception.stump.controller

import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.exception.StumpException
import hu.nullpointerexception.stump.security.StumpPrincipal
import hu.nullpointerexception.stump.service.ProjectService
import hu.nullpointerexception.stump.transport.GenericResponse
import hu.nullpointerexception.stump.transport.ProjectJSONEntity
import hu.nullpointerexception.stump.transport.UserJSONEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.ExceptionHandler
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
        projectService.getProjectsForUser(stumpPrincipal.user.id).collect {u -> new ProjectJSONEntity(u)}
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
