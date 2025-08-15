package model.pieces;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.Move;
import model.move_tools.Position;

public class Knight extends Piece {

    public Knight(Colour colour, Position pos) { super(colour, "Knight", "N", pos); }

    @Override
    public List<Move> validMoves(Board board) {

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
            if (super.isValidPosition(newPos, super.COLOUR, board)) {
                if (!super.isEmptySquare(pos, board)) {
                    validMoveList.add(new Move(this, newPos, true, MoveType.NORMAL));
                } else {
                    validMoveList.add(new Move(this, newPos, false, MoveType.NORMAL));
                }
            }
        }

        return validMoveList;
    }

    @Override
    public Piece copy() { return new Knight(COLOUR, this.pos); }
}