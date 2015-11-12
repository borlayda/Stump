package hu.nullpointerexception.stump.transport

import hu.nullpointerexception.stump.model.Role
import hu.nullpointerexception.stump.model.User

/**
 * Author: Márton Tóth
 */
class UserJSONEntity extends JSONEntity<User> {

    String id
    String name
    String password
    String email
    Role role

    UserJSONEntity(User source) {
        id = source.id
        name = source.name
        //password = source.password HAHA
        email = source.email
        role = source.role
    }

    UserJSONEntity() {
    }

    @Override
    User createNewEntity() {
        def retVal = new User()
        retVal.name = name
        retVal.password = password
        retVal.role = role
        return retVal
    }
}
