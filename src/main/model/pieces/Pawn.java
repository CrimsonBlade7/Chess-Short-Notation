package model.pieces;

import java.util.HashSet;
import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;

public class Pawn extends Piece {

    // EFFECTS: creates a pawn with the given colour
    public Pawn(Colour colour) { super(colour, "Pawn", "P"); }

    // REQUIRES: boardState != null and pos is within the board
    // EFFECTS: returns pseudo-legal pawn moves from pos
    @Override
    public Set<Position> validPositions(BoardState boardState, Position pos) {

        Set<Position> validPositionSet = new HashSet<>();

        int dir = getColour() == Colour.WHITE ? 1 : -1;
        int startingRank = getColour() == Colour.WHITE ? 1 : 6;

        Position newPos = new Position(pos.X, pos.Y + 2 * dir);
        if (pos.Y == startingRank
                && super.isEmptySquare(new Position(pos.X, pos.Y + dir), boardState)
                && super.isEmptySquare(newPos, boardState)) {
            validPositionSet.add(newPos);
        }

        for (int i = -1; i <= 1; i++) {
            newPos = new Position(pos.X + i, pos.Y + dir);
            if (i == 0) {
                if (super.isEmptySquare(newPos, boardState))
                    validPositionSet.add(newPos);
            } else if (super.isValidPosition(newPos, boardState) && !super.isEmptySquare(newPos, boardState)) {
                validPositionSet.add(newPos);
            } else if (super.isEmptySquare(newPos, boardState)
                    && newPos.equals(boardState.getEnpassantTarget())) {
                validPositionSet.add(newPos);
            }
        }

        return validPositionSet;
    }
}
