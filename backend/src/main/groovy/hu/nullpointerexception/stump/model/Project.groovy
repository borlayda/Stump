package hu.nullpointerexception.stump.model

class Project extends Entity {

    User owner
    List<Task> tasks

    def addTask(String title, User owner, String description) {
        tasks.add(new Task(title, owner, description, this))
    }
    
}