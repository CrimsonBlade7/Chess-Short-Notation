package model.move_tools;

import java.util.Set;
import model.misc_vars.MoveTag;
import model.pieces.Piece;

// Represents a move in the chess game.
public class Move {

    public final Piece PIECE;
    public final Position POS;
    public final Set<MoveTag> MOVE_TAGS;

    public Move(Piece piece, Position pos, Set<MoveTag> moveTags) {
        this.PIECE = piece;
        this.POS = pos;
        this.MOVE_TAGS = moveTags;
    }

    public Position getPos() {
        return POS;
    }

    public int getX() {
        return POS.getX();
    }

    public int getY() {
        return POS.getY();
    }

    public void addMoveTag(MoveTag moveTag) {
        MOVE_TAGS.add(moveTag);
    }
}
