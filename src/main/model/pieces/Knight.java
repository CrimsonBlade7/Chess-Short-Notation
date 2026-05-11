package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;

public class Knight extends Piece {

    public Knight(Colour colour) { super(colour, "Knight", "N"); }

    @Override
    public List<Position> validPositions(BoardState boardState, Position pos) {

        List<Position> validPositionList = new ArrayList<>();

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
                validPositionList.add(newPos);
        }

        return validPositionList;
    }
}