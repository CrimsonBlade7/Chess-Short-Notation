package test.pieces;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;
import model.pieces.King;
import model.pieces.Pawn;
import org.junit.jupiter.api.Test;

public class KingTest {

    // EFFECTS: verifies kings move one square in every direction and avoid friendly pieces
    @Test
    void validPositionsIncludeAdjacentSquaresOnly() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        King king = new King(Colour.WHITE);
        Position start = new Position(3, 3);
        state.getBoard().setSquare(king, start);
        state.getBoard().setSquare(new Pawn(Colour.WHITE), new Position(4, 4));

        Set<Position> moves = king.validPositions(state, start);

        assertEquals(7, moves.size());
        assertTrue(moves.contains(new Position(2, 2)));
        assertTrue(moves.contains(new Position(3, 4)));
        assertFalse(moves.contains(new Position(4, 4)));
        assertFalse(moves.contains(new Position(5, 5)));
    }
}
