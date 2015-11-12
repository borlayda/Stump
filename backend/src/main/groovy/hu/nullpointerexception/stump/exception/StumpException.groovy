package hu.nullpointerexception.stump.exception

/**
 * Author: Márton Tóth
 */
class StumpException extends Exception{

    StumpException() {
    }

    StumpException(String var1) {
        super(var1)
    }

    StumpException(String var1, Throwable var2) {
        super(var1, var2)
    }

    StumpException(Throwable var1) {
        super(var1)
    }

    StumpException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4)
    }
}
