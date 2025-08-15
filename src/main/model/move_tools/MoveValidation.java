package model.move_tools;

import model.Board;
import model.misc_vars.MoveType;
import model.pieces.Piece;
import model.pieces.*;

// Represents algebraic notation for chess moves.
public class MoveValidation {

    // TODO: Complete stringToMove method
    public static Move stringToMove(String moveString, Board board) {
        return null; // Placeholder for future implementation
    }

    // REQUIRES: move != null
    // board != null
    // EFFECTS: returns true if the move is a valid and legal
    public static boolean isLegalMove(Move move, Board board) {
        return false; // TODO: Placeholder for future implementation
    }

    // EFFECTS: validates the move type for given move and board, returns true if
    // valid, false otherwise
    private boolean isValidMoveType(Move move, Board board) {
        Piece piece = move.getPiece();
        Position pos = move.getPos();
        MoveType moveType = move.getMoveType();
        boolean isCapture = move.isCapture();
        Piece altPiece = move.getAltPiece();
        Position altPos = move.getAltPos();

        if (moveType == MoveType.KINGSIDE_CASTLE || moveType == MoveType.QUEENSIDE_CASTLE) {
            if (!(piece instanceof King) || !(altPiece instanceof Rook)) {
                return false; // Castling can only be performed by rooks and kings
            }
        }

        if (moveType == MoveType.EN_PASSANT && !(piece instanceof Pawn)) {
            return false; // En passant can only be performed by pawns
        }

        if (moveType == MoveType.PROMOTION) {
            if (!(piece instanceof Pawn)) {
                return false; // Promotion can only be performed by pawns
            }
        }

        if (move.getAltPiece() == null ^ move.getAltPos() == null) {
            return false; // If one is null, the other must also be null
        }

        if (isImpossibleMove(move, board)) {
            return false; // Move is not valid for the piece
        }
        if (board.inCheck(piece.getColour())) {
            return false; // Move puts the player's own king in check
        }
        if (isCapture && board.getSquare(move.getPos()) == null) {
            return false; // Move is a capture but there is no piece to capture
        }
        return true;
    }

    // REQUIRES: move != null
    // board != null
    // EFFECTS: checks all possible moves and returns true if no move is found
    private static boolean isImpossibleMove(Move move, Board board) {
        boolean impossibleMove = true;
        for (Move possibleMove : move.getPiece().validMoves(board)) {
            if (possibleMove.getPos().equals(move.getPos())
                    && possibleMove.getPiece().equals(move.getPiece())
                    && possibleMove.getAltPiece().equals(move.getAltPiece())
                    && possibleMove.getAltPos() == move.getAltPos()) {
                impossibleMove = false; // Found a valid move that matches the requested move
            }
        }
        return impossibleMove;
    }
}