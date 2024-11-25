package org.example.exceptions;

public class ContainedPrisonerIsNowInAnyCellException extends RuntimeException {
    public ContainedPrisonerIsNowInAnyCellException(String message) {
        super(message);
    }
}
