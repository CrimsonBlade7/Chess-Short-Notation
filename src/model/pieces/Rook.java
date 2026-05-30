package model.pieces;

import java.util.HashSet;
import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;

public class Rook extends Piece {

    // EFFECTS: creates a rook with the given colour
    public Rook(Colour colour) { super(colour, "Rook", "R"); }

    // REQUIRES: boardState != null and pos is within the board
    // EFFECTS: returns pseudo-legal rook moves from pos
    @Override
    public Set<Position> validPositions(BoardState boardState, Position pos) {

        Set<Position> validPositionSet = new HashSet<>();

        Position[] directions = {
                new Position(0, 1),
                new Position(1, 0),
                new Position(0, -1),
                new Position(-1, 0),
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
