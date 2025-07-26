package model.move_tools;

public final class Position {

    private final int X;
    private final int Y;

    public Position(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
