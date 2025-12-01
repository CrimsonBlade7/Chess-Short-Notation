package model.pieces;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.Move;
import model.move_tools.Position;

public class Queen extends Piece {

    public Queen(Colour colour, Position pos) {
        super(colour, "Queen", "Q", pos);
    }

    @Override
    public List<Move> validMoves(Board board) {

        List<Move> validMoveList = new ArrayList<>();

        int[][] directions = { { -1, 0 }, // Up
                { 1, 0 }, // Down
                { 0, -1 }, // Left
                { 0, 1 }, // Right
                { -1, 1 }, // Top-left
                { 1, 1 }, // Top-right
                { -1, -1 }, // Bottom-left
                { 1, -1 } // Bottom-right
        };

        for (int[] dir : directions) {
            boolean continueSearch = true;
            do {
                Position newPos = new Position(this.getX() + dir[0], this.getY() + dir[1]);
                if (super.isValidPosition(newPos, super.COLOUR, board)) {
                    if (!super.isEmptySquare(newPos, board)) {
                        validMoveList.add(new Move(this, newPos, true, MoveType.NORMAL));
                        continueSearch = false; // Stop searching in this direction if the move is a capture
                    } else {
                        validMoveList.add(new Move(this, newPos, false, MoveType.NORMAL));
                    }
                } else {
                    continueSearch = false; // Stop searching in this direction if the move is invalid
                }
            } while (continueSearch);
        }
        return validMoveList;
    }

    @Override
    public Piece clone() {
        return new Queen(COLOUR, this.getPos());
    }
}