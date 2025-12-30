package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.BoardState;
import model.move_tools.Move;
import model.move_tools.Position;

public class Bishop extends Piece {

    public Bishop(Colour colour, Position pos) { super(colour, "Bishop", "B", pos); }

    @Override
    public List<Move> validMoves(BoardState boardState) {

        List<Move> validMoveList = new ArrayList<>();

        Position[] directions = {
                new Position(-1, 1), // Top-left
                new Position(1, 1), // Top-right
                new Position(-1, -1), // Bottom-left
                new Position(1, -1) // Bottom-right
        };

        for (Position dir : directions) {
            Position currentPos = new Position(this.getX(), this.getY());
            while (true) {
                Position newPos = currentPos.add(dir);
                if (!super.isValidPosition(newPos, super.COLOUR, boardState))
                    break;
                boolean isCheckMove = boardState.isCheckMove(this.COLOUR, new Move(this, newPos, false, false, MoveType.NORMAL));
                if (super.isEmptySquare(newPos, boardState)) {
                    validMoveList.add(new Move(this, newPos, false, isCheckMove, MoveType.NORMAL));
                }
                else {
                    validMoveList.add(new Move(this, newPos, true, isCheckMove, MoveType.NORMAL));
                    break; // Stop searching in this direction if the move is a capture
                }
            }
        }
        return validMoveList;
    }
}