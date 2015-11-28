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
    List<String> users

    ChangeProjectJSONEntity(String projectId,
                            String title,
                            String description,
                            String status,
                            String ownerId,
                            List<String> users) {

        projectId = projectId
        title = title
        description = description
        status = status
        ownerId = ownerId

    }

    ChangeProjectJSONEntity() {
    }

}
