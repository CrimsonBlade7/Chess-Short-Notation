package model.pieces;

import java.util.HashSet;
import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;

public class King extends Piece {

    // EFFECTS: creates a king with the given colour
    public King(Colour colour) { super(colour, "King", "K"); }

    // REQUIRES: boardState != null and pos is within the board
    // EFFECTS: returns pseudo-legal one-square king moves from pos
    @Override
    public Set<Position> validPositions(BoardState boardState, Position pos) {

        Set<Position> validPositionSet = new HashSet<>();

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0)
                    continue;
                Position newPos = pos.add(new Position(x, y));
                if (super.isValidPosition(newPos, boardState))
                    validPositionSet.add(newPos);
            }
        }
        return validPositionSet;
    }
}
