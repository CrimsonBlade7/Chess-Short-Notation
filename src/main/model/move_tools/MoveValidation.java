package model.move_tools;

import model.Board;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.*;

// Validates moves made in the chess game.
public class MoveValidation {

    // REQUIRES: move != null && board != null
    // EFFECTS: returns true if the move is a valid and the resultant board is in a
    // legal state
    public static boolean isLegalMove(Move move, Board board) {
        if (!isLegalMoveState(move, board)) 
            return false;
        
        if (!isValidMove(move, board)) 
            return false;

        // If own king is in check, move is not legal
        Board newBoard = board.copy();
        newBoard.executeMove(move);
        if (BoardStateValidation.isInCheck(move.COLOUR, newBoard))
            return false;
        
        return true;
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
    public static boolean isLegalMoveState(Move move, Board board) {
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
        Board newBoard = board.copy();
        newBoard.executeMove(move);

        Colour newColour = (move.PIECE.getColour() == Colour.WHITE) ? Colour.BLACK : Colour.WHITE;
        if (isCheck && BoardStateValidation.isInCheck(newColour, newBoard))
            return false;

        // Move is a capture but there is no piece to capture
        if (isCapture && board.getSquare(pos) == null)
            return false;

        return true;
    }
}