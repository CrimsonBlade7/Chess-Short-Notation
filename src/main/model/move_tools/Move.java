package model.move_tools;

import java.util.List;

import model.misc_vars.MoveTag;

public class Move {

    private int ix, iy, fx, fy;
    private boolean capture;
    private List<MoveTag> moveTags;

    public Move(int ix, int iy, int fx, int fy, boolean capture, List<MoveTag> moveTags) {
        this.ix = ix;
        this.iy = iy;
        this.fx = fx;
        this.fy = fy;
        this.moveTags = moveTags;
        this.capture = capture;
    }

    public int getIx() { return ix; }

    public void setIx(int ix) { this.ix = ix; }

    public int getIy() { return iy; }

    public void setIy(int iy) { this.iy = iy; }

    public int getFx() { return fx; }

    public void setFx(int fx) { this.fx = fx; }

    public int getFy() { return fy; }

    public void setFy(int fy) { this.fy = fy; }

    public MoveTag getMoveTag() { return moveTags; }

    public void setMoveTag(MoveTag MoveTag) { this.moveTags = moveTags; }

    public boolean isCapture() { return capture; }

    public void setCapture(boolean capture) { this.capture = capture; }
}
