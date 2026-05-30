package model.pieces;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;
import org.junit.jupiter.api.Test;

public class KnightTest {

    // EFFECTS: verifies knights jump in L-shapes and cannot capture friendly pieces
    @Test
    void validPositionsIncludeOnlyLegalKnightJumps() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        Knight knight = new Knight(Colour.WHITE);
        Position start = new Position(3, 3);
        state.getBoard().setSquare(knight, start);
        state.getBoard().setSquare(new Pawn(Colour.WHITE), new Position(5, 4));
        state.getBoard().setSquare(new Pawn(Colour.BLACK), new Position(1, 2));

        Set<Position> moves = knight.validPositions(state, start);

        assertEquals(7, moves.size());
        assertFalse(moves.contains(new Position(5, 4)));
        assertTrue(moves.contains(new Position(1, 2)));
        assertTrue(moves.contains(new Position(4, 5)));
    }
}
