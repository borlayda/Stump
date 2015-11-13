package hu.nullpointerexception.stump.model

import org.springframework.data.mongodb.core.mapping.DBRef

class Project extends Entity {

    String title
    String description
    User owner
    Status.Project status
    List<Task> tasks
    @DBRef(lazy = true)
    List<User> users

    Project(User owner, String title, String desciption) {
        this.owner = owner
        this.title = title
        this.description = desciption
        this.status = Status.Project.OPEN
    }

    def addTask(String title, User owner, String description) {
        tasks.add(new Task(title, owner, description, this))
    }

    def changeStatus(Status.Project status) {
        this.status = status
    }

}