package hu.nullpointerexception.stump.transport

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Márton Tóth
 */
class ChangeStatusJSONEntity {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String status

    ChangeStatusJSONEntity(String id, String status) {

        id = id
        status = status

    }

    ChangeStatusJSONEntity() {
    }

}
