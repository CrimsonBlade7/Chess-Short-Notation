package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Bishop extends Piece {

    public Bishop(Colour colour, int x, int y) {
        super(PieceType.BISHOP, colour, "Bishop", "B", x, y);
    }

    @Override
    public List<Move> possibleMoves(Board board) {

        List<Move> possibleMoves = new ArrayList<>();

        int[][] directions = {
            {-1, 1},  // Top-left
            {1, 1},   // Top-right
            {-1, -1}, // Bottom-left
            {1, -1}   // Bottom-right
        };

        for (int[] dir : directions) {
            int fx = x;
            int fy = y;
            boolean continueSearch;
            do {
                fx += dir[0];
                fy += dir[1];
                continueSearch = addMove(x, y, fx, fy, board, possibleMoves);
            } while (continueSearch);
        }

        return possibleMoves;
    }
}