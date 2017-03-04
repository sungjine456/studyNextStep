package org.studyStepNext.part9.next;

public class CannotDeleteException extends Exception {
    private static final long serialVersionUID = 1L;

    public CannotDeleteException(String message) {
        super(message);
    }
}