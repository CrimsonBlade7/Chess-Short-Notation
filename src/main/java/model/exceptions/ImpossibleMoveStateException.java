package model.exceptions;

public class ImpossibleMoveStateException extends Exception {

    // REQUIRES: message != null
    // MODIFIES: this
    // EFFECTS: creates an exception describing an impossible move state
    public ImpossibleMoveStateException(String message) {
        super(message);
    }
}
