package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.BoardState;
import model.move_tools.Move;
import model.move_tools.MoveValidation;
import model.move_tools.Position;

public class King extends Piece {

    public King(Colour colour, Position pos) { super(colour, "King", "K", pos); }

    // EFFECTS: Returns a list of possible moves for the king piece
    @Override
    public List<Move> validMoves(BoardState boardState) {

        List<Move> validMoveList = new ArrayList<>();

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0)
                    continue;
                Position newPos = new Position(this.getX() + x, this.getY() + y);
                boolean isCapture = !super.isEmptySquare(newPos, boardState);
                Colour checkColour = this.getColour() == Colour.WHITE ? Colour.BLACK : Colour.WHITE;
                boolean isCheckMove = MoveValidation.isCheckMove(new Move(this, newPos, MoveType.NORMAL), checkColour,
                        boardState);

                validMoveList.add(new Move(this, newPos, isCapture, isCheckMove, MoveType.NORMAL));
            }
        }

        // Castling
        int y = (this.COLOUR == Colour.WHITE) ? 0 : 7;
        if (this.pos.X == 4 && this.pos.Y == y) {
            if (kingsideClear(boardState))
                validMoveList.add(new Move(this, new Position(6, this.pos.Y), false, true, MoveType.KINGSIDE_CASTLE));
            if (queensideClear(boardState))
                validMoveList.add(new Move(this, new Position(2, this.pos.Y), false, true, MoveType.QUEENSIDE_CASTLE));
        }
        return validMoveList;
    }

    // REQUIRES: board != null
    // EFFECTS: Checks if the squares between the king and kingside rook are empty
    // and not under attack
    private boolean kingsideClear(BoardState boardState) {
        int y = (this.COLOUR == Colour.WHITE) ? 0 : 7;
        for (int i = 5; i <= 6; i++) {
            if (boardState.getSquare(new Position(i, y)) != null &&
                    MoveValidation.isCheckMove(new Move(this, new Position(i, y), MoveType.NORMAL),
                            this.getColour() == Colour.WHITE ? Colour.BLACK : Colour.WHITE, boardState)) {
                return false;
            }
        }
        return true;
    }

    // REQUIRES: board != null
    // EFFECTS: Checks if the squares between the king and queenside rook are empty
    // and not under attack
    private boolean queensideClear(BoardState boardState) {
        int y = (this.COLOUR == Colour.WHITE) ? 0 : 7;
        return boardState.getSquare(new Position(1, y)) == null
                && boardState.getSquare(new Position(2, y)) == null
                && boardState.getSquare(new Position(3, y)) == null;
    }
}