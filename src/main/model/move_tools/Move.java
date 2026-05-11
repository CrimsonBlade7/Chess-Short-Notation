package model.move_tools;

import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.Piece;

// Represents a move in the chess game.
public class Move {

    public final Piece PIECE_1;                         // piece to be moved
    public final Position START_POS_1, END_POS_1;       // start, end pos
    public final Piece PIECE_2;                         // castle, promoted piece, ...
    public final Position START_POS_2, END_POS_2;
    public final Colour PREV_TURN;
    public final int PREV_HALF_MOVE_CLOCK;
    public final Piece PROMOTION_PIECE;
    public final MoveType MOVE_TYPE;

    // EFFECTS: constructs a Move object for normal moves
    public Move(Piece piece, Position startPos, Position endPos, MoveType moveType, BoardState boardState) {
        PIECE_1 = piece;
        START_POS_1 = startPos;
        END_POS_1 = endPos;
        PIECE_2 = null;
        START_POS_2 = null;
        END_POS_2 = null;
        MOVE_TYPE = moveType;
        PREV_TURN = boardState.getCurrentTurn();
        PREV_HALF_MOVE_CLOCK = boardState.getHalfMoveClock();
        PROMOTION_PIECE = null;
    }

    // EFFECTS: constructs a Move object for special moves
    public Move(Piece piece1, Position startPos1, Position endPos1, Piece piece2, Position startPos2,
            Position endPos2, Piece promotionPiece, MoveType moveType, BoardState boardState) {
        PIECE_1 = piece1;
        START_POS_1 = startPos1;
        END_POS_1 = endPos1;
        PIECE_2 = piece2;
        START_POS_2 = startPos2;
        END_POS_2 = endPos2;
        MOVE_TYPE = moveType;
        PREV_TURN = boardState.getCurrentTurn();
        PREV_HALF_MOVE_CLOCK = boardState.getHalfMoveClock();
        PROMOTION_PIECE = promotionPiece;
    }
}