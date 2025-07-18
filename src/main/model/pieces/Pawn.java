package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Pawn extends Piece {

    boolean canPromote;
    boolean canBeTakenByEnPassant;
    
    public Pawn(Colour colour, int x, int y) {
        super(PieceType.PAWN, colour, "Pawn", "P", x, y);
    }

    // TODO: Implement possibleMoves for pawn
    @Override
    public List<Move> possibleMoves(Board board) {

        List<Move> possibleMoves = new ArrayList<>();

        if (COLOUR == Colour.WHITE) {

            // Move 2 squares on starting rank
            if (y == 1 && board.getSquare(x, y + 2) == null) addMove(x, y, x, y + 2, board, possibleMoves); // Can move two squares forward from starting position
            
            // Normal move forward
            if (board.getSquare(x, y + 1) == null) addMove(x, y, x, y + 1, board, possibleMoves);

            // Capture moves diagonally
            if (board.getSquare(x - 1, y + 1) != null) addMove(x, y, x - 1, y + 1, board, possibleMoves);
            if (board.getSquare(x + 1, y + 1) != null) addMove(x, y, x + 1, y + 1, board, possibleMoves);

        } else if (COLOUR == Colour.BLACK) {

        } else {
            throw new IllegalStateException("Invalid colour for Pawn: " + COLOUR);
        }

        return possibleMoves;
    }
}