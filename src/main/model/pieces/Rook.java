package model.pieces;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.misc_vars.Colour;
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
        if (canCastle != other.canCastle)
            return false;
        return true;
    }

    public boolean canCastle() {
        return canCastle;
    }

    public void disableCastle() {
        this.canCastle = false;
    }

    @Override
    public List<Position> validPositions(Board board) {

        List<Position> validPositionsList = new ArrayList<>();

        int[][] directions = { { -1, 0 }, // Up
                { 1, 0 }, // Down
                { 0, -1 }, // Left
                { 0, 1 }, // Right
        };

        for (int[] dir : directions) {
            boolean continueSearch = true;
            Position pos = new Position(this.getX(), this.getY());
            do {
                pos = new Position(pos.getX() + dir[0], pos.getY() + dir[1]);
                if (super.isValidPosition(pos, board)) {
                    validPositionsList.add(pos);
                    if (!this.isEmptySquare(pos, board)) {
                        continueSearch = false; // Stop searching in this direction if the move is a capture
                    }
                } else {
                    continueSearch = false; // Stop searching in this direction if the move is invalid
                }
            } while (continueSearch);
        }
        return validPositionsList;
    }

    @Override
    public Piece copy() {
        return new Rook(COLOUR, this.pos, this.canCastle);
    }
}