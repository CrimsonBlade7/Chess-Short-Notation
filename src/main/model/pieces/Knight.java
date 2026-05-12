package model.pieces;

import java.util.HashSet;
import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;

public class Knight extends Piece {

    public Knight(Colour colour) { super(colour, "Knight", "N"); }

    @Override
    public Set<Position> validPositions(BoardState boardState, Position pos) {

        Set<Position> validPositionSet = new HashSet<>();

        Position[] knightMoves = {
                new Position(-2, 1),
                new Position(-1, 2),
                new Position(1, 2),
                new Position(2, 1),
                new Position(2, -1),
                new Position(1, -2),
                new Position(-1, -2),
                new Position(-2, -1)
        };

        for (Position shift : knightMoves) {
            Position newPos = pos.add(shift);
            if (super.isValidPosition(newPos, boardState)) 
                validPositionSet.add(newPos);
        }

        return validPositionSet;
    }
}