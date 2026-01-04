package model.move_tools;

import model.misc_vars.MoveType;
import model.pieces.Piece;

// Represents a move in the chess game.
public class Move {

    public final Piece PIECE_1;
    public final Position START_POS_1, END_POS_1;
    public final Piece PIECE_2;
    public final Position START_POS_2, END_POS_2;
    public final Piece PROMOTION_PIECE;
    public final boolean CAPTURE;
    public final boolean CHECK;
    public final MoveType MOVETYPE;

    // REQUIRES: piece != null, pos != null, moveType != null
    // EFFECTS: constructs a Move object for common moves
    public Move(Piece piece, Position startPos, Position endPos, boolean capture, boolean check, MoveType moveType) {
        this.PIECE_1 = piece;
        this.START_POS_1 = startPos;
        this.END_POS_1 = endPos;
        this.PIECE_2 = null;
        this.START_POS_2 = null;
        this.END_POS_2 = null;
        this.PROMOTION_PIECE = null;
        this.CAPTURE = capture;
        this.CHECK = check;
        this.MOVETYPE = moveType;
    }

    // REQUIRES: piece != null, pos != null, moveType != null
    // EFFECTS: constructs a dummy Move object for check validation
    public Move(Piece piece, Position startPos, Position endPos, MoveType moveType) {
        this.PIECE_1 = piece;
        this.START_POS_1 = startPos;
        this.END_POS_1 = endPos;
        this.PIECE_2 = null;
        this.START_POS_2 = null;
        this.END_POS_2 = null;
        this.PROMOTION_PIECE = null;
        this.CAPTURE = false;
        this.CHECK = false;
        this.MOVETYPE = moveType;
    }

    // REQUIRES: piece != null, pos != null, moveType != null
    // EFFECTS: constructs a Move object for special moves:
    //  1. Castling
    //  2. En passant
    //  3. Promotion
    public Move(Piece piece, Position startPos, Position endPos, Piece promotionPiece,
            boolean isCapture, boolean isCheck, MoveType moveType) {
        this.PIECE_1 = piece;
        this.START_POS_1 = startPos;
        this.END_POS_1 = endPos;
        this.PIECE_2 = null;
        this.START_POS_2 = null;
        this.END_POS_2 = null;
        this.PROMOTION_PIECE = promotionPiece;
        this.CAPTURE = isCapture;
        this.CHECK = isCheck;
        this.MOVETYPE = moveType;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((PIECE_1 == null) ? 0 : PIECE_1.hashCode());
        result = prime * result + ((START_POS_1 == null) ? 0 : START_POS_1.hashCode());
        result = prime * result + ((END_POS_1 == null) ? 0 : END_POS_1.hashCode());
        result = prime * result + ((PIECE_2 == null) ? 0 : PIECE_2.hashCode());
        result = prime * result + ((START_POS_2 == null) ? 0 : START_POS_2.hashCode());
        result = prime * result + ((END_POS_2 == null) ? 0 : END_POS_2.hashCode());
        result = prime * result + ((PROMOTION_PIECE == null) ? 0 : PROMOTION_PIECE.hashCode());
        result = prime * result + (CAPTURE ? 1231 : 1237);
        result = prime * result + (CHECK ? 1231 : 1237);
        result = prime * result + ((MOVETYPE == null) ? 0 : MOVETYPE.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Move other = (Move) obj;
        if (PIECE_1 == null) {
            if (other.PIECE_1 != null)
                return false;
        }
        else if (!PIECE_1.equals(other.PIECE_1))
            return false;
        if (START_POS_1 == null) {
            if (other.START_POS_1 != null)
                return false;
        }
        else if (!START_POS_1.equals(other.START_POS_1))
            return false;
        if (END_POS_1 == null) {
            if (other.END_POS_1 != null)
                return false;
        }
        else if (!END_POS_1.equals(other.END_POS_1))
            return false;
        if (PIECE_2 == null) {
            if (other.PIECE_2 != null)
                return false;
        }
        else if (!PIECE_2.equals(other.PIECE_2))
            return false;
        if (START_POS_2 == null) {
            if (other.START_POS_2 != null)
                return false;
        }
        else if (!START_POS_2.equals(other.START_POS_2))
            return false;
        if (END_POS_2 == null) {
            if (other.END_POS_2 != null)
                return false;
        }
        else if (!END_POS_2.equals(other.END_POS_2))
            return false;
        if (PROMOTION_PIECE == null) {
            if (other.PROMOTION_PIECE != null)
                return false;
        }
        else if (!PROMOTION_PIECE.equals(other.PROMOTION_PIECE))
            return false;
        if (CAPTURE != other.CAPTURE)
            return false;
        if (CHECK != other.CHECK)
            return false;
        if (MOVETYPE != other.MOVETYPE)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Move{");
        sb.append("PIECE_1=").append(PIECE_1);
        sb.append(", START_POS_1=").append(START_POS_1);
        sb.append(", END_POS_1=").append(END_POS_1);
        sb.append(", PIECE_2=").append(PIECE_2);
        sb.append(", START_POS_2=").append(START_POS_2);
        sb.append(", END_POS_2=").append(END_POS_2);
        sb.append(", PROMOTION_PIECE=").append(PROMOTION_PIECE);
        sb.append(", CAPTURE=").append(CAPTURE);
        sb.append(", CHECK=").append(CHECK);
        sb.append(", MOVETYPE=").append(MOVETYPE);
        sb.append('}');
        return sb.toString();
    }

    
}
