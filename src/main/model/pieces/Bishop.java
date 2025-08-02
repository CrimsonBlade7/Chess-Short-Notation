package model.pieces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.Board;
import model.misc_vars.Colour;
import model.misc_vars.MoveTag;
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
            boolean continueSearch = true;
            do {
                Position newPos = new Position(this.getX() + dir[0], this.getY() + dir[1]);
                Move move = new Move(this, newPos, new HashSet<>());
                if (super.isValidMove(move, board)) {
                    validMoveList.add(move);
                    if (!super.isEmptySquare(newPos, board)) {
                        move.getMoveTags().add(MoveTag.CAPTURE);
                        continueSearch = false; // Stop searching in this direction if the move is a capture
                    }
                } else {
                    continueSearch = false; // Stop searching in this direction if the move is invalid
                }
            } while (continueSearch);
        }
        return validMoveList;
    }

    @Override
    public Piece copy() { return new Bishop(COLOUR, this.pos); }
}