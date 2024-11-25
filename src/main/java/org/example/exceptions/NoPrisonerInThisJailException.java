package org.example.exceptions;

public class NoPrisonerInThisJailException extends RuntimeException {
    public NoPrisonerInThisJailException(String message) {
        super(message);
    }
}
