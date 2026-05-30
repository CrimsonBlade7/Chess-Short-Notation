package model.pieces;

import java.util.HashSet;
import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;

public class Queen extends Piece {

    // EFFECTS: creates a queen with the given colour
    public Queen(Colour colour) { super(colour, "Queen", "Q"); }

    // REQUIRES: boardState != null and pos is within the board
    // EFFECTS: returns pseudo-legal queen moves from pos
    @Override
    public Set<Position> validPositions(BoardState boardState, Position pos) {

        Set<Position> validPositionSet = new HashSet<>();

        Position[] directions = {
                new Position(0, 1),
                new Position(1, 1),
                new Position(1, 0),
                new Position(1, -1),
                new Position(0, -1),
                new Position(-1, -1),
                new Position(-1, 0),
                new Position(-1, 1),
        };

        for (Position dir : directions) {
            Position currentPos = pos;
            while (true) {
                Position newPos = currentPos.add(dir);
                if (!super.isValidPosition(newPos, boardState))
                    break;
                validPositionSet.add(newPos);
                if (!super.isEmptySquare(newPos, boardState))
                    break;
                currentPos = newPos;
            }
        }
        return validPositionSet;
    }
}
