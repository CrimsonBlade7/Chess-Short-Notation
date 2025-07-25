package model.exceptions;

public class MoveMismatchException extends Exception {
    private final String message;

    public MoveMismatchException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MoveMismatchException: " + message;
    }
}
