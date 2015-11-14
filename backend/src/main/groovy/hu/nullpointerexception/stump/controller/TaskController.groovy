package hu.nullpointerexception.stump.controller

import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.exception.StumpException
import hu.nullpointerexception.stump.security.StumpPrincipal
import hu.nullpointerexception.stump.service.TaskService
import hu.nullpointerexception.stump.transport.GenericResponse
import hu.nullpointerexception.stump.transport.TaskJSONEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * Created by Márton Tóth
 */
@RestController
@RequestMapping("/api/tasks")
class TaskController {

    private TaskService taskService

    @Autowired
    TaskController(TaskService taskService) {
        this.taskService = taskService
    }

    @RequestMapping(method = RequestMethod.POST)
    def addTask(@RequestBody TaskJSONEntity taskJSONEntity) {
        taskService.addTask(taskJSONEntity.createNewEntity(), taskJSONEntity.ownerId, taskJSONEntity.projectId)
        return GenericResponse.okResponse()
    }

    @RequestMapping
    List<TaskJSONEntity> getAll(@AuthenticationPrincipal StumpPrincipal stumpPrincipal) {
        taskService.getTasksForUser(stumpPrincipal.user.id).collect {u -> new TaskJSONEntity(u)}
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
