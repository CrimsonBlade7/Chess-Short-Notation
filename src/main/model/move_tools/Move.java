package model.move_tools;

import model.misc_vars.Colour;
import model.pieces.Piece;

// Represents a move in the chess game.
public class Move {

    public final Piece PIECE_1;
    public final Position START_POS_1, END_POS_1;
    public final Piece PIECE_2;
    public final Position START_POS_2, END_POS_2;
    public final Colour PREV_TURN;
    public final Position ENPASSANT_TARGET;
    public final int PREV_CASTLING_RIGHTS;
    public final int PREV_HALF_MOVE_CLOCK;
    public final Piece PROMOTION_PIECE;

    // EFFECTS: constructs a Move object for normal moves
    public Move(Piece piece, Position startPos, Position endPos, BoardState boardState) {
        PIECE_1 = piece;
        START_POS_1 = startPos;
        END_POS_1 = endPos;
        PIECE_2 = null;
        START_POS_2 = null;
        END_POS_2 = null;
        PREV_TURN = boardState.getCurrentTurn();
        ENPASSANT_TARGET = boardState.getEnpassantTarget();
        PREV_CASTLING_RIGHTS = boardState.getCastlingRights();
        PREV_HALF_MOVE_CLOCK = boardState.getHalfMoveClock();
        PROMOTION_PIECE = null;
    }

    // EFFECTS: constructs a Move object for special moves
    public Move(Piece piece1, Position startPos1, Position endPos1, Piece piece2, Position startPos2,
            Position endPos2, Piece promotionPiece, BoardState boardState) {
        PIECE_1 = piece1;
        START_POS_1 = startPos1;
        END_POS_1 = endPos1;
        PIECE_2 = piece2;
        START_POS_2 = startPos2;
        END_POS_2 = endPos2;
        PREV_TURN = boardState.getCurrentTurn();
        ENPASSANT_TARGET = boardState.getEnpassantTarget();
        PREV_CASTLING_RIGHTS = boardState.getCastlingRights();
        PREV_HALF_MOVE_CLOCK = boardState.getHalfMoveClock();
        PROMOTION_PIECE = promotionPiece;
    }

    @Override
    public String toString() {
        return "Move [PIECE_1=" + PIECE_1 + ", START_POS_1=" + START_POS_1 + ", END_POS_1=" + END_POS_1 + ", PIECE_2="
                + PIECE_2 + ", START_POS_2=" + START_POS_2 + ", END_POS_2=" + END_POS_2 + ", PREV_TURN=" + PREV_TURN
                + ", ENPASSANT_TARGET=" + ENPASSANT_TARGET + ", PREV_CASTLING_RIGHTS=" + PREV_CASTLING_RIGHTS
                + ", PREV_HALF_MOVE_CLOCK=" + PREV_HALF_MOVE_CLOCK + ", PROMOTION_PIECE=" + PROMOTION_PIECE + "]";
    }
}