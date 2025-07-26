package model.exceptions;

public class InvalidMoveException extends Exception {
    private final String message;

    public InvalidMoveException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "InvalidMoveException: " + message;
    }
}
