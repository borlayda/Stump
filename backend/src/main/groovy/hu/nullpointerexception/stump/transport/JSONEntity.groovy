package hu.nullpointerexception.stump.transport

/**
 * T is the domain object, that gets stringified.
 *
 * Author: Márton Tóth
 */
abstract class JSONEntity<T> {


    JSONEntity(T source) {

    }

    JSONEntity() {
    }

    /**
     * @return a new T instance.
     */
    abstract T createNewEntity()
}
