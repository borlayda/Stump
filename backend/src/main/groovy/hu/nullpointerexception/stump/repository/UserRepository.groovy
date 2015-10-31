package hu.nullpointerexception.stump.repository

import hu.nullpointerexception.stump.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Author: Márton Tóth
 */
@Repository
interface UserRepository extends CrudRepository<User, String> {

    User findByName(String name)

}
