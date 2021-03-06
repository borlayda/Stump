package hu.nullpointerexception.stump.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "tasks")
class Task extends Entity {

    @Indexed(unique = true)
    String title
    @DBRef(lazy = true)
    User owner
    String description
    TaskStatus status
    @DBRef(lazy = true)
    List<Comment> comments
    @DBRef(lazy = true)
    List<User> users
    TaskType type
    Long workTime

    Task(String title, String description, TaskType type) {
        this.title = title
        this.description = description
        this.status = TaskStatus.OPEN
        this.type = type
        this.workTime = 0
    }

    Task() {
        this.comments = new ArrayList<>()
        this.users = new ArrayList<>()
    }


}