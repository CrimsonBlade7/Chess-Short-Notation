package model.pieces;

import java.util.List;

import model.Board;
import model.misc_vars.Colour;
import model.move_tools.Move;
import model.move_tools.Position;

// Represents a generic chess piece.
public abstract class Piece {
    protected final Colour COLOUR;
    private final String NAME, SYMBOL;
    protected Position pos;

    public Piece(Colour colour, String name, String symbol, Position pos) {
        COLOUR = colour;
        NAME = name;
        SYMBOL = symbol;
        this.pos = pos;
    }

    public Colour getColour() { return COLOUR; }

    public String getSymbol() { return SYMBOL; }

    public String getName() { return NAME; }

    public int getX() { return pos.getX(); }

    public int getY() { return pos.getY(); }

    public Position getPos() { return pos; }

    public void setPos(Position pos) { this.pos = pos; }

    // REQUIRES: x and y are within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns a list of possible moves for the at position (x, y)
    // on the given board
    public abstract List<Move> validMoves(Board board);

    @Override
    public String toString() {
        return "Piece [ COLOUR=" + COLOUR + ", NAME=" + NAME + ", SYMBOL=" + SYMBOL
                + ", pos=" + pos + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((COLOUR == null) ? 0 : COLOUR.hashCode());
        result = prime * result + ((NAME == null) ? 0 : NAME.hashCode());
        result = prime * result + ((SYMBOL == null) ? 0 : SYMBOL.hashCode());
        result = prime * result + ((pos == null) ? 0 : pos.hashCode());
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
        } else if (!NAME.equals(other.NAME))
            return false;
        if (SYMBOL == null) {
            if (other.SYMBOL != null)
                return false;
        } else if (!SYMBOL.equals(other.SYMBOL))
            return false;
        if (pos == null) {
            if (other.pos != null)
                return false;
        } else if (!pos.equals(other.pos))
            return false;
        return true;
    }

    public abstract Piece copy();

    // REQUIRES: board != null
    // EFFECTS: Checks if the move to pos is not friendly and is within the bounds
    // of the board.
    // If the position is valid, returns true; otherwise, returns false.
    protected boolean isValidMove(Move move, Board board) {

        int x = move.getTargetX();
        int y = move.getTargetY();

        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return false; // Move is out of bounds
        }

        Piece targetPiece = move.getPiece();

        if (targetPiece != null && targetPiece.getColour() == COLOUR) {
            return false; // Cannot capture own piece
        }

        return true;
    }

    // REQUIRES: pos is within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns true if the position is empty, false otherwise
    protected boolean isEmptySquare(Position pos, Board board) { return board.getSquare(pos) == null; }
}