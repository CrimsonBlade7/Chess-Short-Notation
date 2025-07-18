package model.move_tools;

import java.util.ArrayList;
import model.Board;

// Represents algebraic notation for chess moves.
public class MoveValidation {

    // TODO: Complete stringToMove method
    public static Move stringToMove(String moveString, Board board) {
        return new Move(0, 0, 0, 0, false, new ArrayList<>()); // Placeholder implementation
    }

    // REQUIRES: board != null
    // MODIFIES: board
    // EFFECTS: checks if the move is valid on the given board
    public static boolean isValidMove(Move move, Board board) {

        if (move == null) return false; // Invalid move or board

        int ix = move.getIx();
        int iy = move.getIy();
        int fx = move.getFx();
        int fy = move.getFy();

        //check if the initial square is empty
        if (board.getSquare(ix, iy) == null) return false;

        //check if the initial and final squares are different
        if (iy == fy && ix == iy) return false;

        //check if the initial and final squares are within bounds
        if (iy < 0 || iy > 7) return false;
        if (ix < 0 || ix > 7) return false;
        if (fy < 0 || fy > 7) return false;
        if (fx < 0 || fx > 7) return false;

        return true;
    }

}