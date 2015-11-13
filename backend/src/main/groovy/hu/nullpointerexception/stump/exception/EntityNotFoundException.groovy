package hu.nullpointerexception.stump.exception

/**
 * Author: Márton Tóth
 */
class EntityNotFoundException extends StumpException {

    EntityNotFoundException() {
    }

    EntityNotFoundException(String var1) {
        super(var1)
    }

    EntityNotFoundException(String var1, Throwable var2) {
        super(var1, var2)
    }

    EntityNotFoundException(Throwable var1) {
        super(var1)
    }

    EntityNotFoundException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4)
    }

}
