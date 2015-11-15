package hu.nullpointerexception.stump.transport

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import hu.nullpointerexception.stump.model.Project
import hu.nullpointerexception.stump.model.ProjectStatus
import hu.nullpointerexception.stump.model.User

/**
 * Created by Márton Tóth
 */
class ProjectJSONEntity extends JSONEntity<Project> {

    String id
    String title
    String description
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String ownerId
    @JsonInclude(JsonInclude.Include.NON_NULL)
    UserJSONEntity owner
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<TaskJSONEntity> tasks
    ProjectStatus status

    ProjectJSONEntity(Project source) {
        super(source)
        id = source.id
        title = source.title
        description = source.description
        owner = new UserJSONEntity(source.owner)
        status = source.status
    }

    ProjectJSONEntity() {
    }

    @Override
    Project createNewEntity() {
        def project = new Project(title, description)
        if (status != null) {
            project.status = status
        }
        return project
    }
}
