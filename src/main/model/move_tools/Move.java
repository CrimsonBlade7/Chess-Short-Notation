package model.move_tools;

import java.util.Set;

import model.misc_vars.Colour;
import model.misc_vars.MoveTag;
import model.pieces.Piece;

// Represents a move in the chess game.
public class Move {

    private final Piece PIECE;
    private final Colour COLOUR;
    private final int ix, iy, fx, fy;
    private final Set<MoveTag> moveTags;

    public Move(Piece piece, Colour colour, int ix, int iy, int fx, int fy, Set<MoveTag> moveTags) {
        this.PIECE = piece;
        this.COLOUR = colour;
        this.ix = ix;
        this.iy = iy;
        this.fx = fx;
        this.fy = fy;
        this.moveTags = moveTags;
    }

    public Piece getPiece() {
        return PIECE;
    }

    public Colour getColour() {
        return COLOUR;
    }

    public int getIx() {
        return ix;
    }

    public int getIy() {
        return iy;
    }

    public int getFx() {
        return fx;
    }

    public int getFy() {
        return fy;
    }

    public Set<MoveTag> getMoveTags() {
        return moveTags;
    }

    public void addMoveTag(MoveTag moveTag) {
        moveTags.add(moveTag);
    }
}
