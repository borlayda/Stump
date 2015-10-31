package groovy.hu.nullpointerexception.stump.model

class Comment extends Entity {

    String text
    User author
    List<Comment> comments

    Comment(String text, User author) {
        this.text = text
        this.author = author
    }

    def addComment(String text, User author) {
        this.comments.add(new Comment(text, author))
    }
    

}