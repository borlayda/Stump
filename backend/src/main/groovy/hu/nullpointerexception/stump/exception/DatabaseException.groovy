package hu.nullpointerexception.stump.exception

/**
 * Created by Márton Tóth
 */
class DatabaseException extends StumpException {
    DatabaseException() {
    }

    DatabaseException(String var1) {
        super(var1)
    }

    DatabaseException(String var1, Throwable var2) {
        super(var1, var2)
    }

    DatabaseException(Throwable var1) {
        super(var1)
    }

    DatabaseException(String var1, Throwable var2, boolean var3, boolean var4) {
        super(var1, var2, var3, var4)
    }
}
