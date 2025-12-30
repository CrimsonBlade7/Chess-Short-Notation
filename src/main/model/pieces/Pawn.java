package model.pieces;

import java.util.ArrayList;
import java.util.List;

import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.Move;
import model.move_tools.Position;

public class Pawn extends Piece {

    public Pawn(Colour colour, Position pos) {
        super(colour, "Pawn", "P", pos);
    }

    public Pawn(Colour colour, Position pos, boolean EN_PASSANT) {
        super(colour, "Pawn", "P", pos);
    }

    @Override
    public List<Move> validMoves(Board board) {

        List<Move> validMoveList = new ArrayList<>();

        int coef = getColour() == Colour.WHITE ? 1 : -1; // Determine direction based on colour
        int startingRank = getColour() == Colour.WHITE ? 1 : 6; // Starting rank for pawns

        // Move 2 squares on starting rank
        Position newPos = new Position(this.getX(), this.getY() + 2 * coef);
        if (super.isValidPosition(newPos, super.COLOUR, board)) {
            if (this.getY() == startingRank
                    && this.isEmptySquare(new Position(this.getX(), this.getY() + 1 * coef), board)
                    && this.isEmptySquare(newPos, board)) {
                validMoveList.add(new Move(this, newPos, false, MoveType.NORMAL));
            }
        }

        // Check the 3 squares in front of the pawn
        for (int i = -1; i <= 1; i++) {
            newPos = new Position(this.getX() + i, this.getY() + 1 * coef);
            if (i == 0) {
                if (super.isValidPosition(newPos, super.COLOUR, board)) {
                    if (this.isEmptySquare(newPos, board)) {
                        validMoveList.add(new Move(this, newPos, false, MoveType.NORMAL));
                    }
                }
            } else if (super.isValidPosition(newPos, super.COLOUR, board) && !super.isEmptySquare(newPos, board)) {
                validMoveList.add(new Move(this, newPos, true, MoveType.NORMAL));
            } else if (canEnPassant(newPos, board)) {
                validMoveList.add(new Move(this, newPos, true, MoveType.EN_PASSANT));
            }
        }

        return validMoveList;
    }

    // REQUIRES: pos is within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns true if the pawn can perform an en passant capture at the
    // given position, false otherwise
    private boolean canEnPassant(Position pos, Board board) {
        int coef = super.getColour() == Colour.WHITE ? 1 : -1; // Determine direction based on colour
        return board.getSquare(new Position(pos.getX(), pos.getY() - 1 * coef)) instanceof Pawn pawn
                && pawn.EN_PASSANT;
    }
}