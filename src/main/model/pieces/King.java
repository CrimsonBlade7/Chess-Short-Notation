package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;

public class King extends Piece {

    public King(Colour colour) { super(colour, "King", "K"); }

    // EFFECTS: Returns a list of possible moves for the king piece
    @Override
    public List<Position> validPositions(BoardState boardState, Position pos) {

        List<Position> validPositionList = new ArrayList<>();

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0)
                    continue;
                Position newPos = pos.add(new Position(x, y));
                if (!super.isValidPosition(newPos, boardState)) break;
                validPositionList.add(newPos);
                if (!super.isEmptySquare(newPos, boardState)) break;
            }
        }

        // Castling
        int y = (this.COLOUR == Colour.WHITE) ? 0 : 7;
        if (pos.X == 4 && pos.Y == y) {
            if (kingsideCastleValid(boardState)) 
                validPositionList.add(new Position(pos.Y, 6));
            if (queensideCastleValid(boardState)) 
                validPositionList.add(new Position(pos.Y, 2));
        }
        return validPositionList;
    }

    // REQUIRES: board != null
    // EFFECTS: Checks if the squares between the king and kingside rook are empty
    // and not under attack
    private boolean kingsideCastleValid(BoardState boardState) {
        int y = (this.COLOUR == Colour.WHITE) ? 0 : 7;
        if (boardState.getSquare(new Position(7, y)) instanceof Rook) return false; 
        return boardState.getSquare(new Position(1, y)) == null
                && boardState.getSquare(new Position(2, y)) == null
                && boardState.getSquare(new Position(3, y)) == null;
    }

    // REQUIRES: board != null
    // EFFECTS: Checks if the squares between the king and queenside rook are empty
    // and not under attack
    private boolean queensideCastleValid(BoardState boardState) {
        int y = (this.COLOUR == Colour.WHITE) ? 0 : 7;
        if (boardState.getSquare(new Position(0, y)) instanceof Rook) return false; 
        return boardState.getSquare(new Position(1, y)) == null
                && boardState.getSquare(new Position(2, y)) == null
                && boardState.getSquare(new Position(3, y)) == null;
    }
}