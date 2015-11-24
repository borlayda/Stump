package hu.nullpointerexception.stump.transport

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Márton Tóth
 */
class ChangeTaskJSONEntity {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String taskId
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String title
    String description
    String status
    String ownerId

    ChangeTaskJSONEntity(String taskId, String title, String description, String status, String ownerId) {

        taskId = taskId
        title = title
        description = description
        status = status
        ownerId = ownerId

    }

    ChangeTaskJSONEntity() {
    }

}
