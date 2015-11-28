package hu.nullpointerexception.stump.transport

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Márton Tóth
 */
class UserChangeJSONEntity {

    String taskId
    String projectId
    String userId

    UserChangeJSONEntity(String projectId="", String taskId="", String userId) {

        projectId = projectId
        userId = userId
        taskId = taskId

    }

    UserChangeJSONEntity() {
    }

}
