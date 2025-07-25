package model.pieces;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    // REQUIRES: board != null
    // MODIFIES: possibleMoves
    // EFFECTS: Returns a list of valid moves for this piece at position (x, y) on
    // the given board.
    public abstract List<Move> possibleMoves(Board board);

    // REQUIRES: board != null
    // MODIFIES: possibleMoves
    // EFFECTS: Adds a move to possibleMoves if it is a legal move (within bounds
    // and not capturing friendly piece).
    // Returns true if the move was added, false otherwise.
    protected boolean addMove(Piece piece, int ix, int iy, int fx, int fy, Board board, List<Move> possibleMoves) {

        if (fx < 0 || fx > 7 || fy < 0 || fy > 7) {
            return false; // Out of bounds
        }

        Piece targetPiece = board.getSquare(fx, fy);

        if (targetPiece != null && targetPiece.getColour() == COLOUR) {
            return false; // Friendly piece
        }

        Set<MoveTag> moveTags = new HashSet<>();
        if (targetPiece != null) {
            if (targetPiece instanceof King) {
                // TODO: check if checkmate
                moveTags.add(MoveTag.CHECK);
            } else {
                moveTags.add(MoveTag.CAPTURE);
            }
        }

        possibleMoves.add(new Move(piece, ix, iy, fx, fy, moveTags));
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Sets the position of this piece to the given coordinates (x, y).
    // If x or y is -1, the corresponding coordinate remains unchanged.
    public void setPosition(int x, int y) {
        this.x = x == -1 ? this.x : x;
        this.y = y == -1 ? this.y : y;
    }
}