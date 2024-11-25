package org.example.exceptions;

public class CellSlotIsTakenException extends RuntimeException {
  public CellSlotIsTakenException(String message) {
    super(message);
  }
}
