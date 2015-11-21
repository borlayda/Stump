package hu.nullpointerexception.stump.transport

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Márton Tóth
 */
class ChangeRoleJSONEntity {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String userId
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String newRole

    ChangeRoleJSONEntity(String userId, String newRole) {

        userId = userId
        newRole = newRole

    }

    ChangeRoleJSONEntity() {
    }

}
