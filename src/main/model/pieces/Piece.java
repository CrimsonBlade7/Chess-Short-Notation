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
    
    public PieceType getPieceType() { return PIECE_TYPE; }
    public Colour getColour() { return COLOUR; }
    public String getSymbol() { return SYMBOL; }
    public String getName() { return NAME; }

    // REQUIRES: board != null
    // MODIFIES: possibleMoves
    // EFFECTS: Returns a list of valid moves for this piece at position (x, y) on the given board.
    public abstract List<Move> possibleMoves(Board board);

    //REQUIRES: board != null
    //MODIFIES: possibleMoves
    //EFFECTS: Adds a move to possibleMoves if it is a legal move (within bounds and not capturing friendly piece)
    protected boolean addMove(int ix, int iy, int fx, int fy, Board board, List<Move> possibleMoves) {

        if (fx < 0 || fx > 7 || fy < 0 || fy > 7) return false; // Out of bounds
        if (board.getSquare(fx, fy) != null && board.getSquare(fx, fy).getColour() == COLOUR) return false; // Friendly piece

        boolean isCapture = board.getSquare(fx, fy) != null;
        possibleMoves.add(new Move(ix, iy, fx, fy, isCapture, new ArrayList<>()));
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Sets the position of this piece to (x, y).
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}