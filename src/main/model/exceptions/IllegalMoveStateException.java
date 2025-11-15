package model.exceptions;

public class IllegalMoveStateException extends Exception {
    private final String message;

    public IllegalMoveStateException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "InvalidMoveStateException: " + message;
    }
}
