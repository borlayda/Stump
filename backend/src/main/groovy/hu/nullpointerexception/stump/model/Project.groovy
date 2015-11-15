package hu.nullpointerexception.stump.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "projects")
class Project extends Entity {

    @Indexed(unique = true)
    String title
    String description
    @DBRef(lazy = true)
    User owner
    ProjectStatus status
    @DBRef
    List<Task> tasks
    @DBRef(lazy = true)
    List<User> users

    Project(String title, String desciption) {
        this.title = title
        this.description = desciption
        this.status = ProjectStatus.OPEN
    }

    Project() {
        this.users = new ArrayList<>()
        this.tasks = new ArrayList<>()
    }

}