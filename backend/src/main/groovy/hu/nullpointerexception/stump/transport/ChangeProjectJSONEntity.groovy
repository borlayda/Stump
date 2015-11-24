package hu.nullpointerexception.stump.transport

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Márton Tóth
 */
class ChangeProjectJSONEntity {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String projectId
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String title
    String description
    String status
    String ownerId

    ChangeProjectJSONEntity(String projectId, String title, String description, String status, String ownerId) {

        projectId = projectId
        title = title
        description = description
        status = status
        ownerId = ownerId

    }

    ChangeProjectJSONEntity() {
    }

}
