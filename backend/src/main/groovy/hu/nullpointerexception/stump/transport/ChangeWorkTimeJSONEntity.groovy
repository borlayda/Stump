package hu.nullpointerexception.stump.transport

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Márton Tóth
 */
class ChangeWorkTimeJSONEntity {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long workTime

    ChangeWorkTimeJSONEntity(String id, Long workTime) {

        id = id
        workTime = workTime

    }

    ChangeWorkTimeJSONEntity() {
    }

}
