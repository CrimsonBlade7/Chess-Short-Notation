package model.pieces;

import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

// Represents a generic chess piece.
public abstract class Piece {
    protected final PieceType PIECE_TYPE;
    protected final Colour COLOUR;
    private final String NAME, SYMBOL;
    protected int x;
    protected int y;

    public Piece(PieceType pieceType, Colour colour, String name, String symbol, int x, int y) {
        PIECE_TYPE = pieceType;
        COLOUR = colour;
        NAME = name;
        SYMBOL = symbol;
        this.x = x;
        this.y = y;
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
        return x;
    }

    public int getY() {
        return y;
    }
    
    // REQUIRES: x and y are within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns a list of possible squares for the at position (x, y)
    // on the given board
    protected abstract List<int[]> possibleSquares(int x, int y, Board board);
    
    // REQUIRES: x and y are within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns a list of possible moves for the at position (x, y)
    // on the given board
    protected abstract List<Move> possibleMoves(List<int[]> possibleSquares, Board board);

    // REQUIRES: board != null
    // EFFECTS: Checks if the move to (x, y) is valid for this piece on the given board.
    protected boolean isValidMove(int x, int y, Board board) {

        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return false; // Move is out of bounds
        }

        Piece targetPiece = board.getSquare(x, y);

        if (targetPiece != null && targetPiece.getColour() == COLOUR) {
            return false; // Cannot capture own piece
        }

        return true;
    }

    //
    protected abstract void addMove(int ix, int iy, int fx, int fy, Board board, Board newBoard,
            List<Move> possibleMoves);

    // MODIFIES: this
    // EFFECTS: Sets the position of this piece to the given coordinates (x, y).
    // If x or y is -1, the corresponding coordinate remains unchanged.
    public void setPosition(int x, int y) {
        this.x = x == -1 ? this.x : x;
        this.y = y == -1 ? this.y : y;
    }

    public abstract Piece copy();
}