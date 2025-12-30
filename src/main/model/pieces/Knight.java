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

        int[][] knightMoves = {
                { -2, 1 },
                { -1, 2 },
                { 1, 2 },
                { 2, 1 },
                { 2, -1 },
                { 1, -2 },
                { -1, -2 },
                { -2, -1 }
        };

        for (int[] shift : knightMoves) {
            Position newPos = new Position(this.getX() + shift[0], this.getY() + shift[1]);
            if (super.isValidPosition(newPos, super.COLOUR, boardState)) {
                if (!super.isEmptySquare(newPos, boardState)) {
                    validMoveList.add(new Move(this, newPos, true, false, MoveType.NORMAL));
                } else {
                    validMoveList.add(new Move(this, newPos, false, false, MoveType.NORMAL));
                }
            }
        }

        return validMoveList;
    }
}