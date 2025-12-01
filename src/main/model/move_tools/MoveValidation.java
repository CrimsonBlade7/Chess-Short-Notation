package model.move_tools;

import model.Board;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.*;

// Checks if a move is self-consistent, valid, and legal
//  self-consistent: the move's attributes do not contradict each other
//  valid: the move exists within the piece's possible moves
//  legal: the move does not put own king in check, is valid, and is self-consistent
public class MoveValidation {

    // REQUIRES: move != null && board != null
    // EFFECTS: returns true if the move is a valid and the resultant board is legal
    // state
    public static boolean isLegal(Move move, Board board) {
        if (!isSelfConsistent(move, board))
            return false;

        if (!isValidMove(move, board))
            return false;

        // If own king is in check, move is not legal
        Board newBoard;
        try {
            newBoard = board.clone();
            newBoard.executeMove(move);
            return !board.getBoardState().isInCheck(move.COLOUR, newBoard);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    // EFFECTS: returns true if the move exists within the list of all pieces'
    // possible moves
    public static boolean isValidMove(Move move, Board board) {
        for (Move possibleMove : move.PIECE.validMoves(board)) {
            if (possibleMove.equals(move)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: validates the move type for given move and board, returns true if
    // valid, false otherwise
    private static boolean isSelfConsistent(Move move, Board board) {
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
        if (!isValidMove(move, board))
            return false;

        // Move is a check, but does not put opponent in check
        Board newBoard;
        try {
            newBoard = board.clone();
            newBoard.executeMove(move);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        Colour newColour = (move.PIECE.getColour() == Colour.WHITE) ? Colour.BLACK : Colour.WHITE;
        if (isCheck && board.getBoardState().isInCheck(newColour, newBoard))
            return false;
        // Move is a capture but there is no piece to capture

        return !(isCapture && board.getSquare(pos) == null);
    }
}