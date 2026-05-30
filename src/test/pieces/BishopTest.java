package test.pieces;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;
import model.pieces.Bishop;
import model.pieces.Pawn;
import org.junit.jupiter.api.Test;

public class BishopTest {

    // EFFECTS: verifies bishops move diagonally, stop at blockers, and include captures
    @Test
    void validPositionsRespectDiagonalsAndBlockers() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        Bishop bishop = new Bishop(Colour.WHITE);
        Position start = new Position(3, 3);
        state.getBoard().setSquare(bishop, start);
        state.getBoard().setSquare(new Pawn(Colour.WHITE), new Position(5, 5));
        state.getBoard().setSquare(new Pawn(Colour.BLACK), new Position(1, 1));

        Set<Position> moves = bishop.validPositions(state, start);

        assertTrue(moves.contains(new Position(4, 4)));
        assertFalse(moves.contains(new Position(5, 5)));
        assertTrue(moves.contains(new Position(1, 1)));
        assertFalse(moves.contains(new Position(0, 0)));
        assertTrue(moves.contains(new Position(0, 6)));
    }
}
