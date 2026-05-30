package model.move_tools;

import static org.junit.jupiter.api.Assertions.*;

import model.exceptions.ImpossibleMoveStateException;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.King;
import model.pieces.Pawn;
import model.pieces.Queen;
import model.pieces.Rook;
import org.junit.jupiter.api.Test;

public class NotationConverterTest {

    // EFFECTS: verifies simple pawn notation resolves to the expected move
    @Test
    void parsesSimplePawnMove() {
        BoardState state = new BoardState();
        Move move = NotationConverter.stringToMove("e4", state);

        assertNotNull(move);
        assertEquals(new Position(4, 1), move.START_POS_1);
        assertEquals(new Position(4, 3), move.END_POS_1);
        assertEquals(MoveType.NORMAL, move.MOVE_TYPE);
    }

    // EFFECTS: verifies capture markers must match an actual capture
    @Test
    void rejectsIncorrectCaptureMarker() {
        BoardState state = new BoardState();

        assertNull(NotationConverter.stringToMove("exd3", state));
        assertNull(NotationConverter.stringToMove("xe4", state));
    }

    // EFFECTS: verifies castling notation moves both king and rook
    @Test
    void parsesAndExecutesKingsideCastling() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        state.getBoard().setSquare(null, NotationConverter.algebraicToPosition("f1"));
        state.getBoard().setSquare(null, NotationConverter.algebraicToPosition("g1"));

        Move move = NotationConverter.stringToMove("O-O", state);
        assertNotNull(move);
        state.executeMove(move, false);

        assertTrue(state.getSquare(NotationConverter.algebraicToPosition("g1")) instanceof King);
        assertTrue(state.getSquare(NotationConverter.algebraicToPosition("f1")) instanceof Rook);
    }

    // EFFECTS: verifies queenside castling notation moves both king and rook
    @Test
    void parsesAndExecutesQueensideCastling() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        state.getBoard().setSquare(null, NotationConverter.algebraicToPosition("b1"));
        state.getBoard().setSquare(null, NotationConverter.algebraicToPosition("c1"));
        state.getBoard().setSquare(null, NotationConverter.algebraicToPosition("d1"));

        Move move = NotationConverter.stringToMove("O-O-O", state);
        assertNotNull(move);
        state.executeMove(move, false);

        assertTrue(state.getSquare(NotationConverter.algebraicToPosition("c1")) instanceof King);
        assertTrue(state.getSquare(NotationConverter.algebraicToPosition("d1")) instanceof Rook);
    }

    // EFFECTS: verifies castling is rejected when the king would pass through check
    @Test
    void rejectsCastlingThroughAttackedSquare() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("e1"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), NotationConverter.algebraicToPosition("a1"));
        state.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("h8"));
        state.getBoard().setSquare(new Rook(Colour.BLACK), NotationConverter.algebraicToPosition("d8"));
        state.setCastlingRights(BoardState.WQ);

        assertNull(NotationConverter.stringToMove("O-O-O", state));
    }

    // EFFECTS: verifies castling is rejected when pieces block the path
    @Test
    void rejectsBlockedCastling() {
        BoardState state = new BoardState();

        assertNull(NotationConverter.stringToMove("O-O", state));
    }

    // EFFECTS: verifies en passant notation captures the pawn that advanced two squares
    @Test
    void parsesAndExecutesEnPassant() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        execute(state, "e4");
        execute(state, "a6");
        execute(state, "e5");
        execute(state, "d5");

        Move move = NotationConverter.stringToMove("exd6", state);
        assertNotNull(move);
        assertEquals(MoveType.EN_PASSANT, move.MOVE_TYPE);
        state.executeMove(move, false);

        assertTrue(state.getSquare(NotationConverter.algebraicToPosition("d6")) instanceof Pawn);
        assertNull(state.getSquare(NotationConverter.algebraicToPosition("d5")));
    }

    // EFFECTS: verifies pawn promotion notation replaces the pawn with the chosen piece
    @Test
    void parsesAndExecutesPromotion() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("h1"));
        state.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("h8"));
        state.getBoard().setSquare(new Pawn(Colour.WHITE), NotationConverter.algebraicToPosition("a7"));
        state.setCastlingRights((byte) 0);

        Move move = NotationConverter.stringToMove("a8=Q", state);
        assertNotNull(move);
        assertEquals(MoveType.PROMOTION, move.MOVE_TYPE);
        state.executeMove(move, false);

        assertTrue(state.getSquare(NotationConverter.algebraicToPosition("a8")) instanceof Queen);
    }

    // EFFECTS: verifies ambiguous notation is rejected until disambiguated
    @Test
    void rejectsAmbiguousMoveButAcceptsDisambiguation() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("e3"));
        state.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("h8"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), NotationConverter.algebraicToPosition("a1"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), NotationConverter.algebraicToPosition("h1"));
        state.setCastlingRights((byte) 0);

        assertNull(NotationConverter.stringToMove("Rd1", state));
        assertNotNull(NotationConverter.stringToMove("Rad1", state));
    }

    // REQUIRES: notation is legal in state
    // MODIFIES: state
    // EFFECTS: parses and executes notation
    private void execute(BoardState state, String notation) throws ImpossibleMoveStateException {
        Move move = NotationConverter.stringToMove(notation, state);
        assertNotNull(move, notation);
        state.executeMove(move, false);
    }
}
