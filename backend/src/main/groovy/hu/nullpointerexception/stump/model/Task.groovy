package hu.nullpointerexception.stump.model

class Task extends Entity {

	Long id
    String title
    User owner
    String description

    Task(String title, User owner, String description) {
        this.title = title
        this.owner = owner
        this.description = description
    }

    def changeOwner(User newOwner) {
        this.owner = newOwner
    }

}