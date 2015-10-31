package hu.nullpointerexception.stump.model

class Entity {

    String id
    static Long projectCount = 0
    static Long taskCount = 0
    static Long commentCount = 0

    def addId(String type, Long count) {
        this.id = type + "-" + count.toString()
    }

}
