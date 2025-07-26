package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Position;

public class Knight extends Piece {

    public Knight(Colour colour, Position pos) {
        super(PieceType.KNIGHT, colour, "Knight", "N", pos);
    }

    @Override
    public List<Position> validPositions(Board board) {

        List<Position> validPositionsList = new ArrayList<>();

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
            Position pos = new Position(this.getX() + move[0], this.getY() + move[1]);
            if (isValidPosition(pos, board)) {
                validPositionsList.add(pos);
            }
        }

        return validPositionsList;
    }

    @Override
    public Piece copy() {
        return new Knight(COLOUR, this.pos);
    }
}