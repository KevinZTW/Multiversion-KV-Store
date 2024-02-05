package net.kevinztw.playground.storage.exceptions;

public class DuplicateVersionException extends RuntimeException {

  public DuplicateVersionException(String message) {
    super(message);
  }

  public DuplicateVersionException(String message, Throwable cause) {
    super(message, cause);
  }
}
