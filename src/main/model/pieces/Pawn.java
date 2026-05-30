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

        int dir = getColour() == Colour.WHITE ? 1 : -1; // Determine direction based on colour
        int startingRank = getColour() == Colour.WHITE ? 1 : 6; // Starting rank for pawns

        // 2 squares on starting rank
        Position newPos = new Position(pos.X, pos.Y + 2 * dir);
        if (pos.Y == startingRank
                && super.isEmptySquare(new Position(pos.X, pos.Y + 1 * dir), boardState)
                && super.isEmptySquare(newPos, boardState)) {
            validPositionSet.add(newPos);
        }

        // one forward square, two capture squares, and possible en passant target
        for (int i = -1; i <= 1; i++) {
            newPos = new Position(pos.X + i, pos.Y + 1 * dir);
            // one step forward
            if (i == 0) {
                if (super.isEmptySquare(newPos, boardState))
                    validPositionSet.add(newPos);
            }
            // diagonal captures
            else if (super.isValidPosition(newPos, boardState) && !super.isEmptySquare(newPos, boardState))
                validPositionSet.add(newPos);
            // enpassant
            else if (super.isEmptySquare(newPos, boardState)
                    && newPos.equals(boardState.getEnpassantTarget()))
                validPositionSet.add(newPos);
        }

        return validPositionSet;
    }
}
