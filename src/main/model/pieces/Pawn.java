package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Pawn extends Piece {

    boolean canBeTakenByEnPassant;
    
    public Pawn(Colour colour, int x, int y) {
        super(PieceType.PAWN, colour, "Pawn", "P", x, y);
        canBeTakenByEnPassant = false;
    }

    // TODO: Implement possibleMoves for pawn
    @Override
    public List<Move> possibleMoves(int x, int y, Board board) {

        List<Move> possibleMoves = new ArrayList<>();

        int coef = getColour() == Colour.WHITE ? 1 : -1; // Determine direction based on colour

        // Move 2 squares on starting rank
        if (y == 1 && board.getSquare(x, y + 2 * coef) == null) {
            if (addMove(x, y, x, y + 2 * coef, board, possibleMoves)) {
                canBeTakenByEnPassant = true; // Set en passant flag if pawn moves two squares forward
            }
        }
        
        // Normal move forward
        if (board.getSquare(x, y + 1 * coef) == null) addMove(x, y, x, y + 1 * coef, board, possibleMoves);

        // Capture moves diagonally
        if (board.getSquare(x - 1, y + 1 * coef) != null) addMove(x, y, x - 1, y + 1 * coef, board, possibleMoves);
        if (board.getSquare(x + 1, y + 1 * coef) != null) addMove(x, y, x + 1, y + 1 * coef, board, possibleMoves);

        return possibleMoves;
    }

    // MODIFIES: canBeTakenByEnPassant
    // EFFECTS: sets the canBeTakenByEnPassant flag to false
    public void resetEnPassantFlag() {
        canBeTakenByEnPassant = false;
    }

    @Override
    public Piece copy() {
        Pawn copy = new Pawn(COLOUR, this.x, this.y);
        copy.canBeTakenByEnPassant = this.canBeTakenByEnPassant;
        return copy;
    }
}