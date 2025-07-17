package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Queen extends Piece {

    public Queen(Colour colour) {
        super(PieceType.QUEEN, colour, "Queen", "Q");
    }

    @Override
    public List<Move> validMoves(Board board, int ix, int iy) {
        List<Move> validMoves = new ArrayList<>();

        int[][] directions = {
            {-1, 0},  // Up
            {1, 0},   // Down
            {0, -1},  // Left
            {0, 1},   // Right
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