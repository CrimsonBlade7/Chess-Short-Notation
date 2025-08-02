package model.pieces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.Board;
import model.misc_vars.Colour;
import model.misc_vars.MoveTag;
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
            Position pos = new Position(this.getX() + shift[0], this.getY() + shift[1]);
            Move move = new Move(this, pos, new HashSet<>());
            if (isValidMove(move, board)) {
                validMoveList.add(move);
                if (!super.isEmptySquare(pos, board)) {
                    move.getMoveTags().add(MoveTag.CAPTURE);
                }
            }
        }

        return validMoveList;
    }

    @Override
    public Piece copy() { return new Knight(COLOUR, this.pos); }
}