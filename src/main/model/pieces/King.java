package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.Colour;
import model.misc_vars.PieceType;
import model.move_tools.Position;

public class King extends Piece {

    private boolean canCastle;

    public King(Colour colour, Position pos) {
        super(PieceType.KING, colour, "King", "K", pos);
        canCastle = true;
    }

    public King(Colour colour, Position pos, boolean canCastle) {
        super(PieceType.KING, colour, "King", "K", pos);
        this.canCastle = canCastle;
    }

    @Override
    public List<Position> validPositions(Board board) {

        List<Position> validPositionsList = new ArrayList<>();

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                Position newPos = new Position(this.getX() + x, this.getY() + y);
                if (super.isValidPosition(newPos, board)) {
                    validPositionsList.add(newPos);
                }
            }
        }

        // Check for castling kingside
        if (canCastle(1, board)) {
            validPositionsList.add(new Position(this.getX() + 2, this.getY()));
        }
        
        // Check for castling queenside
        if (canCastle(-1, board)) {
            validPositionsList.add(new Position(this.getX() - 2, this.getY()));
        }
        
        return validPositionsList;
    }

    // REQUIRES: direction is either 1 (kingside) or -1 (queenside)
    // EFFECTS: returns true if castling is possible in the given direction, false
    // otherwise
    private boolean canCastle(int direction, Board board) {
        int startingRank = this.getColour() == Colour.WHITE ? 0 : 7;
        if (this.canCastle && board.getSquare(new Position(7, startingRank)) instanceof Rook rook && rook.canCastle()) {
            for (int x = 4 + direction; x > 1 && x < 7; x++) {
                Position pos = new Position(x, startingRank);
                if (!this.isEmptySquare(pos, board)) {
                    return false;
                }
            }
        }
        return true;
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
        King other = (King) obj;
        if (canCastle != other.canCastle)
            return false;
        return true;
    }

    @Override
    public Piece copy() { return new King(COLOUR, this.pos, this.canCastle); }
}