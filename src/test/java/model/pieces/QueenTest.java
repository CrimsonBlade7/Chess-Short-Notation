package model.pieces;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;
import org.junit.jupiter.api.Test;

public class QueenTest {

    // EFFECTS: verifies queens combine rook and bishop movement
    @Test
    void validPositionsIncludeOrthogonalAndDiagonalMoves() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        Queen queen = new Queen(Colour.WHITE);
        Position start = new Position(3, 3);
        state.getBoard().setSquare(queen, start);

        Set<Position> moves = queen.validPositions(state, start);

        assertTrue(moves.contains(new Position(3, 7)));
        assertTrue(moves.contains(new Position(7, 3)));
        assertTrue(moves.contains(new Position(6, 6)));
        assertTrue(moves.contains(new Position(0, 0)));
        assertFalse(moves.contains(new Position(4, 5)));
    }
}
