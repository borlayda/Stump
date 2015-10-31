package hu.nullpointerexception.stump.model

class User extends Entity {

    String name
    String password
    String email
    Role role

    def changePassword(oldPassWord, newPassword) {
        if (this.password != oldPassWord) {
            throw new RuntimeException()
        }
        this.password = newPassword
    }

}