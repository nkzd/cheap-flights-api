package io.vukotic.flightadvisor.error.exception;

public class UserNotAuthorizedToHandleCommentException extends RuntimeException {
    public UserNotAuthorizedToHandleCommentException() {
        super("User not authorized to edit this comment");
    }
}
