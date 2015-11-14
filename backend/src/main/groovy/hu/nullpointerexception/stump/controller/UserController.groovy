package hu.nullpointerexception.stump.controller

import com.mongodb.DuplicateKeyException
import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.exception.StumpException
import hu.nullpointerexception.stump.security.StumpPrincipal
import hu.nullpointerexception.stump.service.UserService
import hu.nullpointerexception.stump.transport.GenericResponse
import hu.nullpointerexception.stump.transport.UserJSONEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

/**
 * Author: Márton Tóth
 */
@RestController
@RequestMapping("/api/users")
class UserController {

    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService
    }

    @RequestMapping(method = RequestMethod.GET)
    List<UserJSONEntity> getUsers() {
        userService.getAllUsers().collect { new UserJSONEntity(it) }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{userId}")
    UserJSONEntity getUsers(@PathVariable("userId") String userId) {
        new UserJSONEntity(userService.getUser(userId))
    }

    @RequestMapping(method = RequestMethod.GET, value = "me")
    UserJSONEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        def principal = auth.getPrincipal() as StumpPrincipal
        new UserJSONEntity(userService.getUser(principal.user.id))
    }

    @RequestMapping(method = RequestMethod.POST)
    GenericResponse addUser(@RequestBody UserJSONEntity userJSONObject) throws EntityAlreadyExistsException {
        def user = userJSONObject.createNewEntity()
        userService.addUser(user)
        return GenericResponse.okResponse()
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
    GenericResponse deleteUser(@PathVariable("userId") String userId) throws EntityAlreadyExistsException {
        userService.deleteUser(userId)
        return GenericResponse.okResponse()
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    GenericResponse changePassword(@RequestBody ChangePasswordRequest cpr) {
        userService.changePassword(cpr.userId, cpr.oldPassword, cpr.newPassword)
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

    @ExceptionHandler([EntityAlreadyExistsException.class, DuplicateKeyException.class])
    @ResponseStatus(HttpStatus.CONFLICT)
    def handleEntityAlreadyExists() {
        return new GenericResponse("ERROR", "Entity already exists.");
    }

    private static class ChangePasswordRequest {
        String userId
        String oldPassword
        String newPassword
    }

}
