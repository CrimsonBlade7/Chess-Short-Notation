package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.BoardState;
import model.move_tools.Move;
import model.move_tools.MoveValidation;
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

            // Explore in the current direction until an invalid move or capture is encountered
            while (true) {
                Position newPos = currentPos.add(dir);
                boolean isCapture = !super.isEmptySquare(newPos, boardState);
                boolean isCheckMove = boardState.isCheckMove(new Move(this, newPos, isCapture, false, MoveType.NORMAL));
                Move move = new Move(this, newPos, isCapture, isCheckMove, MoveType.NORMAL);
                
                if (!MoveValidation.isLegalMove(move, boardState))
                    break;
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