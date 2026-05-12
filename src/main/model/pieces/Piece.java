package model.pieces;

import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;

// Represents a generic chess piece.
public abstract class Piece {
    protected final Colour COLOUR;
    private final String NAME, SYMBOL;

    public Piece(Colour colour, String name, String symbol) {
        COLOUR = colour;
        NAME = name;
        SYMBOL = symbol;
    }

    public Colour getColour() { return COLOUR; }

    public String getSymbol() { return SYMBOL; }

    public String getName() { return NAME; }

    // REQUIRES: x and y are within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns a list of possible possitions for the at position (x, y)
    // on the given board
    public abstract Set<Position> validPositions(BoardState boardState, Position pos);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((COLOUR == null) ? 0 : COLOUR.hashCode());
        result = prime * result + ((NAME == null) ? 0 : NAME.hashCode());
        result = prime * result + ((SYMBOL == null) ? 0 : SYMBOL.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Piece other = (Piece) obj;
        if (COLOUR != other.COLOUR)
            return false;
        if (NAME == null) {
            if (other.NAME != null)
                return false;
        }
        else if (!NAME.equals(other.NAME))
            return false;
        if (SYMBOL == null) {
            if (other.SYMBOL != null)
                return false;
        }
        else if (!SYMBOL.equals(other.SYMBOL))
            return false;
        return true;
    }

    // REQUIRES: board != null
    // EFFECTS: returns false if out of bounds, otherwise returns true if the
    // position is empty, false otherwise
    protected boolean isEmptySquare(Position pos, BoardState boardState) {
        if (!isInBounds(pos))
            return false;
        return boardState.getSquare(pos) == null;
    }

    // REQUIRES: board != null
    // EFFECTS: returns false if the position is occupied by the same colour or out
    // of bounds, true otherwise
    protected boolean isValidPosition(Position pos, BoardState boardState) {
        if (!isInBounds(pos))
            return false;
        if (isEmptySquare(pos, boardState))
            return true;
        return boardState.getSquare(pos).getColour() != COLOUR;
    }

    // EFFECTS: returns true if the position is in bounds
    private boolean isInBounds(Position pos) { return 0 <= pos.X || pos.X < 8 || 0 <= pos.Y || pos.Y < 8; }
}