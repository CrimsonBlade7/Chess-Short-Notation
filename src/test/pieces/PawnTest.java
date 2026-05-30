package test.pieces;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Position;
import model.pieces.Pawn;
import org.junit.jupiter.api.Test;

public class PawnTest {

    // EFFECTS: verifies pawns can move one or two squares from their starting rank
    @Test
    void validPositionsIncludeStartingDoubleMove() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        Pawn pawn = new Pawn(Colour.WHITE);
        Position start = new Position(4, 1);
        state.getBoard().setSquare(pawn, start);

        Set<Position> moves = pawn.validPositions(state, start);

        assertTrue(moves.contains(new Position(4, 2)));
        assertTrue(moves.contains(new Position(4, 3)));
    }

    // EFFECTS: verifies pawns capture diagonally but do not move through blockers
    @Test
    void validPositionsIncludeCapturesAndExcludeBlockedForwardMove() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        Pawn pawn = new Pawn(Colour.WHITE);
        Position start = new Position(4, 4);
        state.getBoard().setSquare(pawn, start);
        state.getBoard().setSquare(new Pawn(Colour.WHITE), new Position(4, 5));
        state.getBoard().setSquare(new Pawn(Colour.BLACK), new Position(5, 5));

        Set<Position> moves = pawn.validPositions(state, start);

        assertFalse(moves.contains(new Position(4, 5)));
        assertTrue(moves.contains(new Position(5, 5)));
        assertFalse(moves.contains(new Position(3, 5)));
    }

    // EFFECTS: verifies en passant target squares are included for pawns
    @Test
    void validPositionsIncludeEnPassantTarget() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        Pawn pawn = new Pawn(Colour.WHITE);
        Position start = new Position(4, 4);
        state.getBoard().setSquare(pawn, start);
        state.getBoard().setSquare(new Pawn(Colour.BLACK), new Position(3, 4));
        state.setEnpassantTarget(new Position(3, 5));

        assertTrue(pawn.validPositions(state, start).contains(new Position(3, 5)));
    }
}
