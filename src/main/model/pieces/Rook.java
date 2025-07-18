package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Rook extends Piece {
    
    public Rook(Colour colour, int x, int y) {
        super(PieceType.ROOK, colour, "Rook", "R", x, y);
    }

    @Override
    public List<Move> possibleMoves(Board board) {

        List<Move> possibleMoves = new ArrayList<>();

        int[][] directions = {
            {-1, 0},  // Up
            {1, 0},   // Down
            {0, -1},  // Left
            {0, 1},   // Right
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