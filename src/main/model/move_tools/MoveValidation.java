package model.move_tools;

import java.util.Set;

import model.Board;
import model.misc_vars.Colour;
import model.misc_vars.MoveTag;
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
        Piece piece = move.getPiece();
        Set<MoveTag> moveTags = move.getMoveTags();

        if (!(piece instanceof King))

            if (moveTags.contains(MoveTag.KINGSIDE_CASTLE) && moveTags.contains(MoveTag.QUEENSIDE_CASTLE)) {
                return false; // Invalid move tags for a single move
            }

        if ((moveTags.contains(MoveTag.KINGSIDE_CASTLE)
                || moveTags.contains(MoveTag.QUEENSIDE_CASTLE))
                && (moveTags.contains(MoveTag.CAPTURE)
                        || moveTags.contains(MoveTag.EN_PASSANT)
                        || moveTags.contains(MoveTag.PROMOTION))) {
        }

        if (move.getAltPiece() == null ^ move.getAltPos() == null) {
            return false; // If one is null, the other must also be null
        }

        if (piece.getName() != "Pawn"
                && (moveTags.contains(MoveTag.PROMOTION)
                        || moveTags.contains(MoveTag.EN_PASSANT))) {
            return false; // Promotion and en passant can only be performed by pawns
        }

        if (moveTags.contains(MoveTag.PROMOTION) && moveTags.contains(MoveTag.EN_PASSANT)) {
            return false; // Pawns cannot be promoted and captured en passant in the same move
        }

        if (isImpossibleMove(move, board)) {
            return false; // Move is not valid for the piece
        }
        if (board.inCheck(piece.getColour())) {
            return false; // Move puts the player's own king in check
        }
        if (move.getMoveTags().contains(MoveTag.CAPTURE) && board.getSquare(move.getPos()) == null) {
            return false; // Move is a capture but there is no piece to capture
        }
        if (move.getMoveTags().contains(MoveTag.CHECK)
                && board.inCheck(piece.getColour() == Colour.WHITE ? Colour.BLACK : Colour.WHITE)) {
            return false; // Move is marked as check but the opponent's king is not in check
        }
        return true;
    }

    // REQUIRES: move != null
    // board != null
    // EFFECTS: returns true if the move is impossible, false otherwise
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