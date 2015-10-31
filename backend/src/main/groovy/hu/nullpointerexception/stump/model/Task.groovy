package groovy.hu.nullpointerexception.stump.model

class Task extends Entity {

	Long id
    String title
    User owner
    String description
    Status.Task status

    Task(String title, User owner, String description) {
        this.title = title
        this.owner = owner
        this.description = description
        this.status = Status.Task.OPEN
    }

    def changeOwner(User newOwner) {
        this.owner = newOwner
    }

    def changeStatus(Status.Task status){
        this.status = status
    }

}