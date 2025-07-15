package model.pieces;

import java.util.List;
import model.Board;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.misc_vars.PieceType;
import model.move_tools.Move;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final Colour colour;
    private String name, symbol;

    public Piece(PieceType pieceType, Colour colour, String name, String symbol) {
        this.pieceType = pieceType;
        this.colour = colour;
        this.name = name;
        this.symbol = symbol;
    }
    
    public PieceType getPieceType() { return pieceType; }
    public Colour getColour() { return colour; }
    public String getSymbol() { return symbol; }
    public String getName() { return name; }

    public abstract List<Move> validMoves(Board board, int x, int y);

    //REQUIRES: board != null
    //MODIFIES: validMoves
    //EFFECTS: Adds a move to validMoves if it is a legal move (within bounds and not capturing friendly piece)
    protected boolean addMove(int ix, int iy, int fx, int fy, Board board, List<Move> validMoves) {
        if (fx < 0 || fx > 7 || fy < 0 || fy > 7) return false; // Out of bounds
        if (board.getSquare(fx, fy) != null && board.getSquare(fx, fy).getColour() == this.colour) return false; // Friendly piece

        boolean isCapture = board.getSquare(fx, fy) != null;
        validMoves.add(new Move(ix, iy, fx, fy, isCapture, MoveType.NORMAL));
        return true;
    }
}