package test.pieces;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;
import model.pieces.Pawn;
import model.pieces.Rook;
import org.junit.jupiter.api.Test;

public class RookTest {

    // EFFECTS: verifies rooks move in straight lines, stop at blockers, and include captures
    @Test
    void validPositionsRespectStraightLinesAndBlockers() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        Rook rook = new Rook(Colour.WHITE);
        Position start = new Position(3, 3);
        state.getBoard().setSquare(rook, start);
        state.getBoard().setSquare(new Pawn(Colour.WHITE), new Position(3, 5));
        state.getBoard().setSquare(new Pawn(Colour.BLACK), new Position(1, 3));

        Set<Position> moves = rook.validPositions(state, start);

        assertTrue(moves.contains(new Position(3, 4)));
        assertFalse(moves.contains(new Position(3, 5)));
        assertTrue(moves.contains(new Position(1, 3)));
        assertFalse(moves.contains(new Position(0, 3)));
        assertTrue(moves.contains(new Position(7, 3)));
    }
}
