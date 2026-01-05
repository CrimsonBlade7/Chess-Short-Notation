package model.move_tools;

import model.exceptions.InconsistentMoveException;
import model.misc_vars.Colour;
import model.pieces.King;
import model.pieces.Pawn;
import model.pieces.Rook;

// Checks if a move is self-consistent, valid, and legal
//  self-consistent: the move's attributes do not contradict each other
//  valid: the move exists within the piece's possible moves
//  legal: the move does not put own king in check, is valid, and is self-consistent
public class MoveValidation {

    // REQUIRES: move != null && boardState != null
    // EFFECTS: returns true if the move results in a check state for the given
    // colour
    public static boolean isCheckMove(Move move, Colour colour, BoardState boardState) {
        boardState.getBoard().executeMove(move, boardState);
        boolean isInCheck = boardState.isInCheck(colour);
        boardState.getBoard().undoMove(move);
        return isInCheck;
    }

    // EFFECTS: returns true if the move exists within the list of all pieces'
    // possible moves
    public static boolean isValidMove(Move move, BoardState boardState) {
        for (Move possibleMove : move.PIECE_1.validMoves(boardState, move.START_POS_1)) {
            if (possibleMove.equals(move)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: board != null, move is valid
    // EFFECTS: Move is legal if:
    // 1. Is self-consistent
    // 2. Does not put own king in check
    public boolean isLegalMove(Move move, BoardState boardState) {

        // Move is out of bounds
        isSelfConsistent(move, boardState);

        

        // Simulate the move and check if own king is in check
        Colour ownColour = move.PIECE_1.getColour();
        if (isCheckMove(move, ownColour, boardState))
            return false;

        // Move is a valid move
        return move.PIECE_1.validMoves(boardState, move.START_POS_1).contains(move);
    }

    // REQUIRES: move != null, boardState != null
    // EFFECTS: A move is self consistent if:
    // 1. The target square is not friendly
    // 2. Is within the bounds
    public static boolean isSelfConsistent(Move move, BoardState boardState) {
        // general violations
        if (move.END_POS_1.X < 0 || move.END_POS_1.X > 7 || move.END_POS_1.Y < 0 || move.END_POS_1.Y > 7)
            return false;
        if (move.PIECE_1 == null)
            return false;
        if (move.PIECE_1 != boardState.getSquare(move.START_POS_1))
            return false;
        if (move.START_POS_1 == null || move.END_POS_1 == null)
            return false;
        if (move.PIECE_1.getColour() != boardState.getCurrentTurn())
            return false;

        // Target square is occupied by friendly piece
        if (!(boardState.getSquare(move.END_POS_1) != null
                && move.PIECE_1.getColour() == boardState.getSquare(move.END_POS_1).getColour()))
            return false;

        // castling violations
        if ((move.PIECE_2 instanceof Rook) && !(move.PIECE_1 instanceof King))
            return false;

        // en passant violations
        if ((move.PIECE_2 instanceof Pawn) && !(move.PIECE_1 instanceof Pawn))
            return false;

        // promotion violations
        if ((move.PROMOTION_PIECE != null) && !(move.PIECE_1 instanceof Pawn))
            return false;

        return true;
    }
}