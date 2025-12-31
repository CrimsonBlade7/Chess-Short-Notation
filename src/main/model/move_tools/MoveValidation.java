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

        if (!(boardState.getSquare(move.POS) != null && move.PIECE.getColour() == boardState.getSquare(move.POS).getColour()))
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
        if (moveType == MoveType.KINGSIDE_CASTLE || moveType == MoveType.QUEENSIDE_CASTLE) {
            if (isCapture)
                return false;
        }

        // En passant and promotion by a non-pawn piece
        if (moveType == MoveType.EN_PASSANT || moveType == MoveType.PROMOTION) {
            if (!(piece instanceof Pawn))
                return false; // En passant can only be performed by pawns
        }

        // Move is not possible for the piece
        if (!isValidMove(move, boardState))
            return false;

        // Move is a check, but does not put opponent in check
        BoardState newBoardState;
        try {
            newBoardState = boardState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        newBoardState.getBoard().executeMove(move);
        Colour newColour = (move.PIECE.getColour() == Colour.WHITE) ? Colour.BLACK : Colour.WHITE;
        if (isCheck && boardState.isInCheck(newColour))
            return false;
        // Move is a capture but there is no piece to capture

        return !(isCapture && boardState.getBoard().getSquare(pos) == null);
    }
}