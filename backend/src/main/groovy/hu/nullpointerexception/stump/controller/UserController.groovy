package hu.nullpointerexception.stump.controller

import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.service.UserService
import hu.nullpointerexception.stump.transport.GenericResponse
import hu.nullpointerexception.stump.transport.UserJSONEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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
        userService.getAllUsers().collect { new UserJSONEntity(it) };
    }

    @RequestMapping(method = RequestMethod.POST)
    GenericResponse addUser(@RequestBody UserJSONEntity userJSONObject) throws EntityAlreadyExistsException {
        def user = userJSONObject.createNewEntity()
        userService.addUser(user)
        return GenericResponse.okResponse()
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    def handleEntityAlreadyExists() {
        return new GenericResponse("ERROR", "Entity already exists.");
    }

}
