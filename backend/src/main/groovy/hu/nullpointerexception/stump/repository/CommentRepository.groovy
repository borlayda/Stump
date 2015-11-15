package hu.nullpointerexception.stump.repository

import hu.nullpointerexception.stump.model.Comment
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created by Márton Tóth
 */
@Repository
interface CommentRepository extends CrudRepository<Comment, String> {
}
