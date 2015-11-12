package hu.nullpointerexception.stump.exception

/**
 * Author: Márton Tóth
 */
class EntityAlreadyExistsException extends StumpException {
    EntityAlreadyExistsException() {
    }

    EntityAlreadyExistsException(String var1) {
        super(var1)
    }

    EntityAlreadyExistsException(String var1, Throwable var2) {
        super(var1, var2)
    }

    EntityAlreadyExistsException(Throwable var1) {
        super(var1)
    }

    EntityAlreadyExistsException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4)
    }
}
