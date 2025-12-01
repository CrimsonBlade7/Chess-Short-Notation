package model.move_tools;

import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.Piece;

// Represents a move in the chess game.
public class Move {

    public final Piece PIECE;
    public final Position POS;
    public final Colour COLOUR;
    public final boolean CAPTURE;
    public final boolean CHECK;
    public final MoveType MOVETYPE;

    // REQUIRES: piece != null, pos != null, moveType != null
    // EFFECTS: constructs a Move object with the given parameters, throws
    public Move(Piece piece, Position pos, boolean capture, boolean check, MoveType moveType) {
        this.PIECE = piece;
        this.POS = pos;
        this.COLOUR = piece.getColour();
        this.CAPTURE = capture;
        this.CHECK = check;
        this.MOVETYPE = moveType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((PIECE == null) ? 0 : PIECE.hashCode());
        result = prime * result + ((POS == null) ? 0 : POS.hashCode());
        result = prime * result + ((COLOUR == null) ? 0 : COLOUR.hashCode());
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
        if (PIECE == null) {
            if (other.PIECE != null)
                return false;
        }
        else if (!PIECE.equals(other.PIECE))
            return false;
        if (POS == null) {
            if (other.POS != null)
                return false;
        }
        else if (!POS.equals(other.POS))
            return false;
        if (COLOUR != other.COLOUR)
            return false;
        if (CAPTURE != other.CAPTURE)
            return false;
        if (CHECK != other.CHECK)
            return false;
        if (MOVETYPE != other.MOVETYPE)
            return false;
        return true;
    }

   
}
