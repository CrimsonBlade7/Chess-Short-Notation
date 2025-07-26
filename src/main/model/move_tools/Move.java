package model.move_tools;

import java.util.Set;
import model.misc_vars.MoveTag;
import model.pieces.Piece;

// Represents a move in the chess game.
public class Move {

    public final Piece PIECE;
    public final int X, Y;
    public final Set<MoveTag> MOVE_TAGS;

    public Move(Piece piece, int x, int y, Set<MoveTag> moveTags) {
        this.PIECE = piece;
        this.X = x;
        this.Y = y;
        this.MOVE_TAGS = moveTags;
    }

    public void addMoveTag(MoveTag moveTag) {
        MOVE_TAGS.add(moveTag);
    }
}
