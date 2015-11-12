package hu.nullpointerexception.stump.transport

/**
 * Author: Márton Tóth
 */
class GenericResponse {

    String status
    String message

    GenericResponse(String status, String message) {
        this.status = status
        this.message = message
    }

    GenericResponse() {

    }

    static def okResponse() {
        new GenericResponse("OK", null)
    }

}
