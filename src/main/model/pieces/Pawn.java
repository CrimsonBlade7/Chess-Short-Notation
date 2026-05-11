package model.pieces;

import java.util.ArrayList;
import java.util.List;

import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.BoardState;
import model.move_tools.Move;
import model.move_tools.Position;

public class Pawn extends Piece {

    public Pawn(Colour colour, Position pos) {
        super(colour, "Pawn", "P");
    }

    @Override
    public List<Position> validPositions(BoardState boardState, Position pos) {

        List<Position> validPositionList = new ArrayList<>();

        int dir = getColour() == Colour.WHITE ? 1 : -1; // Determine direction based on colour
        int startingRank = getColour() == Colour.WHITE ? 1 : 6; // Starting rank for pawns

        // Move 2 squares on starting rank
        Position newPos = new Position(pos.X, pos.Y + 2 * dir);
        if (super.isValidPosition(newPos, boardState)) {
            if (pos.Y == startingRank
                    && isEmptySquare(new Position(pos.X, pos.Y + 1 * dir), boardState)
                    && isEmptySquare(boardState, newPos)) {
                validPositionList.add(newPos);
            }
        }

        // Check the 3 squares in front of the pawn
        for (int i = -1; i <= 1; i++) {
            newPos = new Position(pos.X + i, pos.Y + 1 * dir);
            if (i == 0) {
                if (super.isValidPosition(newPos, boardState)) {
                    if (isEmptySquare(boardState, newPos)) {
                        validPositionList.add(newPos);
                    }
                }
            } else if (super.isValidPosition(newPos, super.COLOUR, board) && !super.isEmptySquare(newPos, board)) {
                validPositionList.add(new Move(this, newPos, true, MoveType.NORMAL));
            } else if (canEnPassant(newPos, board)) {
                validPositionList.add(new Move(this, newPos, true, MoveType.EN_PASSANT));
            }
        }

        // check enpassant

        return validPositionList;
    }

    private boolean isEmptySquare(BoardState boardState, Position pos) {
        return boardState.getSquare(pos) == null;
    }
}