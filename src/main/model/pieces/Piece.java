package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

// Represents a generic chess piece.
public abstract class Piece {
    protected final PieceType PIECE_TYPE;
    protected final Colour COLOUR;
    private final String NAME, SYMBOL;

    public Piece(PieceType pieceType, Colour colour, String name, String symbol) {
        PIECE_TYPE = pieceType;
        COLOUR = colour;
        NAME = name;
        SYMBOL = symbol;
    }
    
    public PieceType getPieceType() { return PIECE_TYPE; }
    public Colour getColour() { return COLOUR; }
    public String getSymbol() { return SYMBOL; }
    public String getName() { return NAME; }

    public abstract List<Move> validMoves(Board board, int x, int y);

    //REQUIRES: board != null
    //MODIFIES: validMoves
    //EFFECTS: Adds a move to validMoves if it is a legal move (within bounds and not capturing friendly piece)
    protected boolean addMove(int ix, int iy, int fx, int fy, Board board, List<Move> validMoves) {
        if (fx < 0 || fx > 7 || fy < 0 || fy > 7) return false; // Out of bounds
        if (board.getSquare(fx, fy) != null && board.getSquare(fx, fy).getColour() == COLOUR) return false; // Friendly piece

        boolean isCapture = board.getSquare(fx, fy) != null;
        validMoves.add(new Move(ix, iy, fx, fy, isCapture, new ArrayList<>()));
        return true;
    }
}