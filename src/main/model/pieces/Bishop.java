package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;

public class Bishop extends Piece {

    public Bishop(Colour colour, Position pos) { super(colour, "Bishop", "B"); }

    @Override
    public List<Position> validPositions(BoardState boardState, Position pos) {

        List<Position> validPositionList = new ArrayList<>();

        Position[] directions = {
                new Position(-1, 1), // Up-left
                new Position(1, 1), // Up-right
                new Position(-1, -1), // Down-left
                new Position(1, -1) // Down-right
        };

        for (Position dir : directions) {
            Position currentPos = pos;

            // Explore in the current direction until an invalid move or capture is
            // encountered
            while (true) {
                Position newPos = currentPos.add(dir);
                if (!super.isValidPosition(newPos, boardState))
                    break;
                validPositionList.add(newPos);
                if (!super.isEmptySquare(newPos, boardState))
                    break;
            }
        }
        return validPositionList;
    }
}