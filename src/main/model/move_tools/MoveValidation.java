package model.move_tools;

import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.*;

// Checks if a move is self-consistent, valid, and legal
//  self-consistent: the move's attributes do not contradict each other
//  valid: the move exists within the piece's possible moves
//  legal: the move does not put own king in check, is valid, and is self-consistent
public class MoveValidation {

    // REQUIRES: board != null
    // EFFECTS: Checks if the move to pos is not friendly and is within the bounds
    // of the board.
    // If the position is valid, returns true; otherwise, returns false.
    public boolean isLegalMove(Move move, BoardState boardState) {

        int x = move.POS.X;
        int y = move.POS.Y;

        if (x < 0 || x > 7 || y < 0 || y > 7)
            return false; // Move is out of bounds

        if (!(boardState.getSquare(move.POS) != null
                && move.PIECE.getColour() == boardState.getSquare(move.POS).getColour()))
            return false;

        return boardState.isCheckMove(move);
    }

    // EFFECTS: returns true if the move exists within the list of all pieces'
    // possible moves
    public static boolean isValidMove(Move move, BoardState boardState) {
        for (Move possibleMove : move.PIECE.validMoves(boardState)) {
            if (possibleMove.equals(move)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: validates the move type for given move and board, returns true if
    // valid, false otherwise
    private static boolean isSelfConsistent(Move move, BoardState boardState) {
        Piece piece = move.PIECE;
        Position pos = move.POS;
        MoveType moveType = move.MOVETYPE;
        boolean isCapture = move.CAPTURE;
        boolean isCheck = move.CHECK;

        // Castling cannot be a capture
        if (isCapture && (moveType == MoveType.KINGSIDE_CASTLE || moveType == MoveType.QUEENSIDE_CASTLE))
            return false;

        // En passant and promotion by a non-pawn piece
        if ((moveType == MoveType.EN_PASSANT || moveType == MoveType.PROMOTION) && !(piece instanceof Pawn))
            return false;

        // en passant must be a capture
        if (moveType == MoveType.EN_PASSANT && !isCapture)
            return false;

        // Promotion must be to the last rank
        if (moveType == MoveType.PROMOTION) {
            if (piece.getColour() == Colour.WHITE && pos.Y != 7)
                return false;
            if (piece.getColour() == Colour.BLACK && pos.Y != 0)
                return false;
        }

        // Move is not possible for the piece
        if (!isValidMove(move, boardState))
            return false;

        // Move is a check, but does not put opponent in check
        return !(isCheck && !boardState.isCheckMove(move));
    }
}