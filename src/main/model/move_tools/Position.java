package model.move_tools;

import java.util.Objects;

public final class Position {

    public final int X;
    public final int Y;

    // EFFECTS: creates a position with zero-based file x and rank y
    public Position(int x, int y) {
        X = x;
        Y = y;
    }

    // EFFECTS: returns a new position offset by pos
    public Position add(Position pos) { return new Position(this.X + pos.X, this.Y + pos.Y);}

    // EFFECTS: returns true if obj is a position with the same coordinates
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Position other))
            return false;
        return X == other.X && Y == other.Y;
    }

    // EFFECTS: returns a hash based on this position's coordinates
    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }

    // EFFECTS: returns a debugging representation of this position
    @Override
    public String toString() {
        return "Position{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
