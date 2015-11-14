package hu.nullpointerexception.stump.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef

class Task extends Entity {

    @Indexed(unique = true)
    String title
    @DBRef(lazy = true)
    User owner
    @DBRef(lazy = true)
    Project project;
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

    def addComment(String text, User author) {
        this.comments.add(new Comment(text, author, this))
    }

    def changeOwner(User newOwner) {
        this.owner = newOwner
    }

    def changeStatus(TaskStatus status) {
        this.status = status
    }

}