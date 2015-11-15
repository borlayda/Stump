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

    Task(String title, String description) {
        this.title = title
        this.description = description
        this.status = TaskStatus.OPEN
    }

    Task() {
        this.comments = new ArrayList<>()
    }


}