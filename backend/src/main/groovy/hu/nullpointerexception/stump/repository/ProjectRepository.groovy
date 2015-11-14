package hu.nullpointerexception.stump.repository

import hu.nullpointerexception.stump.model.Project
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created by Márton Tóth
 */
@Repository
interface ProjectRepository extends CrudRepository<Project, String> {
}
