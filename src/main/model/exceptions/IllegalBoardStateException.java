package model.exceptions;

public class IllegalBoardStateException extends Exception {
    private final String message;

    public IllegalBoardStateException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "IllegalBoardStateException: " + message;
    }
}
