package model.pieces;

import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Position;

// Represents a generic chess piece.
public abstract class Piece {
    protected final PieceType PIECE_TYPE;
    protected final Colour COLOUR;
    private final String NAME, SYMBOL;
    protected Position pos;

    public Piece(PieceType pieceType, Colour colour, String name, String symbol, Position pos) {
        PIECE_TYPE = pieceType;
        COLOUR = colour;
        NAME = name;
        SYMBOL = symbol;
        this.pos = pos;
    }

    public PieceType getPieceType() {
        return PIECE_TYPE;
    }

    public Colour getColour() {
        return COLOUR;
    }

    public String getSymbol() {
        return SYMBOL;
    }

    public String getName() {
        return NAME;
    }

    public int getX() {
        return pos.getX();
    }

    public int getY() {
        return pos.getY();
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    // REQUIRES: x and y are within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns a list of possible moves for the at position (x, y)
    // on the given board
    public abstract List<Position> validPositions(Board board);

    // REQUIRES: board != null
    // EFFECTS: Checks if the move to (x, y) is valid for this piece on the given
    // board.
    protected boolean isValidPosition(Position pos, Board board) {

        int x = pos.getX();
        int y = pos.getY();

        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return false; // Move is out of bounds
        }

        Piece targetPiece = board.getSquare(pos);

        if (targetPiece != null && targetPiece.getColour() == COLOUR) {
            return false; // Cannot capture own piece
        }

        return true;
    }

    public abstract Piece copy();
}