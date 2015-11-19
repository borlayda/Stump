package hu.nullpointerexception.stump.transport

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Márton Tóth
 */
class ChangeRoleJSONEntity {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String newRole

    ChangeRoleJSONEntity(String id, String newRole) {

        id = id
        newRole = newRole

    }

    ChangeRoleJSONEntity() {
    }

}
