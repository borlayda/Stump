package groovy.hu.nullpointerexception.stump.model

class Comment extends Entity {

    String text
    User author
    List<Comment> comments
    Entity parent

    Comment(String text, User author, Entity parent) {
        this.text = text
        this.author = author
        this.parent = parent
    }

    def addComment(String text, User author) {
        this.comments.add(new Comment(text, author, this))
    }

}