package model.pieces;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.misc_vars.Colour;
import model.move_tools.Position;

public class Pawn extends Piece {

    boolean EN_PASSANT;

    public Pawn(Colour colour, Position pos) {
        super(colour, "Pawn", "P", pos);
        EN_PASSANT = false;
    }

    public Pawn(Colour colour, Position pos, boolean EN_PASSANT) {
        super(colour, "Pawn", "P", pos);
        this.EN_PASSANT = EN_PASSANT;
    }

    @Override
    public List<Position> validPositions(Board board) {

        List<Position> validPositionsList = new ArrayList<>();

        int coef = getColour() == Colour.WHITE ? 1 : -1; // Determine direction based on colour
        int startingRank = getColour() == Colour.WHITE ? 1 : 6; // Starting rank for pawns

        // Move 2 squares on starting rank
        Position targetPos = new Position(this.getX(), this.getY() + 2 * coef);
        if (isValidPosition(targetPos, board)) {
            if (this.getY() == startingRank
                    && this.isEmptySquare(new Position(this.getX(), this.getY() + 1 * coef), board)
                    && this.isEmptySquare(targetPos, board)) {
                validPositionsList.add(targetPos);
            }
        }

        // Check the 3 squares in front of the pawn
        for (int i = -1; i <= 1; i++) {
            targetPos = new Position(this.getX() + i, this.getY() + 1 * coef);
            if (i == 0) {
                if (isValidPosition(targetPos, board)) {
                    if (this.isEmptySquare(targetPos, board)) {
                        validPositionsList.add(targetPos);
                    }
                }
            } else if (isValidPosition(targetPos, board) && !this.isEmptySquare(targetPos, board)) {
                validPositionsList.add(targetPos);
            } else if (canEnPassant(targetPos, board)) {
                validPositionsList.add(targetPos);
            }
        }

        return validPositionsList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (EN_PASSANT ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pawn other = (Pawn) obj;
        if (EN_PASSANT != other.EN_PASSANT)
            return false;
        return true;
    }

    // MODIFIES: EN_PASSANT
    // EFFECTS: sets the EN_PASSANT flag to false
    public void resetEnPassantFlag() { EN_PASSANT = false; }

    @Override
    public Piece copy() {
        Pawn copy = new Pawn(COLOUR, new Position(this.getX(), this.getY()));
        copy.EN_PASSANT = this.EN_PASSANT;
        return copy;
    }

    // REQUIRES: pos is within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns true if the pawn can perform an en passant capture at the
    // given position, false otherwise
    private boolean canEnPassant(Position targetPos, Board board) {
        int coef = getColour() == Colour.WHITE ? 1 : -1; // Determine direction based on colour
        return board.getSquare(new Position(targetPos.getX(), targetPos.getY() - 1 * coef)) instanceof Pawn pawn
                && pawn.EN_PASSANT;
    }
}