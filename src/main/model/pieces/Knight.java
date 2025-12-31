package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.BoardState;
import model.move_tools.Move;
import model.move_tools.Position;

public class Knight extends Piece {

    public Knight(Colour colour, Position pos) { super(colour, "Knight", "N", pos); }

    @Override
    public List<Move> validMoves(BoardState boardState) {

        List<Move> validMoveList = new ArrayList<>();

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
            Position newPos = this.pos.add(shift);
            if (super.isValidPosition(newPos, super.COLOUR, boardState)) {
                boolean isCapture = !super.isEmptySquare(newPos, boardState);
                boolean isCheckMove = boardState.isCheckMove(this.COLOUR, new Move(this, newPos, false, false, MoveType.NORMAL));
                validMoveList.add(new Move(this, newPos, isCapture, isCheckMove, MoveType.NORMAL));
            }
        }

        return validMoveList;
    }
}