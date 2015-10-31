package hu.nullpointerexception.stump.model

class Task extends Entity {

    String title
    User owner
    Project project
    String description
    Status.Task status
    List<Comment> comments

    Task(String title, User owner, String description, Project project) {
        this.title = title
        this.owner = owner
        this.description = description
        this.project = project
        this.status = Status.Task.OPEN
    }

    def addComment(String text, User author) {
        this.comments.add(new Comment(text, author, this))
    }

    def changeOwner(User newOwner) {
        this.owner = newOwner
    }

    def changeStatus(Status.Task status){
        this.status = status
    }

}