package hu.nullpointerexception.stump.repository

import hu.nullpointerexception.stump.model.Task
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created by Márton Tóth
 */
@Repository
interface TaskRepository extends CrudRepository<Task, String> {
}
