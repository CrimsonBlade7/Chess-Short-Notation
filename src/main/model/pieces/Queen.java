package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;

public class Queen extends Piece {

    public Queen(Colour colour) { super(colour, "Queen", "Q"); }

    @Override
    public List<Position> validPositions(BoardState boardState, Position pos) {

        List<Position> validPositionList = new ArrayList<>();

        Position[] directions = {
            new Position(0, 1), // Up
            new Position(1, 1), // Up-right
            new Position(1, 0), // Right
            new Position(1, -1), // Right-down
            new Position(0, -1), // Down
            new Position(-1, -1), // Down-left
            new Position(-1, 0), // Left
            new Position(-1, 1), // Up-left
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