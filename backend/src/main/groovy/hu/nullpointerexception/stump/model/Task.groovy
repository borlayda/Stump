package hu.nullpointerexception.stump.model

import org.springframework.data.mongodb.core.mapping.DBRef

class Task extends Entity {

    String title
    @DBRef User owner
    Project project
    String description
    TaskStatus status
    List<Comment> comments

    Task(String title, User owner, String description, Project project) {
        this.title = title
        this.owner = owner
        this.description = description
        this.project = project
        this.status = TaskStatus.OPEN
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