package model.pieces;

import java.util.ArrayList;
import java.util.List;

import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.Move;
import model.move_tools.Position;

public class Rook extends Piece {

    private boolean canCastle;

    public Rook(Colour colour, Position pos) {
        super(colour, "Rook", "R", pos);
        canCastle = true;
    }
    
    public Rook(Colour colour, Position pos, boolean canCastle) {
        super(colour, "Rook", "R", pos);
        this.canCastle = canCastle;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (canCastle ? 1231 : 1237);
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
        Rook other = (Rook) obj;
        return canCastle == other.canCastle;
    }

    public boolean canCastle() {
        return canCastle;
    }

    public void disableCastle() {
        this.canCastle = false;
    }

    @Override
    public List<Move> validMoves(Board board) {

        List<Move> validMoveList = new ArrayList<>();

        int[][] directions = { { -1, 0 }, // Up
                { 1, 0 }, // Down
                { 0, -1 }, // Left
                { 0, 1 }, // Right
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
}