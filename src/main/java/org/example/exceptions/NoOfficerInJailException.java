package org.example.exceptions;

public class NoOfficerInJailException extends RuntimeException {
    public NoOfficerInJailException(String message) {
        super(message);
    }
}
