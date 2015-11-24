package hu.nullpointerexception.stump.transport

import com.fasterxml.jackson.annotation.JsonInclude
import hu.nullpointerexception.stump.model.Comment
import hu.nullpointerexception.stump.model.Project
import hu.nullpointerexception.stump.model.Task
import hu.nullpointerexception.stump.model.TaskStatus
import hu.nullpointerexception.stump.model.Task
import hu.nullpointerexception.stump.model.TaskStatus
import hu.nullpointerexception.stump.model.TaskType

/**
 * Created by Márton Tóth
 */
class TaskJSONEntity extends JSONEntity<Task> {

    String id
    String title
    String description
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String ownerId
    @JsonInclude(JsonInclude.Include.NON_NULL)
    UserJSONEntity owner
    TaskStatus status
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String projectId
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<CommentJSONEntity> comments
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<UserJSONEntity> users
    TaskType type
    Long workTime

    TaskJSONEntity(Task source) {
        super(source)
        id = source.id
        title = source.title
        description = source.description
        owner = new UserJSONEntity(source.owner)
        status = source.status
        type = source.type
        workTime = source.workTime
    }

    TaskJSONEntity() {
    }

    @Override
    Task createNewEntity() {
        def task = new Task(title, description, type)
        if (status != null) {
            task.status = status
        }
        return task
    }
}
