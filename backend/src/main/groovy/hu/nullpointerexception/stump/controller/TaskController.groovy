package hu.nullpointerexception.stump.controller

import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.exception.StumpException
import hu.nullpointerexception.stump.model.Task
import hu.nullpointerexception.stump.security.StumpPrincipal
import hu.nullpointerexception.stump.service.TaskService
import hu.nullpointerexception.stump.transport.ChangeStatusJSONEntity
import hu.nullpointerexception.stump.transport.ChangeTaskJSONEntity
import hu.nullpointerexception.stump.transport.ChangeWorkTimeJSONEntity
import hu.nullpointerexception.stump.transport.CommentJSONEntity
import hu.nullpointerexception.stump.transport.GenericResponse
import hu.nullpointerexception.stump.transport.TaskJSONEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

import java.nio.file.Path

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
    List<TaskJSONEntity> getAll() {
        taskService.getAll().collect {u -> new TaskJSONEntity(u)}
    }

    @RequestMapping("{taskId}")
    TaskJSONEntity get(@PathVariable("taskId") String taskId) {
        def task = taskService.getTask(taskId)
        def taskJSONEntity = new TaskJSONEntity(task)
        taskJSONEntity.comments = task.comments.collect {c -> new CommentJSONEntity(c)}
        return taskJSONEntity
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{taskId}")
    def deleteTask(@PathVariable("taskId") String taskId) {
        taskService.delete(taskId)
        return GenericResponse.okResponse()
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    GenericResponse changeTask(@RequestBody ChangeTaskJSONEntity ct) {
        taskService.changeTask(ct.taskId, ct.title, ct.description, ct.ownerId, ct.status)
        return GenericResponse.okResponse()
    }

    @RequestMapping(value = "/change-status", method = RequestMethod.POST)
    GenericResponse changeStatus(@RequestBody ChangeStatusJSONEntity csje) {
        taskService.changeStatus(csje.id, csje.status)
        return GenericResponse.okResponse()
    }

    @RequestMapping(value = "/change-worktime", method = RequestMethod.POST)
    GenericResponse changeWorkTime(@RequestBody ChangeWorkTimeJSONEntity cwt) {
        taskService.addWorkTime(cwt.id, cwt.workTime)
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
