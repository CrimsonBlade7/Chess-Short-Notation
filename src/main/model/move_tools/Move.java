package model.move_tools;

import java.util.List;
import model.misc_vars.MoveTag;
import model.pieces.Piece;

// Represents a move in the chess game.
public class Move {

    private int ix, iy, fx, fy;
    private List<MoveTag> moveTags;

    public Move(Piece piece, int ix, int iy, int fx, int fy, List<MoveTag> moveTags) {
        this.ix = ix;
        this.iy = iy;
        this.fx = fx;
        this.fy = fy;
        this.moveTags = moveTags;
    }

    public int getIx() {
        return ix;
    }

    public void setIx(int ix) {
        this.ix = ix;
    }

    public int getIy() {
        return iy;
    }

    public void setIy(int iy) {
        this.iy = iy;
    }

    public int getFx() {
        return fx;
    }

    public void setFx(int fx) {
        this.fx = fx;
    }

    public int getFy() {
        return fy;
    }

    public void setFy(int fy) {
        this.fy = fy;
    }

    public List<MoveTag> getMoveTag() {
        return moveTags;
    }

    public void setMoveTag(List<MoveTag> moveTags) {
        this.moveTags = moveTags;
    }

    public void addMoveTag(MoveTag moveTag) {
        moveTags.add(moveTag);
    }
}
