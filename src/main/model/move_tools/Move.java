package model.move_tools;

import java.util.Set;
import model.misc_vars.MoveTag;
import model.pieces.Piece;

// Represents a move in the chess game.
public class Move {

    private final Piece PIECE;
    private final int X, Y;
    private final Set<MoveTag> moveTags;

    public Move(Piece piece, int x, int y, Set<MoveTag> moveTags) {
        this.PIECE = piece;
        this.X = x;
        this.Y = y;
        this.moveTags = moveTags;
    }

    public Piece getPiece() {
        return PIECE;
    }

    public int getIx() {
        return PIECE.getX();
    }

    public int getIy() {
        return PIECE.getY();
    }

    public int getFx() {
        return X;
    }

    public int getFy() {
        return Y;
    }

    public Set<MoveTag> getMoveTags() {
        return moveTags;
    }

    public void addMoveTag(MoveTag moveTag) {
        moveTags.add(moveTag);
    }
}
