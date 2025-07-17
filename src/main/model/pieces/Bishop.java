package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Bishop extends Piece {

    public Bishop(Colour colour) {
        super(PieceType.BISHOP, colour, "Bishop", "B");
    }

    //REQUIRES: board != null
    //MODIFIES: validMoves
    //EFFECTS: Returns a list of valid moves for the Bishop piece at position (ix, iy) on the given board
    @Override
    public List<Move> validMoves(Board board, int ix, int iy) {
        List<Move> validMoves = new ArrayList<>();

        int[][] directions = {
            {-1, 1},  // Top-left
            {1, 1},   // Top-right
            {-1, -1}, // Bottom-left
            {1, -1}   // Bottom-right
        };

        for (int[] dir : directions) {
            int fx = ix;
            int fy = iy;
            boolean continueSearch;
            do {
                fx += dir[0];
                fy += dir[1];
                continueSearch = addMove(ix, iy, fx, fy, board, validMoves);
            } while (continueSearch);
        }

        return validMoves;
    }
}