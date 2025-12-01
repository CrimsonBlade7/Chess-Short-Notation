package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.Move;
import model.move_tools.Position;

public class Bishop extends Piece {

    public Bishop(Colour colour, Position pos) { super(colour, "Bishop", "B", pos); }

    @Override
    public List<Move> validMoves(Board board) {

        List<Move> validMoveList = new ArrayList<>();

        int[][] directions = {
                { -1, 1 }, // Top-left
                { 1, 1 }, // Top-right
                { -1, -1 }, // Bottom-left
                { 1, -1 } // Bottom-right
        };

        for (int[] dir : directions) {
            while (true) {
                Position newPos = new Position(this.getX() + dir[0], this.getY() + dir[1]);
                if (!super.isValidPosition(newPos, super.COLOUR, board))
                    break;

                if (super.isEmptySquare(newPos, board)) {
                    validMoveList.add(new Move(this, newPos, false, false, MoveType.NORMAL));
                }
                else {
                    validMoveList.add(new Move(this, newPos, true, false, MoveType.NORMAL));
                    break; // Stop searching in this direction if the move is a capture
                }
            }
        }
        return validMoveList;
    }

    @Override
    public Piece clone() { return new Bishop(COLOUR, this.pos); }
}