package model.move_tools;

public final class Position {

    public final int X;
    public final int Y;

    public Position(int x, int y) {
        X = x;
        Y = y;
    }

    public Position add(Position pos) {
        return new Position(this.X + pos.X, this.Y + pos.Y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
