package hu.nullpointerexception.stump.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
class User extends Entity {

    @Indexed(unique=true)
    String name
    String password
    @Indexed(unique=true)
    String email
    Role role
    @DBRef(lazy = true)
    List<Project> projects

    def changePassword(oldPassWord, newPassword) {
        if (this.password != oldPassWord) {
            throw new RuntimeException()
        }
        this.password = newPassword
    }

}