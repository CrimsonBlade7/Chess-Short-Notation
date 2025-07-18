package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Knight extends Piece {

    public Knight(Colour colour, int x, int y) {
        super(PieceType.KNIGHT, colour, "Knight", "N", x, y);
    }

    @Override
    public List<Move> possibleMoves(Board board) {
        
        List<Move> possibleMoves = new ArrayList<>();

        int[][] knightMoves = {
            {-2, 1}, 
            {-1, 2}, 
            {1, 2}, 
            {2, 1},
            {2, -1}, 
            {1, -2}, 
            {-1, -2}, 
            {-2, -1}
        };

        for (int[] move : knightMoves) {
            int dx = move[0];
            int dy = move[1];
            addMove(x, y, x + dx, y + dy, board, possibleMoves);
        }

        return possibleMoves;
    }
}