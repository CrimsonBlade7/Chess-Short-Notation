package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.BoardState;
import model.move_tools.Move;
import model.move_tools.Position;

public class King extends Piece {

    public King(Colour colour, Position pos) { super(colour, "King", "K", pos); }

    // EFFECTS: Returns a list of possible moves for the king piece
    @Override
    public List<Move> validMoves(BoardState boardState) {

        List<Move> validMoveList = new ArrayList<>();

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                Position newPos = new Position(this.getX() + x, this.getY() + y);
                if (super.isValidPosition(newPos, super.COLOUR, boardState)) {
                    if (boardState.getSquare(newPos) != null) {
                        validMoveList.add(new Move(this, newPos, true, false, MoveType.NORMAL));
                    }
                    else {
                        validMoveList.add(new Move(this, newPos, false, false, MoveType.NORMAL));
                    }
                }
            }
        }

        // Castling
        if (this.pos.X == 4) {
            if (kingsideEmpty(boardState))
                validMoveList.add(new Move(this, new Position(6, this.pos.Y), false, true, MoveType.KINGSIDE_CASTLE));
            if (queensideEmpty(boardState))
                validMoveList.add(new Move(this, new Position(2, this.pos.Y), false, true, MoveType.QUEENSIDE_CASTLE));
        }
        return validMoveList;
    }

    // REQUIRES: board != null
    // EFFECTS: Checks if the squares between the king and kingside rook are empty
    private boolean kingsideEmpty(BoardState boardState) {
        int y = (this.COLOUR == Colour.WHITE) ? 0 : 7;
        return boardState.getSquare(new Position(5, y)) == null
                && boardState.getSquare(new Position(6, y)) == null;
    }

    // REQUIRES: board != null
    // EFFECTS: Checks if the squares between the king and queenside rook are empty
    private boolean queensideEmpty(BoardState boardState) {
        int y = (this.COLOUR == Colour.WHITE) ? 0 : 7;
        return boardState.getSquare(new Position(1, y)) == null
                && boardState.getSquare(new Position(2, y)) == null
                && boardState.getSquare(new Position(3, y)) == null;
    }
}