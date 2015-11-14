package hu.nullpointerexception.stump.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "projects")
class Project extends Entity {

    @Indexed(unique = true)
    String title
    String description
    User owner
    ProjectStatus status
    List<Task> tasks
    @DBRef(lazy = true)
    List<User> users

    Project(String title, String desciption) {
        this.title = title
        this.description = desciption
        this.status = ProjectStatus.OPEN
    }

    Project() {
    }

    def addTask(String title, User owner, String description) {
        tasks.add(new Task(title, owner, description, this))
    }

    def changeStatus(ProjectStatus status) {
        this.status = status
    }

}