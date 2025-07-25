package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Position;

public class Bishop extends Piece {

    public Bishop(Colour colour, Position pos) {
        super(PieceType.BISHOP, colour, "Bishop", "B", pos);
    }

    @Override
    public List<Position> validPositions(Board board) {

        List<Position> validPositionsList = new ArrayList<>();

        int[][] directions = { { -1, 1 }, // Top-left
                { 1, 1 }, // Top-right
                { -1, -1 }, // Bottom-left
                { 1, -1 } // Bottom-right
        };

        for (int[] dir : directions) {
            boolean continueSearch = true;
            Position pos = new Position(this.getX(), this.getY());
            do {
                pos = new Position(pos.getX() + dir[0], pos.getY() + dir[1]);
                if (super.isValidPosition(pos, board)) {
                    validPositionsList.add(pos);
                } else {
                    continueSearch = false; // Stop searching in this direction if the move is invalid
                }
            } while (continueSearch);
        }
        return validPositionsList;
    }

    @Override
    public Piece copy() {
        return new Bishop(COLOUR, this.pos);
    }
}