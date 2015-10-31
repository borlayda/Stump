package hu.nullpointerexception.stump.model

class Status {

    enum Task {
        OPEN,
        IN_PROGRESS,
        REVIEW,
        TESTING,
        VERIFYING,
        CLOSED
    }

    enum Project {
        OPEN,
        IN_PROGRESS,
        RESOLVED,
        REOPENED,
        CLOSED
    }

    enum User {
        ACTIVE,
        INACTIVE
    }

}