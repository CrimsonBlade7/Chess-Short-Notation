package model.pieces;

import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;

// Represents a generic chess piece.
public abstract class Piece {
    protected final Colour COLOUR;
    private final String NAME, SYMBOL;

    // REQUIRES: colour, name, and symbol are not null
    // MODIFIES: this
    // EFFECTS: creates a piece with colour, display name, and notation symbol
    public Piece(Colour colour, String name, String symbol) {
        COLOUR = colour;
        NAME = name;
        SYMBOL = symbol;
    }

    // EFFECTS: returns this piece's colour
    public Colour getColour() { return COLOUR; }

    // EFFECTS: returns this piece's short notation symbol
    public String getSymbol() { return SYMBOL; }

    // EFFECTS: returns this piece's display name
    public String getName() { return NAME; }

    // REQUIRES: boardState != null and pos is within the bounds of the board
    // EFFECTS: returns pseudo-legal target positions for this piece, ignoring
    // whether the moving side's king would be left in check
    public abstract Set<Position> validPositions(BoardState boardState, Position pos);

    // EFFECTS: returns a hash based on immutable piece attributes
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((COLOUR == null) ? 0 : COLOUR.hashCode());
        result = prime * result + ((NAME == null) ? 0 : NAME.hashCode());
        result = prime * result + ((SYMBOL == null) ? 0 : SYMBOL.hashCode());
        return result;
    }

    // EFFECTS: returns true if obj is the same piece type with the same attributes
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
        } else if (!NAME.equals(other.NAME)) {
            return false;
        }
        if (SYMBOL == null) {
            if (other.SYMBOL != null)
                return false;
        } else if (!SYMBOL.equals(other.SYMBOL)) {
            return false;
        }
        return true;
    }

    // REQUIRES: boardState != null
    // EFFECTS: returns false if out of bounds, otherwise returns true if the
    // position is empty, false otherwise
    protected boolean isEmptySquare(Position pos, BoardState boardState) {
        if (!isInBounds(pos))
            return false;
        return boardState.getSquare(pos) == null;
    }

    // REQUIRES: boardState != null
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
    protected boolean isInBounds(Position pos) {
        return pos != null && 0 <= pos.X && pos.X < 8 && 0 <= pos.Y && pos.Y < 8;
    }
}
