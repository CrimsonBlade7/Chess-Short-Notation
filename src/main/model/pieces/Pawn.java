package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;
import model.move_tools.Position;

public class Pawn extends Piece {

    boolean canBeTakenByEnPassant;

    public Pawn(Colour colour, Position pos) {
        super(PieceType.PAWN, colour, "Pawn", "P", pos);
        canBeTakenByEnPassant = false;
    }
    
    public Pawn(Colour colour, Position pos, boolean canBeTakenByEnPassant) {
        super(PieceType.PAWN, colour, "Pawn", "P", pos);
        this.canBeTakenByEnPassant = canBeTakenByEnPassant;
    }
    
    // TODO: Implement possibleMoves for pawn
    @Override
    public List<Position> validPositions(Board board) {

        List<Position> validPositionsList = new ArrayList<>();

        int coef = getColour() == Colour.WHITE ? 1 : -1; // Determine direction based on colour

        // Move 2 squares on starting rank
        Position pos = 
        if (this.getY() == 1 && board.getSquare(new Position(this.getX(), this.getY() + 2 * coef)) == null) {
            canBeTakenByEnPassant = true; // Set en passant flag if pawn moves two squares forward
        }

        // Normal move forward
        if (board.getSquare(x, y + 1 * coef) == null)
            addMove(x, y, x, y + 1 * coef, board, possibleMoves);

        // Capture moves diagonally
        if (board.getSquare(x - 1, y + 1 * coef) != null)
            addMove(x, y, x - 1, y + 1 * coef, board, possibleMoves);
        if (board.getSquare(x + 1, y + 1 * coef) != null)
            addMove(x, y, x + 1, y + 1 * coef, board, possibleMoves);

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