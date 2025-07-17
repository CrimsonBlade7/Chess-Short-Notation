package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Knight extends Piece {

    public Knight(Colour colour) {
        super(PieceType.KNIGHT, colour, "Knight", "N");
    }

    @Override
    public List<Move> validMoves(Board board, int ix, int iy) {
        List<Move> validMoves = new ArrayList<>();

        int[][] knightMoves = {
            {-2, 1}, {-1, 2}, {1, 2}, {2, 1},
            {2, -1}, {1, -2}, {-1, -2}, {-2, -1}
        };

        for (int[] move : knightMoves) {
            int dx = move[0];
            int dy = move[1];
            addMove(ix, iy, ix + dx, iy + dy, board, validMoves);
        }

        return validMoves;
    }
}