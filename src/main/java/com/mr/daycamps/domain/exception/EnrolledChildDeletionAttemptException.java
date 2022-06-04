package com.mr.daycamps.domain.exception;

public class EnrolledChildDeletionAttemptException extends RuntimeException {

    private static final String ENROLLED_CHILD_DELETION_ATTEMPT_MESSAGE =
            "Cannot delete child which is (or was) enrolled in a day camp.";

    public EnrolledChildDeletionAttemptException() {
        super(ENROLLED_CHILD_DELETION_ATTEMPT_MESSAGE);
    }

}
